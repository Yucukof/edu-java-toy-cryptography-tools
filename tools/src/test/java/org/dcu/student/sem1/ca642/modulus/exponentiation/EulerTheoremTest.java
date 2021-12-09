package org.dcu.student.sem1.ca642.modulus.exponentiation;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dcu.student.sem1.ca642.modulus.exponentiation.SquareAndMultiply.power;

public class EulerTheoremTest {

    @Test
    public void given_1002_exponent_3755_modulo_10_when_power_then_expect_1() {
        assertThat(power(1002, 3755, 10))
              .isEqualTo(8);
    }

    @Test
    public void given_9876_exponent_180_modulo_77_when_power_then_expect_1() {
        assertThat(power(9876, 180, 77))
              .isOne();
    }

    @Test
    public void given_exponent_108_modulo_109_when_apply_then_expect_exponent_0() {
        assertThat(EulerTheorem.apply(108, 109))
              .isZero();
    }

    @Test
    public void given_exponent_180_modulo_77_when_apply_then_expect_exponent_0() {
        assertThat(EulerTheorem.apply(180, 77))
              .isZero();
    }
}