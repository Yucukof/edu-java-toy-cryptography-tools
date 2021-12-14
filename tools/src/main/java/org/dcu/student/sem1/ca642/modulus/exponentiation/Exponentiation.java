package org.dcu.student.sem1.ca642.modulus.exponentiation;

import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import static org.dcu.student.sem1.ca642.modulus.inverse.ExtendedEuclidean.positiveInverse;
import static org.dcu.student.sem1.ca642.primes.naive.BruteForce.isPrime;

@Slf4j
@Value
@Builder(toBuilder = true)
public class Exponentiation {

    int base;
    int exponent;
    int modulus;

    public static int compute(final int base, final int exponent, final int modulus) {
        return from(base, exponent, modulus)
              .resolve();
    }

    public static Exponentiation from(final int base, final int exponent, final int modulus) {
        return builder()
              .base(base)
              .exponent(exponent)
              .modulus(modulus)
              .build();
    }

    public static int resolve(final int base, final int exponent, final int modulus) {

        if (base == 0) {
            log.info("{}^{} (mod {}) = [0]", base, exponent, modulus);
            return 0;
        }
        if (base == 1 || exponent == 0) {
            log.info("{}^{} (mod {}) = [1]", base, exponent, modulus);
            return 1;
        }
        if (exponent == 1) {
            final int result = base % modulus;
            log.info("{}^{} (mod {}) = [{}]", base, exponent, modulus, result);
            return result;
        }

        final int simpleBase = base % modulus;
        if (simpleBase < base) {
            log.debug("Simplifying base...");
            log.debug("{} == {} (mod {})", base, simpleBase, modulus);
            log.debug("New base = {}", simpleBase);
            return resolve(simpleBase, exponent, modulus);
        }

        if (exponent > modulus || exponent < 0) {
            log.debug("Simplifying exponent...");
            final int simpleExponent = EulerTheorem.apply(exponent, modulus);
            log.debug("New exponent = {}", simpleExponent);
            return resolve(simpleBase, simpleExponent, modulus);
        }

        if (exponent == modulus && isPrime(modulus)) {
            log.debug("Invoking Fermat's Little Theorem...");
            log.debug("{}^{} (mod {}) == {} (mod {})", simpleBase, exponent, modulus, base, modulus);
            return simpleBase;
        }

        return isPrime(modulus)
               ? SquareAndMultiply.compute(base, exponent, modulus)
               : ChineseRemainder.compute(base, exponent, modulus);
    }

    public boolean hasNegativeExponent() {
        return exponent < 0;
    }

    public int resolve() {
        log.info("Resolving {}^{} (mod {})", base, exponent, modulus);
        return hasNegativeExponent()
               ? resolveNegativeExponent()
               : resolvePositiveExponent();
    }

    private int resolveNegativeExponent() {
        final int inverse = resolve(base, -exponent, modulus);
        return positiveInverse(inverse, modulus);
    }

    private int resolvePositiveExponent() {
        return resolve(base, exponent, modulus);
    }

    @Override
    public String toString() {
        return base + "^" + exponent + " (mod " + modulus + ")";
    }
}
