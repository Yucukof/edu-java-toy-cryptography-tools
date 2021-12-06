package org.dcu.student.sem1.ca642.modulus;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.dcu.student.sem1.ca642.primes.Primality.isCoPrimes;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Order {

    public static int compute(final int base, final int modulus) {
        log.info("Computing order of {} for {}", modulus, base);
        final int result = generate(base, modulus).size();
        log.info("Result = [{}]", result);
        return result;
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
            power += modulus;
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
}
