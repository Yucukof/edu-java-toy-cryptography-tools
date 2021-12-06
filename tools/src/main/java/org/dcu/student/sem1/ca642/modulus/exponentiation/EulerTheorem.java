package org.dcu.student.sem1.ca642.modulus.exponentiation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.dcu.student.sem1.ca642.primes.EulerTotient.phi;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EulerTheorem {

    public static int apply(final int exponent, final int modulus) {

        final int phi = phi(modulus);
        final int simpleExponent = (phi + exponent) % phi;

        final int finalExponent;
        if (simpleExponent < exponent || exponent < 0) {
            log.info("Invoking Euler's Theorem");
            log.debug("{} (mod {}) = [{}]", exponent, phi, simpleExponent);
            finalExponent = simpleExponent;
        } else {
            finalExponent = exponent;
        }
        return finalExponent;
    }
}

