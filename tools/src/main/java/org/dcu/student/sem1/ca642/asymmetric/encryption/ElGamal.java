package org.dcu.student.sem1.ca642.asymmetric.encryption;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.asymmetric.signatures.HashFunction;
import org.dcu.student.sem1.ca642.modulus.exponentiation.Exponentiation;

import java.util.Random;

import static org.dcu.student.sem1.ca642.modulus.inverse.ExtendedEuclidean.inverse;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ElGamal {

    private static Ciphertext encrypt(final int g, final int p, final int y, final int m, final int k) {
        log.info("Encrypting {}", m);

        log.info("Calculating C1 = g^k (mod p)...");
        final int c1 = Exponentiation.compute(g, k, p);
        log.debug("C1 = {}^{} (mod {}) = {}", g, k, p, c1);

        log.info("Calculating  y^k (mod p)...");
        final int yk = Exponentiation.compute(y, k, p);
        log.debug("y^k = {}^{} (mod {}) = {}", y, k, p, yk);

        log.info("Calculating C2 = my^k (mod p)...");
        final int c2 = (m * yk) % p;
        log.debug("C2 = {} x ({}^{}) (mod {}) = {}", m, y, k, p, c2);

        final Ciphertext ciphertext = new Ciphertext(c1, c2);
        log.info("c = [{}]\n", ciphertext);
        return ciphertext;
    }

    private static int decrypt(final int p, final int x, final Ciphertext c) {
        log.info("Decrypting {}...", c);

        final int c1 = c.getC1();
        final int c2 = c.getC2();

        log.info("Calculating s = c_1^(p-1-x) (mod p)...");
        final int s = Exponentiation.compute(c1, -x, p);
        log.debug("s = {}^{} (mod {}) = {}", c1, -x, p, s);

        log.info("Calculating m = c1^(-x) x c2 (mod p)...");
        final int m = (s * c2) % p;
        log.debug("m = ({} x {}) (mod {}) = {}", s, c2, p, m);

        log.info("m = [{}]\n", m);
        return m;
    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class PrivateKey {

        /**
         * A prime number of length greater than 512 bits.
         */
        private final int p;
        /**
         * A generator of multiples in $\mathbb{Z}_p^*$
         *
         * @see #p
         */
        private final int g;
        /**
         * The public key component
         */
        private final int y;
        /**
         * The private key component.
         *
         * @see PublicKey#y
         */
        private final int x;

        public static PrivateKey from(final int p, final int g, final int x) {
            final int y = generateY(p, g, x);
            return builder()
                  .p(p)
                  .g(g)
                  .y(y)
                  .x(x)
                  .build();
        }

        private static int generateY(final int p, final int g, final int x) {
            final int y = Exponentiation.compute(g, x, p);
            log.info("y = {}^{} (mod {}) = [{}]\n", g, x, p, y);
            return y;
        }

        /**
         * Decrypt a given ciphertext c back to its original plaintext m.
         *
         * @param c the ciphertext $(c1||c2)$ to decrypt.
         * @return an integer representing $m$.
         * @see PublicKey#encrypt(int, int)
         */
        public int decrypt(final Ciphertext c) {
            return ElGamal.decrypt(p, x, c);
        }

        public PublicKey getPublicKey() {
            return PublicKey.from(g, p, y);
        }

        public Ciphertext sign(final int m, final int k, final HashFunction hashFunction) {
            log.info("Signing {}", m);
            final int s1 = Exponentiation.compute(g, k, p);
            log.debug("S1 = {}^{} (mod {}) = {}", g, k, p, s1);
            final int hash = hashFunction.hash(m);
            final int kInverse = inverse(k, p - 1);
            log.debug("{}⁻¹ (mod {}-1) = {}", k, p, kInverse);
            final int s2 = (kInverse * (hash - (x * s1))) % (p - 1);
            log.info("S2 = {}*(h({}) - {} x {}) (mod {} - 1) = {}", k, m, x, s1, p, s2);
            final Ciphertext ciphertext = new Ciphertext(s1, s2);
            log.info("Signature = [{}]\n", ciphertext);
            return ciphertext;
        }

        @Override
        public String toString() {
            return "( x = " + x + ")";
        }
    }

    @Builder(access = AccessLevel.PRIVATE)
    @Getter
    @RequiredArgsConstructor
    public static class PublicKey {

        private static final Random rd = new Random();
        /**
         * A prime number of length greater than 512 bits.
         */
        private final int p;
        /**
         * A generator of multiples in $\mathbb{Z}_p^*$
         *
         * @see #p
         */
        private final int g;
        /**
         * The derived public integer y computed as $y=g^x\pmod{p}$.
         *
         * @see #g
         * @see #p
         * @see PrivateKey#x
         */
        private final int y;

        private static PublicKey from(final int generator, final int prime, final int y) {
            return builder()
                  .p(prime)
                  .g(generator)
                  .y(y)
                  .build();
        }

        /**
         * Encrypt a given plaintext $m$ into a ciphertext $(c1||c2)$.
         *
         * @param m the plaintext to encrypt (must be smaller than ${@link PublicKey#p p}$)
         * @param k an ephemeral key smaller than ${@link PublicKey#p p}-1$ (see {@link
         *          #getEphemeralKey() getEphemeralKey}).
         * @return a tuple (c1||c2)
         * @see PrivateKey#decrypt(Ciphertext)
         */
        public Ciphertext encrypt(final int m, final int k) {
            return ElGamal.encrypt(g, p, y, m, k);
        }

        /**
         * Generate a random ephemeral key according to the public key values.
         * <br> i.e. $0 < k < p-1$
         *
         * @return a random integer smaller than $p-1$.
         */
        private int getEphemeralKey() {
            final int p = this.p;
            int k = 0;
            do {
                k = rd.nextInt();
            } while (k <= 0 || k >= p);
            return k;
        }

        @Override
        public String toString() {
            return "(p = " + p + ", g = " + g + ", y = " + y + ")";
        }

        public boolean verify(final int m, final Ciphertext signature, final HashFunction hashFunction) {
            log.info("Verifying {} with {}", m, signature);
            final int s1 = signature.c1;
            final int s2 = signature.c2;
            final int hash = hashFunction.hash(m);

            final int v1 = Exponentiation.compute(g, hash, p);
            final int ys1 = Exponentiation.compute(y, s1, p);
            final int s1s2 = Exponentiation.compute(s1, s2, p);
            final int v2 = (ys1 * s1s2) % p;

            return v1 == v2;
        }
    }

    @Value
    public static class Ciphertext {

        int c1;
        int c2;

        public Ciphertext(final int c1, final int c2) {
            this.c1 = c1;
            this.c2 = c2;
        }

        @Override
        public String toString() {
            return "(" + c1 + "||" + c2 + ")";
        }
    }
}
