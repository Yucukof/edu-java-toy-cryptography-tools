package org.dcu.student.sem1.ca642.primes.probabilistic;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dcu.student.sem1.ca642.primes.probabilistic.MillerRabin.*;

public class MillerRabinTest {

    @Test
    public void given_non_prime_number_when_getAnyLiar_then_expect_valid_liar() {
        final List<Integer> liars = MillerRabin.getLiars(91);
        final Optional<Integer> liar = MillerRabin.getAnyLiar(91);

        assertThat(liar)
              .isPresent();

        final int value = liar.get();
        assertThat(liars)
              .isNotNull()
              .contains(value);
    }

    @Test
    public void given_non_prime_number_when_getAnyWitness_then_expect_valid_witness() {
        final List<Integer> witnesses = MillerRabin.getWitnesses(91);
        final Optional<Integer> witness = MillerRabin.getAnyWitness(91);

        assertThat(witness)
              .isPresent();

        final Integer value = witness.get();
        assertThat(witnesses)
              .isNotNull()
              .contains(value);
    }
    @Test
    public void given_non_prime_number_and_strong_liar_when_isWitness_then_expect_false() {
        AssertionsForClassTypes.assertThat(isWitness(14, 15))
              .isFalse();
        AssertionsForClassTypes.assertThat(isLiar(14, 15))
              .isTrue();
    }

    @Test
    public void given_non_prime_number_and_witness_when_isWitness_then_expect_true() {
        AssertionsForClassTypes.assertThat(isWitness(12, 15))
              .isTrue();
        AssertionsForClassTypes.assertThat(isLiar(12, 15))
              .isFalse();
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
        final List<Integer> witnesses = getWitnesses(15);
        Assertions.assertThat(witnesses)
              .isNotNull()
              .hasSize(12)
              .contains(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
    }

    @Test
    public void given_prime_number_17_when_getComposition_then_expect_correct_exponent_and_remainder() {
        final MillerRabin.Composition composition = getComposition(17 - 1);

        assertThat(composition).isNotNull();
        assertThat(composition.getExponent()).isEqualTo(4);
        assertThat(composition.getRemainder()).isOne();
    }

    @Test
    public void given_prime_number_29_when_getComposition_then_expect_correct_exponent_and_remainder() {
        final MillerRabin.Composition composition = getComposition(29 - 1);

        assertThat(composition).isNotNull();
        assertThat(composition.getExponent()).isEqualTo(2);
        assertThat(composition.getRemainder()).isEqualTo(7);
    }

    @Test
    public void given_prime_number_and_liar_when_isWitness_then_expect_false() {
        AssertionsForClassTypes.assertThat(SolovayStrassen.isWitness(12, 17)).isFalse();
    }

    @Test
    public void given_prime_number_repeated_when_isPseudoPrime_then_expect_true() {
        AssertionsForClassTypes.assertThat(isPseudoPrime(17, 17)).isTrue();
    }

    @Test
    public void given_prime_number_when_getLiars_then_expect_only_liars() {
        final List<Integer> liars = getLiars(17);
        Assertions.assertThat(liars)
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
        AssertionsForClassTypes.assertThat(isPseudoPrime(17, 1)).isTrue();
    }

}