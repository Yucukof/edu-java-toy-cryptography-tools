package org.dcu.student.sem1.ca642.hashing;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dcu.student.sem1.ca642.hashing.Functions.averageCollision;

public class FunctionsTest {

    @Test
    public void given_size_only_when_averageCollision_then_expect_correct_exponent() {
        assertThat(averageCollision(128))
              .isEqualTo(Math.pow(2, 64));
    }

    @Test
    public void given_length_and_size_when_averageCollision_then_expect_correct_exponent() {
        assertThat(averageCollision(1024,128))
              .isEqualTo(Math.pow(2, 896));
    }

}