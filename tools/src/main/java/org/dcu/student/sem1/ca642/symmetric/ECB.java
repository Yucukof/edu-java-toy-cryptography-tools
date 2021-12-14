package org.dcu.student.sem1.ca642.symmetric;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class ECB extends EncryptionBox {

    public ECB(final Map<Block, Block> box) {
        super(box);
    }

    @Override
    public Block revert(final Block cipher) {
        return unmap(cipher);
    }

    @Override
    public Block translate(final Block plain) {
        log.debug("Translating block...");
        return map(plain);
    }
}
