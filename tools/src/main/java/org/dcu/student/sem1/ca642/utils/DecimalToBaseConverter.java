package org.dcu.student.sem1.ca642.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.utils.MathUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DecimalToBaseConverter {

    public static int[] exponentsBase2(final int value) {
        return exponents(value, 2);
    }

    public static int[] exponents(final int value, final int base) {

        log.info("Converting {} to base {}", value, base);

        final int maxExponent = getMaxExponent(value, base);

        final int[] factors = new int[maxExponent + 1];
        Arrays.fill(factors, 0);

        int remaining = value;
        while (remaining > 0) {
            final int exponent = getMaxExponent(remaining, base);
            remaining -= MathUtils.power(base, exponent);
            factors[exponent] = 1;
        }

        display(value, base, factors);
        combine(base, factors);

        return factors;
    }

    /**
     * Computes the exponent to which elevate the base to obtain the maximum value possible under a given target.
     *
     * @param target the maximum value to reach.
     * @param base   the base to use.
     * @return an integer to which elevate the base such that
     *       <br> $x\in\mathbb{N}: base^x <= target \wedge (\not\exists y\in\mathbb{N}:base^y > base^x \wedge base^y <=
     *       target)
     */
    private static int getMaxExponent(final int target, final int base) {
        int exponent = 0;
        int value = 1;
        while (target - (value * base) >= 0) {
            value *= base;
            exponent++;
        }
        return exponent;
    }

    /**
     * Simply display the calculation to convert a value in a given base.
     *
     * @param value     the value to convert.
     * @param base      the base to use.
     * @param exponents the exponents to apply to the base to obtain the value in the given base.
     */
    private static void display(final int value, final int base, final int[] exponents) {

        if (log.isDebugEnabled()) {
            final String details = IntStream.range(0, exponents.length)
                  .filter(i -> exponents[i] == 1)
                  .mapToObj(i -> String.format("%s^%s", base, i))
                  .collect(Collectors.joining("+"));

            log.debug("{} = [{}]", value, details);
        }
    }

    /**
     * Simply displays a value in a given base.
     *
     * @param base      the base to use.
     * @param exponents the exponents used to represent the actual value in the given base.
     */
    private static void combine(final int base, final int[] exponents) {

        final List<Integer> list = Arrays.stream(exponents)
              .boxed()
              .collect(Collectors.toList());

        Collections.reverse(list);

        final String number = list.stream()
              .map(i -> String.format("%s", i))
              .collect(Collectors.joining());

        log.info("{} = [{}]", base, number);
    }
}
