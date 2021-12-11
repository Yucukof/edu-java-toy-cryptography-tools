package org.dcu.student.sem1.ca642.asymmetric.blind;

import org.dcu.student.sem1.ca642.asymmetric.encryption.RSA;
import org.dcu.student.sem1.ca642.asymmetric.signatures.FakeHashFunction;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BlindRSATest {

    @Test
    public void given_blinded_message_when_sign_then_expect_correct_signature() {
        final int p = 5;
        final int q = 11;
        final int e = 17;
        final int m = 29;
        final int n = p * q;
        final int b = 7;
        final BlindRSA blind = BlindRSA.from(b, n);

        final int blinded = blind.obfuscate(m, e);

        final RSA.PrivateKey privateKey = RSA.PrivateKey.from(p, q, e);
        final int signed = privateKey.sign(blinded, new FakeHashFunction());

        assertThat(signed).isEqualTo(3);
    }

    @Test
    public void given_message_when_blind_then_expect_correct_blinded_message() {
        final int p = 5;
        final int q = 11;
        final int e = 17;
        final int m = 29;
        final int n = p * q;
        final int b = 7;
        final BlindRSA blind = BlindRSA.from(b, n);

        final int blinded = blind.obfuscate(m, e);

        assertThat(blinded).isEqualTo(53);
    }

    @Test
    public void given_signature_when_unblind_then_expect_correct_unblinded() {
        final int p = 5;
        final int q = 11;
        final int e = 17;
        final int m = 29;
        final int n = p * q;
        final int b = 7;
        final BlindRSA blind = BlindRSA.from(b, n);

        final int blinded = blind.obfuscate(m, e);

        final RSA.PrivateKey privateKey = RSA.PrivateKey.from(p, q, e);
        final int signed = privateKey.sign(blinded, new FakeHashFunction());
        final int unblinded = blind.reveal(signed, e);

        assertThat(unblinded).isEqualTo(24);
    }

}