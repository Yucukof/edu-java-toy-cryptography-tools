package org.dcu.student.sem1.ca642.asymmetric.encryption;


import org.dcu.student.sem1.ca642.asymmetric.encryption.Rabin;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RabinTest {

    @Test
    public void given_large_privateKey_and_valid_ciphertext_when_decrypt_then_expect_correct_plaintexts_list() {
        final int p = 127;
        final int q = 131;

        final Rabin.RabinPrivateKey privateKey = Rabin.RabinPrivateKey.builder()
              .p(p)
              .q(q)
              .build();

        assertThat(privateKey).isNotNull();
        assertThat(privateKey.isValid()).isTrue();

        final List<Integer> ms = Rabin.decrypt(16084, privateKey);
        assertThat(ms)
              .isNotNull()
              .hasSize(4)
              .contains(4410);
    }

    @Test
    public void given_large_publicKey_when_encrypt_then_expect_ciphertext() {
        final int p = 127;
        final int q = 131;

        final Rabin.RabinPrivateKey privateKey = Rabin.RabinPrivateKey.builder()
              .p(p)
              .q(q)
              .build();

        assertThat(privateKey).isNotNull();
        assertThat(privateKey.isValid()).isTrue();

        final Rabin.RabinPublicKey publicKey = privateKey.getPublicKey();
        assertThat(publicKey).isNotNull();
        assertThat(publicKey.getN()).isEqualTo(16637);

        final long c = Rabin.encrypt(4410, publicKey);
        assertThat(c).isEqualTo(16084);
    }

    @Test
    public void given_simple_privateKey_and_valid_ciphertext_when_decrypt_then_expect_correct_plaintexts_list() {
        final int p = 7;
        final int q = 11;

        final Rabin.RabinPrivateKey privateKey = Rabin.RabinPrivateKey.builder()
              .p(p)
              .q(q)
              .build();

        assertThat(privateKey).isNotNull();
        assertThat(privateKey.isValid()).isTrue();

        final List<Integer> solutions = Rabin.decrypt(37, privateKey);
        assertThat(solutions)
              .isNotNull()
              .hasSize(4)
              .contains(24,31,46,53);
    }

    @Test
    public void given_valid_privateKey_with_large_parameters_when_getPublicKey_then_expect_n() {
        final int p = 127;
        final int q = 131;

        final Rabin.RabinPrivateKey privateKey = Rabin.RabinPrivateKey.builder()
              .p(p)
              .q(q)
              .build();

        assertThat(privateKey).isNotNull();
        assertThat(privateKey.isValid()).isTrue();

        final Rabin.RabinPublicKey publicKey = privateKey.getPublicKey();
        assertThat(publicKey).isNotNull();
        assertThat(publicKey.getN()).isEqualTo(16637);
    }

}