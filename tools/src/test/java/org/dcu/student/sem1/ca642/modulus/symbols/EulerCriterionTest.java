package org.dcu.student.sem1.ca642.modulus.symbols;

import org.dcu.student.sem1.ca642.modulus.symbols.EulerCriterion;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EulerCriterionTest {

    @Test
    public void given_quadratic_residue_when_compute_then_expect_1(){
        final int a = 9;
        final int p = 11;

        final EulerCriterion value = EulerCriterion.resolve(a,p);
        assertThat(value)
              .isEqualTo(EulerCriterion.QUADRATIC_RESIDUE);
    }

    @Test
    public void given_quadratic_non_residue_when_compute_then_expect_minus_1(){
        final int a = 10;
        final int p = 11;

        final EulerCriterion value = EulerCriterion.resolve(a,p);
        assertThat(value)
              .isEqualTo(EulerCriterion.QUADRATIC_NON_RESIDUE);
    }

}