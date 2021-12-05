package org.dcu.student.sem1.ca642;

import org.dcu.student.sem1.ca642.utils.MathUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MathUtilsTest {


    @Test
    public void given_2_exponent_0_when_power_then_expect_1() {
        final int power = MathUtils.power(2, 0);
        assertThat(power).isEqualTo(1);
    }

    @Test
    public void given_2_exponent_3_when_power_then_expect_8() {
        final int power = MathUtils.power(2, 3);
        assertThat(power).isEqualTo(8);
    }

    @Test
    public void given_2_exponent_minus_1_when_power_then_expect_exception() {

        assertThatThrownBy(() -> MathUtils.power(2, -1))
              .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    public void given_3_exponent_4_when_power_then_expect_81() {
        final int power = MathUtils.power(3, 4);
        assertThat(power).isEqualTo(81);
    }

}