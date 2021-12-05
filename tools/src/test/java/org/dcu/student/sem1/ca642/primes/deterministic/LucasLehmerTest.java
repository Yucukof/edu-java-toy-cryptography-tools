package org.dcu.student.sem1.ca642.primes.deterministic;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.dcu.student.sem1.ca642.primes.deterministic.LucasLehmer.*;

public class LucasLehmerTest {

    @Test
    public void given_2s_multiple_when_getComposition_then_expect_correct_exponent() {
        final LucasLehmer.Composition composition = getComposition(64);
        assertThat(composition)
              .isNotNull();

        assertThat(composition.getExponent())
              .isEqualTo(6);
    }

    @Test
    public void given_mersenne_number_when_isMersenneNumber_then_expect_true() {
        final boolean mersenneNumber = isMersenneNumber(63);
        assertThat(mersenneNumber)
              .isTrue();
    }

    @Test
    public void given_non_mersenne_even_number_when_isMersenneNumber_then_expect_false() {
        final boolean mersenneNumber = isMersenneNumber(64);
        assertThat(mersenneNumber)
              .isFalse();
    }

    @Test
    public void given_non_mersenne_number_when_isMersenneNumber_then_expect_false() {
        final boolean mersenneNumber = isMersenneNumber(65);
        assertThat(mersenneNumber)
              .isFalse();
    }

    @Test
    public void given_non_mersenne_number_when_isPrime_then_expect_true() {
        final boolean mersenneNumber = isPrime(65);
        assertThat(mersenneNumber)
              .isFalse();
    }

    @Test
    public void given_non_prime_mersenne_number_when_isPrime_then_expect_false() {
        final boolean mersenneNumber = isPrime(63);
        assertThat(mersenneNumber)
              .isFalse();
    }

    @Test
    public void given_prime_mersenne_number_when_isPrime_then_expect_true() {
        final boolean mersenneNumber = isPrime(127);
        assertThat(mersenneNumber)
              .isTrue();
    }

}