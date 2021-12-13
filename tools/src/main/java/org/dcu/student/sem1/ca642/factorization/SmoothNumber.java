package org.dcu.student.sem1.ca642.factorization;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SmoothNumber {

    public static boolean isBSmooth(final int n) {
        final int b = getBSmooth(n);
        return b != n;
    }

    public static int getBSmooth(final int n) {
        log.info("Computing smoothness of {}...", n);
        final List<Factor> factors = Factor.factor(n);
        final int b = factors.stream()
              .map(Factor::getBase)
              .max(Integer::compareTo)
              .orElse(n);
        log.info("{} is {}-smooth", n, b);
        return b;
    }

    public static boolean isBPowerSmooth(final int n) {
        final int b = getBPowerSmoot(n);
        return b != n;
    }

    public static int getBPowerSmoot(final int n) {
        log.info("Computing power-smoothness of {}...", n);
        final List<Factor> factors = Factor.factor(n);
        final int b = factors.stream()
              .map(Factor::getValue)
              .max(Integer::compareTo)
              .orElse(n);
        log.info("{} is {}-power smooth", n, b);
        return b;
    }

    public static boolean isSmooth(final int n) {
        log.info("Checking if {} is smooth...", n);
        final int b = getBSmooth(n);
        // Not an actual rule...
        final boolean isSmooth = b < n;
        log.info("{} is {}reasonably smooth...", n, isSmooth ? "not " : "");
        return isSmooth;
    }
}
