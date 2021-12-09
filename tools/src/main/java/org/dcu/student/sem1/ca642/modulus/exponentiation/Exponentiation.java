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

    public boolean hasPrimeModulus() {
        return isPrime(modulus);
    }

    public int resolve() {
        log.info("Resolving {}^{} (mod {})", base, exponent, modulus);
        return hasNegativeExponent()
              ? resolveNegativeExponent()
              : resolvePositiveExponent();
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

}
