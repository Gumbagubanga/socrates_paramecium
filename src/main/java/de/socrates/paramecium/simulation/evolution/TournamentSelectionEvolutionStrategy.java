package de.socrates.paramecium.simulation.evolution;

import de.socrates.paramecium.simulation.EvolutionStrategy;
import de.socrates.paramecium.simulation.Performance;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SplittableRandom;

public class TournamentSelectionEvolutionStrategy implements EvolutionStrategy {

    private final int sampleSize;

    public TournamentSelectionEvolutionStrategy(int sampleSize) {
        this.sampleSize = sampleSize;
    }

    @Override
    public List<Performance> selection(List<Performance> performances) {
        int size = performances.size();

        if (size <= sampleSize) {
            return performances;
        }

        Set<Integer> losers = new HashSet<>();

        SplittableRandom splittableRandom = new SplittableRandom();
        while (losers.size() < sampleSize) {
            int i1 = splittableRandom.nextInt(size);
            int i2 = splittableRandom.nextInt(size);

            if (!losers.contains(i1) && !losers.contains(i2)) {
                int o1 = performances.get(i1).getTicks();
                int o2 = performances.get(i2).getTicks();

                losers.add(o1 <= o2 ? i1 : i2);
            }
        }

        List<Performance> result = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            if (!losers.contains(i)) {
                result.add(performances.get(i));
            }
        }

        return result;
    }
}
