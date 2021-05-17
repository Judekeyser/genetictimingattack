/*
 * Please respect the author work by not copying abusively this class.
 * Creativity is not a property, this work is free to use, but you'll gain in learning to grow your own.
 *
 * Justin Dekeyser
 */

package genetictimingattack.geneticgreet;

import org.junit.Test;

import static genetictimingattack.geneticgreet.HandOfGod.randomCharacter;
import static genetictimingattack.geneticgreet.HandOfGod.randomInteger;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.String.valueOf;
import static java.lang.System.arraycopy;

public abstract class StringGeneticOperator implements GeneticOperator<String> {

    @Override
    public String mutation (String individual) {
        char[] genetic = individual.toCharArray();
        int howManyHit = randomInteger(max(2, genetic.length / 3));
        for(; howManyHit > 0; howManyHit--)
            genetic[randomInteger (genetic.length)] = randomCharacter();
        return valueOf (genetic);
    }

    @Override
    public String crossOver (String a, String b) {
        char[] aGenetic = a.toCharArray();
        char[] bGenetic = b.toCharArray();
        int aCut = randomInteger(min(aGenetic.length, bGenetic.length));

        char[] crossOver = new char[bGenetic.length];
        arraycopy(aGenetic, 0, crossOver, 0, aCut);
        arraycopy(bGenetic, aCut, crossOver, aCut, bGenetic.length - aCut);
        return valueOf (crossOver);
    }

    public static class T {
        @Test
        public void crossOver_mergesCorrectly() {
            var a = "Hello World";
            var b = "chaton";

            var op = new StringGeneticOperator() {};

            System.out.println(op.crossOver(a, b));
            System.out.println(op.crossOver(a, b));
            System.out.println(op.crossOver(a, b));
            System.out.println(op.crossOver(a, b));
        }
        @Test
        public void mutation_mutatesACharacter() {
            var a = "Hello World";

            var op = new StringGeneticOperator() {};

            System.out.println(op.mutation(a));
            System.out.println(op.mutation(a));
            System.out.println(op.mutation(a));
            System.out.println(op.mutation(a));
        }
    }

}
