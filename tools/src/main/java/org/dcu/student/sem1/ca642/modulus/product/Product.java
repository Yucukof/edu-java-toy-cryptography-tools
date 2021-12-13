package org.dcu.student.sem1.ca642.modulus.product;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Data
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Product {

    final int modulus;
    @Singular
    final List<Integer> terms;

    public static int resolve(final int modulus, final int... factors) {
        return from(modulus, factors).resolve();
    }

    public int resolve() {
        log.info("Computing {} (mod {})", productString(terms), modulus);

        final List<Integer> simpleTerms = getSimpleTerms();
        final Integer product = getProduct(simpleTerms);
        final Integer result = getResult(product);

        log.info("{} (mod {}) = [{}]", productString(terms), modulus, result);
        return result;
    }

    public static Product from(final int modulus, final int... terms) {
        final ProductBuilder builder = builder().modulus(modulus);
        Arrays.stream(terms).forEach(builder::term);
        return builder.build();
    }

    private String productString(final List<Integer> terms) {
        return terms.stream()
              .map(Objects::toString)
              .collect(Collectors.joining(" x "));
    }

    private List<Integer> getSimpleTerms() {

        final List<Integer> simpleTerms = terms.stream()
              .map(this::simplify)
              .collect(Collectors.toList());

        final String termsStr = productString(terms);
        final String simpleTermsStr = productString(simpleTerms);

        if (!simpleTerms.containsAll(terms)) {
            log.debug("{} == {} (mod {})", termsStr, simpleTermsStr, modulus);
        }
        return simpleTerms;
    }

    private Integer getProduct(final List<Integer> simpleTerms) {
        final Integer product = simpleTerms.stream()
              .reduce((a, b) -> a * b)
              .orElse(0);
        log.debug("{} = {}", productString(simpleTerms), product);
        return product;
    }

    private Integer getResult(final Integer product) {
        final Integer result = product % modulus;
        log.debug("{} (mod {}) = {}", product, modulus, result);
        return result;
    }

    private int simplify(final Integer term) {
        return term % modulus;
    }
}
