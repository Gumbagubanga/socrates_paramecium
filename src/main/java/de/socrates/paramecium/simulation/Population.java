package de.socrates.paramecium.simulation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.SplittableRandom;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Population {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newWorkStealingPool();

    private List<Program> individuals;

    private Population(List<Program> individuals) {
        this.individuals = individuals;
    }

    public static Population create(int sampleSize, int programSize) {
        ProgramGenerator programGenerator = new ProgramGenerator(programSize);
        List<Program> individuals = IntStream.range(0, sampleSize)
                .mapToObj(i -> programGenerator.randomProgram())
                .collect(Collectors.toList());
        return new Population(individuals);
    }

    public void evolve(int maxGeneration, EvolutionStrategy evolutionStrategy) {
        for (int generation = 1; generation <= maxGeneration; generation++) {
            breed();

            individuals = evolutionStrategy.selection(this);
        }
    }

    public Population breed() {
        SplittableRandom splittableRandom = new SplittableRandom();

        List<Program> descendants = IntStream.range(0, individuals.size())
                .mapToObj(i -> splittableRandom
                        .ints(2, 0, individuals.size())
                        .mapToObj(individuals::get)
                        .reduce(Program::mate)
                        .orElseThrow())
                .collect(Collectors.toList());

        individuals.addAll(descendants);

        return this;
    }

    public Performance findFittest() {
        List<Performance> results = evaluate();

        return results.stream()
                .sorted(Comparator.comparing(Performance::getTicks).reversed())
                .limit(1)
                .findFirst()
                .orElseThrow();
    }

    public List<Performance> evaluate() {
        List<Callable<Performance>> collect = individuals.stream()
                .map(p -> (Callable<Performance>) () -> ProgramRunner.executeProgram(p))
                .collect(Collectors.toList());

        List<Performance> results = new ArrayList<>();
        try {
            List<Future<Performance>> futures = EXECUTOR_SERVICE.invokeAll(collect);
            for (Future<Performance> future : futures) {
                Performance performance = future.get();
                results.add(performance);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return results;
    }

    public List<Program> individuals() {
        return individuals;
    }
}
