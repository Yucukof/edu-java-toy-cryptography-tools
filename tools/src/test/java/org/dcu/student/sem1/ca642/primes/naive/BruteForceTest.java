package org.dcu.student.sem1.ca642.primes.naive;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BruteForceTest {

    @Test
    public void given_number_with_non_prime_factors_when_isPrime_return_false() {
        assertThat(BruteForce.isPrime(9)).isFalse();
    }

    @Test
    public void given_number_with_prime_factors_when_isPrime_return_false() {
        assertThat(BruteForce.isPrime(10)).isFalse();
    }

    @Test
    public void given_prime_number_when_isPrime_return_true() {
        assertThat(BruteForce.isPrime(5)).isTrue();
        assertThat(BruteForce.isPrime(11)).isTrue();
    }
}