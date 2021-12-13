package org.dcu.student.sem1.ca642.modulus;

import org.dcu.student.sem1.ca642.modulus.exponentiation.Exponentiation;
import org.dcu.student.sem1.ca642.modulus.exponentiation.SquareAndMultiply;
import org.dcu.student.sem1.ca642.modulus.fraction.Fraction;
import org.dcu.student.sem1.ca642.modulus.product.Product;
import org.dcu.student.sem1.ca642.modulus.roots.SquareRoot;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ModulusTest {

    @Test
    public void given_another_simple_fraction_when_divide_then_expect_correct_resolution() {
        final Modulus modulus = Modulus.from(6, 11);
        final Fraction fraction = modulus.getFraction(9);

        assertThat(fraction.resolve())
              .isEqualTo(8);
    }

    @Test
    public void given_difficult_fraction_when_divide_then_expect_correct_resolution() {
        final Modulus modulus = Modulus.from(77, 191);
        final Fraction fraction = modulus.getFraction(117);

        assertThat(fraction.resolve())
              .isEqualTo(48);
    }

    @Test
    public void given_difficult_multiplication_when_multiply_then_expect_product() {
        final Modulus modulus = Modulus.from(57, 11);
        final Product product = modulus.getProduct(42);

        assertThat(product.resolve())
              .isEqualTo(7);
    }

    @Test
    public void given_multiplication_when_resolve_then_expect_correct_product() {
        final Modulus modulus = Modulus.from(6, 11);
        final Product product = modulus.getProduct(7);

        assertThat(product.resolve())
              .isEqualTo(9);
    }

    @Test
    public void given_several_moduluses_when_combine_then_expect_correct_modulus() {
        final Modulus modulus = Modulus.from(2, 3);
        final Modulus modulus2 = Modulus.from(3, 4);
        final Modulus combined = modulus.combine(modulus2);

        assertThat(combined.getModulus())
              .isEqualTo(12);
        assertThat(combined.getValue())
              .isEqualTo(11);
    }

    @Test
    public void given_simple_fraction_when_divide_then_expect_correct_resolution() {
        final Modulus modulus = Modulus.from(6, 13);
        final Fraction fraction = modulus.getFraction(7);

        assertThat(fraction.resolve())
              .isEqualTo(12);
    }

    @Test
    public void given_quadratic_non_residue_modulus_when_squareRoot_then_expect_exception() {
        final Modulus modulus = Modulus.from(2, 59);

        assertThatThrownBy(modulus::getSquareRoot)
              .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void given_quadratic_residue_modulus_when_squareRoot_then_expect_correct_resolution() {
        final Modulus modulus = Modulus.from(5, 59);
        final SquareRoot squareRoot = modulus.getSquareRoot();

        assertThat(squareRoot.get())
              .isNotNull()
              .contains(8, 51);
    }

    @Test
    public void given_prime_modulus_when_phi_then_expect_correct_prime_minus_one() {
        final Modulus modulus = Modulus.from(5, 37);

        assertThat(modulus.getPhi())
              .isEqualTo(36);
    }

    @Test
    public void given_prime_composite_modulus_when_phi_then_expect_correct_value() {
        final Modulus modulus = Modulus.from(5, 143);

        assertThat(modulus.getPhi())
              .isEqualTo(120);
    }

    @Test
    public void given_non_prime_composite_modulus_when_phi_then_expect_correct_value() {
        final Modulus modulus = Modulus.from(5, 72);

        assertThat(modulus.getPhi())
              .isEqualTo(24);
    }

    @Test
    public void given_exponentiation_with_multiple_base_when_resolve_then_expect_0() {
        final Modulus modulus = Modulus.from(65, 13);

        assertThat(modulus.power(37))
              .isZero();
    }

    @Test
    public void given_exponentiation_with_non_multiple_base_and_multiple_exponent_when_resolve_then_expect_1() {
        final Modulus modulus = Modulus.from(65, 37);

        assertThat(modulus.power(36))
              .isOne();
    }

    @Test
    public void given_exponentiation_with_non_multiple_base_and_non_multiple_exponent_when_square_and_multiply_then_expect_correct_value() {
        final int result = SquareAndMultiply.power(8767,2123, 15);

        assertThat(result)
              .isEqualTo(13);
    }

    @Test
    public void given_another_exponentiation_with_non_multiple_base_and_non_multiple_exponent_when_square_and_multiply_then_expect_correct_value() {
        final int result = SquareAndMultiply.power(1002,3755, 10);

        assertThat(result)
              .isEqualTo(8);
    }

    @Test
    public void given_order_43_when_resolve_then_expect_correct_value() {
        final Modulus modulus = Modulus.from(7,43);
        final Order order = modulus.getOrder(7);

        assertThat(order.resolve())
              .isEqualTo(6);
    }

    @Test
    public void given_order_29_when_resolve_then_expect_correct_value() {
        final Modulus modulus = Modulus.from(7,29);
        final Order order = modulus.getOrder(7);

        assertThat(order.resolve())
              .isEqualTo(7);
    }

    @Test
    public void given_primitiveRoot_when_isPrimitiveRoot_then_expect_true() {
        final Modulus modulus = Modulus.from(2,19);
        final boolean primitiveRoot = modulus.isPrimitiveRoot();

        assertThat(primitiveRoot)
              .isTrue();
    }
}