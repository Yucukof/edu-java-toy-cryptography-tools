package org.dcu.student.sem1.ca642.symmetric;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class CFB extends EncryptionBox {

    private Block vector;

    protected CFB(final Map<Block, Block> box, final String iv) {
        super(box);
        this.vector = Block.from(iv);
        log.info("IV: {}", iv);
    }


    @Override
    public Block revert(final Block cipher) {

        log.debug("Translating vector...");
        final Block v = map(vector);

        log.debug("XORing vector & plain...");
        final Block plain = v.xor(cipher);
        log.info("{} ⊕ {} = {}", v, cipher, plain);

        log.debug("Updating vector...");
        this.vector = cipher;
        log.debug("V = {}", cipher);

        return plain;
    }

    @Override
    public Block translate(final Block plain) {

        log.debug("Translating vector...");
        final Block v = map(vector);

        log.debug("XORing vector & plain...");
        final Block cipher = v.xor(plain);
        log.info("{} ⊕ {} = {}", v, plain, cipher);

        log.debug("Updating vector...");
        this.vector = cipher;
        log.debug("V = {}", cipher);

        return cipher;
    }
}
