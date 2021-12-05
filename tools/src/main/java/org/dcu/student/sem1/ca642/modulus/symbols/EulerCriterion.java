package org.dcu.student.sem1.ca642.modulus.symbols;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import static org.dcu.student.sem1.ca642.modulus.exponentiation.SquareAndMultiply.squareAndMultiply;
import static org.dcu.student.sem1.ca642.primes.naive.BruteForce.isPrime;

@Slf4j
public enum EulerCriterion {

    QUADRATIC_RESIDUE(1),
    QUADRATIC_NON_RESIDUE(-1);

    private final int value;

    EulerCriterion(final int value) {
        this.value = value;
    }

    public static EulerCriterion resolve(final int a, final int p) {
        return resolve(compute(a, p));

    }

    public static EulerCriterion resolve(final int value) {
        return Arrays.stream(EulerCriterion.values())
              .filter(val -> val.matches(value))
              .findAny()
              .orElse(QUADRATIC_NON_RESIDUE);
    }

    public static int compute(final int a, final int p) {
        log.info("Calculating Euler's Criterion ({}/{})...", a, p);

        if (!isPrime(p)) {
            throw new IllegalArgumentException(String.format("Prime p=%s is not a valid prime!", p));
        }
        if (p == 2) {
            throw new IllegalArgumentException("Prime is the number 2");
        }

        log.debug("(p-1)/2");
        final int exponent = (p - 1) / 2;

        log.debug("(a^{(p-1)/2} (mod p)");
        final int result = squareAndMultiply(a, exponent, p);

        log.debug("({}^{({}-1)/2} (mod {})={}", a, p, p, result);

        log.info("Result = [{}]", result);
        return result;
    }

    private boolean matches(final int value) {
        return this.value == value;
    }
}
