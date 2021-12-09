package org.dcu.student.sem1.ca642.modulus.fraction;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FractionTest {

    @Test
    public void given_fraction_when_compute_then_expect_correct_result() {
        final Fraction fraction = new Fraction(77, 117, 191);

        assertThat(fraction.resolve())
              .isEqualTo(48);

    }

}