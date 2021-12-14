package org.dcu.student.sem1.ca642.modulus.symbols;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

import static org.dcu.student.sem1.ca642.factorization.Naive.toPrimeFactors;
import static org.dcu.student.sem1.ca642.primes.naive.BruteForce.isPrime;

/**
 * Credits to <a href="https://martin-thoma.com/how-to-calculate-the-legendre-symbol/">Martin Thomas</a> for the
 * original pseudo-code and Python code that served to the elaboration of this class.
 */
@Slf4j
public enum LegendreSymbol implements SymbolValue {

    QUADRATIC_RESIDUE(1),
    QUADRATIC_NON_RESIDUE(-1),
    DIVISOR(0);

    private final int value;

    LegendreSymbol(final int value) {
        this.value = value;
    }

    public static LegendreSymbol resolve(final int a, final int p) {
        log.info("Resolving Legendre's Symbol ({}/{})...", a, p);

        final int value = compute(a, p);
        log.debug("Legendre's value = {}", value);

        final LegendreSymbol symbol = resolve(value);
        log.info("Legendre's Symbol = [{}]\n", symbol.name());

        return symbol;
    }

    private static int compute(int a, final int p) {
        log.debug("Computing {}/{}", a, p);
        if (a == 0) {
            return isDivisor(a, p);
        }
        if (a == 1) {
            return property3(a, p);
        }
        if (a == 2) {
            return property5(a, p);
        }
        if (a == p - 1) {
            return property4(a, p);
        }
        if (a < 0 || a >= p) {
            return property1(a, p);
        }

        log.debug("2 < {} < p-1", a);
        if (!isPrime(a)) {
            return property2(a, p);
        } else {
            return property6(a, p);
        }
    }

    /**
     * Compute the value of the Legendre's Symbol by observing "a" is a perfect dividend of "p", that is "a" is a
     * multiple of p.
     *
     * @param a a multiple of p.
     * @param p a prime number p.
     * @return 0, the value of the Legendre's Symbol to denote a multiple, that is 0.
     * @see #DIVISOR
     */
    private static int isDivisor(final int a, final int p) {
        log.debug("{}|{}", a, p);
        return 0;
    }

    /**
     * Compute the value of the Legendre's Symbol according to the property 1:
     * $a\equiv b\pmod{p}\implies \left(\frac{a}{p}\right)=\left(\frac{b}{p}\right)$
     *
     * @param a the value a.
     * @param p a prime number p.
     * @return the value of the Legendre's Symbol with (b = a%p) == a (mod p).
     */
    private static int property1(final int a, final int p) {
        log.debug("> property 1");
        log.debug("Simplifying dividend...");
        final int a_p = a % p;
        log.debug("{} == {} (mod {})", a, a_p, p);
        return compute(a_p, p);
    }

    /**
     * Compute the value of the Legendre's Symbol according to the property 2:
     * $\left(\frac{ab}{p}\right)=\left(\frac{a}{p}\right)\times\left(\frac{b}{p}\right)$
     *
     * @param a a non-prime number to be decomposed into factors.
     * @param p a prime number p.
     * @return the product of the Legendre's Symbols of each factor as 1 x (f1 / p) x (f2 / p) x (f... / p).
     */
    private static Integer property2(final int a, final int p) {
        log.debug("> property 2");
        log.debug("{} is not a prime", a);
        log.debug("Simplifying dividend...");

        log.debug("> Decomposing {} into factors...", a);
        final List<Integer> factors = toPrimeFactors(a);
        log.debug("Factors of a = {}", factors);

        final int symbol = factors.stream()
              .map(factor -> compute(factor, p))
              .reduce((s1, s2) -> s1 * s2)
              .orElse(1);

        log.debug("({} / {}) = {}", a, p, symbol);
        return symbol;
    }

    /**
     * Compute the value of the Legendre's Symbol according to the property 3:
     * $\left(\frac{1}{p}\right)=1$
     *
     * @param a the value 1, always.
     * @param p a prime number p.
     * @return the value of the Legendre's Symbol following property 3, that is 1.
     * @see #QUADRATIC_RESIDUE
     */
    private static int property3(final int a, final int p) {
        log.debug("> property 3");
        log.debug("({} / {}) = {}", a, p, a);
        return a;
    }

    /**
     * Compute the value of the Legendre's Symbol according to the property 4:
     * $\left(\frac{-1}{p}\right)
     * =\left\{\begin{array}{rll}1 & \text{if }p\equiv 1\pmod{4}\\-1 & \text{if }p\equiv 3\pmod{4}\end{array}\right.$
     *
     * @param a the value p-1, always.
     * @param p a prime number p.
     * @return 1 if p == 1 (mod 4), -1 if p == 3 (mod 4)
     */
    private static int property4(final int a, final int p) {
        log.debug("> property 4");
        log.debug("{} == {}-1 ( mod {})", a, p, p);
        log.debug("Checking if {} (mod 4) ∈ {1}", p);
        final int p_4 = p % 4;
        log.debug("{} (mod 4) = {}", p, p_4);
        return (p_4 == 1) ? 1 : -1;
    }

    /**
     * Compute the value of the Legendre's Symbol according to the property 5:
     * $\left(\frac{2}{p}\right)=(-1)^{(p^2-1)/8}$
     *
     * @param a the value 2, always.
     * @param p a number p.
     * @return 1 if p == 1 (mod 8) or p == -1 (mod 8) (that is, p² == 1 (mod 8), -1 otherwise.
     */
    private static int property5(final int a, final int p) {
        log.debug("> property 5");
        log.debug("Checking if {} (mod 8) ∈ {1,{}}", p, p - 1);
        final int p_8 = p % 8;
        log.debug("{} (mod 8) = {}", p, p_8);
        return (p_8 == 1 || p_8 == 7) ? 1 : -1;
    }

    /**
     * Computes the value of the Legendre's Symbol according to the property 6:
     * $\left(\frac{p}{q}\right)=(-1)^{\frac{p-1}{2}\frac{q-1}{2}}\times\left(\frac{q}{p}\right)$
     *
     * @param a a prime number a.
     * @param p a prime number p.
     * @return todo understand pattern
     */
    private static int property6(final int a, final int p) {
        log.debug("> property 6");
        log.debug("{} and {} are primes", a, p);
        log.debug("> Inverting fraction");
        final boolean expAEven = ((a - 1) / 2) % 2 == 0;
        final boolean expPEven = ((p - 1) / 2) % 2 == 0;

        // TODO: 27/11/2021 review

        if (expAEven || expPEven) {
            return compute(p, a);
        } else {
            return -compute(p, a);
        }
    }

    public static LegendreSymbol resolve(final int value) {
        return Arrays.stream(LegendreSymbol.values())
              .filter(val -> val.matches(value))
              .findAny()
              .orElseThrow(RuntimeException::new);
    }

    @Override
    public boolean isQuadraticResidue() {
        return this == QUADRATIC_RESIDUE;
    }

    private boolean matches(final int value) {
        return this.value == value;
    }
}
