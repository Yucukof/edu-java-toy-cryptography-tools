package org.dcu.student.sem1.ca642.symmetric;

import org.dcu.student.sem1.ca642.symmetric.Block;
import org.dcu.student.sem1.ca642.symmetric.OFB;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class OFBTest {

    @Test
    public void given_complex_box_and_plaintext_when_encrypt_then_expect_correct_ciphertext() {

        final OFB box = setComplexBox();

        final String plaintext = "0110110000011010";
        final String ciphertext = box.encrypt(plaintext);
        assertThat(ciphertext)
              .isNotNull()
              .isEqualTo("0011001110110110");
    }

    public OFB setComplexBox() {
        final Map<Block, Block> map = new HashMap<>();
        map.put(Block.from("0000"), Block.from("1101"));
        map.put(Block.from("0001"), Block.from("0111"));
        map.put(Block.from("0010"), Block.from("1001"));
        map.put(Block.from("0011"), Block.from("0000"));
        map.put(Block.from("0100"), Block.from("1011"));
        map.put(Block.from("0101"), Block.from("1111"));
        map.put(Block.from("0110"), Block.from("0001"));
        map.put(Block.from("0111"), Block.from("0010"));
        map.put(Block.from("1000"), Block.from("1110"));
        map.put(Block.from("1001"), Block.from("0011"));
        map.put(Block.from("1010"), Block.from("1100"));
        map.put(Block.from("1011"), Block.from("0101"));
        map.put(Block.from("1100"), Block.from("0100"));
        map.put(Block.from("1101"), Block.from("0110"));
        map.put(Block.from("1110"), Block.from("1000"));
        map.put(Block.from("1111"), Block.from("1010"));
        return new OFB(map, "1011");
    }

    @Test
    public void given_valid_box_and_ciphertext_when_decrypt_then_expect_correct_plaintext() {

        final OFB box = setBox();

        final String plaintext = "00011011";
        final String ciphertext = box.decrypt(plaintext);
        assertThat(ciphertext)
              .isNotNull()
              .isEqualTo("11011101");
    }

    public OFB setBox() {
        final Map<Block, Block> map = new HashMap<>();
        map.put(Block.from("00"), Block.from("01"));
        map.put(Block.from("01"), Block.from("10"));
        map.put(Block.from("10"), Block.from("11"));
        map.put(Block.from("11"), Block.from("00"));
        return new OFB(map, "10");
    }

    @Test
    public void given_valid_box_and_plaintext_when_encrypt_then_expect_correct_ciphertext() {

        final OFB box = setBox();

        final String plaintext = "00011011";
        final String ciphertext = box.encrypt(plaintext);
        assertThat(ciphertext)
              .isNotNull()
              .isEqualTo("11011101");
    }

}