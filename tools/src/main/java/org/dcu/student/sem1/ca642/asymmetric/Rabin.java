package org.dcu.student.sem1.ca642.asymmetric;

import lombok.*;
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

    public static List<Integer> decrypt(final int c, final RabinPrivateKey key) {

        log.info("Decrypting {}", c);

        final int p = key.p;
        final int q = key.q;

        log.info("\tp = {}", p);
        log.info("\tq = {}", q);

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

    private static int resolve(final int p, final int q, final int rp, final int rq) {

        log.info("Computing solution for roots ({},{})", rp, rq);

        final int modulus = p * q;

        final ChineseRemainderReverse.Component c1 = ChineseRemainderReverse.Component.builder()
              .n(p)
              .a(rp)
              .modulus(modulus)
              .build();
        final int t1 = c1.multiply();
        log.info("\tT1 = ({} x {} x {}) == {} (mod {})", c1.getA(), c1.getN(), c1.getY(), t1, modulus);

        final ChineseRemainderReverse.Component c2 = ChineseRemainderReverse.Component.builder()
              .n(q)
              .a(rq)
              .modulus(modulus)
              .build();
        final int t2 = c2.multiply();
        log.info("\tT2 = ({} x {} x {}) == {} (mod {})", c2.getA(), c2.getN(), c2.getY(), t2, modulus);

        final int sum = (t1 + t2) % (modulus);
        log.info("\t{} + {} (mod {}) = {}", t1, t2, modulus, sum);
        return sum;

    }

    /**
     * Encrypts a plaintext $m$ using the public key $n$ as $c=(m^2)\pmod{n}$.
     *
     * @param m   the plaintext to encrypt.
     * @param key the key $n$ to use
     * @return a ciphertext.
     */
    public static int encrypt(final int m, final RabinPublicKey key) {
        log.info("Encrypting message m [{}]...", m);
        log.info("Calculating m²(mod n)");
        final int result = squareAndMultiply(m, 2, key.n);
        log.info("\t{}^2 (mod {})={}", m, key.n, result);
        return result;
    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class RabinPrivateKey {

        /**
         * A co-prime $p$
         *
         * @see #q
         * @see #isValid()
         */
        private final int p;
        /**
         * A co-prime $q$
         *
         * @see #p
         * @see #isValid()
         */
        private final int q;

        public RabinPublicKey getPublicKey() {
            log.debug("n = p*q");
            final int n = getN();
            log.debug("{}x{} = {}", p, q, n);
            return RabinPublicKey.builder()
                  .n(n)
                  .build();
        }

        public int getN() {
            return p * q;
        }

        public boolean isValid() {
            return (p % 4) == 3
                  && (q % 4) == 3;
        }
    }

    @Builder(access = AccessLevel.PRIVATE)
    @Getter
    @RequiredArgsConstructor
    public static class RabinPublicKey {

        /**
         * The private key component resulting from the combination of the co-primes {@link RabinPrivateKey#p $p$} and
         * {@link RabinPrivateKey#q $q$}.
         */
        private final int n;

    }
}
