package org.dcu.student.sem1.ca642.primes;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.primes.naive.BruteForce;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Primality {

    public static boolean isCoPrimes(final int a, final int b) {
        log.info("Evaluating relative primality of {} to {}...", a, b);
        final boolean coprimality = (a % b != 0) && (b % a != 0);
        if (coprimality) {
            log.info("Result = [true]");
        } else {
            final boolean a_divides_b = a % b == 0;
            log.debug("{} % {} = 0", a_divides_b ? a : b, a_divides_b ? b : a);
            log.info("Result = [false]");
        }
        return coprimality;
    }

    public static boolean isPrimeComposite(final int n) {
        log.info("Evaluating primality of {} factors...", n);
        int value = n;
        int prime = 2;
        while (value > 1) {
            if (value % prime == 0) {
                value /= prime;
            }
            if (value % prime == 0) {
                log.info("Result = [false]");
                log.debug("Can be divided twice by {}", prime);
                return false;
            }
            prime = BruteForce.getNextPrime(prime);
        }
        log.info("Result = [true]");
        return true;
    }

}
