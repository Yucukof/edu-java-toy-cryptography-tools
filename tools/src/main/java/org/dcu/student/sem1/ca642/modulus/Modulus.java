package org.dcu.student.sem1.ca642.modulus;

import lombok.Value;
import org.dcu.student.sem1.ca642.modulus.exponentiation.Exponentiation;
import org.dcu.student.sem1.ca642.modulus.fraction.Fraction;
import org.dcu.student.sem1.ca642.modulus.inverse.Inverse;
import org.dcu.student.sem1.ca642.modulus.roots.SquareRoot;

import java.util.Set;

@Value
public class Modulus {

    int value;
    int modulus;

    public static Modulus from(final int value, final int modulus) {
        return new Modulus(value, modulus);
    }

    public int getExponentiation(final int exponent) {
        return Exponentiation.compute(value, exponent, modulus);
    }

    public int getFraction(final int divisor) {
        return Fraction.compute(value, divisor, modulus);
    }

    public int getInverse() {
        return Inverse.compute(value, modulus);
    }

    public Set<Integer> getSquareRoot() {
        return SquareRoot.compute(value, modulus);
    }
}
