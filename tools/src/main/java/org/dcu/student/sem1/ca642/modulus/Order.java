package org.dcu.student.sem1.ca642.modulus;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.factorization.Factor;
import org.dcu.student.sem1.ca642.modulus.exponentiation.Exponentiation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.dcu.student.sem1.ca642.factorization.Factor.factor;
import static org.dcu.student.sem1.ca642.primes.EulerTotient.phi;
import static org.dcu.student.sem1.ca642.primes.Primality.isCoPrimes;

@Slf4j
@Builder
public class Order {

    final int base;
    final int modulus;

    private static boolean isPrimitiveRootBrutForce(final int value, final int modulus) {
        final int phi = phi(modulus);
        final int order = resolve(value, modulus);
        return order == phi;
    }

    public static int resolve(final int base, final int modulus) {
        return from(base, modulus)
              .resolve();
    }

    public int resolve() {
        log.info("Computing order of {} for {}", modulus, base);
        final int result = generate(base, modulus).size();
        log.info("Result = [{}]", result);
        return result;
    }

    public static Order from(final int base, final int modulus) {
        return builder()
              .base(base)
              .modulus(modulus)
              .build();
    }

    public static Set<Integer> generate(final int base, final int modulus) {
        log.info("Generating subset of Z^*_{} from {}", modulus, base);

        if (!isCoPrimes(base, modulus)) {
            throw new IllegalArgumentException(String.format("%s and %s are not relatively primes!", base, modulus));
        }

        final Set<Integer> part = new HashSet<>();

        int power = 1;
        int exponent = 0;
        part.add(1);
        for (int i = 0; i < modulus; i++) {
            power *= base;
            power %= modulus;
            exponent++;
            part.add(power);
            log.debug("{}^{} (mod {}) = {}", base, exponent, modulus, power);
            if (power == 1) {
                break;
            }
        }
        log.info("Result = {}", part.stream().sorted().collect(Collectors.toList()));
        return part;
    }

    public Set<Integer> generate() {
        return generate(base, modulus);
    }

    public boolean isPrimitiveRoot() {
        return isPrimitiveRoot(base, modulus);
    }

    public static boolean isPrimitiveRoot(final int value, final int modulus) {
        log.info("Checking if {} is a primitive root of {}...", value, modulus);
        final int phi = phi(modulus);

        final List<Integer> primeFactors = factor(phi).stream()
              .map(Factor::getBase)
              .collect(Collectors.toList());
        log.debug("Prime Factors = {}", primeFactors);

        final boolean isRoot = primeFactors.stream()
              .peek(factor -> log.debug("Processing prime factor {}...", factor))
              .map(factor -> process(value, modulus, phi, factor))
              .noneMatch(result -> result == 1);
        log.info("{} is a primitive root of {}", value, modulus);

        return isRoot;
    }

    private static int process(final int value, final int modulus, final int phi, final Integer factor) {
        log.debug("Computing {}^{phi({})/{}} (mod {})", value, modulus, factor, modulus);
        final int exponent = phi / factor;
        return Exponentiation.compute(value, exponent, modulus);
    }

}
