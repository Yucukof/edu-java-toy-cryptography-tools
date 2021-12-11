package org.dcu.student.sem1.ca642.asymmetric.encryption;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ElGamalTest {

    @Test
    public void given_parameters_when_generating_publicKey_then_expect_correct_y() {

        final ElGamal.PrivateKey privateKey = ElGamal.PrivateKey.from(17, 6, 5);

        final ElGamal.PublicKey publicKey = privateKey.getPublicKey();
        assertThat(publicKey).isNotNull();

        final int y = publicKey.getY();
        assertThat(y).isEqualTo(7);
    }

    @Test
    public void given_simple_privateKey_and_valid_ciphertext_when_decrypt_then_expect_correct_plaintext() {

        final ElGamal.PrivateKey privateKey = ElGamal.PrivateKey.from(17, 6, 5);

        final int c1 = 15;
        final int c2 = 9;

        final ElGamal.Ciphertext c = new ElGamal.Ciphertext(c1, c2);

        final int m = privateKey.decrypt(c);

        assertThat(m)
              .isEqualTo(13);
    }

    @Test
    public void given_simple_privateKey_and_valid_ciphertext_when_encrypt_decrypt_then_expect_same_message() {

        final ElGamal.PrivateKey privateKey = ElGamal.PrivateKey.from(23, 4, 10);
        final ElGamal.PublicKey publicKey = privateKey.getPublicKey();

        final int m = 19;
        final int k = 3;

        final ElGamal.Ciphertext c = publicKey.encrypt(m, k);
        final int actual = privateKey.decrypt(c);

        assertThat(m).isEqualTo(actual);
    }

    @Test
    public void given_simple_publicKey_and_valid_plaintext_when_encrypt_then_expect_correct_ciphertext() {

        final ElGamal.PrivateKey privateKey = ElGamal.PrivateKey.from(17, 6, 5);

        final int m = 13;
        final int k = 10;

        final int y = 7;

        final ElGamal.PublicKey publicKey = privateKey.getPublicKey();
        assertThat(publicKey).isNotNull();

        assertThat(publicKey.getY())
              .isEqualTo(y);

        final ElGamal.Ciphertext encrypted = publicKey.encrypt(m, k);
        assertThat(encrypted)
              .isNotNull();

        assertThat(encrypted.getC1())
              .isEqualTo(15);

        assertThat(encrypted.getC2())
              .isEqualTo(9);
    }
}