/*
 * Please respect the author work by not copying abusively this class.
 * Creativity is not a property, this work is free to use, but you'll gain in learning to grow your own.
 *
 * Justin Dekeyser
 */

package genetictimingattack.geneticgreet;

import static java.lang.Double.compare;

interface TestedIndividual<T> extends Comparable<TestedIndividual<T>> {

    @Override
    default int compareTo(TestedIndividual<T> o) {
        if (this == o) return 0;
        return compare(score(), o.score());
    }

    double score();

    T getGenes();

}
