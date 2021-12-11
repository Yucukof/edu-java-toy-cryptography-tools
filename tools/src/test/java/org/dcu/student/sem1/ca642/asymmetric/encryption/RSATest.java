package org.dcu.student.sem1.ca642.asymmetric.encryption;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RSATest {

    @Test
    public void given_ciphertext_and_valid_primary_key_when_decrypt_then_expect_correct_plaintext() {
        final RSA.PrivateKey privateKey = new RSA.PrivateKey(7, 11, 37);

        final int c = privateKey.decrypt(51);
        assertThat(c)
              .isEqualTo(2);
    }

    @Test
    public void given_plaintext_and_valid_public_key_when_encrypt_then_expect_correct_ciphertext() {
        final RSA.PrivateKey privateKey = new RSA.PrivateKey(7, 11, 37);
        final RSA.PublicKey publicKey = privateKey.getPublicKey();

        final int c = publicKey.encrypt(2);
        assertThat(c)
              .isEqualTo(51);
    }

    @Test
    public void given_valid_parameter_when_new_PrivateKey_then_expect_correct_d_and_n() {
        final RSA.PrivateKey privateKey = new RSA.PrivateKey(7, 11, 37);

        assertThat(privateKey)
              .isNotNull();

        assertThat(privateKey.getN())
              .isEqualTo(77);
        assertThat(privateKey.getE())
              .isEqualTo(37);
        assertThat(privateKey.getD())
              .isEqualTo(13);
    }

    @Test
    public void given_valid_primary_key_when_getPublicKey_then_expect_correct_e_and_n() {
        final RSA.PrivateKey privateKey = new RSA.PrivateKey(7, 11, 37);
        final RSA.PublicKey publicKey = privateKey.getPublicKey();

        assertThat(publicKey)
              .isNotNull();

        assertThat(publicKey.getE())
              .isEqualTo(37);
        assertThat(publicKey.getN())
              .isEqualTo(77);
    }

}