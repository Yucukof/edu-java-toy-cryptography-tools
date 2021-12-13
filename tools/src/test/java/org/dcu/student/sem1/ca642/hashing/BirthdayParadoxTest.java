package org.dcu.student.sem1.ca642.hashing;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dcu.student.sem1.ca642.hashing.BirthdayParadox.*;

public class BirthdayParadoxTest {

    @Test
    public void given_length_and_size_when_averageCollision_then_expect_correct_exponent() {
        assertThat(averageCollision(1024, 128))
              .isEqualTo(Math.pow(2, 896));
    }

    @Test
    public void given_n_and_0k_when_compute_then_expect_correct_result() {
        final float probability = compute(1, 2);
        assertThat(probability)
              .isOne();
    }

    @Test
    public void given_n_and_k_when_compute_then_expect_correct_result() {
        final float probability = compute(10, 8);
        assertThat(probability)
              .isEqualTo(0.981856f);
    }

    @Test
    public void given_n_and_probability_when_approximateThreshold_then_expect_correct_result() {
        final int threshold = approximateThreshold(9);
        assertThat(threshold)
              .isEqualTo(3);
    }

    @Test
    public void given_n_and_probability_when_threshold_then_expect_correct_result() {
        final int threshold = threshold(9);
        assertThat(threshold)
              .isEqualTo(3);
    }
}