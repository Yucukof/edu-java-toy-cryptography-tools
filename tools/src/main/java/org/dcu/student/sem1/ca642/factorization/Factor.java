package org.dcu.student.sem1.ca642.factorization;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.primes.naive.BruteForce;
import org.dcu.student.sem1.ca642.utils.MathUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Value
public class Factor {

    int base;
    int exponent;

    public Factor(final int base) {
        this.base = base;
        this.exponent = 1;
    }

    public Factor(final int base, final int exponent) {
        this.base = base;
        this.exponent = exponent;
    }


    static int productOf(final List<Integer> factors) {
        return factors.stream()
              .reduce((a, b) -> a * b)
              .orElse(0);
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

    public int getValue() {
        return MathUtils.power(base, exponent);
    }

    @Override
    public String toString() {
        return String.format("%s^%s", base, exponent);
    }
}
