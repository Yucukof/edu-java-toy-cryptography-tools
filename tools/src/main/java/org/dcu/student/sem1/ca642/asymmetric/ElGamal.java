package org.dcu.student.sem1.ca642.asymmetric;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Random;

import static org.dcu.student.sem1.ca642.modulus.exponentiation.SquareAndMultiply.power;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ElGamal {

    private static final Random rd = new Random();

    /**
     * Encrypt a given plaintext $m$ into a ciphertext $(c1||c2)$.
     *
     * @param m   the plaintext to encrypt (must be smaller than ${@link EGPublicKey#prime p}$)
     * @param k   an ephemeral key smaller than ${@link EGPublicKey#prime p}-1$ (see {@link
     *            #getEphemeralKey(EGPublicKey) getEphemeralKey}).
     * @param key the ElGamal public key
     * @return a tuple (c1||c2)
     * @see #decrypt(Pair, EGPrivateKey)
     */
    public static Pair<Integer, Integer> encrypt(final int m, final int k, final EGPublicKey key) {

        log.info("Encrypting plaintext m = {}", m);
        split(m, key);

        final int p = key.prime;
        final int g = key.generator;
        final int y = key.value;

        log.info("Calculating  y^k (mod p)...");
        final int yk = power(y, k, p);
        log.debug("{}^{} (mod {})={}", y, k, p, yk);

        log.info("Calculating C1 = g^k (mod p)...");
        final int c1 = power(g, k, p);
        log.debug("{}^{} (mod {})={}", g, k, p, c1);

        log.info("Calculating C2 = my^k (mod p)...");
        final int c2 = (m * yk) % p;
        log.debug("{}x{} (mod {})={}", m, yk, p, c2);

        log.info("Ciphertext c = ({}||{})", c1, c2);
        return new ImmutablePair<>(c1, c2);
    }

    /**
     * Split the key in numbers of size $0 < m < p-1$
     *
     * @param m   the message to split.
     * @param key the private key to use.
     */
    private static void split(final int m, final EGPublicKey key) {
        if (m >= key.prime) {
            // Not supported :grimacing:
            // TODO: 26/11/2021 implement
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Generate a random ephemeral key according to the public key values.
     * <br> i.e. $0 < k < p-1$
     *
     * @param key the public key for which generate an ephemeral key.
     * @return a random integer smaller than $p-1$.
     */
    private static int getEphemeralKey(final EGPublicKey key) {
        final int p = key.prime;
        int k = 0;
        do {
            k = rd.nextInt();
        } while (k <= 0 || k >= p);
        return k;
    }

    /**
     * Decrypt a given ciphertext c back to its original plaintext m.
     *
     * @param c   the ciphertext $(c1||c2)$ to decrypt.
     * @param key the private key to use.
     * @return an integer representing $m$.
     * @see #encrypt(int, int, EGPublicKey)
     */
    public static int decrypt(final Pair<Integer, Integer> c, final EGPrivateKey key) {

        log.info("Decrypting ciphertext ({}||{})...", c.getLeft(), c.getRight());
        final int p = key.prime;
        final int x = key.exponent;

        log.info("Calculating exponent (p-1-x)...");
        final int exponent = p - 1 - x;
        log.debug("{}-1-{}={}", p, x, exponent);

        final int c1 = c.getLeft();
        final int c2 = c.getRight();

        log.info("Calculating c_1^(p-1-x) (mod p)...");
        log.debug("NB: c_1^(p-1-x)\\equiv c_1^{-x}");
        final int c1_ = power(c1, exponent, p);
        log.debug("{}^{} (mod {}) = {}", c1, exponent, p, c1_);

        log.info("Calculating m = c1^(-x)*c2 (mod p)...");
        final int m = (c1_ * c2) % p;
        log.debug("({}*{}) (mod {})={}", c1_, c2, p, m);

        log.info("Plaintext m = {}", m);
        return m;
    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class EGPrivateKey {

        /**
         * A prime number of length greater than 512 bits.
         */
        private final int prime;
        /**
         * A generator of multiples in $\mathbb{Z}_p^*$
         *
         * @see #prime
         */
        private final int generator;
        /**
         * The private key component.
         *
         * @see EGPublicKey#value
         */
        private final int exponent;

        public EGPublicKey getPublicKey() {
            final int y = power(generator, exponent, prime);
            return EGPublicKey.builder()
                  .prime(prime)
                  .generator(generator)
                  .value(y)
                  .build();
        }
    }

    @Builder(access = AccessLevel.PRIVATE)
    @Getter
    @RequiredArgsConstructor
    public static class EGPublicKey {

        /**
         * A prime number of length greater than 512 bits.
         */
        private final int prime;
        /**
         * A generator of multiples in $\mathbb{Z}_p^*$
         *
         * @see #prime
         */
        private final int generator;
        /**
         * The derived public integer y computed as $y=g^x\pmod{p}$.
         *
         * @see #generator
         * @see #prime
         * @see EGPrivateKey#exponent
         */
        private final int value;

    }
}
