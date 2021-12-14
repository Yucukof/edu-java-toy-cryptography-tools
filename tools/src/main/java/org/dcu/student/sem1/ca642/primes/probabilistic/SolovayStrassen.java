package org.dcu.student.sem1.ca642.primes.probabilistic;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.modulus.exponentiation.Exponentiation;
import org.dcu.student.sem1.ca642.modulus.symbols.JacobiSymbol;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.dcu.student.sem1.ca642.primes.EuclideanAlgorithm.isCoPrimes;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SolovayStrassen {

    private static final Random rd = new Random();

    public static Optional<Integer> getAnyLiar(final int n) {
        log.info("Searching any liar for {}", n);
        final Optional<Integer> liar = IntStream.range(2, n)
              .boxed()
              .filter(a -> !isWitness(a, n))
              .findFirst();
        log.info("Liar = [{}]", liar.map(Objects::toString).orElse(""));
        return liar;
    }

    public static boolean isWitness(final int a, final int n) {
        final int jacobi = JacobiSymbol.compute(a, n);
        final int symbol = (jacobi + n) % n; // TODO: 11/12/2021 [HBA]: review
        final int witness = getWitness(a, n);
        final boolean result = symbol != witness;
        log.debug("Jacobi Symbol {} {} witness {}", symbol, result ? "!=" : "==", witness);
        log.info("{} = [{}]", a, result ? "WITNESS" : "LIAR");
        return result;
    }

    private static int getWitness(final int a, final int n) {
        final int exponent = (n - 1) / 2;
        return Exponentiation.compute(a, exponent, n);
    }

    public static List<Integer> getLiars(final int n) {
        log.info("Computing liars of {}", n);
        final List<Integer> liars = IntStream.range(1, n)
              .filter(a -> !isWitness(a, n))
              .boxed()
              .collect(Collectors.toList());
        log.info("Liars = {}\n", liars);
        return liars;
    }

    public static Optional<Integer> getAnyWitness(final int n) {
        log.info("Searching any witness for {}...", n);
        final Optional<Integer> witness = IntStream.range(1, n)
              .boxed()
              .filter(a -> isWitness(a, n))
              .findFirst();
        log.info("Witness = [{}]", witness.map(Objects::toString).orElse(""));
        return witness;
    }

    public static List<Integer> getWitnesses(final int n) {
        log.info("Computing witnesses of {}", n);
        final List<Integer> witnesses = IntStream.range(1, n)
              .filter(a -> isWitness(a, n))
              .boxed()
              .collect(Collectors.toList());
        log.info("Witnesses = {}\n", witnesses);
        return witnesses;
    }

    public static boolean isPseudoPrime(final int n, final int k) {

        for (int i = 0; i < k; i++) {
            final int a = rd.nextInt(n - 2) + 2;
            if (!isCoPrimes(a, n)) {
                log.debug("{} is a composite number with {}", n, a);
                log.info("{} prime ? = [false]", n);
                return false;
            }

            final boolean witness = isWitness(a, n);
            if (witness) {
                log.info("{} prime ? = [false]", n);
                return false;
            }
        }
        log.info("{} prime ? = [true]", n);
        return true;
    }
}