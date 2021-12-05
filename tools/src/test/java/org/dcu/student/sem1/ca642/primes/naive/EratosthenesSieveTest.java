package org.dcu.student.sem1.ca642.primes.naive;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EratosthenesSieveTest {

    @Test
    public void given_n_equal_2_when_sieveOfEratosthenes_then_expect_2() {

        final int n = 2;
        final List<Integer> primes = EratosthenesSieve.get(n);
        assertThat(primes)
              .isNotNull()
              .isNotEmpty()
              .hasSize(1)
              .contains(2);
    }

    @Test
    public void given_n_equal_3_when_sieveOfEratosthenes_then_expect_2_3() {

        final int n = 3;
        final List<Integer> primes = EratosthenesSieve.get(n);
        assertThat(primes)
              .isNotNull()
              .isNotEmpty()
              .hasSize(2)
              .contains(2)
              .contains(3);
    }

    @Test
    public void given_n_equal_10_when_sieveOfEratosthenes_then_expect_2_3_5_7() {

        final int n = 10;
        final List<Integer> primes = EratosthenesSieve.get(n);
        assertThat(primes)
              .isNotNull()
              .isNotEmpty()
              .hasSize(4)
              .contains(2)
              .contains(3)
              .contains(5)
              .contains(7);
    }
}