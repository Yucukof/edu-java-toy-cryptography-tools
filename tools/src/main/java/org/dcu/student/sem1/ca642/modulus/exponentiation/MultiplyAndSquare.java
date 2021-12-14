package org.dcu.student.sem1.ca642.modulus.exponentiation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.dcu.student.sem1.ca642.utils.DecimalToBaseConverter;

import static org.dcu.student.sem1.ca642.primes.naive.BruteForce.isPrime;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MultiplyAndSquare {

    public static int power(final int base, final int exponent, final int modulus) {

        log.info("Computing {}^{} (mod {})", base, exponent, modulus);

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
            return power(simpleBase, exponent, modulus);
        }

        if (exponent > modulus || exponent < 0) {
            log.debug("Simplifying exponent...");
            final int simpleExponent = EulerTheorem.apply(exponent, modulus);
            log.debug("New exponent = {}", simpleExponent);
            return power(simpleBase, simpleExponent, modulus);
        }

        if (exponent == modulus && isPrime(modulus)) {
            log.debug("Invoking Fermat's Little Theorem...");
            log.debug("{}^{} (mod {}) == {} (mod {})", simpleBase, exponent, modulus, base, modulus);
            return simpleBase;
        }

        return multiplyAndSquare(simpleBase, exponent, modulus);
    }

    public static int multiplyAndSquare(final int base, final int exponent, final int modulus) {

        final int[] factors = DecimalToBaseConverter.exponentsBase2(exponent);
        log.info("Factors: {}", factors);

        int value = 1;
        int power = base;
        for (int factor : factors) {
            if (factor == 1) {
                value = multiply(value, power, modulus);
            }
            power = square(power, modulus);
        }
        log.info("{}^{} (mod {}) = [{}]", base, exponent, modulus, value);
        return value;
    }

    private static int multiply(int value, final int base, final int modulus) {

        log.debug("MULTIPLY");

        final int multiple = value * base;
        log.debug("{} x {} = {}", value, base, multiple);

        final int result = multiple % modulus;
        log.debug("{} (mod {}) = {}", multiple, modulus, result);

        return result;
    }

    private static int square(final int value, final int modulus) {

        log.debug("SQUARE");

        final int square = value * value;
        log.debug("{}²\t\t= {}", value, square);

        final int result = square % modulus;
        log.debug("{} % {}\t= {}", square, modulus, result);

        return result;
    }
}
