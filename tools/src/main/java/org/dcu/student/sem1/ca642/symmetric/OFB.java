package org.dcu.student.sem1.ca642.symmetric;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class OFB extends EncryptionBox {

    private Block vector;

    protected OFB(final Map<Block, Block> box, final String iv) {
        super(box);
        this.vector = Block.from(iv);
        log.info("IV: {}", iv);

    }

    @Override
    public Block revert(final Block cipher) {
        return translate(cipher);
    }

    @Override
    public Block translate(final Block plain) {

        log.debug("Translating vector...");
        final Block v = map(vector);

        log.debug("XORing vector & plain...");
        final Block cipher = v.xor(plain);
        log.debug("{} ⊕ {} = {}", v, plain, cipher);

        log.debug("Updating vector...");
        this.vector = v;
        log.debug("V = {}", v);

        return cipher;
    }
}
