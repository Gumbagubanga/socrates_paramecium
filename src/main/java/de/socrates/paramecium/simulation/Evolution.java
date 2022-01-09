package de.socrates.paramecium.simulation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.SplittableRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Evolution {

    private final int sampleSize;
    private final int mutationRate;
    private final int maxGeneration;
    private final int programSize;

    private final Statistics statistics;
    private final SplittableRandom splittableRandom;

    public Evolution(EvolutionParameters evolutionParameters) {
        this.sampleSize = evolutionParameters.sampleSize;
        this.mutationRate = evolutionParameters.mutationRate;
        this.maxGeneration = evolutionParameters.maxGeneration;
        this.programSize = evolutionParameters.programSize;
        this.statistics = new Statistics();
        this.splittableRandom = new SplittableRandom();
    }

    public void evolve() {
        ProgramGenerator programGenerator = new ProgramGenerator(programSize, splittableRandom);

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
        if (mutationRate > 0) {
            ancestors.stream()
                    .parallel()
                    .forEach(p -> p.mutate(mutationRate, programGenerator, splittableRandom));
        }

        return ancestors;
    }

    public List<Program> breed(List<Program> individuals) {
        List<Program> descendants = new ArrayList<>(sampleSize);

        while (descendants.size() < sampleSize) {
            int codeBreak = splittableRandom.nextInt(programSize);

            if (codeBreak != 0 && codeBreak != programSize) {
                int motherIndex = splittableRandom.nextInt(individuals.size());
                int fatherIndex = splittableRandom.nextInt(individuals.size());

                if (motherIndex != fatherIndex) {
                    Program mother = individuals.get(motherIndex);
                    Program father = individuals.get(fatherIndex);

                    descendants.add(mother.mate(father, codeBreak));
                }
            }
        }

        return descendants;
    }

    public List<Performance> selection(List<Performance> performances) {
        int size = performances.size();

        if (size <= sampleSize) {
            return performances;
        }

        int losers = 0;

        while (size - losers > sampleSize) {
            int i1 = splittableRandom.nextInt(size);
            int i2 = splittableRandom.nextInt(size);

            Performance p1 = performances.get(i1);
            Performance p2 = performances.get(i2);

            if (p1 != null && p2 != null) {
                int o1 = p1.getTicks();
                int o2 = p2.getTicks();

                performances.set(o1 <= o2 ? i1 : i2, null);
                losers++;
            }
        }

        return performances.stream()
                .parallel()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(Performance::getTicks).reversed())
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
