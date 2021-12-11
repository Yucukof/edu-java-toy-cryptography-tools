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

    public static int compute(final int value, final int exponent, final int modulus) {
        return from(value, exponent, modulus)
              .resolve();
    }

    public int resolve() {
        log.info("Resolving {}^{} (mod {})", base, exponent, modulus);
        return hasNegativeExponent()
              ? resolveNegativeExponent()
              : resolvePositiveExponent();
    }

    public static Exponentiation from(final int base, final int exponent, final int modulus) {
        return builder()
              .base(base)
              .exponent(exponent)
              .modulus(modulus)
              .build();
    }

    public boolean hasNegativeExponent() {
        return exponent < 0;
    }

    private int resolveNegativeExponent() {
        final int inverse = resolve(base, -exponent, modulus);
        return positiveInverse(inverse, modulus);
    }

    private int resolvePositiveExponent() {
        return resolve(base, exponent, modulus);
    }

    private static int resolve(final int base, final int exponent, final int modulus) {
        return isPrime(modulus)
              ? SquareAndMultiply.power(base, exponent, modulus)
              : ChineseRemainder.power(base, exponent, modulus);
    }

    public boolean hasPrimeModulus() {
        return isPrime(modulus);
    }

    @Override
    public String toString() {
        return base + "^" + exponent + " (mod " + modulus + ")";
    }
}
