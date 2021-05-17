/*
 * Please respect the author work by not copying abusively this class.
 * Creativity is not a property, this work is free to use, but you'll gain in learning to grow your own.
 *
 * Justin Dekeyser
 */

package genetictimingattack.geneticgreet;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class LevenshteinDistance implements TestFunction<CharSequence> {

    @Override
    public double compute(CharSequence A, CharSequence B) {
        return ApacheBridge.levenshteinDistance(A, B);
    }

    enum ApacheBridge {;
        static final org.apache.commons.text.similarity.LevenshteinDistance INSTANCE
                = org.apache.commons.text.similarity.LevenshteinDistance.getDefaultInstance();
        static int levenshteinDistance (CharSequence A, CharSequence B) {
            return INSTANCE.apply(A, B);
        }
    }

    public static class T {
        LevenshteinDistance dist = new LevenshteinDistance() {};

        @Test
        public void checkDistanceWithSame() {
            assertEquals(0, dist.compute("Hello", "Hello"), 0.0001);
        }

        @Test
        public void checkDistanceIsSymmetric() {
            assertEquals(
                    dist.compute("Hello", "World"),
                    dist.compute("World", "Hello"), 0.0001
            );
        }

        @Test
        public void checkNicheChienExample() {
            assertEquals(5,
                    dist.compute("NICHE", "CHIENS"), 0.0001
            );
        }

        @Test
        public void checkValue() {
            assertEquals(3,
                    dist.compute("examen", "eaxman"), 0.0001
            );
        }

        @Test
        public void check() {
            assertEquals(13,
                    dist.compute("A DVRVQUETDO", "HELLO DISCORD"), 0.0001
            );
        }
    }

}
