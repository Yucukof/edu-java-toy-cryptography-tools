package org.dcu.student.sem1.ca642.symmetric;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ComparisonTest {

    private static final Map<Block, Block> map = new HashMap<>();
    private static final String input = "0110111000011010";
    private static final String iv = "1011";

    private EncryptionBox box;

    @BeforeClass
    public static void setup() {
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
    }

    @Test
    public void CBC() {

        box = new CBC(map, iv);
        final String output = box.encrypt(input);

        assertThat(output)
              .isNotNull()
              .isNotBlank();
    }

    @Test
    public void CFB() {

        box = new CFB(map, iv);
        final String output = box.encrypt(input);

        assertThat(output)
              .isNotNull()
              .isNotBlank();
    }

    @Test
    public void Counter() {

        box = new Counter(map, iv);
        final String output = box.encrypt(input);

        assertThat(output)
              .isNotNull()
              .isNotBlank();
    }

    @Test
    public void ECB() {

        box = new ECB(map);
        final String output = box.encrypt(input);

        assertThat(output)
              .isNotNull()
              .isNotBlank();
    }

    @Test
    public void OFB() {

        box = new OFB(map, iv);
        final String output = box.encrypt(input);

        assertThat(output)
              .isNotNull()
              .isNotBlank();
    }
}