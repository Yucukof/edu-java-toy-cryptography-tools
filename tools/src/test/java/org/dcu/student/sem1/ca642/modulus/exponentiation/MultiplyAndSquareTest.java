package org.dcu.student.sem1.ca642.modulus.exponentiation;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dcu.student.sem1.ca642.modulus.exponentiation.MultiplyAndSquare.multiplyAndSquare;

public class MultiplyAndSquareTest {

    @Test
    public void given_base123_exponent5_modulus511_when_multiplyAndSquare_then_expect_359() {
        final int base = 123;
        final int exponent = 5;
        final int modulus = 511;
        final int result = 359;

        final int actualResult = multiplyAndSquare(base, exponent, modulus);
        assertThat(actualResult).isEqualTo(result);
    }

}