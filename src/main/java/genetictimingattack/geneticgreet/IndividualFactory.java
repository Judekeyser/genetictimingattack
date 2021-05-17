/*
 * Please respect the author work by not copying abusively this class.
 * Creativity is not a property, this work is free to use, but you'll gain in learning to grow your own.
 *
 * Justin Dekeyser
 */

package genetictimingattack.geneticgreet;

import static java.lang.String.format;

abstract class IndividualFactory<T> {
    protected abstract double evaluateTest (T a);
    protected String genomeToString(T genome) {
        return genome.toString();
    }

    TestedIndividual<T> newIndividual (T genome) {
        return new Individual(genome);
    }

    final class Individual implements TestedIndividual<T> {
        private final double distanceToTarget;
        private final T genes;
        Individual(T genes) {
            this.genes = genes;
            this.distanceToTarget = evaluateTest(genes);
        }

        @Override
        public String toString() {
            return format("\"%s\"(score = %f)", genomeToString(genes), distanceToTarget);
        }

        @Override
        public T getGenes() {
            return genes;
        }

        @Override
        public double score() {
            return distanceToTarget;
        }
    }
}
