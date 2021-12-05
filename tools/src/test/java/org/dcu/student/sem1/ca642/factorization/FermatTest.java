package org.dcu.student.sem1.ca642.factorization;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.dcu.student.sem1.ca642.factorization.Fermat.factor;

public class FermatTest {

    @Test
    public void given_even_number_when_factor_then_expect_exception() {
        assertThatThrownBy(() -> factor(216))
              .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void given_odd_number_217_when_factor_then_expect_correct_factors() {
        assertThat(factor(217))
              .isNotNull()
              .hasSize(2)
              .contains(31, 7);
    }

    @Test
    public void given_odd_number_319_when_factor_then_expect_correct_factors() {
        assertThat(factor(319))
              .isNotNull()
              .hasSize(2)
              .contains(11, 29);
    }

    @Test
    public void given_prime_number_when_factor_then_expect_prime() {
        assertThat(factor(17))
              .isNotNull()
              .hasSize(1)
              .contains(17);
    }
}