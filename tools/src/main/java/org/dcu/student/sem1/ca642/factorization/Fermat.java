package org.dcu.student.sem1.ca642.factorization;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.utils.MathUtils;

import java.util.Arrays;
import java.util.List;

import static org.dcu.student.sem1.ca642.utils.MathUtils.isEven;
import static org.dcu.student.sem1.ca642.utils.MathUtils.isPerfectSquare;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Fermat {

    public static List<Integer> factor(final int n) {
        log.info("Factoring {}...", n);

        if (isEven(n)) {
            throw new IllegalArgumentException(String.format("%s is not an odd number!", n));
        }

        final int lower = MathUtils.intSqrt(n);
        final int upper = (n + 1) / 2;

        for (int x = lower; x < upper; x++) {
            final int squareX = x * x;
            final int squareY = squareX - n;

            if (isPerfectSquare(squareY)) {
                final int y = MathUtils.intSqrt(squareY);
                final int a = x + y;
                final int b = x - y;

                log.info("{} = {} x {}", n, a, b);
                return Arrays.asList(a, b);
            }
        }
        log.info("{} cannot be factored!", n);
        return List.of(n);
    }
}