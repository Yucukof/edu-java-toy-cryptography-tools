package org.dcu.student.sem1.ca642.asymmetric.encryption;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.modulus.exponentiation.Exponentiation;

import java.util.Random;

import static org.dcu.student.sem1.ca642.modulus.inverse.ExtendedEuclidean.inverse;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RSA {

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

        public PrivateKey(final int p, final int q, final int e) {
            this.p = p;
            this.q = q;
            this.e = e;

            this.n = product();
            this.phiN = phi();
            this.d = generate();
        }

        private int product() {
            log.info("Computing n...");
            final int n = p * q;
            log.info("{} * {} = {}", p, q, n);
            return n;
        }

        private int phi() {
            log.info("Computing phi...");
            final int phi = (p - 1) * (q - 1);
            log.info("({} -1) x ({} -1) = {}", p, q, phi);
            return phi;
        }

        public int generate() {
            log.debug("Computing e = d⁻¹ (mod n)....");
            final int d = inverse(e, phiN);
            log.debug("{}⁻¹ (mod {}) = {}", e, phiN, d);
            log.debug("Result = [{}]", d);
            return d;
        }

        private int pick() {
            log.debug("Picking random e...");
            final int e = random.nextInt(phiN - 1) + 1;
            log.debug("Result = [{}]", e);
            return e;
        }

        public int decrypt(final int c) {
            log.info("Decrypting {}", c);
            final int m = Exponentiation
                  .from(c, d, n)
                  .resolve();
            log.debug("{}^{} (mod {}) = {}", c, d, n, m);
            log.info("Result = [{}]", m);
            return m;
        }

        public PublicKey getPublicKey() {
            log.debug("Generating public key");
            final PublicKey publicKey = new PublicKey(e, n);
            log.debug("Result = [{}]", publicKey);
            return publicKey;
        }

        @Override
        public String toString() {
            return "(" + d + ")";
        }
    }

    @Value
    @RequiredArgsConstructor
    public static class PublicKey {

        int e;
        int n;

        public int encrypt(final int m) {
            log.info("Encrypting {}", m);
            final int c = Exponentiation
                  .from(m, e, n)
                  .resolve();
            log.info("Result = [{}]", c);
            return c;
        }

        @Override
        public String toString() {
            return "(" + e + "," + n + ")";
        }
    }
}
