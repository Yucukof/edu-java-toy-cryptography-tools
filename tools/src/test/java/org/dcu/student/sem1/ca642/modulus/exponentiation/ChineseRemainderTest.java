package org.dcu.student.sem1.ca642.modulus.exponentiation;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dcu.student.sem1.ca642.modulus.exponentiation.ChineseRemainder.power;

public class ChineseRemainderTest {

    @Test
    public void given_exponentiation_with_composite_modulus_when_power_then_expect_correct_result() {
        final int base = 27;
        final int exponent = 37;
        final int modulus = 55;

        final int value = power(base, exponent, modulus);

        assertThat(value).isEqualTo(47);
    }
}