package org.dcu.student.sem1.ca642.modulus.inverse;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InverseTest {

    @Test
    public void given_inverse_23_mod_10_when_resolve_then_expect__correct_inverse() {
        final int x = 23;
        final int y = 10;

        final Inverse inverse = new Inverse(x, y);
        assertThat(inverse.resolve())
              .isEqualTo(7);
    }

    @Test
    public void given_inverse_10_mod_23_when_resolve_then_expect__correct_inverse() {
        final int x = 10;
        final int y = 23;

        final Inverse inverse = new Inverse(x, y);
        assertThat(inverse.resolve())
              .isEqualTo(7);
    }

    @Test
    public void given_inverse_117_mod_191_when_resolve_then_expect__correct_inverse() {
        final int x = 117;
        final int y = 191;

        final Inverse inverse = new Inverse(x, y);
        assertThat(inverse.resolve())
              .isEqualTo(80);
    }

    @Test
    public void given_inverse_191_mod_117_when_resolve_then_expect__correct_inverse() {
        final int x = 191;
        final int y = 117;

        final Inverse inverse = new Inverse(x, y);
        assertThat(inverse.resolve())
              .isEqualTo(68);
    }

}