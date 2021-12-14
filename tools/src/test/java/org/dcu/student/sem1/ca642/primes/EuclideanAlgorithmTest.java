package org.dcu.student.sem1.ca642.primes;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dcu.student.sem1.ca642.primes.EuclideanAlgorithm.gcd;

public class EuclideanAlgorithmTest {

    @Test
    public void given_prime_numbers_when_gcd_then_expect_1() {
        assertThat(gcd(2,3))
              .isOne();
    }

    @Test
    public void given_non_prime_numbers_2_4_when_gcd_then_expect_2() {
        assertThat(gcd(2,4))
              .isEqualTo(2);
    }

    @Test
    public void given_non_prime_numbers_24_18_when_gcd_then_expect_6() {
        assertThat(gcd(18,24))
              .isEqualTo(6);
    }

}