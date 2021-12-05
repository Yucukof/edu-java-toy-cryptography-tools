package org.dcu.student.sem1.ca642.primes;

import org.dcu.student.sem1.ca642.utils.DecimalToBaseConverter;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DecimalToBaseConverterTest {

    @Test
    public void given_10_when_factorize_then_expect_correct_values() {
        final int n = 10;
        final int[] factors = DecimalToBaseConverter.exponentsBase2(n);
        assertThat(factors)
              .isNotNull()
              .isNotEmpty()
              .hasSize(4)
              .containsExactly(0, 1, 0, 1);
    }

    @Test
    public void given_17_when_factorize_then_expect_correct_values() {
        final int n = 17;
        final int[] factors = DecimalToBaseConverter.exponentsBase2(n);
        assertThat(factors)
              .isNotNull()
              .isNotEmpty()
              .hasSize(5)
              .containsExactly(1, 0, 0, 0, 1);
    }

}