package org.dcu.student.sem1.ca642.modulus.roots;

import lombok.Data;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.modulus.symbols.LegendreSymbol;

import java.util.HashSet;
import java.util.Set;

import static org.dcu.student.sem1.ca642.modulus.exponentiation.SquareAndMultiply.power;
import static org.dcu.student.sem1.ca642.primes.naive.BruteForce.isPrime;

@Slf4j
@Data
public class SquareRootPrime implements SquareRoot {

    final int value;
    final int modulus;

    RootPair roots;

    public SquareRootPrime(final int value, final int modulus) {
        this.value = value;
        this.modulus = modulus;
        this.roots = resolve();
    }

    private RootPair resolve() {
        log.info("Calculating square roots of {} (mod {})...", value, modulus);
        if (!isPrime(modulus)) {
            throw new IllegalStateException(String.format("%s is not a valid prime!", modulus));
        }
        if (!hasSolution()) {
            throw new IllegalStateException(String.format("SQRT(%s) (mod %s) has no solution!", value, modulus));
        }
        final RootPair roots = compute();
        log.info("Roots = [{}]", roots);
        return roots;
    }

    private boolean hasSolution() {
        log.debug("Verifying Legendre's Symbol...");
        final LegendreSymbol symbol = LegendreSymbol.resolve(value, modulus);

        if (symbol != LegendreSymbol.QUADRATIC_RESIDUE) {
            log.error("Legendre's Symbol: KO!");
            log.error("No solution possible...");
            return false;
        }
        log.debug("Legendre's Symbol: OK.");
        return true;
    }

    private RootPair compute() {

        log.debug("Checking if p == 3 (mod 4)");
        final boolean p_congruent_3 = modulus % 4 == 3;
        log.debug("{} (mod 4) = {}", modulus, modulus % 4);


        log.debug("Computing roots...");
        return p_congruent_3 ? computeRootsModulo3(value, modulus) : computeRootsModulo8(value, modulus);

    }

    private RootPair computeRootsModulo3(final int a, final int p) {

        log.debug("Calculating (p+1)/4");

        final int exponent = (p + 1) / 4;
        log.debug("({} + 1) / 4 = {}", p, exponent);
        final int root = power(a, exponent, p);
        log.debug(" {}^(({} + 1) / 4) == {} (mod {})", a, p, root, p);

        return toPair(root);
    }

    private RootPair computeRootsModulo8(final int a, final int p) {

        log.debug("Computing a^((p-1)/4) == 1 (mod 4)");
        final int exponent = (p - 1) / 4;
        log.debug("({} - 1) / 4 = {}", p, exponent);
        final int clue = power(a, exponent, p);
        log.debug("{}^(({}-1)/4) (mod {}) == {}", a, p, p, clue);

        switch (clue) {
            case 1:
                return computesRootsModulo38();
            case -1:
                return computesRootsModulo58();
            default:
                throw new IllegalStateException("Should not happen");
        }
    }

    private RootPair toPair(final int root) {
        return new RootPair(root, modulus - root);
    }

    private RootPair computesRootsModulo38() {
        log.debug("Calculating (p+3)/8");

        final int exponent = (modulus + 3) / 8;
        log.debug("({} + 3) / 8 = {}", modulus, exponent);
        final int root = power(value, exponent, modulus);
        log.debug(" {}^(({}+1)/4) == {} (mod {})", value, modulus, root, modulus);

        return toPair(root);
    }

    private RootPair computesRootsModulo58() {

        log.debug("Calculating (p-5)/8");

        final int exponent = (modulus - 5) / 8;
        log.debug("({} - 5) / 8 = {}", modulus, exponent);

        final int a_exp = power(value, exponent, modulus);
        log.debug("(4 x {})^{} (mod {}) = {}", 4 * value, exponent, modulus, a_exp);

        final int root = (2 * value * a_exp) % modulus;
        log.debug("2 x ({}) (mod {}) = {}", value, modulus, root);

        log.debug("2 x (4 x {})^(({}-5)/4) == {} (mod {})", value, modulus, root, modulus);
        return toPair(root);
    }

    @Override
    public Set<Integer> get() {
        return roots.get();
    }

    public int getNegative() {
        return roots.getNegative();
    }

    public int getPositive() {
        return roots.getPositive();
    }


    @Override
    public String toString() {
        return "sqrt(" + value + ") (mod " + modulus + ") = " + roots;
    }

    @Value
    public static class RootPair {
        int positive;
        int negative;

        public Set<Integer> get() {
            final Set<Integer> set = new HashSet<>();
            set.add(positive);
            set.add(negative);
            return set;
        }

        @Override
        public String toString() {
            return "(" + positive + "," + negative + ")";
        }
    }
}
