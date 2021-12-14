package org.dcu.student.sem1.ca642.logarithm;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.dcu.student.sem1.ca642.modulus.exponentiation.Exponentiation;

import java.util.HashMap;
import java.util.Map;

import static org.dcu.student.sem1.ca642.utils.MathUtils.intSqrt;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GiantStepBabyStep {

    public static int resolve(final int b, final int n, final int a) {

        log.info("Resolving {} = {}^k (mod {})...", a, b, n);

        final int m = getM(n);

        final Map<Integer, Integer> babySteps = babySteps(b, n, m);
        log.debug("Baby Steps : {}\n", babySteps);
        final Pair<Integer, Integer> pair = giantSteps(b, n, a, m, babySteps);

        final int i = pair.getLeft();
        final int j = pair.getRight();
        log.debug("i = {}", i);
        log.debug("j = {}", j);

        final int k = i * m + j;
        log.debug("k = {} x {} + {} = {}", i, m, j, k);
        log.info("Result = [{}]", k);
        return k;
    }

    /**
     * Return the absolute root of n-1.
     *
     * @param n the number to use to compute M.
     * @return an integer.
     */
    public static int getM(final int n) {
        log.debug("Computing m...");
        final int sqrt = intSqrt(n - 1);
        log.info("m = √({}-1) = {}", n, sqrt);
        return sqrt;
    }

    private static Map<Integer, Integer> babySteps(final int base, final int modulus, final int m) {

        log.debug("Taking baby steps fro 0 to {}", m - 1);
        final Map<Integer, Integer> babySteps = new HashMap<>();
        for (int j = 0; j < m; j++) {
            final int value = takeBabyStep(base, j, modulus);
            log.debug("Baby step {}^{} (mod {}) = {}", base, j, modulus, value);
            babySteps.put(value, j);
        }
        return babySteps;
    }

    private static Pair<Integer, Integer> giantSteps(final int b, final int n, final int a, final int m, final Map<Integer, Integer> babySteps) {

        log.debug("Taking giant steps fro 0 to {}", m - 1);
        for (int i = 0; i < m; i++) {
            final int step = takeGiantStep(b, n, a, m, i);
            log.debug("Giant step {} x {}^({} x {}) (mod {}) = {}", a, b, -m, i, n, step);
            if (babySteps.containsKey(step)) {
                log.debug("Giant Step matching Baby Step!");
                final int j = babySteps.get(step);
                return new ImmutablePair<>(i, j);
            }
        }
        throw new IllegalStateException("Should not happen!");
    }

    private static int takeBabyStep(final int base, final int exponent, final int modulus) {
        final Exponentiation exponentiation = Exponentiation.from(base, exponent, modulus);
        return exponentiation.resolve();
    }

    private static int takeGiantStep(final int b, final int n, final int a, final int m, final int i) {
        final int exponent = -i * m;
        final int bim = Exponentiation.compute(b, exponent, n);
        return (a * bim) % n;
    }
}
