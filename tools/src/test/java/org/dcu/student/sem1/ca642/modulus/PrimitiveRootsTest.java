package org.dcu.student.sem1.ca642.modulus;

import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dcu.student.sem1.ca642.modulus.PrimitiveRoots.*;

public class PrimitiveRootsTest {

    @Test
    public void given_modulus_17_and_base_2_when_isPrimitiveRoot_then_expect_false() {
        assertThat(isPrimitiveRoot(2, 19))
              .isTrue();
    }

    @Test
    public void given_prime_17_and_base_3_when_isPrimitiveRoot_then_expect_true() {
        assertThat(isPrimitiveRoot(3, 17))
              .isTrue();
    }

    @Test
    public void given_prime_17_when_getAnyPrimitiveRoot_then_expect_primitiveRoot() {

        final List<Integer> primitiveRoots = getPrimitiveRoots(17);
        final Optional<Integer> anyPrimitiveRoot = getAnyPrimitiveRoot(17);
        assertThat(anyPrimitiveRoot)
              .isPresent();
        assertThat(primitiveRoots)
              .isNotNull()
              .contains(anyPrimitiveRoot.get());
    }

    @Test
    public void given_prime_17_when_getPrimitiveRoot_then_expect_correct_roots() {
        final List<Integer> primitiveRoots = getPrimitiveRoots(17);
        assertThat(primitiveRoots)
              .isNotNull()
              .hasSize(8)
              .contains(3, 5, 6, 7, 10, 11, 12, 14);
    }

    @Test
    public void given_prime_17_when_hasPrimitiveRoot_then_expect_true() {
        assertThat(hasPrimitiveRoots(17))
              .isTrue();
    }

    @Test
    public void given_prime_17_when_primitiveRootsCount_then_expect_8() {
        assertThat(primitiveRootsCount(17))
              .isEqualTo(8);
    }

}