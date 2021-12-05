package org.dcu.student.sem1.ca642.primes.probabilistic;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.modulus.symbols.JacobiSymbol;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.dcu.student.sem1.ca642.modulus.EuclideanAlgorithm.isCoPrimes;
import static org.dcu.student.sem1.ca642.modulus.exponentiation.SquareAndMultiply.power;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SolovayStrassen {

    private static final Random rd = new Random();

    public static boolean isPseudoPrime(final int n, final int k) {

        for (int i = 0; i < k; i++) {
            final int a = rd.nextInt(n - 2) + 2;
            if (!isCoPrimes(a, n)) {
                log.debug("{} is a composite number with {}", n, a);
                log.info("Result = [false]");
                return false;
            }

            final boolean witness = isWitness(a, n);
            if (witness) {
                log.info("Result = [false]");
                return false;
            }
        }
        log.info("Result = [true]");
        return true;
    }

    public static boolean isWitness(final int a, final int n) {
        final int jacobi = JacobiSymbol.compute(a, n);
        final int symbol = (jacobi + n) % n;
        final int witness = getWitness(a, n);
        final boolean result = symbol != witness;
        log.debug("Jacobi Symbol {} {} witness {}", symbol, result ? "!=" : "==", witness);
        return result;
    }

    private static int getWitness(final int a, final int n) {
        final int exponent = (n - 1) / 2;
        return power(a, exponent, n);
    }


    public static List<Integer> getLiars(final int n) {
        return IntStream.range(1, n)
              .filter(a -> !isWitness(a, n))
              .boxed()
              .collect(Collectors.toList());
    }

    public static List<Integer> getWitnesses(final int n) {
        return IntStream.range(1, n)
              .filter(a -> isWitness(a, n))
              .boxed()
              .collect(Collectors.toList());
    }
}