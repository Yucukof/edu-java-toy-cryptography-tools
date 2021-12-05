package org.dcu.student.sem1.ca642.primes;


import org.dcu.student.sem1.ca642.factorization.Naive;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dcu.student.sem1.ca642.primes.Primality.*;

public class PrimalityTest {

    @Test
    public void given_non_prime_number_with_prime_factors_when_isPrimeComposite_then_return_true() {
        assertThat(isPrimeComposite(10)).isTrue();
    }

    @Test
    public void given_non_prime_number_without_prime_factors_when_isPrimeComposite_then_return_true() {
        assertThat(isPrimeComposite(9)).isFalse();
    }

    @Test
    public void given_one_prime_and_one_non_prime_when_isCoPrimes_then_expect_true() {
        assertThat(isCoPrimes(5, 9)).isTrue();
        assertThat(isCoPrimes(9, 5)).isTrue();
    }

    @Test
    public void given_prime_number_when_getFactors_then_expect_self() {
        assertThat(Naive.toNonPrimesFactors(13))
              .isNotNull()
              .hasSize(1)
              .contains(13);
    }

    @Test
    public void given_prime_number_when_isPrimeComposite_then_return_true() {
        assertThat(isPrimeComposite(5)).isTrue();
        assertThat(isPrimeComposite(11)).isTrue();
    }

    @Test
    public void given_two_non_primes_non_relatively_primes_when_isCoPrimes_then_expect_false() {
        assertThat(isCoPrimes(9, 81)).isFalse();
        assertThat(isCoPrimes(81, 9)).isFalse();
    }

    @Test
    public void given_two_non_primes_relatively_primes_when_isCoPrimes_then_expect_true() {
        assertThat(isCoPrimes(9, 16)).isTrue();
        assertThat(isCoPrimes(16, 9)).isTrue();
    }

    @Test
    public void given_two_primes_when_isCoPrimes_then_expect_true() {
        assertThat(isCoPrimes(5, 11)).isTrue();
    }
}