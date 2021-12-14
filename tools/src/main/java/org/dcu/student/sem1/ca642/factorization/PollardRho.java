package org.dcu.student.sem1.ca642.factorization;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

import static org.dcu.student.sem1.ca642.primes.EuclideanAlgorithm.gcd;
import static org.dcu.student.sem1.ca642.primes.naive.BruteForce.isPrime;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PollardRho {

    private static final Random rd = new Random();

    public static List<Integer> factor(final int n) {

        do {
            final int c = rd.nextInt(n - 1) + 1;
            final int x0 = rd.nextInt(n - 1) + 1;

            final List<Integer> factors = factor(n, c, x0);
            if (!factors.isEmpty()) {
                log.info("{} = [{}]", n, factors.stream()
                      .map(Objects::toString)
                      .collect(Collectors.joining(" x ")));
                return factors;
            }
        } while (true);
    }

    public static List<Integer> factor(final int n, final int c, final int x0) {

        log.info("Factoring {} using Pollard Rho...", n);
        log.debug("Using constant {} and initial vector {}", c, x0);

        if (isPrime(n)) {
            throw new IllegalArgumentException(String.format("%s is a prime number!", n));
        }

        final Optional<Integer> optFactor = walk(x0, c, n);
        if (!optFactor.isPresent()) {
            return Collections.emptyList();
        }
        final Integer fact = optFactor.get();
        final int complement = n / fact;
        return Arrays.asList(fact, complement);
    }

    private static Optional<Integer> walk(final int x0, final int c, final int n) {

        int previous = x0;
        int next = x0;
        int current;

        int d = 0;
        do {
            log.debug("Computing X...");
            previous = next(previous, c, n);
            log.debug("Computing Y...");
            current = next(next, c, n);
            next = next(current, c, n);
            final int value = (n + next - previous) % n;
            log.debug("d = {} - {} (mod {}) = {}", next, previous, n, value);
            if (value != 0) {
                d = gcd(value, n);
                log.debug("{} - {} (mod {}) = {}", next, previous, n, d);
            }
        } while (d == 0 || d == 1);
        return Optional.ofNullable(d);
    }

    private static int next(final int x, final int c, final int n) {
        final int square = x * x;
        final int next = (square + c) % n;
        log.debug("{}²+{} (mod {}) = {}", x, c, n, next);
        return next;
    }
}
