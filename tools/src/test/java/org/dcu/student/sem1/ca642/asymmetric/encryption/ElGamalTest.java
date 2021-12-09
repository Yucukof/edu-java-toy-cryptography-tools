package org.dcu.student.sem1.ca642.asymmetric.encryption;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.dcu.student.sem1.ca642.asymmetric.encryption.ElGamal;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ElGamalTest {

    @Test
    public void given_parameters_when_generating_publicKey_then_expect_correct_y() {
        final int p = 17;
        final int g = 6;
        final int x = 5;

        final ElGamal.EGPrivateKey privateKey = ElGamal.EGPrivateKey.builder()
              .prime(p)
              .generator(g)
              .exponent(x)
              .build();

        final ElGamal.EGPublicKey publicKey = privateKey.getPublicKey();
        assertThat(publicKey).isNotNull();

        final int y = publicKey.getValue();
        assertThat(y).isEqualTo(7);
    }

    @Test
    public void given_simple_publicKey_and_valid_plaintext_when_encrypt_then_expect_correct_ciphertext() {

        final int p = 17;
        final int g = 6;
        final int x = 5;

        final ElGamal.EGPrivateKey privateKey = ElGamal.EGPrivateKey.builder()
              .prime(p)
              .generator(g)
              .exponent(x)
              .build();

        final ElGamal.EGPublicKey publicKey = privateKey.getPublicKey();
        assertThat(publicKey).isNotNull();

        assertThat(publicKey.getValue())
              .isEqualTo(7);

        final int m = 13;
        final int k = 10;

        final Pair<Integer, Integer> encrypted = ElGamal.encrypt(m, k, publicKey);
        assertThat(encrypted).isNotNull();

        assertThat(encrypted.getLeft())
              .isNotNull()
              .isEqualTo(15);

        assertThat(encrypted.getRight())
              .isNotNull()
              .isEqualTo(9);
    }

    @Test
    public void given_simple_privateKey_and_valid_ciphertext_when_decrypt_then_expect_correct_plaintext() {
        final int p = 17;
        final int g = 6;
        final int x = 5;

        final ElGamal.EGPrivateKey privateKey = ElGamal.EGPrivateKey.builder()
              .prime(p)
              .generator(g)
              .exponent(x)
              .build();

        final int c1 = 15;
        final int c2 = 9;

        final Pair<Integer, Integer> c = new ImmutablePair<>(c1, c2);

        final int m = ElGamal.decrypt(c, privateKey);

        assertThat(m)
              .isEqualTo(13);
    }
}