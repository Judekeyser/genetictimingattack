/*
 * Please respect the author work by not copying abusively this class.
 * Creativity is not a property, this work is free to use, but you'll gain in learning to grow your own.
 *
 * Justin Dekeyser
 */

package genetictimingattack.geneticgreet;

import java.util.Collection;

@FunctionalInterface
public interface EvolutionPlan<T> {

    void compute(Collection<T> randomInitialGenomes, int maxIterationSize);

}

