package org.dcu.student.sem1.ca642.modulus;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dcu.student.sem1.ca642.modulus.Order.resolve;
import static org.dcu.student.sem1.ca642.modulus.Order.isPrimitiveRoot;

public class OrderTest {

    @Test
    public void given_ord_17_base_2_when_generate_then_expect_correct_part() {
        assertThat(Order.generate(2, 17))
              .isNotNull()
              .hasSize(8)
              .contains(1, 2, 4, 8, 9, 13, 15, 16);
    }

    @Test
    public void given_ord_17_base_2_when_get_then_expect_correct_order() {
        assertThat(resolve(2, 17))
              .isEqualTo(8);
    }

    @Test
    public void given_ord_37_base_11_when_get_then_expect_correct_order() {
        assertThat(resolve(11, 37))
              .isEqualTo(6);
    }

    @Test
    public void given_root_value_and_modulus_when_isPrimitiveRoot_then_expect_true(){
        assertThat(isPrimitiveRoot(3,7))
              .isTrue();
    }

    @Test
    public void given_non_root_value_and_modulus_when_isPrimitiveRoot_then_expect_false(){
        assertThat(isPrimitiveRoot(2,7))
              .isFalse();
    }
}