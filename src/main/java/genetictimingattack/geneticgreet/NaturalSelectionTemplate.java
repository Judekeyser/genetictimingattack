/*
 * Please respect the author work by not copying abusively this class.
 * Creativity is not a property, this work is free to use, but you'll gain in learning to grow your own.
 *
 * Justin Dekeyser
 */

package genetictimingattack.geneticgreet;

import java.util.*;

import static genetictimingattack.geneticgreet.HandOfGod.randomInteger;
import static genetictimingattack.geneticgreet.HandOfGod.randomTrue;
import static java.lang.Math.round;

abstract class NaturalSelectionTemplate<T> implements NaturalSelection<T>, GeneticOperator<T> {
    protected double CROSS_OVER_RATIO = 0.1;
    protected double MUTATION_RATIO = 0.59;
    protected double TOP_SELECTION_STRATEGY = 0.1;

    final protected GeneticOperator<T> operator;
    NaturalSelectionTemplate(GeneticOperator<T> operator) {
        this.operator = operator;
    }

    @Override
    public List<T> computeNextGeneration (List<T> scoreSortedPopulation) {
        final int POPULATION_SIZE = scoreSortedPopulation.size();
        final int TOP_SELECTION = (int) round(POPULATION_SIZE * TOP_SELECTION_STRATEGY);
        var queue = new ArrayList<T>(scoreSortedPopulation.size());
        int cursor = 0;
        for (var genome : scoreSortedPopulation) {
            if (queue.size() > POPULATION_SIZE) break;
            if (cursor < TOP_SELECTION) {
                queue.add(genome);
                cursor += 1;
            }
            genome = crossOver(scoreSortedPopulation.get(randomInteger(TOP_SELECTION)), genome);
            genome = mutation(genome);
            queue.add(genome);
        }
        return queue;
    }

    private boolean shouldCrossOver () {
        return randomTrue(CROSS_OVER_RATIO);
    }

    private boolean shouldMutate () {
        return randomTrue(MUTATION_RATIO);
    }

    @Override
    public T mutation (T source) {
        return shouldMutate() ? operator.mutation(source) : source;
    }

    @Override
    public T crossOver (T a, T b) {
        return shouldCrossOver() ? operator.crossOver(a, b) : b;
    }

}
