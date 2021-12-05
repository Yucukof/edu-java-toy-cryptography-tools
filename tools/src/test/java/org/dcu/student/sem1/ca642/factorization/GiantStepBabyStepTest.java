package org.dcu.student.sem1.ca642.factorization;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dcu.student.sem1.ca642.factorization.GiantStepBabyStep.resolve;

public class GiantStepBabyStepTest {

    @Test
    public void given_valid_input_when_resolve_then_expect_correct_value() {
        assertThat(resolve(3, 101,37))
              .isEqualTo(24);
    }

}