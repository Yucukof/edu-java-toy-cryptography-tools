package org.dcu.student.sem1.ca642.modulus.symbols;

import static org.dcu.student.sem1.ca642.primes.naive.BruteForce.isPrime;

public interface SymbolValue {

    default boolean isQuadraticResidue(final int dividend, final int divisor) {
        return evaluate(dividend, divisor).isQuadraticResidue();
    }

    boolean isQuadraticResidue();

    static SymbolValue evaluate(final int dividend, final int divisor) {
        return isPrime(divisor)
               ? LegendreSymbol.resolve(dividend, divisor)
               : JacobiSymbol.resolve(dividend, divisor);
    }

}
