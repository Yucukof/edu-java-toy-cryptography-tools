package org.dcu.student.sem1.ca642.primes.naive;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.utils.MathUtils;

import java.util.stream.IntStream;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BruteForce {

    public static int getNextPrime(final int n) {
        int prime = n;
        do {
            prime++;
        } while (!isPrime(prime));
        return prime;
    }

    public static boolean isPrime(final int p) {
        log.info("Evaluating primality of {} by brute-force...", p);
        if (p == 2) {
            log.info("Result = [true]");
            return true;
        }
        final int sqrt = MathUtils.intSqrt(p);
        final boolean isPrime = IntStream.rangeClosed(2, sqrt)
              .noneMatch(i -> p % i == 0);
        log.info("Result = [{}]", isPrime ? "true" : "false");
        return isPrime;
    }
}
