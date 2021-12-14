package org.dcu.student.sem1.ca642.symmetric;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class CBC extends EncryptionBox {

    private Block vector;

    public CBC(final Map<Block, Block> box, final String iv) {
        super(box);
        this.vector = Block.from(iv);
        log.info("IV: {}", iv);
    }

    @Override
    public Block revert(final Block cipher) {

        log.debug("Translating XORed block...");
        final Block xor = unmap(cipher);

        log.debug("XORing vector & plain...");
        final Block plain = vector.xor(xor);
        log.debug("{} ⊕ {} = {}", xor, vector, plain);

        log.debug("Updating vector...");
        this.vector = cipher;
        log.debug("V = {}", cipher);

        return plain;
    }

    @Override
    public Block translate(final Block plain) {

        log.debug("XORing vector & plain...");
        final Block xor = vector.xor(plain);
        log.debug("{} ⊕ {} = {}", plain, vector, xor);

        log.debug("Translating XORed block...");
        final Block cipher = map(xor);

        log.debug("Updating vector...");
        this.vector = cipher;
        log.debug("V = {}", cipher);

        return cipher;
    }

}
