package org.dcu.student.sem1.ca642.factorization;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.utils.MathUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.dcu.student.sem1.ca642.modulus.EuclideanAlgorithm.gcd;
import static org.dcu.student.sem1.ca642.modulus.exponentiation.SquareAndMultiply.power;
import static org.dcu.student.sem1.ca642.primes.naive.BruteForce.getNextPrime;
import static org.dcu.student.sem1.ca642.primes.naive.BruteForce.isPrime;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PollardP {

    /**
     * Factor a number n using the Pollard "p-1" method.
     *
     * @param n the number to factor
     * @return a list of factors that compose n.
     */
    public static List<Integer> factor(final int n) {
        log.info("Computing factor of {}...", n);
        if (isPrime(n)) {
            log.debug("{} is a prime number", n);
            return List.of(n);
        }

        int factor = guess(n);
        final int complement = n / factor;

        log.debug("{} = [{} x {}]", n, factor, complement);
        return Arrays.asList(factor, complement);
    }

    /**
     * Finds the value of the first factor of n using elaborated guess and confirm.
     *
     * @param n the number to factor.
     * @return a factor of n.
     */
    private static int guess(final int n) {
        // We search the first factor of n.
        Optional<Integer> factor;
        // Guess the smallest smoothness of n starting from prime 2.
        int b = 1;
        do {
            // Fetch the next prime number.
            b = getNextPrime(b);
            // Try and guess the value of the smallest factor using this smoothness value.
            factor = guess(b, n);
            // Check if a valid factor was found.
        } while (!factor.isPresent());
        // Returns the valid factor found.
        return factor.get();
    }

    /**
     * Try and guess the value of a factor of n for a given value of B-smoothness.
     *
     * @param b the index of B-smoothness
     * @param n the number to factor.
     * @return a factor if a valid factor could be found, otherwise empty.
     */
    private static Optional<Integer> guess(final int b, final int n) {
        // For all positive integers from 2 to B included
        for (int a = 2; a <= b; a++) {
            // Check if the value is relatively prime to n.
            if (isValidA(a, n)) {
                // If yes, then compute the value of exponent M.
                final int m = getM(b);
                // Get the value of the factor.
                final int factor = getFactor(a, m, n);
                // Verify if the factor is valid (i.e. not 1 or n-1).
                if (isFactor(factor, n)) {
                    // If yes, then return the factor.
                    return Optional.of(factor);
                }
            }
        }
        // Otherwise, return "no factor found".
        return Optional.empty();
    }

    /**
     * Check if the value of a is relatively prime to b.
     *
     * @param a a positive integer ranging between 2 and B included.
     * @param b the number to compare to a.
     * @return true if the GCD between a & b is 1, false otherwise.
     */
    private static boolean isValidA(final int a, final int b) {
        return gcd(a, b) == 1;
    }

    public static Integer getM(final int n) {
        log.debug("Computing Least Common Multiple of exponentiated primes factors of {}", n);
        final List<Integer> terms = getTerms(n).stream()
              .map(Factor::getValue)
              .collect(Collectors.toList());
        final Integer lcm = MathUtils.lcm(terms);
        log.debug("Result = [{}]", lcm);
        return lcm;
    }

    private static int getFactor(final int a, final int m, final int n) {
        log.debug("Computing gcd(a^m-1 (mod {}), {})", n, n);
        final int a_ = (power(a, m, n) - 1) % n;
        final int gcd = gcd(a_, n);
        log.debug("Result = [{}]", gcd);
        return gcd;
    }

    private static boolean isFactor(final int factor, final int n) {
        return factor != 1 && factor != n;
    }

    private static List<Factor> getTerms(final int n) {
        log.debug("Computing primes exponentiation under {}", n);
        final List<Factor> factors = new ArrayList<>();

        for (int prime = 2; prime <= n; prime = getNextPrime(prime)) {
            final int exponent = getMaxExponent(n, prime);
            final Factor factor = new Factor(prime, exponent);
            factors.add(factor);
        }
        log.debug("Result = {}", factors);
        return factors;
    }

    private static int getMaxExponent(final int n, final int prime) {
        log.trace("Computing max exponent of {} such that {}^exp <= {}", prime, prime, n);
        int value = 1;
        int power = 0;

        while (value * prime <= n) {
            value *= prime;
            power++;
        }
        log.trace("Result = {}", power);
        return power;
    }
}
