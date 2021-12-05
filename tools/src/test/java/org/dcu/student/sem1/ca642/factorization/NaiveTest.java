package org.dcu.student.sem1.ca642.factorization;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NaiveTest {

    @Test
    public void given_composite_prime_number_when_toNonPrimesFactors_then_expect_correct_prime_factors() {
        assertThat(Naive.toNonPrimesFactors(26))
              .isNotNull()
              .hasSize(2)
              .contains(2)
              .contains(13);
    }

    @Test
    public void given_composite_prime_number_when_toPrimeFactors_then_expect_correct_prime_factors() {
        assertThat(Naive.toPrimeFactors(26))
              .isNotNull()
              .hasSize(2)
              .contains(2)
              .contains(13);
    }

    @Test
    public void given_non_prime_number_when_toNonPrimesFactors_then_expect_correct_factors() {
        assertThat(Naive.toNonPrimesFactors(18))
              .isNotNull()
              .hasSize(2)
              .contains(2)
              .contains(9);
    }

}