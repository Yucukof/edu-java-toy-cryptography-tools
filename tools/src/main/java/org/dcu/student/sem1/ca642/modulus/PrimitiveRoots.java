package org.dcu.student.sem1.ca642.modulus;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.factorization.Factor;
import org.dcu.student.sem1.ca642.modulus.exponentiation.Exponentiation;
import org.dcu.student.sem1.ca642.primes.naive.BruteForce;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.dcu.student.sem1.ca642.factorization.Factor.factor;
import static org.dcu.student.sem1.ca642.primes.EulerTotient.phi;
import static org.dcu.student.sem1.ca642.primes.Primality.isPrimeComposite;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PrimitiveRoots {

    public static int primitiveRootsCount(final int modulus) {
        if (!BruteForce.isPrime(modulus)) {
            return 0;
        }
        return phi(modulus - 1);
    }

    public static Optional<Integer> getAnyPrimitiveRoot(final int modulus) {

        if (!hasPrimitiveRoots(modulus)) {
            return Optional.empty();
        }

        return IntStream.range(2, modulus)
              .boxed()
              .filter(i -> isPrimitiveRoot(i, modulus))
              .findAny();
    }

    public static boolean hasPrimitiveRoots(final int modulus) {
        log.debug("Computing if {} has primitive roots", modulus);
        if (modulus == 2 || modulus == 4) {
            log.debug("{} proved directly", modulus);
            log.info("{} has primitives roots.\n", modulus);
            return true;
        }

        final List<Factor> primeFactors = factor(modulus);
        if (primeFactors.size() == 1) {
            log.debug("{} proved by being a prime", modulus);
            log.info("{} has primitives roots.\n", modulus);
            return true;
        }

        if (primeFactors.size() == 2) {
            final Factor factor1 = primeFactors.get(0);
            final Factor factor2 = primeFactors.get(1);

            if (factor1.getBase() == factor2.getBase()) {
                log.debug("Proved by {} = {}^{} x {}^{}", modulus, factor1.getBase(), factor1.getExponent(), factor2.getBase(), factor2.getExponent());
                log.info("{} has primitives roots.\n", modulus);
                return true;
            }
        }
        log.info("{} has NO primitives roots.\n", modulus);
        return false;
    }

    public static boolean isPrimitiveRoot(final int base, final int modulus) {
        log.info("Computing if {} is a primitive root of {}", base, modulus);

        if (!isPrimeComposite(modulus)) {
            return Order.isPrimitiveRoot(base, modulus);
        }

        final int phi = phi(modulus);
        final List<Factor> factors = factor(phi);

        log.debug("Checking if ");
        final boolean result = factors.stream()
              .map(Factor::getBase)
              .peek(factor -> log.debug("Computing phi({}) / {}", phi, factor))
              .map(factor -> phi / factor)
              .peek(exponent -> log.debug("Computing {}^{} (mod {})", base, exponent, modulus))
              .map(exponent -> Exponentiation.compute(base, exponent, modulus))
              .peek(remainder -> log.debug("Remainder = {}\n", remainder))
              .noneMatch(remainder -> remainder == 1);

        log.debug("{} is{} a primitive root of [{}]\n", base, result ? "" : " not", modulus);
        return result;

    }

    public static List<Integer> getPrimitiveRoots(final int modulus) {

        if (!hasPrimitiveRoots(modulus)) {
            return Collections.emptyList();
        }

        return IntStream.range(2, modulus)
              .filter(i -> isPrimitiveRoot(i, modulus))
              .boxed()
              .collect(Collectors.toList());
    }

}