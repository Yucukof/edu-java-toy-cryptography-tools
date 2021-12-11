package org.dcu.student.sem1.ca642.modulus;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.dcu.student.sem1.ca642.modulus.exponentiation.Exponentiation;
import org.dcu.student.sem1.ca642.modulus.fraction.Fraction;
import org.dcu.student.sem1.ca642.modulus.inverse.Inverse;
import org.dcu.student.sem1.ca642.modulus.roots.SquareRoot;

import java.util.Set;

@Value
@AllArgsConstructor
public class Modulus {

    int value;
    int modulus;

    public int getExponentiation(final int exponent) {
        return Exponentiation.compute(value, exponent, modulus);
    }

    public int getFraction(final int divisor) {
        return Fraction.compute(value, divisor, modulus);
    }

    final int getInverse() {
        return Inverse.compute(value, modulus);
    }

    public Set<Integer> getSquareRoot() {
        return SquareRoot.compute(value, modulus);
    }
}
