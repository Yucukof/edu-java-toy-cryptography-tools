package org.dcu.student.sem1.ca642.modulus.roots;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SquareRootPrimeTest {

    @Test
    public void given_squareRootPrime11_numbers_when_getSquareRoot_then_expect_correct_root() {
        final SquareRootPrime squareRoots = new SquareRootPrime(37, 11);
        assertThat(squareRoots)
              .isNotNull();
        assertThat(squareRoots.get())
              .isNotNull()
              .contains(2, 9);
    }

    @Test
    public void given_squareRootPrime13_numbers_when_getSquareRoot_then_expect_correct_root() {
        final SquareRootPrime squareRoots = new SquareRootPrime(3, 13);
        assertThat(squareRoots)
              .isNotNull();
        assertThat(squareRoots.get())
              .isNotNull()
              .contains(4, 9);
    }
}