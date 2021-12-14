package org.dcu.student.sem1.ca642.modulus.symbols;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Value
public class Symbol implements SymbolValue {

    int dividend;
    int divisor;

    public static Symbol from(final int value, final int modulus) {
        return new Symbol(value, modulus);
    }

    @Override
    public boolean isQuadraticResidue() {
        return getValue()
              .isQuadraticResidue();
    }

    public SymbolValue getValue() {
        return SymbolValue.evaluate(dividend, divisor);
    }

    @Override
    public String toString() {
        return "(" + dividend + "/" + divisor + ")";
    }
}
