package org.dcu.student.sem1.ca642.factorization;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dcu.student.sem1.ca642.factorization.PollardRho.factor;

public class PollardRhoTest {

    @Test
    public void given_non_prime_number2_when_factor_then_expect_correct_factors() {
        assertThat(factor(209, 1, 1))
              .isNotNull()
              .hasSize(2)
              .contains(11, 19);
    }

    @Test
    public void given_non_prime_number_when_factor_then_expect_correct_factors() {
        assertThat(factor(187, 1, 1))
              .isNotNull()
              .hasSize(2)
              .contains(11, 17);
    }

    @Test
    public void given_non_prime_number_when_factor_without_parameter_then_expect_correct_factors() {
        assertThat(factor(187))
              .isNotNull()
              .hasSize(2)
              .contains(11, 17);
    }

    @Test
    public void given_non_prime_number2_when_factor_without_parameter_then_expect_correct_factors() {
        assertThat(factor(209))
              .isNotNull()
              .hasSize(2)
              .contains(11, 19);
    }
}