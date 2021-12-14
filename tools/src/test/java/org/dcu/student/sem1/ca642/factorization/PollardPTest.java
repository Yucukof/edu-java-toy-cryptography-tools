package org.dcu.student.sem1.ca642.factorization;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dcu.student.sem1.ca642.factorization.PollardP.factor;
import static org.dcu.student.sem1.ca642.factorization.PollardP.getM;

public class PollardPTest {

    @Test
    public void given_non_prime_number_when_factor_then_expect_correct_factor() {
        assertThat(factor(5917))
              .isNotNull()
              .hasSize(2)
              .contains(61, 97);
    }

    @Test
    public void given_non_prime_number_with_b_6_when_factor_then_expect_correct_factor() {
        assertThat(PollardP.guess(247, 6))
              .isPresent()
              .hasValue(13);
    }

    @Test
    public void given_non_prime_number_when_lcm_then_expect_correct_factors() {
        assertThat(getM(5))
              .isEqualTo(60);
    }

    @Test
    public void given_prime_number_when_factor_then_expect_self() {
        assertThat(factor(127))
              .isNotNull()
              .hasSize(1)
              .contains(127);

    }

    @Test
    public void given_non_prime_number2_when_factor_then_expect_correct_factor() {
        assertThat(factor(5917))
              .isNotNull()
              .hasSize(2)
              .contains(61, 97);
    }

}