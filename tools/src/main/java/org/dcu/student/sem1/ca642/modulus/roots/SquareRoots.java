package org.dcu.student.sem1.ca642.modulus.roots;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.dcu.student.sem1.ca642.modulus.symbols.LegendreSymbol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.dcu.student.sem1.ca642.factorization.Naive.toPrimeFactors;
import static org.dcu.student.sem1.ca642.modulus.exponentiation.SquareAndMultiply.power;
import static org.dcu.student.sem1.ca642.primes.naive.BruteForce.isPrime;


@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SquareRoots {

    public static List<Integer> get(final int a, final int p) {
        log.info("Computing square roots of {} (mod {})", a, p);
        final List<Integer> roots = isPrime(p)
              ? getPrimeRoots(a, p)
              : getNonPrimeRoots(a, p);
        log.info("Roots = {}", roots);
        return roots;
    }

    private static List<Integer> getPrimeRoots(final int a, final int p) {
        log.debug("Computing roots for prime number {}", p);
        final Pair<Integer, Integer> roots = sqrt(a, p);
        return Arrays.asList(roots.getLeft(), roots.getRight());
    }

    public static Pair<Integer, Integer> sqrt(final int a, final int p) {
        log.info("Calculating square roots of {} (mod {})...", a, p);

        if (!isPrime(p)) {
//            throw new IllegalArgumentException(String.format("%s is not a valid prime", p));
            final int i = ChineseRemainderReverse.squareRoots(a, p);
            return new ImmutablePair<>(i, p - i);
        }

        log.debug("Verifying Legendre's Symbol...");
        final LegendreSymbol symbol = LegendreSymbol.resolve(a, p);

        if (symbol != LegendreSymbol.QUADRATIC_RESIDUE) {
            log.error("Legendre's Symbol: KO!");
            log.error("No solution possible...");
            return new ImmutablePair<>(0, 0);
        }
        log.debug("Legendre's Symbol: OK.");

        log.debug("Checking if p == 3 (mod 4)");
        final boolean p_congruent_3 = p % 4 == 3;
        log.debug("{} (mod 4) = {}", p, p % 4);

        log.debug("Computing roots...");
        final Pair<Integer, Integer> roots;
        if (p_congruent_3) {
            roots = computeRootsModulo3(a, p);
        } else {
            roots = computeRootsModulo8(a, p);
        }

        log.info("Roots: [{}]", roots);
        return roots;
    }

    private static Pair<Integer, Integer> computeRootsModulo3(final int a, final int p) {

        log.debug("Calculating (p+1)/4");

        final int exponent = (p + 1) / 4;
        log.debug("({} + 1) / 4 = {}", p, exponent);
        final int root = power(a, exponent, p);
        log.debug(" {}^(({} + 1) / 4) == {} (mod {})", a, p, root, p);

        return new ImmutablePair<>(root, p - root);
    }

    private static Pair<Integer, Integer> computeRootsModulo8(final int a, final int p) {

        log.debug("Computing a^((p-1)/4) == 1 (mod 4)");
        final int exponent = (p - 1) / 4;
        log.debug("({} - 1) / 4 = {}", p, exponent);
        final int clue = power(a, exponent, p);
        log.debug("{}^(({}-1)/4) (mod {}) == {}", a, p, p, clue);

        if (clue == 1) {
            return computesRootsModulo38(a, p);
        }
        if (clue == -1) {
            return computesRootsModulo58(a, p);
        }

        throw new IllegalStateException("Should not happen");
    }

    private static Pair<Integer, Integer> computesRootsModulo38(final int a, final int p) {
        log.debug("Calculating (p+3)/8");

        final int exponent = (p + 3) / 8;
        log.debug("({} + 3) / 8 = {}", p, exponent);
        final int root = power(a, exponent, p);
        log.debug(" {}^(({}+1)/4) == {} (mod {})", a, p, root, p);

        return new ImmutablePair<>(root, p - root);
    }

    private static Pair<Integer, Integer> computesRootsModulo58(final int a, final int p) {

        log.debug("Calculating (p-5)/8");
        final int exponent = (p - 5) / 8;
        log.debug("({} - 5) / 8 = {}", p, exponent);
        final int a_exp = power(a, exponent, p);
        log.debug("(4*{})^{} (mod {}) = {}", 4 * a, exponent, p, a_exp);
        final int root = (2 * a * a_exp) % p;
        log.debug("2a*({}) (mod {}) = {}", a, p, root);
        log.debug("2{}*(4*{})^(({}-5)/4) == {} (mod {})", a, a, p, root, p);

        return new ImmutablePair<>(root, p - root);
    }

    public static List<Integer> getNonPrimeRoots(final int value, final int modulus) {

        log.debug("Computing roots from non-prime {}...", modulus);

        log.debug("Computing prime factors of {}...", modulus);
        final List<Integer> factors = toPrimeFactors(modulus);
        log.debug("Factors: {}", factors);

        log.debug("Computing square roots of factors {}...", factors);
        final List<Pair<Integer, Integer>> roots = factors.stream()
              .map(factor -> sqrt(value, factor))
              .collect(Collectors.toList());
        log.debug("Factors' square roots = {}", roots);

        return getRecursiveRoots(factors, roots, new ArrayList<>(), modulus);

    }

    private static List<Integer> getRecursiveRoots(final List<Integer> factors, final List<Pair<Integer, Integer>> pairs, final List<Integer> roots, final int modulus) {

        if (pairs.isEmpty()) {

            log.debug("Processing CRT with roots {}", roots);

            final List<Integer> terms = IntStream.range(0, factors.size())
                  .mapToObj(i -> crt(factors, roots, modulus, i))
                  .map(ChineseRemainderReverse.Component::multiply)
                  .collect(Collectors.toList());
            log.debug("CRT terms = {}", terms);

            final int sum = terms.stream()
                  .reduce(Integer::sum)
                  .orElseThrow(RuntimeException::new);
            log.debug("Sum = {}", sum);

            final int root = sum % modulus;
            log.info("Root = [{}]", root);

            return Collections.singletonList(root);
        }

        final List<Integer> left = fork(factors, pairs, roots, modulus, true);
        final List<Integer> right = fork(factors, pairs, roots, modulus, false);

        return merge(left, right);
    }

    private static List<Integer> merge(final List<Integer> left, final List<Integer> right) {
        final List<Integer> list = new ArrayList<>();
        list.addAll(left);
        list.addAll(right);
        return list;
    }

    private static ChineseRemainderReverse.Component crt(final List<Integer> factors, final List<Integer> roots, final int modulus, final int i) {

        final Integer n = factors.get(i);
        final Integer a = roots.get(i);

        return ChineseRemainderReverse.Component.builder()
              .a(a)
              .n(n)
              .modulus(modulus)
              .build();
    }

    private static List<Integer> fork(final List<Integer> factors, final List<Pair<Integer, Integer>> pairs, final List<Integer> roots, final int modulus, final boolean useLeft) {

        final List<Pair<Integer, Integer>> newPairs = new ArrayList<>(pairs);

        final Pair<Integer, Integer> rootsPair = newPairs.get(0);
        newPairs.remove(0);
        final Integer root = useLeft ? rootsPair.getLeft() : rootsPair.getRight();

        final List<Integer> rootsLeft = new ArrayList<>(roots);
        rootsLeft.add(root);

        return getRecursiveRoots(factors, newPairs, rootsLeft, modulus);
    }
}
