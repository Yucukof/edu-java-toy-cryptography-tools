package org.dcu.student.sem1.ca642.modulus;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderTest {

    @Test
    public void given_modulus_17_and_base_2_when_isPrimitiveRoot_then_expect_false() {
        assertThat(PrimitiveRoots.isPrimitiveRoot(2, 17))
              .isFalse();
    }

    @Test
    public void given_ord_17_base_2_when_generate_then_expect_correct_part() {
        assertThat(PrimitiveRoots.generate(2, 17))
              .isNotNull()
              .hasSize(8)
              .contains(1, 2, 4, 8, 9, 13, 15, 16);
    }

    @Test
    public void given_ord_17_base_2_when_get_then_expect_correct_order() {
        assertThat(PrimitiveRoots.order(2, 17))
              .isEqualTo(8);
    }

    @Test
    public void given_prime_17_and_base_3_when_isPrimitiveRoot_then_expect_true() {
        assertThat(PrimitiveRoots.isPrimitiveRoot(3, 17))
              .isTrue();
    }

    @Test
    public void given_prime_17_when_getPrimitiveRoot_then_expect_correct_roots() {
        final List<Integer> primitiveRoots = PrimitiveRoots.getPrimitiveRoots(17);
        assertThat(primitiveRoots)
              .isNotNull()
              .hasSize(10)
              .contains(3, 5, 6, 7, 10, 11, 12, 14, 15, 16);
    }

    @Test
    public void given_prime_17_when_hasPrimitiveRoot_then_expect_true() {
        assertThat(PrimitiveRoots.hasPrimitiveRoots(17))
              .isTrue();
    }

    @Test
    public void given_prime_17_when_primitiveRootsCount_then_expect_8() {
        assertThat(PrimitiveRoots.primitiveRootsCount(17))
              .isEqualTo(8);
    }

}