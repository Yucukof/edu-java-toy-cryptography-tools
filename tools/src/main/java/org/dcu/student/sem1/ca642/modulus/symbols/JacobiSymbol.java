package org.dcu.student.sem1.ca642.modulus.symbols;

import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.utils.MathUtils;

import java.util.Arrays;
import java.util.List;

import static org.dcu.student.sem1.ca642.factorization.Naive.toPrimeFactors;
import static org.dcu.student.sem1.ca642.primes.naive.BruteForce.isPrime;

@Slf4j
public enum JacobiSymbol {
    CONFIRMED_NON_RESIDUE(-1),
    POTENTIAL_RESIDUE(1),
    DIVISOR(0);

    private final int value;

    JacobiSymbol(final int value) {
        this.value = value;
    }

    public static JacobiSymbol resolve(final int a, final int p) {
        final int symbol = compute(a, p);
        return resolve(symbol);
    }

    public static int compute(int a, final int n) {
        log.info("Computing Jacobi's Symbol ({}/{})", a, n);

        if (a == 0) {
            return isDivisor(a, n);
        }
        if (a == 1) {
            return property4(a, n);
        }
        if (a == 2) {
            return property6(a, n);
        }
        if (a == n - 1) {
            return property5(a, n);
        }
        if (a < 0 || a >= n) {
            return property1(a, n);
        }

        log.debug("2 < {} < p-1", a);
        if (!isPrime(a)) {
            return property2(a, n);
        }
        if (!isPrime(n)) {
            return property3(a, n);
        }
        return property7(a, n);
    }

    /**
     * Compute the value of the Jacobi's Symbol by observing "a" is a perfect dividend of "p", that is "a" is a
     * multiple of p.
     *
     * @param a a multiple of p now 0.
     * @param p a prime number p.
     * @return 0, the value of the Legendre's Symbol to denote a multiple, that is 0.
     * @see #DIVISOR
     */
    private static int isDivisor(final int a, final int p) {
        log.debug("{}|{}", a, p);
        final int symbol = 0;
        log.info("Jacobi's Symbol = [{}]", symbol);
        return symbol;
    }

    /**
     * Compute the value of the Jacobi's Symbol according to the property 1:
     * $a\equiv b\pmod{n}\implies \left(\frac{a}{n}\right)=\left(\frac{b}{n}\right)$
     *
     * @param a the value a.
     * @param n a number n.
     * @return the value of the Jacobi's Symbol with (b = a%p) == a (mod p).
     */
    private static int property1(final int a, final int n) {
        log.debug("> property 1");
        final int a_p = a % n;
        log.debug("{} == {} (mod {})", a, a_p, n);
        return compute(a_p, n);
    }

    /**
     * Compute the value of the Jacobi's Symbol according to the property 2:
     * $\left(\frac{ab}{n}\right)=\left(\frac{a}{n}\right)\times\left(\frac{b}{n}\right)$
     *
     * @param a a non-prime number to be decomposed into factors.
     * @param n a number n.
     * @return the product of the Jacobi's Symbols of each factor as 1 x (f1 / p) x (f2 / p) x (f... / p).
     */
    private static Integer property2(final int a, final int n) {
        log.debug("> property 2");
        log.info("{} is not a prime", a);
        log.debug("> Decomposing {} into factors...", a);
        final List<Integer> factors = toPrimeFactors(a);
        final Integer symbol = factors.stream()
              .map(factor -> compute(factor, n))
              .reduce((s1, s2) -> s1 * s2)
              .orElse(1);

        log.info("Jacobi's Symbol = [{}]", symbol);
        return symbol;
    }

    /**
     * Compute the value of the Jacobi's Symbol according to the property 3:
     * $\left(\frac{a}{mn}\right)=\left(\frac{a}{mn}\right)\times \left(\frac{a}{n}\right)$
     *
     * @param a the value a.
     * @param n a number n to be decomposed into prime factors.
     * @return the value of the Jacobi's Symbol following property 3, that is 1.
     * @see #POTENTIAL_RESIDUE
     */
    private static int property3(final int a, final int n) {
        log.debug("> property 3");
        log.info("{} is not a prime", n);
        log.debug("> Decomposing {} into factors...", n);
        final List<Integer> factors = toPrimeFactors(n);
        final Integer symbol = factors.stream()
              .map(factor -> compute(a, factor))
              .reduce((s1, s2) -> s1 * s2)
              .orElse(1);

        log.info("Jacobi's Symbol = [{}]", symbol);
        return symbol;
    }

    /**
     * Compute the value of the Jacobi's Symbol according to the property 4:
     * $\left(\frac{1}{n}\right)=1$
     *
     * @param a the value 1, always.
     * @param n a number n.
     * @return the value of the Jacobi's Symbol following property 4, that is 1.
     * @see #POTENTIAL_RESIDUE
     */
    private static int property4(final int a, final int n) {
        log.debug("> property 4");
        log.debug("({} / {}) = {}", a, n, a);

        log.info("Jacobi's Symbol = [{}]", a);
        return a;
    }

    /**
     * Compute the value of the Jacobi's Symbol according to the property 5:
     * $\left(\frac{-1}{n}\right) = (-1)^{\frac{n-1}{2}}$
     *
     * @param a the value -1, always.
     * @param n a number n.
     * @return 1 if n == 1 (mod 2), -1 otherwise.
     */
    private static int property5(final int a, final int n) {
        log.debug("> property 5");

        log.debug("(n - 1) / 2");
        final int exponent = (n - 1) / 2;
        log.debug("({} - 1) / 2 = {}", n, exponent);
        final int symbol = MathUtils.power(-1, exponent);
        log.debug("(-1)^{}={}", exponent, symbol);

        log.info("Jacobi's Symbol = [{}]", symbol);
        return symbol;
    }

    /**
     * Compute the value of the Jacobi's Symbol according to the property 6:
     * $\left(\frac{2}{n}\right)=(-1)^{\frac{n^2-1}{8}}$
     *
     * @param a the value 2, always.
     * @param n a number n.
     * @return 1 if n == 1 (mod 8) or n == -1 (mod 8) (that is, n² == 1 (mod 8), 1 otherwise.
     */
    private static int property6(final int a, final int n) {
        log.debug("> property 6");
        final int n_8 = n % 8;
        log.debug("{} (mod 8) = {}", n, n_8);
        final int symbol = (n_8 == 1 || n_8 == 7) ? 1 : -1;
        log.debug("{} -> {}", n_8, symbol);

        log.info("Jacobi's Symbol = [{}]", symbol);
        return symbol;
    }

    /**
     * Computes the value of the Jacobi's Symbol according to the property 7:
     * $\left(\frac{m}{n}\right)=(-1)^{\frac{m-1}{2}\frac{n-1}{2}}\times\left(\frac{n}{m}\right)$
     *
     * @param a a prime number a.
     * @param n a prime number n.
     * @return todo understand pattern
     */
    private static int property7(final int a, final int n) {
        log.debug("> property 7");
        log.info("{} is a prime", a);
        log.debug("> Inverting fraction");
        final boolean expAEven = ((a - 1) / 2) % 2 == 0;
        final boolean expNEven = ((n - 1) / 2) % 2 == 0;

        // TODO: 27/11/2021 review

        if (expAEven || expNEven) {
            return compute(n, a);
        } else {
            return -compute(n, a);
        }
    }

    public static JacobiSymbol resolve(final int value) {
        return Arrays.stream(JacobiSymbol.values())
              .filter(val -> val.matches(value))
              .findAny()
              .orElseThrow(RuntimeException::new);
    }

    private boolean matches(final int value) {
        return this.value == value;
    }
}
