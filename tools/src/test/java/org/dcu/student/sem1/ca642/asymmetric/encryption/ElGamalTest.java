package org.dcu.student.sem1.ca642.asymmetric.encryption;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ElGamalTest {

    private static ElGamal.PrivateKey privateKey;

    @BeforeClass
    public static void setUp(){
        final int p = 17;
        final int g = 6;
        final int x = 5;

        privateKey = ElGamal.PrivateKey.from(p,g,x);

    }

    @Test
    public void given_parameters_when_generating_publicKey_then_expect_correct_y() {

        final ElGamal.PublicKey publicKey = privateKey.getPublicKey();
        assertThat(publicKey).isNotNull();

        final int y = publicKey.getValue();
        assertThat(y).isEqualTo(7);
    }

    @Test
    public void given_simple_privateKey_and_valid_ciphertext_when_decrypt_then_expect_correct_plaintext() {

        final int c1 = 15;
        final int c2 = 9;
        
        final ElGamal.Ciphertext c = new ElGamal.Ciphertext(c1, c2);

        final int m = privateKey.decrypt(c);

        assertThat(m)
              .isEqualTo(13);
    }

    @Test
    public void given_simple_publicKey_and_valid_plaintext_when_encrypt_then_expect_correct_ciphertext() {

        final int m = 13;
        final int k = 10;

        final int y = 7;

        final ElGamal.PublicKey publicKey = privateKey.getPublicKey();
        assertThat(publicKey).isNotNull();

        assertThat(publicKey.getValue())
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