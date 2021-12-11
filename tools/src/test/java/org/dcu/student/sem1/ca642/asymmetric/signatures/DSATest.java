package org.dcu.student.sem1.ca642.asymmetric.signatures;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DSATest {

    @Test
    public void given_valid_message_and_private_key_when_sign_then_expect_correct_signature() {

        final DSA.PrivateKey privateKey = DSA.PrivateKey.from(23, 11, 4, 10);

        final int message = 19;
        final int k = 3;

        final DSA.Message signature = privateKey.sign(message, k, new FakeHashFunction());
        assertThat(signature)
              .isNotNull();

        assertThat(signature.getS1())
              .isEqualTo(7);
        assertThat(signature.getS2())
              .isEqualTo(4);
    }

    @Test
    public void given_valid_signature_and_public_key_and_cipher_when_check_then_expect_true() {

        final DSA.PrivateKey privateKey = DSA.PrivateKey.from(23, 11, 4, 10);
        final DSA.PublicKey publicKey = privateKey.getPublicKey();

        final int m = 19;
        final int k = 3;

        final HashFunction hashFunction = new FakeHashFunction();

        final DSA.Message signature = privateKey.sign(m, k, hashFunction);

        final boolean check = publicKey.verify(m, signature, hashFunction);
        assertThat(check).isTrue();
    }

}