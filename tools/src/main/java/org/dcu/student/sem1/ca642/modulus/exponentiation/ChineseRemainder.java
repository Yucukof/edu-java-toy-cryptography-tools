package org.dcu.student.sem1.ca642.modulus.exponentiation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.modulus.exponentiation.SquareAndMultiply;

import java.util.List;

import static org.dcu.student.sem1.ca642.modulus.ExtendedEuclidean.positiveInverse;
import static org.dcu.student.sem1.ca642.factorization.Naive.toNonPrimesFactors;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChineseRemainder {

    public static int power(final int base, final int exponent, final int modulus) {
        log.info("Computing {}^{} (mod {}) with CRT...", base, exponent, modulus);

        final List<Integer> factors = toNonPrimesFactors(modulus);
        log.debug("CRT Factors = {}", factors);

        final Integer sum = factors.stream()
              .map(factor -> power(factor, base, exponent, modulus))
              .reduce(Integer::sum)
              .orElseThrow(RuntimeException::new);
        log.debug("CRT Sum = {}", sum);

        final int result = sum % modulus;
        log.info("CRT Result = {}", result);

        return result;
    }

    private static int power(final Integer factor, final int base, final int exponent, final int modulus) {

        log.debug("Computing term {}^{} (mod {})", base, exponent, factor);

        final int a = a(factor, base, exponent);
        final int bigN = bigN(factor, modulus);
        final int y = y(factor, bigN);

        return multiplication(a, bigN, y, factor);
    }

    private static int a(final Integer factor, final int base, final int exponent) {

        final int a = SquareAndMultiply.power(base, exponent, factor);
        log.debug("a = {}^{} (mod {}) = {}", base, exponent, factor, a);

        return a;
    }

    private static int bigN(final Integer factor, final int modulus) {
        log.debug("Computing N = modulus / factor...");

        final int bigN = modulus / factor;
        log.debug("N = {}/{} = {}", modulus, factor, bigN);

        return bigN;
    }

    public static int y(final Integer factor, final int bigN) {
        log.debug("Computing y = N⁻¹ (mod factor)...");

        final int y = positiveInverse(factor, bigN);
        log.debug("y == {}⁻¹ == {} (mod {})", bigN, y, factor);

        return y;
    }

    public static int multiplication(final int a, final int bigN, final int y, final int factor) {

        log.debug("Computing multiplication...");

        final int product = a * bigN * y;
        log.debug("{} x {} x {} = {}", a, bigN, y, product);

        final int modulus = bigN * factor;
        log.debug("{} x {} = {}", bigN, factor, modulus);

        final int result = product % modulus;
        log.debug("{} (mod {}) = {}", product, modulus, result);

        return result;
    }

}