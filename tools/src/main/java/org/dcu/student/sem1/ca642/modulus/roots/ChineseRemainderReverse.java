package org.dcu.student.sem1.ca642.modulus.roots;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.modulus.exponentiation.ChineseRemainder;

import java.util.List;
import java.util.stream.Collectors;

import static org.dcu.student.sem1.ca642.factorization.Naive.toNonPrimesFactors;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChineseRemainderReverse {

    public static int squareRoots(final Component component) {

        final int modulus = component.getModulus();

        final List<Integer> factors = toNonPrimesFactors(modulus);
        final List<Component> components = toFactorComponent(component, factors);

        return compute(components, modulus);
    }

    private static List<Component> toFactorComponent(final Component component, final List<Integer> factors) {
        return factors.stream()
              .map(factor -> component.toBuilder()
                    .n(factor)
                    .build())
              .collect(Collectors.toList());
    }

    private static Integer compute(final List<Component> compo, final int modulus) {
        return compo.stream()
              .map(Component::multiply)
              .reduce(Integer::sum)
              .map(a -> a % modulus)
              .orElseThrow(RuntimeException::new);
    }

    @Builder(toBuilder = true)
    @Getter
    public static class Component {
        /**
         * A factor from a composite modulus
         */
        private final int n;
        /**
         * A value mod (n)
         */
        private final int a;
        /**
         * The composite modulus
         */
        private final int modulus;

        /**
         * @return a x N x (N⁻¹ (mod n)) (mod modulus)
         */
        public int multiply() {
            log.info("Computing a x N x (y (mod n)) (mod modulus)");
            log.debug("n = {}", n);
            log.debug("a = {}", a);
            final int bigN = getBigN();
            final int y = getY(bigN);
            final int product = ChineseRemainder.multiplication(a, bigN, y, n);
            log.debug("({} x {} x {}) == {} (mod {})", a, bigN, y, product, modulus);
            return product;

        }

        private int getBigN() {
            final int bigN = modulus / n;
            log.debug("N = {}", bigN);
            return bigN;
        }

        /**
         * @return N⁻¹ (mod n)
         * @param bigN
         */
        public int getY(final int bigN) {
            final int y = ChineseRemainder.y(n, bigN);
            log.debug("y = {}", y);
            return y;
        }
    }
}
