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
        log.info("Resolving Euler's Criterion ({}/{})...", a, p);

        final int value = compute(a, p);
        log.debug("Euler's Criterion value = [{}]", value);
        final EulerCriterion symbol = resolve(value);
        log.info("Euler's Criterion = [{}]\n",symbol);
        return symbol;

    }

    public static EulerCriterion resolve(final int value) {
        return Arrays.stream(EulerCriterion.values())
              .filter(val -> val.matches(value))
              .findAny()
              .orElse(QUADRATIC_NON_RESIDUE);
    }

    private static int compute(final int a, final int p) {

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

        return result;
    }

    private boolean matches(final int value) {
        return this.value == value;
    }
}
