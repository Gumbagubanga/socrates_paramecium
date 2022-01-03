package de.socrates.paramecium.simulation.evolution;

import de.socrates.paramecium.simulation.EvolutionStrategy;
import de.socrates.paramecium.simulation.Performance;
import de.socrates.paramecium.simulation.Program;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BestSelectEvolutionStrategy implements EvolutionStrategy {
    private final int sampleSize;

    public BestSelectEvolutionStrategy(int sampleSize) {
        this.sampleSize = sampleSize;
    }

    @Override
    public List<Program> selection(List<Performance> performances) {
        return performances.stream()
                .sorted(Comparator.comparing(Performance::getTicks).reversed())
                .limit(sampleSize)
                .map(Performance::getProgram)
                .collect(Collectors.toList());
    }
}
