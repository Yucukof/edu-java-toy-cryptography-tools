package org.dcu.student.sem1.ca642.modulus.symbols;

import org.dcu.student.sem1.ca642.modulus.symbols.LegendreSymbol;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LegendreSymbolTest {

    @Test
    public void given_divisor_when_resolve_then_expect_DIVISOR() {
        final int a = 9;
        final int p = 3;

        final LegendreSymbol value = LegendreSymbol.resolve(a, p);
        assertThat(value)
              .isEqualTo(LegendreSymbol.DIVISOR);
    }

    @Test
    public void given_quadratic_non_residue_when_resolve_then_expect_QUADRATIC_NON_RESIDUE() {
        final int a = 10;
        final int p = 11;

        final LegendreSymbol value = LegendreSymbol.resolve(a, p);
        assertThat(value)
              .isEqualTo(LegendreSymbol.QUADRATIC_NON_RESIDUE);
    }

    @Test
    public void given_quadratic_residue_when_resolve_then_expect_QUADRATIC_RESIDUE() {
        final int a = 47;
        final int p = 127;

        final LegendreSymbol value = LegendreSymbol.resolve(a, p);
        assertThat(value)
              .isEqualTo(LegendreSymbol.QUADRATIC_RESIDUE);
    }

}