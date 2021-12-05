package org.dcu.student.sem1.ca642.factorization;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.dcu.student.sem1.ca642.utils.MathUtils;

import java.util.List;

@Value
@EqualsAndHashCode
public class Factor {

    int base;
    int exponent;

    public Factor(final int base) {
        this.base = base;
        this.exponent = 1;
    }

    public Factor(final int base, final int exponent) {
        this.base = base;
        this.exponent = exponent;
    }


    static int productOf(final List<Integer> factors) {
        return factors.stream()
              .reduce((a, b) -> a * b)
              .orElse(0);
    }

    public int getValue() {
        return MathUtils.power(base, exponent);
    }

    @Override
    public String toString() {
        return String.format("%s^%s", base, exponent);
    }
}
