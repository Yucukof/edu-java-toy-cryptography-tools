package org.dcu.student.sem1.ca642.modulus.symbols;

import org.dcu.student.sem1.ca642.modulus.symbols.JacobiSymbol;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class JacobiSymbolTest {

    @Test
    public void given_divisor_when_compute_then_expect_0() {
        assertThat(JacobiSymbol.compute(30,15))
              .isZero();
    }

    @Test
    public void given_property_1_when_compute_then_expect_minus_1() {
        assertThat(JacobiSymbol.compute(7,15))
              .isEqualTo(-1);
    }

    @Test
    public void given_property_2_when_compute_then_expect_minus_1() {
        assertThat(JacobiSymbol.compute(6,17))
              .isEqualTo(-1);
    }

    @Test
    public void given_property_3_when_compute_then_expect_0() {
        assertThat(JacobiSymbol.compute(5,15))
              .isZero();
    }

    @Test
    public void given_property_4_when_compute_then_expect_1() {
        assertThat(JacobiSymbol.compute(1,17))
              .isEqualTo(1);
    }

    @Test
    public void given_property_5_when_compute_then_expect_1() {
        assertThat(JacobiSymbol.compute(16,17))
              .isEqualTo(1);
    }

    @Test
    public void given_property_6_when_compute_then_expect_1() {
        assertThat(JacobiSymbol.compute(2,17))
              .isEqualTo(1);
    }

    @Test
    public void given_property_3_and_property1_when_compute_then_expect_0() {
        assertThat(JacobiSymbol.compute(13,17))
              .isOne();
    }
}