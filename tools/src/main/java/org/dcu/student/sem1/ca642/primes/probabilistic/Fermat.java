package org.dcu.student.sem1.ca642.primes.probabilistic;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.modulus.exponentiation.Exponentiation;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.dcu.student.sem1.ca642.primes.EulerTotient.phi;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Fermat {

    private static final Random rd = new Random();

    public static boolean isPseudoPrime(final int n, final int k) {
        final int phi = phi(n);
        for (int i = 0; i < k; i++) {
            final int a = rd.nextInt(n - 1) + 1;
            final boolean witness = isWitness(a, n);
            log.debug("{}^{} (mod {}) = {}", a, phi, n, witness);
            if (witness) {
                log.info("{} prime ? = [false]", n);
                return false;
            }
        }
        log.info("{} prime ? = [true]", n);
        return true;
    }

    public static boolean isWitness(final int a, final int n) {
        log.debug("Computing if {} is a Fermat pseudo-prime base {}", n, a);
        final int value = Exponentiation.compute(a, n - 1, n);
        log.debug("{}^({}-1) (mod {}) = {}", a, n, n, value);
        final boolean witness = value != 1;
        log.debug("{} {} 1", value, witness ? "==" : "!=");

        log.info("{} = [{}]", a, witness ? "WITNESS" : "LIAR");
        return witness;
    }

    public static Optional<Integer> getAnyLiar(final int n) {
        log.info("Searching any liar for {}", n);
        final Optional<Integer> liar = IntStream.range(2, n)
              .boxed()
              .filter(a -> !isWitness(a, n))
              .findFirst();
        log.info("Liar = [{}]", liar.map(Objects::toString).orElse(""));
        return liar;
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
}
