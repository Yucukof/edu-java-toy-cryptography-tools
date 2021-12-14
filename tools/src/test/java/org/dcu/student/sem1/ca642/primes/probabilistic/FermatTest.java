package org.dcu.student.sem1.ca642.primes.probabilistic;

import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class FermatTest {

    @Test
    public void given_non_prime_number_when_getAnyLiar_then_expect_valid_liar() {
        final List<Integer> liars = Fermat.getLiars(91);
        final Optional<Integer> liar = Fermat.getAnyLiar(91);

        assertThat(liar)
              .isPresent();

        final int value = liar.get();
        assertThat(liars)
              .isNotNull()
              .contains(value);
    }

    @Test
    public void given_non_prime_number_when_getAnyWitness_then_expect_valid_witness() {
        final List<Integer> witnesses = Fermat.getWitnesses(91);
        final Optional<Integer> witness = Fermat.getAnyWitness(91);

        assertThat(witness)
              .isPresent();

        final Integer value = witness.get();
        assertThat(witnesses)
              .isNotNull()
              .contains(value);
    }

    @Test
    public void given_non_prime_number_when_getLiars_then_expect_correct_liars() {
        final List<Integer> liars = Fermat.getLiars(15);
        assertThat(liars)
              .isNotNull()
              .hasSize(4)
              .contains(1, 4, 11, 14);
    }

    @Test
    public void given_non_prime_number_when_getWitnesses_then_expect_correct_witnesses() {
        final List<Integer> witnesses = Fermat.getWitnesses(15);
        assertThat(witnesses)
              .isNotNull()
              .hasSize(10)
              .contains(2, 3, 5, 6, 7, 8, 9, 10, 12, 13);
    }

    @Test
    public void given_prime_number_when_getLiars_then_expect_only_liars() {
        final List<Integer> liars = Fermat.getLiars(17);
        assertThat(liars)
              .isNotNull()
              .hasSize(16);
    }

    @Test
    public void given_prime_number_when_getWitnesses_then_expect_none() {
        final List<Integer> witnesses = Fermat.getWitnesses(17);
        assertThat(witnesses)
              .isNotNull()
              .isEmpty();
    }

    @Test
    public void given_valid_prime_when_isPseudoPrime_all_then_expect_true() {
        assertThat(Fermat.isPseudoPrime(17, 17))
              .isTrue();
    }

    @Test
    public void given_valid_prime_when_isPseudoPrime_then_expect_true() {
        assertThat(Fermat.isPseudoPrime(17, 1))
              .isTrue();
    }
}