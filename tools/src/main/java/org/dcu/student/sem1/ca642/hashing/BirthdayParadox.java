package org.dcu.student.sem1.ca642.hashing;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BirthdayParadox {

    public static float compute(final int n, final int k) {
        log.info("Computing probability of collision using Birthday Paradox...");
        float noCollisionProbability = 1;
        for (int i = 0; i < k; i++) {
            final float p = ((float) n - i) / n;
            log.debug("p = ({} - {}) / {} = {}", n, i, n, p);
            noCollisionProbability *= p;
            log.debug("P = {}", noCollisionProbability);
        }
        final float probability = 1f - noCollisionProbability;
        log.info("Probability = [{}]", probability);
        return probability;
    }

    public static int threshold(final int n) {
        final float threshold = .5f;
        log.info("Computing probability of collision using Birthday Paradox...");
        float noCollisionProbability = 1f;
        for (int i = 0; i < n; i++) {
            final float p = ((float) n - i) / n;
            log.debug("p = ({} - {}) / {} = {}", n, i, n, p);
            noCollisionProbability *= p;
            log.debug("P = {}", noCollisionProbability);
            if (noCollisionProbability <= threshold) {
                log.info("Threshold = [{}]", i);
                return i;
            }
        }
        throw new IllegalStateException("Should not happen");
    }

    public static int approximateThreshold(final int n) {
        return (int) Math.sqrt(n);
    }

    public static double averageCollision(final int messageLength, final int hashSize) {
        log.info("Computing average collision of messages of length {} on a {}-bit hash...", messageLength, hashSize);
        final int exponent = messageLength - hashSize;
        log.info("Result : [2^{}]", exponent);
        return Math.pow(2, exponent);
    }
}
