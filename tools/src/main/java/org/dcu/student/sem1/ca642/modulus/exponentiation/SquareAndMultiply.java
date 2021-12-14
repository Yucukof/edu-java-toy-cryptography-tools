package org.dcu.student.sem1.ca642.modulus.exponentiation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.dcu.student.sem1.ca642.utils.DecimalToBaseConverter;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SquareAndMultiply {

    public static int compute(final int base, final int exponent, final int modulus) {

        log.info("Computing {}^{} (mod {}) using Square&Multiply", base, exponent, modulus);
        final int[] factors = DecimalToBaseConverter.exponentsBase2(exponent);
        ArrayUtils.reverse(factors);
        log.debug("Factors: {}", factors);

        int result = 1;
        for (final int factor : factors) {
            result = square(result, modulus);
            if (factor == 1) {
                result = multiply(result, base, modulus);
            }
        }
        log.info("{}^{} (mod {}) = [{}]", base, exponent, modulus, result);
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

    private static int multiply(int value, final int base, final int modulus) {

        log.debug("MULTIPLY");

        final int multiple = value * base;
        log.debug("{} x {} = {}", value, base, multiple);

        final int result = multiple % modulus;
        log.debug("{} (mod {}) = {}", multiple, modulus, result);

        return result;
    }
}
