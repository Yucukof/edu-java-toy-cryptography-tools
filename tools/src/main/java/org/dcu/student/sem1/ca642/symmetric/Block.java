package org.dcu.student.sem1.ca642.symmetric;

import lombok.Value;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Value
public class Block {

    String value;
    int length;
    int size;

    public Block(final String value) {
        this.value = value;
        this.length = value.length();
        this.size = (int) Math.pow(2, length);
    }

    public static List<Block> toBlocks(final String message, final int blockSize) {

        final int length = message.length();

        isValidLength(length, blockSize);

        final int blockCount = length / blockSize;
        return IntStream.range(0, blockCount)
              .map(i -> i * blockSize)
              .mapToObj(i -> message.substring(i, i + blockSize))
              .map(Block::from)
              .collect(Collectors.toList());
    }

    private static void isValidLength(final int length, final int blockSize) {
        if (length % blockSize != 0) {
            throw new IllegalArgumentException(String.format("Invalid message length! (%s mod %s != 0)", length, blockSize));
        }
    }

    public static Block from(final String value) {
        return new Block(value);
    }

    static String toBlock(final List<Block> encryptedBlocks) {
        return encryptedBlocks.stream()
              .map(Block::getValue)
              .collect(Collectors.joining());
    }

    public Block decrement() {
        final int decrement = -1;
        return add(decrement);
    }

    Block add(final int i) {
        final int val = Integer.parseUnsignedInt(value, 2) + i;
        final String str = Integer.toString(val % size, 2);
        final String inc = StringUtils.leftPad(str, length, "0");
        return Block.from(inc);
    }

    public Block increment() {
        final int increment = 1;
        return add(increment);
    }

    @Override
    public String toString() {
        return value;
    }

    public Block xor(final Block that) {
        final int thisInt = this.toInt();
        final int thatInt = that.toInt();
        final int xor = thisInt ^ thatInt;
        final String str = Integer.toString(xor, 2);
        final String padded = StringUtils.leftPad(str, length, "0");
        return Block.from(padded);
    }

    public int toInt() {
        return Integer.parseUnsignedInt(this.value, 2);
    }

}
