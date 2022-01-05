package de.socrates.paramecium.simulation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.SplittableRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Population {

    private List<Performance> individuals;

    private Population(List<Performance> individuals) {
        this.individuals = individuals;
    }

    public static Population create(int sampleSize, int programSize) {
        ProgramGenerator programGenerator = new ProgramGenerator(programSize);
        List<Program> individuals = IntStream.range(0, sampleSize)
                .mapToObj(i -> programGenerator.randomProgram())
                .collect(Collectors.toList());
        return new Population(evaluate(individuals));
    }

    public void evolve(int maxGeneration, EvolutionStrategy evolutionStrategy) {
        for (int generation = 1; generation <= maxGeneration; generation++) {
            List<Program> descendants = breed();

            List<Performance> evaluate = evaluate(descendants);

            List<Performance> testGroup = new ArrayList<>();
            testGroup.addAll(individuals);
            testGroup.addAll(evaluate);

            individuals = evolutionStrategy.selection(testGroup);
        }
    }

    public List<Program> breed() {
        List<Program> collect = new SplittableRandom()
                .ints(individuals.size() * 2L, 0, individuals.size())
                .mapToObj(individuals::get)
                .map(Performance::getProgram)
                .collect(Collectors.toList());

        List<Program> descendants = new ArrayList<>();
        for (int i = 0; i < collect.size(); i = i + 2) {
            Program mother = collect.get(i);
            Program father = collect.get(i + 1);
            Program child = mother.mate(father);
            descendants.add(child);
        }

        return descendants;
    }

    public Performance findFittest() {
        return individuals.stream()
                .sorted(Comparator.comparing(Performance::getTicks).reversed())
                .limit(1)
                .findFirst()
                .orElseThrow();
    }

    public static List<Performance> evaluate(List<Program> individuals) {
        return individuals.stream()
                .parallel()
                .map(ProgramRunner::executeProgram)
                .collect(Collectors.toList());
    }

    public List<Program> individuals() {
        return individuals.stream().map(Performance::getProgram).collect(Collectors.toList());
    }
}
