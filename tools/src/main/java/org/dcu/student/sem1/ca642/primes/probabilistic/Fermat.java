package org.dcu.student.sem1.ca642.primes.probabilistic;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.dcu.student.sem1.ca642.modulus.exponentiation.SquareAndMultiply.power;
import static org.dcu.student.sem1.ca642.primes.EulerTotient.phi;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Fermat {

    private static final Random rd = new Random();

    public static boolean isPseudoPrime(final int n, final int k) {
        final int phi = phi(n);
        for (int i = 0; i < k; i++) {
            final int a = rd.nextInt(n);
            final boolean witness = isWitness(a, n);
            log.debug("{}^{} (mod {}) = {}", a, phi, n, witness);
            if (witness) {
                log.info("Result = [false]");
                return false;
            }
        }
        log.info("Result = [true]");
        return true;
    }

    public static boolean isWitness(final int a, final int n) {
        log.debug("Computing if {} is a Fermat pseudoprime base {}", n, a);
        final int value = power(a, n - 1, n);
        log.debug("{}^({}-1) (mod {}) = {}", a, n, n, value);
        final boolean witness = value != 1;
        log.debug("Result = [{}]", witness);
        return witness;
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
