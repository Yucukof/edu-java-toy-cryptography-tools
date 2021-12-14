package org.dcu.student.sem1.ca642.modulus.symbols;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SymbolTest {

    @Test
    public void given_prime_modulus_and_root_when_isSquareRoot_then_expect_true(){
        final int a = 47;
        final int p = 127;

        final Symbol symbol = new Symbol(a,p);

        final SymbolValue value = symbol.getValue();
        assertThat(value)
              .isInstanceOf(LegendreSymbol.class)
              .isEqualTo(LegendreSymbol.QUADRATIC_RESIDUE);

        assertThat(symbol.isQuadraticResidue())
              .isTrue();
    }

    @Test
    public void given_prime_modulus_and_non_root_when_isSquareRoot_then_expect_false(){
        final int a = 10;
        final int p = 11;

        final Symbol symbol = new Symbol(a,p);

        final SymbolValue value = symbol.getValue();
        assertThat(value)
              .isInstanceOf(LegendreSymbol.class)
              .isEqualTo(LegendreSymbol.QUADRATIC_NON_RESIDUE);

        assertThat(symbol.isQuadraticResidue())
              .isFalse();
    }

    @Test
    public void given_non_prime_modulus_and_root_when_isSquareRoot_then_expect_true(){
        final int a = 67;
        final int p = 187;

        final Symbol symbol = new Symbol(a,p);

        final SymbolValue value = symbol.getValue();
        assertThat(value)
              .isInstanceOf(JacobiSymbol.class)
              .isEqualTo(JacobiSymbol.POTENTIAL_RESIDUE);

        assertThat(symbol.isQuadraticResidue())
              .isTrue();
    }

    @Test
    public void given_non_prime_modulus_and_non_root_when_isSquareRoot_then_expect_false(){
        final int a = 3;
        final int p = 187;

        final Symbol symbol = new Symbol(a,p);

        final SymbolValue value = symbol.getValue();
        assertThat(value)
              .isInstanceOf(JacobiSymbol.class)
              .isEqualTo(JacobiSymbol.CONFIRMED_NON_RESIDUE);

        assertThat(symbol.isQuadraticResidue())
              .isFalse();
    }

}