/*
 * Please respect the author work by not copying abusively this class.
 * Creativity is not a property, this work is free to use, but you'll gain in learning to grow your own.
 *
 * Justin Dekeyser
 */

package genetictimingattack.geneticgreet;

import java.util.List;

@FunctionalInterface
public interface NaturalSelection<T> {

    List<T> computeNextGeneration (List<T> population);

}
