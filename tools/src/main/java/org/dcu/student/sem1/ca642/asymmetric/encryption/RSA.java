package org.dcu.student.sem1.ca642.asymmetric.encryption;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.asymmetric.signatures.HashFunction;
import org.dcu.student.sem1.ca642.modulus.exponentiation.Exponentiation;

import java.util.Random;

import static org.dcu.student.sem1.ca642.modulus.inverse.ExtendedEuclidean.positiveInverse;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RSA {

    private static int translate(final int base, final int exponent, final int modulus) {
        final int value = Exponentiation
              .from(base, exponent, modulus)
              .resolve();
        log.debug("{}^{} (mod {}) = {}", base, exponent, modulus, value);
        return value;
    }

    @Value
    @RequiredArgsConstructor
    public static class PrivateKey {

        private static final Random random = new Random();

        int p;
        int q;
        int n;
        int phiN;
        int e;
        int d;

        public PrivateKey(final int p, final int q) {
            this.p = p;
            this.q = q;
            this.e = pick();
            this.n = product();
            this.phiN = phi();
            this.d = generate();
        }

        private int pick() {
            log.debug("Picking random e...");
            final int e = random.nextInt(phiN - 1) + 1;
            log.debug("e = {}", e);
            return e;
        }

        private int product() {
            log.info("Computing n...");
            final int n = p * q;
            log.info("n = {} * {} = {}", p, q, n);
            return n;
        }

        private int phi() {
            log.info("Computing phi...");
            final int phi = (p - 1) * (q - 1);
            log.info("ϕ(n) = ({} -1) x ({} -1) = {}", p, q, phi);
            return phi;
        }

        public int generate() {
            log.debug("Computing e = d⁻¹ (mod n)....");
            final int d = positiveInverse(e, phiN);
            log.debug("d = {}⁻¹ (mod {}) = {}\n", e, phiN, d);
            return d;
        }

        public PrivateKey(final int p, final int q, final int e) {
            this.p = p;
            this.q = q;
            this.e = e;

            this.n = product();
            this.phiN = phi();
            this.d = generate();
        }

        public static PrivateKey from(final int p, final int q, final int e) {
            return new PrivateKey(p, q, e);
        }

        public int decrypt(final int c) {
            log.info("Decrypting {}", c);
            return RSA.translate(c, d, n);
        }

        public PublicKey getPublicKey() {
            log.debug("Generating public key");
            final PublicKey publicKey = new PublicKey(e, n);
            log.debug("Public Key = [{}]\n", publicKey);
            return publicKey;
        }

        public int sign(final int message, final HashFunction hashFunction) {
            final int hash = hashFunction.hash(message);
            return encrypt(hash);
        }

        public int encrypt(final int m) {
            log.info("Encrypting {}", m);
            return RSA.translate(m, d, n);
        }

        @Override
        public String toString() {
            return "(d = " + d + ")";
        }
    }

    @Value
    @RequiredArgsConstructor
    public static class PublicKey {

        /**
         * Public exponent
         */
        int e;
        /**
         * Public composite prime
         */
        int n;

        public int encrypt(final int m) {
            log.info("Encrypting {}", m);
            final int c = RSA.translate(m, e, n);
            log.info("c = [{}]\n", c);
            return c;
        }

        @Override
        public String toString() {
            return "(e = " + e + ", n = " + n + ")";
        }

        public boolean verify(final int signature, final int c, final HashFunction hashFunction) {
            final int m = decrypt(c);
            final int hash = hashFunction.hash(m);
            final int sign = decrypt(signature);
            return sign == hash;
        }

        public int decrypt(final int c) {
            log.info("Decrypting {}", c);
            final int m = RSA.translate(c, e, n);
            log.info("m = [{}]\n", m);
            return m;
        }
    }
}
