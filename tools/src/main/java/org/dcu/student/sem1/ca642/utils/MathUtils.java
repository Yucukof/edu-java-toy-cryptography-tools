package org.dcu.student.sem1.ca642.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MathUtils {

    public static boolean isOdd(final int a) {
        return !isEven(a);
    }

    public static boolean isEven(final int a) {
        return a % 2 == 0;
    }

    public static int power(final int base, final int exponent) {
        if (exponent < 0) {
            throw new UnsupportedOperationException();
        }

        int value = 1;
        for (int i = 1; i <= exponent; i++) {
            value *= base;
        }
        return value;
    }

    public static int intSqrt(final int p) {
        final double sqrt = Math.sqrt(p);
        final int sqrti = (int) sqrt;

        return sqrt > sqrti ? sqrti + 1 : sqrti;
    }

    public static boolean isPerfectSquare(final int n) {
        int root = 1;
        int power;
        do {
            root++;
            power = root * root;
        } while (power < n);
        return power == n;
    }

    public static Integer lcm(final List<Integer> terms) {
        log.info("Computing Least Common Multiple of {}", terms);
        final Integer lcm = terms.stream()
              .reduce((a, b) -> a * b)
              .orElseThrow(RuntimeException::new);
        log.debug("{} = {}", terms.stream().map(Objects::toString).collect(Collectors.joining(" x ")), lcm);
        log.info("LCM({}) = [{}]", terms, lcm);
        return lcm;
    }
}
