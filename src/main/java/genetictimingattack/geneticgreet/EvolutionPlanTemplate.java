/*
 * Please respect the author work by not copying abusively this class.
 * Creativity is not a property, this work is free to use, but you'll gain in learning to grow your own.
 *
 * Justin Dekeyser
 */

package genetictimingattack.geneticgreet;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

abstract class EvolutionPlanTemplate<T> implements EvolutionPlan<T> {

    protected NaturalSelection<T> naturalSelection;
    protected IndividualFactory<T> individualFactory;
    EvolutionPlanTemplate(NaturalSelection<T> naturalSelection, IndividualFactory<T> individualFactory) {
        this.naturalSelection = naturalSelection;
        this.individualFactory = individualFactory;
    }

    @Override
    public void compute(Collection<T> initialGenomes, int maxIterationSize) {
        final int POPULATION_SIZE = initialGenomes.size();

        class _NaturalSelection implements NaturalSelection<TestedIndividual<T>> {
            @Override
            public List<TestedIndividual<T>> computeNextGeneration(List<TestedIndividual<T>> population) {
                var genomes = population.stream()
                        .map(TestedIndividual::getGenes)
                        .collect(Collectors.toUnmodifiableList());
                return evaluateAndSort(naturalSelection.computeNextGeneration(genomes), POPULATION_SIZE);
            }
        }

        var individualSelection = new _NaturalSelection();
        var population = evaluateAndSort(initialGenomes);

        int LIMIT = 0;
        do {
            population = individualSelection.computeNextGeneration(population);
            if (population.get(0).score() == 0) {
                logSuccess(population);
                break;
            }
            log (LIMIT + 1, population, POPULATION_SIZE);
        } while (++LIMIT < maxIterationSize);
    }

    private void log (int iterationCount, List<TestedIndividual<T>> population, int POPULATION_SIZE) {
        System.out.printf("BEST CANDIDATE OF GENERATION %d IS %s,\n\t\tpercentage of as good candidates is %f,\n\t\tCardinal size of Levenshtein-distance-ball of radius 4 is %d / %d,\n\t\tMaximal Levenshtein-distance to optimal is %d\n", iterationCount, population.get(0),
                Optional.of(population.get(0).score())
                        .<Predicate<TestedIndividual<T>>> map(score -> indiv -> indiv.score() <= score)
                        .<ToDoubleFunction<List<TestedIndividual<T>>>> map(p -> col -> col.stream().filter(p).count())
                        .orElseThrow().applyAsDouble(population) / POPULATION_SIZE * 100,
                Optional.of((String) population.get(0).getGenes())
                        .<Predicate<TestedIndividual<T>>> map(gene -> indiv -> LevenshteinDistance.ApacheBridge.levenshteinDistance((String) indiv.getGenes(), gene) <= 4)
                        .<ToLongFunction<List<TestedIndividual<T>>>> map(p -> col -> col.stream().filter(p).count())
                        .orElseThrow().applyAsLong(population),
                population.size(),
                Optional.of((String) population.get(0).getGenes())
                        .<ToLongFunction<TestedIndividual<T>>> map(gene -> indiv -> LevenshteinDistance.ApacheBridge.levenshteinDistance((String) indiv.getGenes(), gene))
                        .<ToLongFunction<List<TestedIndividual<T>>>> map(f -> col -> col.stream().mapToLong(f).max().orElseThrow())
                        .orElseThrow().applyAsLong(population)
        );
    }

    private void logSuccess(List<TestedIndividual<T>> population) {
        System.out.printf("*! BEST CANDIDATE FOUND AS %s\n", population.get(0));
    }

    private List<TestedIndividual<T>> evaluateAndSort(Collection<T> source, int limit) {
        var stream = source.stream();
        if (limit != -1) stream = stream.limit(limit);
        return stream.map(individualFactory::newIndividual)
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private List<TestedIndividual<T>> evaluateAndSort(Collection<T> source) {
        return evaluateAndSort(source, -1);
    }

}
