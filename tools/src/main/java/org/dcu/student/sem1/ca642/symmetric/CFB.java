package org.dcu.student.sem1.ca642.symmetric;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class CFB extends EncryptionBox {

    private Block vector;

    protected CFB(final Map<Block, Block> box, final String iv) {
        super(box);
        this.vector = Block.from(iv);
    }


    @Override
    public Block revert(final Block cipher) {
        final Block v = map(vector);
        final Block plain = v.xor(cipher);
        log.info("{} ⊕ {} = {}", v, cipher, plain);
        this.vector = cipher;
        return plain;
    }

    @Override
    public Block translate(final Block plain) {
        final Block v = map(vector);
        final Block cipher = v.xor(plain);
        log.info("{} ⊕ {} = {}", v, plain, cipher);
        this.vector = cipher;
        return cipher;
    }
}
