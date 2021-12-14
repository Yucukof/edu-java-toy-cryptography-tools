package org.dcu.student.sem1.ca642.modulus;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.dcu.student.sem1.ca642.modulus.exponentiation.ChineseRemainder;
import org.dcu.student.sem1.ca642.modulus.exponentiation.Exponentiation;
import org.dcu.student.sem1.ca642.modulus.fraction.Fraction;
import org.dcu.student.sem1.ca642.modulus.inverse.Inverse;
import org.dcu.student.sem1.ca642.modulus.product.Product;
import org.dcu.student.sem1.ca642.modulus.roots.SquareRoot;
import org.dcu.student.sem1.ca642.modulus.symbols.Symbol;
import org.dcu.student.sem1.ca642.modulus.symbols.SymbolValue;

import static org.dcu.student.sem1.ca642.primes.EulerTotient.phi;

@Slf4j
@Value
public class Modulus {

    int value;
    int modulus;

    public static Modulus from(final int value, final int modulus) {
        int simpleValue = value % modulus;
        if (simpleValue < value) {
            log.info("{} == {} (mod {})", value, simpleValue, modulus);
        }
        if (simpleValue < 0) {
            simpleValue = modulus + value;
            log.info("{} == {} (mod {})", simpleValue, value, modulus);
        }
        return new Modulus(simpleValue, modulus);
    }

    public Modulus combine(final Modulus... moduluses) {
        final Modulus[] modulusesWithThis = ArrayUtils.add(moduluses, this);
        return ChineseRemainder.combine(modulusesWithThis);
    }

    public int divide(final int factor) {
        return Fraction.resolve(value, factor, modulus);
    }

    public Exponentiation getExponentiation(final int exponent) {
        return Exponentiation.from(value, exponent, modulus);
    }

    public Fraction getFraction(final int divisor) {
        return Fraction.from(value, divisor, modulus);
    }

    public Inverse getInverse() {
        return Inverse.from(value, modulus);
    }

    public int getPhi() {
        return phi(modulus);
    }

    public Product getProduct(final int... factors) {
        final int[] factorsTimesValue = ArrayUtils.add(factors, value);
        return Product.from(modulus, factorsTimesValue);
    }

    public SymbolValue getSymbolValue(){
        return getSymbol()
              .getValue();
    }

    public Symbol getSymbol(){
        return Symbol.from(value,modulus);
    }

    public SquareRoot getSquareRoot() {
        return SquareRoot
              .from(value, modulus);
    }

    public boolean isPrimitiveRoot() {
        return getOrder(value)
              .isPrimitiveRoot();
    }

    public Order getOrder(final int value) {
        return Order.from(value, modulus);
    }

    public int multiply(final int... factors) {
        final int[] factorsTimesValue = ArrayUtils.add(factors, value);
        return Product.resolve(modulus, factorsTimesValue);
    }

    public int power(final int exponent) {
        return Exponentiation.resolve(value, exponent, modulus);
    }

    @Override
    public String toString() {
        return value + " (mod " + modulus + ")";
    }
}
