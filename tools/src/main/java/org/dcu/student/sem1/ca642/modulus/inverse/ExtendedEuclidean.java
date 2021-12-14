package org.dcu.student.sem1.ca642.modulus.inverse;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

import static org.dcu.student.sem1.ca642.primes.EuclideanAlgorithm.isCoPrimes;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExtendedEuclidean {

    /**
     * Returns the multiplicative inverse of value.
     */
    public static int inverse(final int value, final int modulus) {

        log.info("Computing {}⁻¹ (mod {})...", value, modulus);

        validateInput(value, modulus);

        final Context context = new Context(modulus, value);

        final int inverse = modulus < value
              ? inverse(value, modulus, context)
              : inverse(modulus, value, context);

        log.info("Inverse = [{}]", inverse);
        return inverse;
    }

    private static void validateInput(final int value, final int modulus) {

        if (value == 0 || modulus == 0) {
            throw new IllegalArgumentException("One of the arguments is zero");
        }

        if (value < 0 || modulus < 0) {
            throw new IllegalArgumentException("One of the arguments is negative");
        }

        if (modulus == value) {
            throw new IllegalArgumentException("Left and right arguments are identical");
        }

        if (!isCoPrimes(value, modulus)) {
            throw new IllegalArgumentException(String.format("%s and %s are not relatively primes", value, modulus));
        }
    }

    private static int inverse(final int x, final int y, final Context context) {

        final boolean inverted = x < y;

        final int left = inverted ? y : x;
        final int right = inverted ? x : y;

        final int value = left - right;
        log.debug("{} - {} = {}", left, right, value);

        context.compute(left, right, value);

        return stopOrAgain(context, right, value);
    }

    private static int stopOrAgain(final Context context, final int right, final int value) {

        if (value <= 0) {
            throw new IllegalStateException("Reached ZERO!");
        }

        if (value > 1) {
            return inverse(right, value, context);
        } else {
            return context.getInverse();
        }
    }

    public static int positiveInverse(final int value, final int modulus) {
        log.info("Computing positive {}⁻¹ (mod {})...", value, modulus);
        final int inverse = inverse(value, modulus);
        final int result = (modulus + inverse) % modulus;
        log.info("Positive inverse = [{}]", result);
        return result;
    }

    @Builder
    public static class Context {

        final int x;
        final int y;
        final Map<Integer, Pair<Integer, Integer>> history = new HashMap<>();

        public Context(final int x, final int y) {

            this.x = x;
            this.y = y;

            put(x, 1, 0);
            put(y, 0, 1);
        }

        public void put(final int key, final int left, final int right) {
            final Pair<Integer, Integer> value = new ImmutablePair<>(left, right);
            history.put(key, value);
        }

        private void compute(final int left, final int right, final int value) {

            final Pair<Integer, Integer> leftPair = getCount(left);
            final Pair<Integer, Integer> rightPair = getCount(right);

            final int countValueLeft = leftPair.getLeft() - rightPair.getLeft();
            final int countValueRight = leftPair.getRight() - rightPair.getRight();

            log.debug("( {}*{} + {}*{} ) = {}", countValueLeft, x, countValueRight, y, value);

            put(value, countValueLeft, countValueRight);
        }

        public Pair<Integer, Integer> getCount(final int key) {
            return history.getOrDefault(key, new ImmutablePair<>(0, 0));
        }

        private int getInverse() {
            final Pair<Integer, Integer> count = getCount(1);
            return count.getRight();
        }
    }
}