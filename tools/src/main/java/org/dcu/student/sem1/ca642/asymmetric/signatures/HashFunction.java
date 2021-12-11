package org.dcu.student.sem1.ca642.asymmetric.signatures;

import java.util.function.Function;

public interface HashFunction extends Function<Integer, Integer> {

    default int hash(final int message) {
        return apply(message);
    }
}
