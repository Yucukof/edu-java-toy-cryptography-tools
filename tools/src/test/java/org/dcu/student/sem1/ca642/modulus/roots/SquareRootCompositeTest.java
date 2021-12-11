package org.dcu.student.sem1.ca642.modulus.roots;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SquareRootCompositeTest {

    @Test
    public void given_composite_prime_and_value_when_resolve_then_expect_solution() {
        final SquareRootComposite root = new SquareRootComposite(3,143);
        assertThat(root.get())
              .isNotNull()
              .hasSize(4)
              .contains(17,61,82,126);
    }

}