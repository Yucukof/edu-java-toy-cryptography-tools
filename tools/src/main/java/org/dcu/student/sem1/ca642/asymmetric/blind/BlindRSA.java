package org.dcu.student.sem1.ca642.asymmetric.blind;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.modulus.exponentiation.Exponentiation;

import java.util.Random;

import static org.dcu.student.sem1.ca642.primes.EuclideanAlgorithm.gcd;
import static org.dcu.student.sem1.ca642.modulus.inverse.ExtendedEuclidean.inverse;

@Slf4j
@RequiredArgsConstructor
public class BlindRSA {

    private static final Random random = new Random();

    /**
     * Blind value
     */
    final int b;
    /**
     * Modulus
     */
    final int n;

    public static BlindRSA from(final int b, final int n) {
        return new BlindRSA(b, n);
    }

    private static BlindRSA generate(final int n) {
        do {
            final int b = random.nextInt(n - 3) + 2;
            if (gcd(b, n) == 1) {
                return new BlindRSA(b, n);
            }
        } while (true);
    }

    final int obfuscate(final int message, final int exponent) {
        log.info("Blinding {}", message);
        final int blind = Exponentiation.compute(b, exponent, n);
        log.debug("Blind = {}^{} (mod {}) = {}", b, exponent, n, blind);
        final int blinded = (message * blind) % n;
        log.info("Blinded value = [{}]", blinded);
        return blinded;
    }

    final int reveal(final int blinded, final int exponent) {
        log.info("Unblinding {}", blinded);
        final int unblind = inverse(b, n);
        log.debug("Invere = {}⁻¹ (mod {}) = ", b, n, unblind);
        final int message = (blinded * unblind) % n;
        log.info("Original value = [{}]", message);
        return message;
    }

}
