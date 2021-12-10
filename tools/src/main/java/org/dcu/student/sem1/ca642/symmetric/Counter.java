package org.dcu.student.sem1.ca642.symmetric;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class Counter extends EncryptionBox {

    private Block vector;

    protected Counter(final Map<Block, Block> box, final String str) {
        super(box);
        this.vector = Block.from(str);
    }

    @Override
    public Block revert(final Block cipher) {
        return translate(cipher);
    }

    @Override
    public Block translate(final Block plain) {
        final Block v = map(vector);
        final Block cipher = v.xor(plain);
        log.info("{} ⊕ {} = {}", v, plain, cipher);
        this.vector = vector.increment();
        return cipher;
    }
}
