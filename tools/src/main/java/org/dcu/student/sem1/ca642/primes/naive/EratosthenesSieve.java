package org.dcu.student.sem1.ca642.primes.naive;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EratosthenesSieve {

    public static List<Integer> get(int n) {
        log.info("Getting primes up to {}", n);
        boolean[] candidates = declare(n);
        boolean[] primes = compute(candidates);
        final List<Integer> list = collect(primes);
        log.info("Result = {}", list);
        return list;
    }

    /**
     * Declares an array of unconditional primality statuses for numbers up to n.
     *
     * @param n the number of primality status to declare.
     * @return an array of unconfirmed primality statuses.
     */
    private static boolean[] declare(final int n) {
        boolean[] candidates = new boolean[n - 1];
        Arrays.fill(candidates, true);
        return candidates;
    }

    /**
     * Given a list of candidates primes, compute the primality status of all numbers.
     *
     * @param candidates an array of unconfirmed primality statuses.
     * @return an array of confirmed primality statuses.
     */
    private static boolean[] compute(final boolean[] candidates) {

        final int n = candidates.length + 2;
        // For each number whose squared value is smaller than the maximum value
        for (int p = 2; p * p <= n; p++) {
            // Check if number is prime
            if (isPrime(candidates, p)) {
                // If true, then remove all multiples until n.
                removeMultiplesOfP(candidates, p);
            }
            // Otherwise, continue
        }
        // Returns the array with confirmed primality status
        return candidates;
    }

    /**
     * Convert the primality status list into a collection of primes.
     *
     * @param primes the array containing the confirmed primality statuses.
     * @return a list of prime numbers.
     */
    private static List<Integer> collect(final boolean[] primes) {
        return IntStream.range(2, primes.length + 2)
              .boxed()
              .filter(i -> isPrime(primes, i))
              .collect(Collectors.toList());
    }

    /**
     * check if the number p is prime by check its primality status value.
     *
     * @param candidates the primality status array.
     * @param p          the number for which check primality.
     * @return true if the number is a confirmed prime, false otherwise.
     */
    private static boolean isPrime(final boolean[] candidates, final int p) {
        return candidates[p - 2];
    }

    /**
     * Marks all multiples of p as non-prime, that is turns their primality status to false.
     *
     * @param candidates the array containing the primality statuses to confirm.
     * @param p          the prime number for which removes all multiples.
     */
    private static void removeMultiplesOfP(final boolean[] candidates, final int p) {
        final int n = candidates.length + 2;
        for (int i = p * 2; i < n; i += p) {
            candidates[i - 2] = false;
        }
    }
}
