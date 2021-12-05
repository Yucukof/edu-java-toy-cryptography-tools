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
public class Naive {

    public static List<Integer> toPrimeFactors(final int n) {
        log.info("Factoring {} to strictly prime factors...", n);

        final List<Integer> factors = new ArrayList<>();
        int value = n;
        int prime = 2;
        while (value > 1) {
            while (value % prime == 0) {
                value /= prime;
                factors.add(prime);
            }
            if (value == 1) break;
            prime = BruteForce.getNextPrime(prime);
        }
        log.debug("{} = {}", n, factors.stream().map(Objects::toString).collect(Collectors.joining("x")));
        return factors;
    }

    public static List<Integer> toNonPrimesFactors(final int n) {
        log.info("Factoring {}...", n);
        final List<Integer> factors = new ArrayList<>();
        int value = n;
        int prime = 2;
        while (value > 1) {
            int factor = 1;
            while (value % prime == 0) {
                value /= prime;
                factor *= prime;
            }
            if (factor != 1) {
                factors.add(factor);
            }
            prime = BruteForce.getNextPrime(prime);
        }
        log.info("Result = [{} = {}]", n, factors.stream().map(Objects::toString).collect(Collectors.joining("x")));
        return factors;
    }

}
