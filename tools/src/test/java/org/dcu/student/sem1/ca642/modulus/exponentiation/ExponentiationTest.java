package org.dcu.student.sem1.ca642.modulus.exponentiation;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExponentiationTest {

    @Test
    public void given_exponentiation_with_composite_modulus_when_power_then_expect_correct_result() {

        final Exponentiation exponentiation = new Exponentiation(27, 37, 55);

        assertThat(exponentiation.resolve())
              .isEqualTo(47);
    }

    @Test
    public void given_exponentiation_with_negative_exponent_when_power_then_expect_correct_result() {

        final Exponentiation exponentiation = new Exponentiation(27, -3, 55);

        assertThat(exponentiation.resolve())
              .isEqualTo(47);
    }
}