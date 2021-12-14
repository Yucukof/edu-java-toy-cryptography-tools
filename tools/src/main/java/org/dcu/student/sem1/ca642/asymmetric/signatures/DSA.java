package org.dcu.student.sem1.ca642.asymmetric.signatures;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.asymmetric.encryption.ElGamal;
import org.dcu.student.sem1.ca642.modulus.exponentiation.Exponentiation;
import org.dcu.student.sem1.ca642.modulus.fraction.Fraction;

import static org.dcu.student.sem1.ca642.primes.EuclideanAlgorithm.gcd;
import static org.dcu.student.sem1.ca642.modulus.inverse.ExtendedEuclidean.inverse;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DSA {

    @Value
    public static class Message {

        int s1;
        int s2;

        public Message(final int s1, final int s2) {
            this.s1 = s1;
            this.s2 = s2;
        }

        @Override
        public String toString() {
            return "(" + s1 + "||" + s2 + ")";
        }
    }

    @RequiredArgsConstructor
    public static class PrivateKey {
        final int p;
        final int q;
        final int g;
        final int y;

        final int x;

        public static DSA.PrivateKey fromTemplate(final int p, final int q, final int h, final int x) {

            if (h <= 1 || h >= p - 1) {
                throw new IllegalArgumentException("H is not in interval ]1,p-1[");
            }

            if (x <= 0 || x >= q) {
                throw new IllegalArgumentException("X is not in interval ]0,q[");
            }

            final int g = generateG(p, q, h);
            if (g == 1) {
                throw new IllegalArgumentException("G is equal to 1");
            }

            final int y = generateY(p, x, g);

            return new DSA.PrivateKey(p, q, g, y, x);
        }

        private static int generateG(final int p, final int q, final int h) {
            final int exponent = Fraction.resolve(p - 1, q, p);
            return Exponentiation.compute(h, exponent, p);
        }

        private static int generateY(final int p, final int x, final int g) {
            return Exponentiation.compute(g, x, p);
        }

        public static DSA.PrivateKey from(final int p, final int q, final int g, final int x) {
            if (x <= 0 || x >= q) {
                throw new IllegalArgumentException("X is not in interval ]0,q[");
            }
            if (g == 1) {
                throw new IllegalArgumentException("G is equal to 1");
            }
            final int y = generateY(p, x, g);
            return new DSA.PrivateKey(p, q, g, y, x);
        }

        public PublicKey getPublicKey() {
            return PublicKey.from(p, q, g, y);
        }

        public Message sign(final int m, final int k, final HashFunction hashFunction) {
            validate(k);

            final int s1 = computeS1(k);
            final int s2 = computeS2(m, k, hashFunction, s1);

            return new Message(s1, s2);
        }

        private void validate(final int k) {
            if (k <= 0 || k >= p - 1) {
                throw new IllegalArgumentException("K is not in the interval ]1,p-1[");
            }
            if (gcd(k, p - 1) != 1) {
                throw new IllegalArgumentException("K is not prime with p");
            }
        }

        private int computeS1(final int k) {
            final int s1 = Exponentiation.compute(g, k, p) % q;
            log.debug("S1 = ({}^{} (mod {})) (mod{}) = {}", g, k, p, q, s1);
            return s1;
        }

        private int computeS2(final int m, final int k, final HashFunction hashFunction, final int s1) {
            final int hash = hashFunction.hash(m);
            final int kInverse = inverse(k, q);
            return (kInverse * (hash + (x * s1))) % (p - 1);
        }

        @Override
        public String toString() {
            return "(x = " + x + ")";
        }

    }

    @RequiredArgsConstructor
    public static class PublicKey {

        final int p;
        final int q;
        final int g;

        final int y;

        public static DSA.PublicKey from(final int p, final int q, final int g, final int y) {
            return new PublicKey(p, q, g, y);
        }

        private int decrypt(final ElGamal.Ciphertext c) {
            return 0;
        }

        @Override
        public String toString() {
            return "(p = " + p + ", q = " + q + ", g = " + g + ", y = " + y + ")";
        }

        final boolean verify(final int m, final Message signature, final HashFunction hashFunction) {
            final int w = inverse(signature.s2, q);
            final int hash = hashFunction.hash(m);
            final int u1 = (hash * w) % q;
            final int u2 = (signature.s1 * w) % q;
            final int gu1 = Exponentiation.compute(g, u1, p);
            final int yu2 = Exponentiation.compute(y, u2, p);
            final int v = ((gu1 * yu2) % p) % q;
            return signature.s1 == v;
        }
    }
}
