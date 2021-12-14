package org.dcu.student.sem1.ca642.modulus.exponentiation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.modulus.Modulus;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.dcu.student.sem1.ca642.factorization.Naive.toNonPrimesFactors;
import static org.dcu.student.sem1.ca642.modulus.inverse.ExtendedEuclidean.positiveInverse;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChineseRemainder {

    public static int compute(final int base, final int exponent, final int modulus) {
        log.info("Computing {}^{} (mod {})...", base, exponent, modulus);

        final List<Integer> factors = toNonPrimesFactors(modulus);
        log.debug("CRT Factors = {}", factors);

        final List<Integer> terms = factors.stream()
              .map(factor -> compute(factor, base, exponent, modulus))
              .collect(Collectors.toList());

        final Integer sum = terms.stream()
              .reduce(Integer::sum)
              .orElseThrow(RuntimeException::new);
        log.info("{} = {}", terms.stream().map(Object::toString).collect(Collectors.joining(" + ")), sum);

        final int result = sum % modulus;
        log.info("{} == {} (mod {})", sum, result, modulus);

        log.info("CRT Result = [{}]", result);

        return result;
    }

    private static int compute(final Integer factor, final int base, final int exponent, final int modulus) {

        log.debug("Computing term {}^{} (mod {})", base, exponent, factor);

        final int a = a(factor, base, exponent);
        final int bigN = bigN(factor, modulus);
        final int y = y(factor, bigN);

        return multiplication(a, bigN, y, factor);
    }

    private static int a(final Integer factor, final int base, final int exponent) {

        final int a = Exponentiation.resolve(base, exponent, factor);
        log.info("a = {}^{} (mod {}) = {}", base, exponent, factor, a);

        return a;
    }

    private static int bigN(final Integer factor, final int modulus) {
        log.debug("Computing N = modulus / factor...");

        final int bigN = modulus / factor;
        log.info("N = {}/{} = {}", modulus, factor, bigN);

        return bigN;
    }

    public static int y(final Integer factor, final int bigN) {
        log.debug("Computing y = N⁻¹ (mod factor)...");

        final int y = positiveInverse(bigN, factor);
        log.info("y == {}⁻¹ == {} (mod {})", bigN, y, factor);

        return y;
    }

    public static int multiplication(final int a, final int bigN, final int y, final int factor) {

        log.debug("Computing multiplication...");

        final int product = a * bigN * y;
        log.debug("product = {} x {} x {} = {}", a, bigN, y, product);

        final int modulus = bigN * factor;
        log.debug("modulus = {} x {} = {}", bigN, factor, modulus);

        final int result = product % modulus;
        log.debug("{} (mod {}) = {}", product, modulus, result);

        log.info("{} x {} x {} (mod {}) = {}", a, bigN, y, modulus, result);

        return result;
    }

    public static Modulus combine(final Modulus... moduluses) {
        log.info("Combining {}", Arrays.stream(moduluses).map(Objects::toString).collect(Collectors.joining(" ∧ ")));
        final int modulus = Arrays.stream(moduluses)
              .map(Modulus::getModulus)
              .reduce((a, b) -> a * b)
              .orElseThrow(RuntimeException::new);

        final List<Integer> terms = Arrays.stream(moduluses)
              .map(term -> process(modulus, term))
              .collect(Collectors.toList());

        final Integer sum = terms.stream()
              .reduce(Integer::sum)
              .orElse(0);
        log.debug("{} = {}", terms.stream().map(Objects::toString).collect(Collectors.joining(" + ")), sum);

        final int value = sum % modulus;
        log.debug("{} == {} (mod {})", sum, value, modulus);
        final Modulus result = Modulus.from(value, modulus);
        log.info("Result = [{}]", result);
        return result;
    }

    private static int process(final int modulus, final Modulus mod) {
        final int factor = mod.getModulus();
        log.debug("Computing term {}...", factor);

        final int a = mod.getValue();
        log.debug("a = {}", a);
        final int bigN = modulus / factor;
        log.debug("N = {}", bigN);
        final int y = positiveInverse(bigN, factor);
        log.debug("y = {}⁻¹ (mod {}) = {}", bigN, factor, y);

        final int result = multiplication(a, bigN, y, factor);
        log.info("{} x {} x {} (mod {}) = {}", a, bigN, y, modulus, result);
        return result;
    }

}