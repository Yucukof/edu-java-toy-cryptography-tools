package org.dcu.student.sem1.ca642.symmetric;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public abstract class EncryptionBox {

    private final Map<Block, Block> box;
    private final Map<Block, Block> reverseBox;

    protected EncryptionBox(final Map<Block, Block> box) {
        this.box = box;
        this.reverseBox = getReverseBox(box);
    }

    private static Map<Block, Block> getReverseBox(final Map<Block, Block> box) {
        return box.entrySet().stream()
              .map(entry -> new ImmutablePair<>(entry.getValue(), entry.getKey()))
              .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    public String decrypt(final String cipher) {

        assert isValid();

        log.info("Ciphertext = [{}]", cipher);

        final List<Block> blocks = Block.toBlocks(cipher, getBlockSize());
        log.debug("Blocks = {}", blocks);

        final String plain = reprocess(blocks);
        log.info("Plaintext = [{}]", plain);
        return plain;
    }

    public boolean isValid() {
        return hasConstantBlockSize(box.keySet())
              && hasConstantBlockSize(box.values())
              && producesSameBlockSize();
    }

    public int getBlockSize() {
        return getBlockSize(box.keySet());
    }

    private String reprocess(final List<Block> blocks) {
        return blocks.stream()
              .map(this::revert)
              .map(Block::getValue)
              .collect(Collectors.joining());
    }

    private boolean hasConstantBlockSize(final Collection<Block> blocks) {
        return blocks.stream()
              .map(Block::getLength)
              .distinct()
              .count() == 1;
    }

    private boolean producesSameBlockSize() {
        return getBlockSize(box.keySet()) == getBlockSize(box.values());
    }

    private int getBlockSize(final Collection<Block> strings) {
        return strings.stream()
              .map(Block::getLength)
              .distinct()
              .findAny()
              .orElse(0);
    }

    public abstract Block revert(final Block cipher);

    public String encrypt(final String plain) {

        assert isValid();

        log.info("Plaintext = [{}]", plain);

        final List<Block> blocks = Block.toBlocks(plain, getBlockSize());
        log.debug("Blocks = {}", blocks);

        final String cipher = process(blocks);
        log.info("Ciphertext = [{}]", cipher);
        return cipher;
    }

    private String process(final List<Block> blocks) {
        return blocks.stream()
              .map(this::translate)
              .map(Block::getValue)
              .collect(Collectors.joining());
    }

    public abstract Block translate(final Block plain);

    public Block map(final Block plain) {
        final Block cipher = box.get(plain);
        log.debug("{} -> {}", plain, cipher);
        return cipher;
    }

    public Block unmap(final Block cipher) {
        final Block plain = reverseBox.get(cipher);
        log.debug("{} -> {}", cipher, plain);
        return plain;
    }

}
