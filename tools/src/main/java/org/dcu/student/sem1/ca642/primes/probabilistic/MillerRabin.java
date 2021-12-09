package org.dcu.student.sem1.ca642.primes.probabilistic;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.utils.MathUtils;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.dcu.student.sem1.ca642.modulus.exponentiation.SquareAndMultiply.power;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MillerRabin {

    private static final Random rd = new Random();

    public static boolean isPseudoPrime(final int n, final int k) {
        log.info("Checking it {} is a pseudo-prime...", n);

        final Composition composition = getComposition(n - 1);

        for (int i = 0; i < k; i++) {
            final int base = getRandomElement(n);
            log.debug("Computing witness for base {}", base);
            final boolean isWitness = isWitness(base, composition);
            if (isWitness) {
                log.info("Result = [false]");
                return false;
            }
        }
        log.info("Result = [true]");
        return true;
    }

    public static Composition getComposition(final int n) {
        log.debug("Computing m value from {}...", n);
        if (n % 2 != 0) {
            throw new IllegalArgumentException(String.format("%s is not an even number!", n));
        }
        int exponent = 0;
        int remainder = n;
        while (remainder % 2 == 0) {
            remainder /= 2;
            exponent++;
        }
        final Composition composition = new Composition(n, exponent, remainder);
        log.debug("{}", composition);
        return composition;
    }

    private static int getRandomElement(final int n) {
        return rd.nextInt(n - 2) + 2;
    }

    public static boolean isWitness(final int base, final Composition composition) {

        final int n = composition.getValue() + 1;
        final int exponent = composition.getExponent();
        final int remainder = composition.getRemainder();
        final int root = power(base, remainder, n);

        // Either a^m == 1 (mod n)
        // Or a^m == -1 (mod n)
        if (root == 1 || root == n - 1) {
            log.debug("{}^{} (mod {}) = [{}]", base, remainder, n, root);
            return false;
        }

        int value = root;
        for (int j = 1; j <= exponent; j++) {

            value *= value;
            value %= n;

            // Check the intermediate value
            if (value == 1) {
                // There is a value (a^m)^(2^k) == 1 (mod n), for which one of the root is not in {1,-1}
                // Thus, n cannot be prime
                // STRONG WITNESS
                log.debug("({}^{})^(2^{})) (mod {}) = {}", base, remainder, j, n, value);
                return true;
            }
            if (value == n - 1 && j != exponent) {
                // There is 1 element (a^m)^(2^s) == -1 (mod n)
                // Thus, n is possibly a prime
                // STRONG LIAR
                return false;
            }
        }
        return true;
    }

    public static List<Integer> getWitnesses(final int n) {
        final List<Integer> witnesses = IntStream.range(1, n).filter(a -> isWitness(a, n)).boxed().collect(Collectors.toList());
        log.debug("Witnesses : {}", witnesses);
        return witnesses;
    }

    public static boolean isWitness(final int base, final int n) {
        return isWitness(base, getComposition(n - 1));
    }

    public static boolean isLiar(final int base, final int n) {
        return !isWitness(base, n);
    }

    public static List<Integer> getLiars(final int n) {
        final List<Integer> liars = IntStream.range(1, n).filter(a -> !isWitness(a, n)).boxed().collect(Collectors.toList());
        log.debug("Liars = {}", liars);
        return liars;
    }

    @Data
    @RequiredArgsConstructor
    public static class Composition {

        private final int value;
        private final int exponent;
        private final int remainder;

        @Override
        public String toString() {
            return String.format("%s = %s x %s = 2^%s x %s", value, get2Exponentiation(), remainder, exponent, remainder);
        }

        public int get2Exponentiation() {
            return MathUtils.power(2, exponent);
        }
    }

}
