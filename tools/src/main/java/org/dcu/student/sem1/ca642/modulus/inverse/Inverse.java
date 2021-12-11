package org.dcu.student.sem1.ca642.modulus.inverse;

import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import static org.dcu.student.sem1.ca642.modulus.inverse.ExtendedEuclidean.positiveInverse;

@Slf4j
@Value
@Builder(toBuilder = true)
public class Inverse {

    int value;
    int modulus;

    public static int compute(final int value, final int modulus) {
        return from(value, modulus)
              .resolve();
    }

    public int resolve() {
        return positiveInverse(value, modulus);
    }

    public static Inverse from(final int value, final int modulus) {
        return builder()
              .value(value)
              .modulus(modulus)
              .build();
    }

    @Override
    public String toString() {
        return value + "⁻¹ (mod " + modulus + ")";
    }
}
