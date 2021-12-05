package org.dcu.student.sem1.ca642.modulus.roots;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.modulus.exponentiation.ChineseRemainder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.dcu.student.sem1.ca642.factorization.Naive.toNonPrimesFactors;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChineseRemainderReverse {

    public static int squareRoots(final int value, final int modulus) {
        log.info("Computing SQRT({}) (mod {})", value, modulus);
        final Component component = Component.builder()
              .a(value)
              .modulus(modulus)
              .build();

        final int result = squareRoots(component);
        log.info("Result = [{}]", result);
        return result;
    }

    public static int squareRoots(final Component component) {

        final int modulus = component.getModulus();

        final List<Integer> factors = toNonPrimesFactors(modulus);

        final List<Component> components = factors.stream()
              .map(factor -> component.toBuilder()
                    .n(factor)
                    .build())
              .collect(Collectors.toList());

        return compute(components, modulus);
    }

    private static Integer compute(final List<Component> compo, final int modulus) {
        return compo.stream()
              .map(Component::multiply)
              .reduce(Integer::sum)
              .map(a -> a % modulus)
              .orElseThrow(RuntimeException::new);
    }

    public static int squareRoots(final Component... components) {

        final List<Component> componentList = Arrays.stream(components)
              .collect(Collectors.toList());

        return compute(componentList);
    }

    private static Integer compute(final List<Component> compo) {
        final int modulus = compo.stream()
              .map(Component::getN)
              .reduce((a, b) -> a * b)
              .orElseThrow(RuntimeException::new);

        return compute(compo, modulus);
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
            log.info("Computing a x N x (N⁻¹ (mod n)) (mod modulus)");
            log.info("\tn = {}", n);
            log.info("\ta = {}", a);
            final int bigN = getBigN();
            final int y = getY();
            final int product = ChineseRemainder.multiplication(a, bigN, y, n);
            log.info("\t({} x {} x {}) == {} (mod {})", a, bigN, y, product, modulus);
            return product;

        }

        private int getBigN() {
            return modulus / n;
        }

        /**
         * @return N⁻¹ (mod n)
         */
        public int getY() {
            return ChineseRemainder.y(n, getBigN());
        }
    }
}
