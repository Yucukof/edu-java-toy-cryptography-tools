package org.dcu.student.sem1.ca642.modulus.fraction;

import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import static org.dcu.student.sem1.ca642.modulus.inverse.ExtendedEuclidean.positiveInverse;

@Slf4j
@Value
@Builder(toBuilder = true)
public class Fraction {

    int dividend;
    int divisor;
    int modulus;

    public int resolve() {
        return compute(dividend, divisor, modulus);
    }

    public static int compute(final int dividend, final int divisor, final int modulus) {

        log.info("Computing {}/{} (mod {})", dividend, divisor, modulus);
        final int inverse = positiveInverse(divisor, modulus);
        log.debug("{}⁻¹ (mod {}) = {}", divisor, modulus, inverse);
        final int product = dividend * inverse;
        log.debug("{} x {}⁻¹ = {}", dividend, divisor, product);
        final int result = product % modulus;
        log.debug("{} x {}⁻¹ (mod {}) = [{}]", dividend, divisor, modulus, result);

        log.info("Result = [{}]", result);
        return result;
    }
}
