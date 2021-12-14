package org.dcu.student.sem1.ca642.modulus.symbols;

import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.modulus.exponentiation.SquareAndMultiply;

import java.util.Arrays;

import static org.dcu.student.sem1.ca642.primes.naive.BruteForce.isPrime;

@Slf4j
public enum EulerCriterion implements SymbolValue {

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
        log.info("Euler's Criterion = [{}]\n", symbol);
        return symbol;

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
        final int result = SquareAndMultiply.compute(a, exponent, p);

        log.debug("({}^{({}-1)/2} (mod {})={}", a, p, p, result);

        return result;
    }

    public static EulerCriterion resolve(final int value) {
        return Arrays.stream(EulerCriterion.values())
              .filter(val -> val.matches(value))
              .findAny()
              .orElse(QUADRATIC_NON_RESIDUE);
    }

    private boolean matches(final int value) {
        return this.value == value;
    }

    @Override
    public boolean isQuadraticResidue() {
        return this == QUADRATIC_RESIDUE;
    }
}
