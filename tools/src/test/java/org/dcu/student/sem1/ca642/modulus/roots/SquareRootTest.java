package org.dcu.student.sem1.ca642.modulus.roots;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SquareRootTest {

    @Test
    public void given_SquareRoot_and_composite_prime_and_value_when_resolve_then_expect_solution() {
        final SquareRoot root = SquareRoot.from(3,143);
        assertThat(root.get())
              .isNotNull()
              .hasSize(4)
              .contains(17,61,82,126);
    }

    @Test
    public void given_prime_numbers11_when_resolve_then_expect_correct_root() {
        final SquareRoot squareRoots = SquareRoot.from(37, 11);
        assertThat(squareRoots)
              .isNotNull();
        assertThat(squareRoots.get())
              .isNotNull()
              .contains(2, 9);
    }

    @Test
    public void given_prime_numbers13_when_resolve_then_expect_correct_root() {
        final SquareRoot squareRoots = SquareRoot.from(3, 13);
        assertThat(squareRoots)
              .isNotNull();
        assertThat(squareRoots.get())
              .isNotNull()
              .contains(4, 9);
    }

}