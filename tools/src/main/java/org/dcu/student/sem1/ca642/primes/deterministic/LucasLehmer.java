package org.dcu.student.sem1.ca642.primes.deterministic;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.primes.naive.BruteForce;
import org.dcu.student.sem1.ca642.utils.MathUtils;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LucasLehmer {

    private static final int b0 = 4;

    public static boolean isPrime(final int n) {

        log.info("Evaluating primality of {}", n);

        try {
            final LucasLehmer.Composition composition = getComposition(n + 1);

            final int k = composition.getExponent();
            log.debug("{}=2^{}-1", n, k);

            if (!BruteForce.isPrime(k)) {
                log.debug("{} is not a valid prime", k);
                log.info("Result = [false]");
                return false;
            }

            int number = b0;
            for (int i = 0; i < k - 2; i++) {
                number *= number;
                number -= 2;
                number %= n;
            }

            final boolean isPrime = number == 0;
            log.debug("b_({}-2) (mod {})= {}", k, n, number);
            log.info("Result = [{}]", isPrime ? "true" : "false");
            return isPrime;

        } catch (IllegalArgumentException e) {

            log.debug("{} is not a valid Mersenne number", n);
            log.info("Result = [false]");
            return false;
        }
    }

    public static Composition getComposition(final int n) {
        log.debug("Decomposing {}...", n);

        if (n % 2 != 0) {
            throw new IllegalArgumentException(String.format("%s is not an even number!", n));
        }

        int exponent = 0;
        int value = 1;
        while (value < n) {
            value *= 2;
            exponent++;
        }

        if (value > n) {
            throw new IllegalArgumentException(String.format("%s is not a valid multiple of 2", n));
        }

        final Composition composition = new Composition(n, exponent);
        log.debug("{}", composition);
        return composition;
    }

    public static boolean isMersenneNumber(final int n) {
        try {
            getComposition(n + 1);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Data
    @RequiredArgsConstructor
    public static class Composition {

        private final int value;
        private final int exponent;

        @Override
        public String toString() {
            return String.format("%s = %s - 1 = 2^%s - 1", value, get2Exponentiation(), exponent);
        }

        public int get2Exponentiation() {
            return MathUtils.power(2, exponent);
        }
    }

}
