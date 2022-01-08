package de.socrates.paramecium.simulation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SplittableRandom;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Evolution {

    private final int sampleSize;
    private final int mutationRate;
    private final int maxGeneration;
    private final int programSize;

    private final Statistics statistics;

    public Evolution(EvolutionParameters evolutionParameters) {
        this.sampleSize = evolutionParameters.sampleSize;
        this.mutationRate = evolutionParameters.mutationRate;
        this.maxGeneration = evolutionParameters.maxGeneration;
        this.programSize = evolutionParameters.programSize;
        this.statistics = new Statistics();
    }

    public void evolve() {
        ProgramGenerator programGenerator = new ProgramGenerator(programSize);

        List<Program> ancestors = IntStream.range(0, sampleSize)
                .mapToObj(i -> programGenerator.randomProgram())
                .collect(Collectors.toList());

        for (int generation = 0; generation < maxGeneration; generation++) {
            List<Program> descendants = mutate(breed(ancestors), programGenerator);

            List<Program> pool = new ArrayList<>(ancestors.size() + descendants.size());
            pool.addAll(ancestors);
            pool.addAll(descendants);

            List<Performance> performances = selection(evaluate(pool));

            statistics.addFittest(performances);

            ancestors = performances.stream().map(Performance::getProgram).collect(Collectors.toList());
        }
    }

    public List<Program> mutate(List<Program> ancestors, ProgramGenerator programGenerator) {
        List<Program> result = ancestors;
        if (mutationRate > 0) {
            result = ancestors.stream()
                    .parallel()
                    .map(p -> p.mutate(mutationRate, programGenerator))
                    .collect(Collectors.toList());
        }

        return result;
    }

    public List<Program> breed(List<Program> individuals) {
        List<Program> collect = new SplittableRandom()
                .ints(individuals.size() * 2L, 0, individuals.size())
                .mapToObj(individuals::get)
                .collect(Collectors.toList());

        List<Program> descendants = new ArrayList<>();
        for (int i = 0; i < collect.size(); i = i + 2) {
            Program mother = collect.get(i);
            Program father = collect.get(i + 1);
            descendants.add(mother.mate(father));
            descendants.add(father.mate(mother));
        }

        return descendants;
    }

    public List<Performance> selection(List<Performance> performances) {
        int size = performances.size();

        if (size <= sampleSize) {
            return performances;
        }

        Set<Integer> losers = new HashSet<>();

        SplittableRandom splittableRandom = new SplittableRandom();
        while (size - losers.size() > sampleSize) {
            int i1 = splittableRandom.nextInt(size);
            int i2 = splittableRandom.nextInt(size);

            if (!losers.contains(i1) && !losers.contains(i2)) {
                int o1 = performances.get(i1).getTicks();
                int o2 = performances.get(i2).getTicks();

                losers.add(o1 <= o2 ? i1 : i2);
            }
        }

        return IntStream.range(0, size)
                .boxed()
                .filter(Predicate.not(losers::contains))
                .map(performances::get)
                .collect(Collectors.toList());
    }

    private static List<Performance> evaluate(List<Program> individuals) {
        return individuals.stream()
                .parallel()
                .map(ProgramRunner::executeProgram)
                .collect(Collectors.toList());
    }

    public Statistics getStatistics() {
        return statistics;
    }
}
