package org.dcu.student.sem1.ca642.modulus.roots;


import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dcu.student.sem1.ca642.modulus.roots.SquareRoots.get;
import static org.dcu.student.sem1.ca642.modulus.roots.SquareRoots.sqrt;

public class SquareRootsTest {

    @Test
    public void given_another_two_prime_numbers_when_getSquareRoot_then_expect_correct_root() {
        final Pair<Integer, Integer> squareRoots = sqrt(3, 13);
        assertThat(squareRoots)
              .isNotNull();
        assertThat(squareRoots.getLeft())
              .isNotNull()
              .isEqualTo(9);
        assertThat(squareRoots.getRight())
              .isNotNull()
              .isEqualTo(4);
    }

    @Test
    public void given_two_prime_numbers_when_getSquareRoot_then_expect_correct_root() {
        final Pair<Integer, Integer> squareRoots = sqrt(37, 11);
        assertThat(squareRoots)
              .isNotNull();
        assertThat(squareRoots.getLeft())
              .isNotNull()
              .isEqualTo(9);
        assertThat(squareRoots.getRight())
              .isNotNull()
              .isEqualTo(2);
    }

    @Test
    public void given_composite_modulus_when_get_then_expect_correct_roots() {
        final List<Integer> squareRoots = get(3, 143);
        assertThat(squareRoots)
              .isNotNull()
              .hasSize(4)
              .contains(17)
              .contains(61)
              .contains(82)
              .contains(126);
    }
}