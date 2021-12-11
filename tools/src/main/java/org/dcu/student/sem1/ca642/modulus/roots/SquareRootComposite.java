package org.dcu.student.sem1.ca642.modulus.roots;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.modulus.symbols.JacobiSymbol;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.dcu.student.sem1.ca642.factorization.Naive.toPrimeFactors;
import static org.dcu.student.sem1.ca642.modulus.symbols.JacobiSymbol.POTENTIAL_RESIDUE;


@Slf4j
@Data
public class SquareRootComposite implements SquareRoot {

    final int value;
    final int modulus;

    List<Integer> factors;
    List<SquareRootPrime> factorRoots;

    Set<Integer> roots;

    public SquareRootComposite(final int value, final int modulus) {
        this.value = value;
        this.modulus = modulus;

        log.debug("Computing SQRT({}) (mod {})...", value, modulus);
        if (!hasSolution()) {
            throw new IllegalArgumentException(String.format("SQRT(%s) (mod %s) has not solution", value, modulus));
        }
        this.factors = toPrimeFactors(modulus);
        this.factorRoots = resolveFactors();
        this.roots = resolve();
    }


    public SquareRootComposite(final int value, final int... factors) {
        this.value = value;
        this.modulus = multiply(factors);

        log.debug("Computing SQRT({}) (mod {})...", value, modulus);
        if (!hasSolution()) {
            throw new IllegalArgumentException(String.format("SQRT(%s) (mod %s) has not solution", value, modulus));
        }
        this.factors = toList(factors);
        this.factorRoots = resolveFactors();
        this.roots = resolve();
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

    private int computeCompositeRoot(final List<Integer> roots) {
        log.debug("Processing CRT...");

        final List<Integer> terms = getTerms(roots);
        final int sum = getSum(terms);
        final int root = sum % modulus;
        log.info("Root = [{}]", root);

        return root;
    }

    private Set<Integer> fork(final List<Integer> combination, final int root) {
        final List<Integer> branch = new ArrayList<>(combination);
        branch.add(root);
        return mergeFactorRoots(branch);
    }

    @Override
    public Set<Integer> get() {
        return roots;
    }

    private int getSum(final List<Integer> terms) {
        log.debug("Computing CRT sum...");
        final int sum = terms.stream()
              .reduce(Integer::sum)
              .orElseThrow(RuntimeException::new);
        log.debug("Sum = {}", sum);
        return sum;
    }

    private List<Integer> getTerms(final List<Integer> roots) {
        log.debug("Computing CRT terms with roots {} and factors {}...", roots, factors);
        final List<Integer> terms = IntStream.range(0, factors.size())
              .mapToObj(i -> crt(factors, roots, modulus, i))
              .map(ChineseRemainderReverse.Component::multiply)
              .collect(Collectors.toList());
        log.debug("CRT terms = {}", terms);
        return terms;
    }

    private boolean hasSolution() {
        final JacobiSymbol symbol = JacobiSymbol.resolve(value, modulus);
        return symbol.equals(POTENTIAL_RESIDUE);
    }

    private Set<Integer> mergeFactorRoots(final List<Integer> combination) {
        final int index = combination.size();

        if (index == factors.size()) {
            log.debug("Computing roots with combination {}...", combination);
            final Integer root = computeCompositeRoot(combination);
            log.debug("Root = [{}]", root);
            return Collections.singleton(root);
        }

        final SquareRootPrime root = factorRoots.get(index);
        final Set<Integer> left = fork(combination, root.getPositive());
        final Set<Integer> right = fork(combination, root.getNegative());

        final Set<Integer> set = new HashSet<>();
        set.addAll(left);
        set.addAll(right);
        return set;

    }

    private int multiply(final int[] factors) {
        return Arrays.stream(factors).reduce((a, b) -> a * b).orElse(0);
    }

    private Set<Integer> resolve() {
        log.debug("Computing effective roots...");
        final Set<Integer> roots = mergeFactorRoots(new ArrayList<>());
        log.info("Roots = [{}]", roots);
        return roots;

    }

    private List<SquareRootPrime> resolveFactors() {
        log.debug("Computing factor roots...");
        final List<SquareRootPrime> roots = factors.stream()
              .map(factor -> new SquareRootPrime(value, factor))
              .collect(Collectors.toList());
        log.debug("Factor Roots = {}", roots);
        return roots;
    }

    private List<Integer> toList(final int[] factors) {
        return Arrays.stream(factors)
              .boxed()
              .collect(Collectors.toList());
    }
}
