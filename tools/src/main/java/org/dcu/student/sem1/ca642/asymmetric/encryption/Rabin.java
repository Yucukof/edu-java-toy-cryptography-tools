package org.dcu.student.sem1.ca642.asymmetric.encryption;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.dcu.student.sem1.ca642.modulus.exponentiation.Exponentiation;
import org.dcu.student.sem1.ca642.modulus.roots.SquareRoot;
import org.dcu.student.sem1.ca642.modulus.roots.SquareRootComposite;

import java.util.Set;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Rabin {

    @Value
    public static class PrivateKey {

        /**
         * A co-prime $p$
         *
         * @see #q
         * @see #isValid()
         */
        int p;
        /**
         * A co-prime $q$
         *
         * @see #p
         * @see #isValid()
         */
        int q;

        public PrivateKey(final int p, final int q) {
            this.p = p;
            this.q = q;
        }

        public static PrivateKey from(final int p, final int q) {
            return new PrivateKey(p, q);
        }

        public Set<Integer> decrypt(final int c) {

            log.info("Decrypting {}", c);

            log.debug("p = {}", p);
            log.debug("q = {}", q);

            final SquareRoot roots = new SquareRootComposite(c, p, q);
            final Set<Integer> messages = roots.get();
            log.info("Solutions = {}", messages);
            return messages;
        }

        public PublicKey getPublicKey() {
            log.debug("n = p x q");
            final int n = p * q;
            log.debug("{} x {} = {}\n", p, q, n);
            return PublicKey.from(n);
        }

        public boolean isValid() {
            return (p % 4) == 3
                  && (q % 4) == 3;
        }
    }

    @Value
    @Builder
    public static class PublicKey {

        /**
         * The private key component resulting from the combination of the co-primes {@link PrivateKey#p $p$} and
         * {@link PrivateKey#q $q$}.
         */
        int n;

        private static PublicKey from(final int n) {
            return builder()
                  .n(n)
                  .build();
        }

        /**
         * Encrypts a plaintext $m$ using the public key $n$ as $c=(m^2)\pmod{n}$.
         *
         * @param m the plaintext to encrypt.
         * @return a ciphertext.
         */
        public int encrypt(final int m) {
            log.info("Encrypting message m [{}]...", m);
            log.info("Calculating m²(mod n)");
            final int result = Exponentiation.compute(m, 2, n);
            log.debug("{}^2 (mod {})={}", m, n, result);
            return result;
        }
    }
}
