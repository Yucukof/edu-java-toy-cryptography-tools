package org.dcu.student.sem1.ca642.asymmetric.signatures;

import org.dcu.student.sem1.ca642.asymmetric.encryption.RSA;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RSATest {

    @Test
    public void given_valid_message_and_private_key_when_sign_then_expect_correct_signature() {

        final RSA.PrivateKey privateKey = RSA.PrivateKey.from(3, 11, 17);

        final int hash = 29;
        final int expected = 2;

        final int signature = privateKey.sign(hash, new FakeHashFunction());
        assertThat(signature)
              .isEqualTo(expected);
    }

    @Test
    public void given_valid_signature_and_public_key_and_cipher_when_check_then_expect_true() {

        final RSA.PrivateKey privateKey = RSA.PrivateKey.from(3, 11, 17);
        final RSA.PublicKey publicKey = privateKey.getPublicKey();

        final int m = 29;

        final HashFunction hashFunction = new FakeHashFunction();

        final int c = privateKey.encrypt(m);
        final int signature = privateKey.sign(m, hashFunction);

        final boolean check = publicKey.verify(signature, c, hashFunction);
        assertThat(check).isTrue();
    }

}