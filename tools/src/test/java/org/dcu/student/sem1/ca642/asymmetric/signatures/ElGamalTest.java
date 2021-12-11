package org.dcu.student.sem1.ca642.asymmetric.signatures;

import org.dcu.student.sem1.ca642.asymmetric.encryption.ElGamal;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ElGamalTest {

    @Test
    public void given_valid_message_and_private_key_when_sign_then_expect_correct_signature() {

        final ElGamal.PrivateKey privateKey = ElGamal.PrivateKey.from(29, 3, 11);

        final int hash = 19;
        final int k = 3;

        final ElGamal.Ciphertext signature = privateKey.sign(hash, k, new FakeHashFunction());
        assertThat(signature)
              .isNotNull();

        assertThat(signature.getC1())
              .isEqualTo(27);
        assertThat(signature.getC2())
              .isEqualTo(10);
    }

    @Test
    public void given_valid_signature_and_public_key_and_cipher_when_check_then_expect_true() {

        final ElGamal.PrivateKey privateKey = ElGamal.PrivateKey.from(29, 3, 11);
        final ElGamal.PublicKey publicKey = privateKey.getPublicKey();

        final int m = 19;
        final int k = 3;

        final HashFunction hashFunction = new FakeHashFunction();

        final ElGamal.Ciphertext signature = privateKey.sign(m, k, hashFunction);

        final boolean check = publicKey.verify(m, signature, hashFunction);
        assertThat(check).isTrue();
    }

}