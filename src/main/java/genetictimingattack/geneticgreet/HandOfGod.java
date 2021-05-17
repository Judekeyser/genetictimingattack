/*
 * Please respect the author work by not copying abusively this class.
 * Creativity is not a property, this work is free to use, but you'll gain in learning to grow your own.
 *
 * Justin Dekeyser
 */

package genetictimingattack.geneticgreet;

import java.util.Random;

public class HandOfGod {

    private final static Random rd = new Random(15458431487L);
    private final static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ";

    static char randomCharacter () {
        return alphabet.charAt(randomInteger(alphabet.length()));
    }

    static int randomInteger (final int maxBound) {
        return rd.nextInt(maxBound);
    }

    static boolean randomTrue(double probability) {
        return rd.nextDouble() < probability;
    }

}
