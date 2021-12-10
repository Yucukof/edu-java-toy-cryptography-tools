package org.dcu.student.sem1.ca642.symmetric;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CFBTest {

    private static CFB box;

    @Test
    public void given_valid_box_and_ciphertext_when_decrypt_then_expect_correct_plaintext() {

        final String plaintext = "00011011";
        final String ciphertext = box.decrypt(plaintext);
        assertThat(ciphertext)
              .isNotNull()
              .isEqualTo("11000000");
    }

    @Test
    public void given_valid_box_and_plaintext_when_encrypt_then_expect_correct_ciphertext() {

        final String plaintext = "00011011";
        final String ciphertext = box.encrypt(plaintext);
        assertThat(ciphertext)
              .isNotNull()
              .isEqualTo("11010010");
    }

    @Before
    public void setBox() {
        final Map<Block, Block> map = new HashMap<>();
        map.put(Block.from("00"), Block.from("01"));
        map.put(Block.from("01"), Block.from("10"));
        map.put(Block.from("10"), Block.from("11"));
        map.put(Block.from("11"), Block.from("00"));
        box = new CFB(map, "10");
    }

}