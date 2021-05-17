/*
 * Please respect the author work by not copying abusively this class.
 * Creativity is not a property, this work is free to use, but you'll gain in learning to grow your own.
 *
 * Justin Dekeyser
 */

package genetictimingattack.geneticgreet;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.ToDoubleFunction;

import static genetictimingattack.geneticgreet.HandOfGod.randomCharacter;
import static java.lang.String.valueOf;

public class GeneticTimerAttack {

    static class CredentialCheck {
        final String SECRET_PASSWORD;
        CredentialCheck (String password) {
            SECRET_PASSWORD = password;
        }
        boolean checkCredentials (String test) {
            if (test.length() != SECRET_PASSWORD.length())
                return false;
            for (int i = 0; i < test.length(); i++)
                if (test.charAt(i) != SECRET_PASSWORD.charAt(i)) return false;
            return true;
        }
    }

    static class MeasuredCredentialCheck {
        final CredentialCheck credentialCheck;
        MeasuredCredentialCheck (CredentialCheck credentialCheck) {
            this.credentialCheck = credentialCheck;
        }
        long elapsedTime;
        boolean checkCredentials (String test) {
            elapsedTime = System.nanoTime();
            try {
                return credentialCheck.checkCredentials(test);
            } finally {
                elapsedTime = System.nanoTime() - elapsedTime;
            }
        }

        static double measureInaccuracy(CredentialCheck credentialCheck, String test) {
            var checker = new MeasuredCredentialCheck(credentialCheck);
            var checkCredentials = checker.checkCredentials(test);
            return !checkCredentials
                    ? 1.0 / (1 + checker.elapsedTime)
                    : 0;
        }
    }

    static class IndividualFactoryTest extends IndividualFactory<String> {
        ToDoubleFunction<String> distanceToTarget = Optional.of("SECRET PASSWORD").map(CredentialCheck::new)
                .<ToDoubleFunction<String>> map(check -> str -> MeasuredCredentialCheck.measureInaccuracy(check, str))
                .orElseThrow();
        @Override
        protected double evaluateTest (String genome) {
            return distanceToTarget.applyAsDouble(genome);
        }
    }

    @Test
    public void attack() {
        var geneticOperator = new StringGeneticOperator() {};
        var naturalSelection = new NaturalSelectionTemplate<>(geneticOperator) {};
        var individualFactory = new IndividualFactoryTest(); // don't mind this line
        var randomInitialGenomes = createRandomGenomes(1 << 9, 19);
        var evolutionPlan = new EvolutionPlanTemplate<>(
                naturalSelection,
                individualFactory
        ) {};

        long tic = System.currentTimeMillis();
        evolutionPlan.compute(randomInitialGenomes, 1000);
        System.out.println("Elapsed attack time: " + (System.currentTimeMillis() - tic));
    }

    @Test
    public void brutForce() {
        StringBuilder builder = new StringBuilder();
        var check = new CredentialCheck("SECRET PASSWORD");
        final int MEAN_COUNT = 1_000_000;

        int sizeCount = 1;
        while(sizeCount <= 16) {
            String prefix = builder.toString();
            char bestChar = 0;
            double bestScore = Double.MAX_VALUE;
            for (char c : "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()) {
                double cumulScore = 0;
                int count = MEAN_COUNT;

                String singleton = prefix + valueOf(c);
                while (count-- > 0)
                    cumulScore += MeasuredCredentialCheck.measureInaccuracy(check, singleton);
                double averageScore = cumulScore / MEAN_COUNT;

                if (averageScore < bestScore) {
                    bestChar = c;
                    bestScore = averageScore;
                }
            }
            builder.append(bestChar);
            sizeCount++;
        }

        System.out.println("RESULT IS " + builder.toString());
    }

    ArrayList<String> createRandomGenomes(int expectedSize, int maximalLength) {
        var population = new ArrayList<String>(maximalLength*expectedSize);
        for (int size = 1; size < maximalLength; size++) {
            for (int i = 0; i < expectedSize; i++)
                population.add(createRandomGenome(size));
        }
        return population;
    }

    String createRandomGenome(int size) {
        char[] genetic = new char[size];
        for (int i = 0; i < genetic.length; i++) {
            genetic[i] = randomCharacter();
        }
        return valueOf (genetic);
    }

}
