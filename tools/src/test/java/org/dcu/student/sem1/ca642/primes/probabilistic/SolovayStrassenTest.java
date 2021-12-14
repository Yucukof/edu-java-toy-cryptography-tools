package org.dcu.student.sem1.ca642.primes.probabilistic;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.dcu.student.sem1.ca642.primes.probabilistic.SolovayStrassen.*;

public class SolovayStrassenTest {

    @Test
    public void given_non_prime_number_when_getAnyLiar_then_expect_valid_liar() {
        final List<Integer> liars = SolovayStrassen.getLiars(91);
        final Optional<Integer> liar = SolovayStrassen.getAnyLiar(91);

        Assertions.assertThat(liar)
              .isPresent();

        final int value = liar.get();
        Assertions.assertThat(liars)
              .isNotNull()
              .contains(value);
    }

    @Test
    public void given_non_prime_number_when_getAnyWitness_then_expect_valid_witness() {
        final List<Integer> witnesses = SolovayStrassen.getWitnesses(91);
        final Optional<Integer> witness = SolovayStrassen.getAnyWitness(91);

        Assertions.assertThat(witness)
              .isPresent();

        final Integer value = witness.get();
        Assertions.assertThat(witnesses)
              .isNotNull()
              .contains(value);
    }

    @Test
    public void given_non_prime_number_and_liar_when_isWitness_then_expect_false() {
        assertThat(isWitness(14, 15)).isFalse();
    }

    @Test
    public void given_non_prime_number_and_witness_when_isWitness_then_expect_true() {
        assertThat(isWitness(13, 15)).isTrue();
    }

    @Test
    public void given_non_prime_number_when_getLiars_then_expect_correct_liars() {
        final List<Integer> liars = getLiars(15);
        Assertions.assertThat(liars)
              .isNotNull()
              .hasSize(2)
              .contains(1, 14);
    }

    @Test
    public void given_non_prime_number_when_getWitnesses_then_expect_correct_witnesses() {
        final List<Integer> liars = getWitnesses(15);
        Assertions.assertThat(liars)
              .isNotNull()
              .hasSize(12)
              .contains(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
    }

    @Test
    public void given_prime_number_and_liar_when_isWitness_then_expect_false() {
        assertThat(isWitness(12, 17)).isFalse();
    }

    @Test
    public void given_prime_number_repeated_when_isPseudoPrime_then_expect_true() {
        assertThat(isPseudoPrime(17, 17)).isTrue();
    }

    @Test
    public void given_prime_number_when_getLiars_then_expect_only_liars() {
        final List<Integer> witnesses = getLiars(17);
        Assertions.assertThat(witnesses)
              .isNotNull()
              .hasSize(16);
    }

    @Test
    public void given_prime_number_when_getWitnesses_then_expect_none() {
        final List<Integer> witnesses = getWitnesses(17);
        Assertions.assertThat(witnesses)
              .isNotNull()
              .isEmpty();
    }

    @Test
    public void given_prime_number_when_isPseudoPrime_then_expect_true() {
        assertThat(isPseudoPrime(17, 1)).isTrue();
    }
}

