package org.dcu.student.sem1.ca642.factorization;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SmoothNumberTest {

    @Test
    public void given_non_prime_number_when_toPrimeComposedFactors_then_expect_correct_factors() {
        final List<Factor> factors = SmoothNumber.factor(18);
        assertThat(factors)
              .isNotNull()
              .hasSize(2);

        final Factor factor2 = factors.get(0);
        assertThat(factor2.getBase()).isEqualTo(2);
        assertThat(factor2.getExponent()).isEqualTo(1);

        final Factor factor3 = factors.get(1);
        assertThat(factor3.getBase()).isEqualTo(3);
        assertThat(factor3.getExponent()).isEqualTo(2);
    }

    @Test
    public void given_smooth_number_when_getBPowerSmooth_then_expect_32() {
        final int b = SmoothNumber.getBPowerSmoot(9504);
        assertThat(b).isEqualTo(32);
    }

    @Test
    public void given_smooth_number_when_getBSmooth_then_expect_11() {
        final int b = SmoothNumber.getBSmooth(9504);
        assertThat(b).isEqualTo(11);
    }

}