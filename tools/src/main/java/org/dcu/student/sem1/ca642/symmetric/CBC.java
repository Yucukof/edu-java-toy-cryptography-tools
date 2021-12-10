package org.dcu.student.sem1.ca642.symmetric;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class CBC extends EncryptionBox {

    private Block vector;

    public CBC(final Map<Block, Block> box, final String iv) {
        super(box);
        this.vector = Block.from(iv);
    }

    @Override
    public Block revert(final Block cipher) {
        final Block xor = unmap(cipher);
        final Block plain = vector.xor(xor);
        log.debug("{} ⊕ {} = {}", xor, vector, plain);
        this.vector = cipher;
        return plain;
    }

    @Override
    public Block translate(final Block plain) {
        final Block xor = vector.xor(plain);
        log.debug("{} ⊕ {} = {}", plain, vector, xor);
        final Block cipher = map(xor);
        this.vector = cipher;
        return cipher;
    }

}
