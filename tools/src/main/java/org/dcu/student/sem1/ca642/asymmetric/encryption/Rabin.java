package org.dcu.student.sem1.ca642.asymmetric.encryption;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.dcu.student.sem1.ca642.modulus.roots.ChineseRemainderReverse;

import java.util.Arrays;
import java.util.List;

import static org.dcu.student.sem1.ca642.modulus.exponentiation.SquareAndMultiply.squareAndMultiply;
import static org.dcu.student.sem1.ca642.modulus.roots.SquareRoots.sqrt;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Rabin {

    @Value
    public static class PrivateKey {

        /**
         * A co-prime $p$
         *
         * @see #q
         * @see #isValid()
         */
        int p;
        /**
         * A co-prime $q$
         *
         * @see #p
         * @see #isValid()
         */
        int q;
        /**
         * The product of p*q
         */
        int n;

        public PrivateKey(final int p, final int q) {
            this.p = p;
            this.q = q;
            this.n = product(p, q);
        }

        private int product(final int p, final int q) {
            final int n = p * q;
            log.debug("{} x {} = {}", p, q, n);
            return n;
        }

        public List<Integer> decrypt(final int c) {

            log.info("Decrypting {}", c);

            log.debug("p = {}", p);
            log.debug("q = {}", q);

            final Pair<Integer, Integer> rootsForP = sqrt(c, p);
            final Pair<Integer, Integer> rootsForQ = sqrt(c, q);

            log.info("Roots: {} - {}", rootsForP, rootsForQ);

            final int solution1 = resolve(p, q, rootsForP.getLeft(), rootsForQ.getLeft());
            final int solution2 = resolve(p, q, rootsForP.getRight(), rootsForQ.getLeft());
            final int solution3 = resolve(p, q, rootsForP.getLeft(), rootsForQ.getRight());
            final int solution4 = resolve(p, q, rootsForP.getRight(), rootsForQ.getRight());

            final List<Integer> solutions = Arrays.asList(
                  solution1,
                  solution2,
                  solution3,
                  solution4
            );
            log.info("Solutions = {}", solutions);
            return solutions;
        }

        private int resolve(final int p, final int q, final int rp, final int rq) {

            log.info("Computing solution for roots ({},{})", rp, rq);

            log.debug("Computing T1");
            final int t1 = computeForFactor(p, rp);
            log.debug("Computing T2");
            final int t2 = computeForFactor(q, rq);

            final int sum = (t1 + t2) % (n);
            log.debug("{} + {} (mod {}) = {}", t1, t2, n, sum);
            return sum;

        }

        private int computeForFactor(final int q, final int rq) {
            final ChineseRemainderReverse.Component c2 = ChineseRemainderReverse.Component.builder()
                  .n(q)
                  .a(rq)
                  .modulus(n)
                  .build();
            final int t = c2.multiply();
            log.debug("({} x {} x {}) == {} (mod {})", c2.getA(), c2.getN(), c2.getY(), t, n);
            return t;
        }

        public PublicKey getPublicKey() {
            log.debug("n = p*q");
            return PublicKey.builder()
                  .n(n)
                  .build();
        }

        public boolean isValid() {
            return (p % 4) == 3
                  && (q % 4) == 3;
        }
    }

    @Value
    @Builder
    public static class PublicKey {

        /**
         * The private key component resulting from the combination of the co-primes {@link PrivateKey#p $p$} and
         * {@link PrivateKey#q $q$}.
         */
        int n;

        /**
         * Encrypts a plaintext $m$ using the public key $n$ as $c=(m^2)\pmod{n}$.
         *
         * @param m the plaintext to encrypt.
         * @return a ciphertext.
         */
        public int encrypt(final int m) {
            log.info("Encrypting message m [{}]...", m);
            log.info("Calculating m²(mod n)");
            final int result = squareAndMultiply(m, 2, n);
            log.debug("{}^2 (mod {})={}", m, n, result);
            return result;
        }
    }
}
