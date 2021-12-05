package org.dcu.student.sem1.ca642.modulus.exponentiation;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dcu.student.sem1.ca642.modulus.exponentiation.SquareAndMultiply.multiplyAndSquare;
import static org.dcu.student.sem1.ca642.modulus.exponentiation.SquareAndMultiply.squareAndMultiply;

public class SquareAndMultiplyTest {

    @Test
    public void given_base123_exponent5_modulus511_when_multiplyAndSquare_then_expect_359() {
        final int base = 123;
        final int exponent = 5;
        final int modulus = 511;
        final int result = 359;

        final int actualResult = multiplyAndSquare(base, exponent, modulus);
        assertThat(actualResult).isEqualTo(result);
    }

    @Test
    public void given_base123_exponent5_modulus511_when_squareAndMultiply_then_expect_359() {
        final int base = 123;
        final int exponent = 5;
        final int modulus = 511;
        final int result = 359;

        final int actualResult = squareAndMultiply(base, exponent, modulus);
        assertThat(actualResult).isEqualTo(result);
    }

    @Test
    public void given_base27_exponent10_modulus33_when_multiplyAndSquare_then_expect_8() {
        final int base = 27;
        final int exponent = 10;
        final int modulus = 33;
        final int result = 12;

        final int actualResult = squareAndMultiply(base, exponent, modulus);
        assertThat(actualResult).isEqualTo(result);
    }

    @Test
    public void given_base27_exponent10_modulus33_when_squareAndMultiply_then_expect_8() {
        final int base = 27;
        final int exponent = 10;
        final int modulus = 33;
        final int result = 12;

        final int actualResult = squareAndMultiply(base, exponent, modulus);
        assertThat(actualResult).isEqualTo(result);
    }

    @Test
    public void given_base6_exponent5_modulus17_when_multiplyAndSquare_then_expect_7() {
        final int base = 6;
        final int exponent = 5;
        final int modulus = 17;
        final int result = 7;

        final int actualResult = squareAndMultiply(base, exponent, modulus);
        assertThat(actualResult).isEqualTo(result);
    }

    @Test
    public void given_base6_exponent5_modulus17_when_squareAndMultiply_then_expect_7() {
        final int base = 6;
        final int exponent = 5;
        final int modulus = 17;
        final int result = 7;

        final int actualResult = squareAndMultiply(base, exponent, modulus);
        assertThat(actualResult).isEqualTo(result);
    }

}