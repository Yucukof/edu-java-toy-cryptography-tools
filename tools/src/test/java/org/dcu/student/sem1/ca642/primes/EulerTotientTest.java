package org.dcu.student.sem1.ca642.primes;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dcu.student.sem1.ca642.primes.EulerTotient.phi;

public class EulerTotientTest {

    @Test
    public void given_31_when_phi_then_expect_30() {
        assertThat(phi(31)).isEqualTo(30);
    }


    @Test
    public void given_77_when_phi_then_expect_60() {
        assertThat(phi(77)).isEqualTo(60);
    }

    @Test
    public void given_108_when_phi_then_expect_36() {
        assertThat(phi(108)).isEqualTo(36);
    }

}