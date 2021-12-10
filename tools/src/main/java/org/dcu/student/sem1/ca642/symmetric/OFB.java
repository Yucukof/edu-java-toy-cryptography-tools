package org.dcu.student.sem1.ca642.symmetric;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class OFB extends EncryptionBox {

    private Block vector;

    protected OFB(final Map<Block, Block> box, final String iv) {
        super(box);
        this.vector = Block.from(iv);
    }

    @Override
    public Block revert(final Block cipher) {
        return translate(cipher);
    }

    @Override
    public Block translate(final Block plain) {
        final Block v = map(vector);
        log.debug("{} -> {}", vector, v);
        this.vector = v;

        final Block cipher = v.xor(plain);
        log.info("{} ⊕ {} = {}", v, plain, cipher);
        return cipher;
    }
}
