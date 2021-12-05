package org.dcu.student.sem1.ca642.modulus;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExtendedEuclideanTest {

    @Test
    public void given_valid_xy_when_resolved_then_expect__correct_inverse() {
        final int x = 23;
        final int y = 10;

        final int inverse = ExtendedEuclidean.inverse(y, x);
        assertThat(inverse)
              .isEqualTo(7);
    }

    @Test
    public void given_valid_yx_when_resolved_then_expect_correct_inverse() {
        final int x = 10;
        final int y = 23;

        final int inverse = ExtendedEuclidean.inverse(y, x);
        assertThat(inverse)
              .isEqualTo(-3);
    }
}