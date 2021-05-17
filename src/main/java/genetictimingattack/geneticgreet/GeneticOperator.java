/*
 * Please respect the author work by not copying abusively this class.
 * Creativity is not a property, this work is free to use, but you'll gain in learning to grow your own.
 *
 * Justin Dekeyser
 */

package genetictimingattack.geneticgreet;

public interface GeneticOperator<T> {

    T mutation (T individual);

    T crossOver (T a, T b);

}
