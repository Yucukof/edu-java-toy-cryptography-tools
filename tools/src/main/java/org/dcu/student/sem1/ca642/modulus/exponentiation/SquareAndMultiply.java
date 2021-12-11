package org.dcu.student.sem1.ca642.modulus.exponentiation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.dcu.student.sem1.ca642.utils.DecimalToBaseConverter;

import static org.dcu.student.sem1.ca642.primes.naive.BruteForce.isPrime;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SquareAndMultiply {

    public static int power(final int base, final int exponent, final int modulus) {

        log.info("Computing {}^{} (mod {})", base, exponent, modulus);

        if (exponent == 0) {
            log.info("Result = [1]");
            return 1;
        }
        if (exponent == 1) {
            final int result = base % modulus;
            log.info("Result = [{}]", result);
            return result;
        }

        final int simpleBase = base % modulus;
        if (simpleBase < base) {
            log.debug("Simplifying base...");
            return power(simpleBase, exponent, modulus);
        } else {
            log.debug("Could not simplify base.");
        }

        final int simpleExponent = EulerTheorem.apply(exponent, modulus);
        if (simpleExponent != exponent) {
            log.debug("Simplifying exponent...");
            return power(simpleBase, simpleExponent, modulus);
        } else {
            log.debug("Could not simplify exponent.");
        }

        if (simpleExponent == modulus && isPrime(modulus)) {
            log.debug("Invoking Fermat's Little Theorem...");
            log.debug("{}^{} (mod {}) == {} (mod {})", simpleBase, simpleExponent, modulus, base, modulus);
            return simpleBase;
        }

        return squareAndMultiply(simpleBase, simpleExponent, modulus);
    }

    public static int multiplyAndSquare(final int base, final int exponent, final int modulus) {

        log.info("Computing {}^{} (mod {}) using Multiply&Square", base, exponent, modulus);
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
        log.info("Result = [{}]", value);
        return value;
    }

    public static int squareAndMultiply(final int base, final int exponent, final int modulus) {

        log.info("Computing {}^{} (mod {}) using Square&Multiply", base, exponent, modulus);
        final int[] factors = DecimalToBaseConverter.exponentsBase2(exponent);
        ArrayUtils.reverse(factors);
        log.debug("Factors: {}", factors);

        int value = 1;
        for (final int factor : factors) {
            value = square(value, modulus);
            if (factor == 1) {
                value = multiply(value, base, modulus);
            }
        }
        log.info("Result = [{}]", value);
        return value;
    }

    private static int square(final int value, final int modulus) {

        log.debug("SQUARE");

        final int square = value * value;
        log.debug("{}²\t\t= {}", value, square);

        final int result = square % modulus;
        log.debug("{} % {}\t= {}", square, modulus, result);

        return result;
    }

    private static int multiply(int value, final int base, final int modulus) {

        log.debug("MULTIPLY");

        final int multiple = value * base;
        log.debug("{} * {}\t= {}", value, base, multiple);

        final int result = multiple % modulus;
        log.debug("{} % {}\t= {}", value, modulus, result);

        return result;
    }

}
