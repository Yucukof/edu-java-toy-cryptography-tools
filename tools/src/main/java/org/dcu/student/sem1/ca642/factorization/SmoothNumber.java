package org.dcu.student.sem1.ca642.factorization;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.primes.naive.BruteForce;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SmoothNumber {

    public static boolean isBSmooth(final int n) {
        final int b = getBSmooth(n);
        return b != n;
    }

    public static int getBSmooth(final int n) {
        log.info("Computing smoothness of {}...", n);
        final List<Factor> factors = factor(n);
        final int b = factors.stream()
              .map(Factor::getBase)
              .max(Integer::compareTo)
              .orElse(n);
        log.info("{} is {}-smooth", n, b);
        return b;
    }

    public static List<Factor> factor(final int n) {
        log.info("Factoring {}...", n);
        final List<Factor> factors = new ArrayList<>();

        int value = n;
        int prime = 2;
        int exponent = 0;

        while (value > 1) {
            while (value % prime == 0) {
                value /= prime;
                exponent++;
            }
            if (exponent != 0) {
                final Factor factor = new Factor(prime, exponent);
                factors.add(factor);
            }
            prime = BruteForce.getNextPrime(prime);
            exponent = 0;
        }
        log.info("Result = [{} = {}]", n, factors.stream().map(Objects::toString).collect(Collectors.joining("x")));
        return factors;
    }

    public static boolean isBPowerSmooth(final int n) {
        final int b = getBPowerSmoot(n);
        return b != n;
    }

    public static int getBPowerSmoot(final int n) {
        log.info("Computing power-smoothness of {}...", n);
        final List<Factor> factors = factor(n);
        final int b = factors.stream()
              .map(Factor::getValue)
              .max(Integer::compareTo)
              .orElse(n);
        log.info("{} is {}-power smooth", n, b);
        return b;
    }

    public static boolean isSmooth(final int n) {
        log.info("Checking if {} is smooth...", n);
        final int b = getBSmooth(n);
        // Not an actual rule...
        final boolean isSmooth = b < n;
        log.info("{} is {}reasonably smooth...", n, isSmooth ? "not " : "");
        return isSmooth;
    }
}
