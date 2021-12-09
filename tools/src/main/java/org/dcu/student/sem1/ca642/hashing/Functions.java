package org.dcu.student.sem1.ca642.hashing;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Functions {

    public static double averageCollision(final int size) {
        log.info("Computing average collision on a {}-bit hash...", size);
        final int exponent = size / 2;
        log.info("Result : [2^{}]", exponent);
        return Math.pow(2, exponent);
    }

    public static double averageCollision(final int messageLength, final int hashSize) {
        log.info("Computing average collision of messages of length {} on a {}-bit hash...", messageLength, hashSize);
        final int exponent = messageLength - hashSize;
        log.info("Result : [2^{}]", exponent);
        return Math.pow(2, exponent);
    }
}
