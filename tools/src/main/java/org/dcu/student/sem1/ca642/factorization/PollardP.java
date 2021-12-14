package org.dcu.student.sem1.ca642.factorization;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.modulus.exponentiation.Exponentiation;
import org.dcu.student.sem1.ca642.utils.MathUtils;

import java.util.*;
import java.util.stream.Collectors;

import static org.dcu.student.sem1.ca642.primes.EuclideanAlgorithm.gcd;
import static org.dcu.student.sem1.ca642.primes.naive.BruteForce.getNextPrime;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PollardP {

    /**
     * Factor a number n using the Pollard "p-1" method.
     *
     * @param n the number to factor
     * @return a list of factors that compose n.
     */
    public static Set<Integer> factor(final int n) {
        log.info("Computing factor of {}...", n);

        final Optional<Integer> optFactor = guess(n);
        return getFactors(n, optFactor);
    }

    /**
     * Finds the value of the first factor of n using elaborated guess and confirm.
     *
     * @param n the number to factor.
     * @return a factor of n.
     */
    public static Optional<Integer> guess(final int n) {
        // We search the first factor of n.
        Optional<Integer> factor;
        // Guess the smallest smoothness of n starting from prime 2.
        int b = 1;
        do {
            // Fetch the next prime number.
            b = getNextPrime(b);
            // Try and guess the value of the smallest factor using this smoothness value.
            factor = guess(n, b);
            // Check if a valid factor was found.
        } while (!factor.isPresent() && b < n);
        // Returns the valid factor found.
        return factor;
    }

    private static Set<Integer> getFactors(final int n, final Optional<Integer> optFactor) {
        final Set<Integer> factors = new HashSet<>();

        if (optFactor.isPresent()) {
            final int factor = optFactor.get();
            final int complement = n / factor;

            log.info("{} = [{} x {}]\n", n, factor, complement);
            factors.add(factor);
            factors.add(complement);
        } else {
            log.info("{} is presumably prime", n);
            factors.add(n);
        }
        return factors;
    }

    /**
     * Try and guess the value of a factor of n for a given value of B-smoothness.
     *
     * @param n the number to factor.
     * @param b the index of B-smoothness
     * @return a factor if a valid factor could be found, otherwise empty.
     */
    public static Optional<Integer> guess(final int n, final int b) {
        log.info("Guessing with {}-smoothness bound...", b);
        // For all positive integers from 2 to B included
        for (int a = 2; a <= b; a++) {
            log.debug("Iterating with {}", a);
            // Check if the value is relatively prime to n.
            if (isValidA(a, n)) {
                // If yes, then compute the value of exponent M.
                final int m = getM(b);
                // Get the value of the factor.
                final int factor = getFactor(a, m, n);
                // Verify if the factor is valid (i.e. not 1 or n-1).
                if (isFactor(factor, n)) {
                    log.debug("{} is a factor of {}", factor, n);
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
        log.debug("Checking if {} is relatively prime to {}", a, b);
        final boolean relativePrimes = gcd(a, b) == 1;
        log.debug("{} is{} relatively prime to {}", a, relativePrimes ? "" : " not", b);
        return relativePrimes;
    }

    public static Integer getM(final int n) {
        log.debug("Computing Least Common Multiple of primes factors ≤ {}", n);
        final List<Integer> terms = getTerms(n).stream()
              .map(Factor::getValue)
              .collect(Collectors.toList());
        return terms.size() > 1
               ? MathUtils.lcm(terms)
               : terms.get(0);
    }

    private static int getFactor(final int a, final int m, final int n) {
        final int power = Exponentiation.compute(a, m, n);
        log.debug("{}^{} (mod {}) = {}", a, m, n, power);
        final int a_ = (power - 1) % n;
        log.debug("({}-1) (mod {}) = {}", power, n, a_);
        return a_ == 0 ? 0 : gcd(a_, n);
    }

    private static boolean isFactor(final int factor, final int n) {
        final boolean isFactor = factor != 1 && factor != n && factor != 0;
        log.debug("{} {} {1,-1}\n", factor, isFactor ? "∉" : "∈");
        return isFactor;
    }

    private static List<Factor> getTerms(final int n) {
        log.debug("Computing max exponent for each prime ≤ {}", n);
        final List<Factor> factors = new ArrayList<>();

        for (int prime = 2; prime <= n; prime = getNextPrime(prime)) {
            final int exponent = getMaxExponent(n, prime);
            final Factor factor = new Factor(prime, exponent);
            factors.add(factor);
        }
        log.debug("Factors = {}", factors);
        return factors;
    }

    /**
     * Computes max exponent of prime such that prime^exponent <= b.
     *
     * @param b     the B-Smoothness index
     * @param prime the prime to use as base for exponentiation
     * @return e\in\mathbb{N}: prime^e <= b \wedge \not\exist e'\in\matbb{N}: e'> e \wedge prime^e' <= b.
     */
    private static int getMaxExponent(final int b, final int prime) {
        int value = 1;
        int power = 0;

        while (value * prime <= b) {
            value *= prime;
            power++;
        }
        log.debug("{}^{} = {} ≤ {}", prime, power, value, b);
        return power;
    }
}
