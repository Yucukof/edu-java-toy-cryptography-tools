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

    public static Fraction from(final int dividend, final int divisor, final int modulus) {
        return builder()
              .dividend(dividend)
              .divisor(divisor)
              .modulus(modulus)
              .build();
    }

    public int resolve() {
        return resolve(dividend, divisor, modulus);
    }

    public static int resolve(final int dividend, final int divisor, final int modulus) {

        log.info("Computing {}/{} (mod {})", dividend, divisor, modulus);

        // Simplification
        final int simpleDividend = getSimpleDividend(dividend, modulus);
        final int simpleDivisor = getSimpleDivisor(divisor, modulus);

        // Calculus
        return compute(simpleDividend, simpleDivisor, modulus);
    }

    private static int getSimpleDividend(final int dividend, final int modulus) {
        final int simpleDividend = dividend % modulus;
        if (dividend >= modulus) {
            log.debug("Simplifying dividend {} == {} (mod {})...", dividend, simpleDividend, modulus);
        }
        return simpleDividend;
    }

    private static int getSimpleDivisor(final int divisor, final int modulus) {
        final int simpleDivisor = divisor % modulus;
        if (divisor >= modulus) {
            log.debug("Simplifying divisor {} == {} (mod {})", divisor, simpleDivisor, modulus);
        }
        return simpleDivisor;
    }

    private static int compute(final int dividend, final int divisor, final int modulus) {
        final int inverse = positiveInverse(divisor, modulus);
        log.debug("{}⁻¹ (mod {}) = {}", divisor, modulus, inverse);
        final int product = dividend * inverse;
        log.debug("{} x {} = {}", dividend, inverse, product);
        final int result = product % modulus;
        log.debug("{} == {} (mod {})", product, result, modulus);

        log.debug("{} x {}⁻¹ (mod {}) = [{}]", dividend, divisor, modulus, result);
        return result;
    }
}
