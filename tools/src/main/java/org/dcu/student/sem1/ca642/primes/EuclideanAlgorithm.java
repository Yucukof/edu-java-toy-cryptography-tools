package org.dcu.student.sem1.ca642.primes;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EuclideanAlgorithm {

    public static boolean isCoPrimes(final int a, final int b) {
        log.info("Checking if {} is relatively prime to {}...", a, b);
        final boolean result = gcd(a, b) == 1;
        log.info("{} co-prime {}? = [{}]", a, b, result);
        return result;
    }

    public static int gcd(final int a, final int b) {
        log.info("Computing GCD of {} and {}...", a, b);
        int high = Math.max(a, b);
        int low = Math.min(a, b);

        if (low == 0) {
            throw new IllegalArgumentException("One of the arguments is zero");
        }

        while (low > 0) {
            final int remainder = high % low;
            log.debug("{} = {} x {} + {}", high, high / low, low, remainder);
            if (remainder < low) {
                high = low;
            }
            low = remainder;
        }
        log.info("GCD({},{}) = [{}]", a, b, high);
        return high;
    }
}
