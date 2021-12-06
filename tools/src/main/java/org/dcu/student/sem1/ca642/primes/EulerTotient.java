package org.dcu.student.sem1.ca642.primes;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.factorization.Factor;
import org.dcu.student.sem1.ca642.factorization.Naive;
import org.dcu.student.sem1.ca642.factorization.SmoothNumber;
import org.dcu.student.sem1.ca642.utils.MathUtils;

import java.util.List;

import static org.dcu.student.sem1.ca642.primes.naive.BruteForce.isPrime;
import static org.dcu.student.sem1.ca642.primes.Primality.isPrimeComposite;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EulerTotient {

    public static int phi(final int n) {
        log.info("Calculating phi({})", n);

        if (isPrime(n)) {
            return phiPrime(n);
        }
        if (isPrimeComposite(n)) {
            return phiPrimeComposite(n);
        }
        return phiComposite(n);
    }

    private static int phiPrime(final int n) {
        final int result = n - 1;
        log.debug("{} - 1 = {}", n, result);
        log.info("Result = [{}]", result);
        return result;
    }

    private static int phiPrimeComposite(final int n) {
        final List<Integer> factors = Naive.toPrimeFactors(n);
        log.debug("Factors = {}", factors);
        final int result = factors.stream()
              .map(p -> p - 1)
              .reduce((a, b) -> a * b)
              .orElseThrow(RuntimeException::new);

        log.info("Result = [{}]", result);
        return result;
    }

    private static int phiComposite(final int n) {
        final int result = SmoothNumber.factor(n).stream()
              .map(EulerTotient::computeTerm)
              .reduce((a, b) -> a * b)
              .orElseThrow(RuntimeException::new);
        log.info("Result = [{}]", result);
        return result;
    }

    private static int computeTerm(final Factor factor) {
        final int base = factor.getBase();
        final int exponent = factor.getExponent();
        final int power = MathUtils.power(base, (exponent - 1));
        final int prime = base - 1;
        final int result = power * prime;
        log.debug("{}^({}-1) x ({}-1) = {}", base, exponent, base, result);
        return result;
    }
}
