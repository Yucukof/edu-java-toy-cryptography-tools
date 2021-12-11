package org.dcu.student.sem1.ca642.modulus.roots;

import java.util.Set;

import static org.dcu.student.sem1.ca642.primes.naive.BruteForce.isPrime;

public interface SquareRoot {

    static Set<Integer> compute(final int value, final int modulus) {
        final SquareRoot root = from(value, modulus);
        return root.get();
    }

    static SquareRoot from(final int value, final int modulus) {
        return isPrime(modulus)
              ? new SquareRootPrime(value, modulus)
              : new SquareRootComposite(value, modulus);
    }

    Set<Integer> get();

}
