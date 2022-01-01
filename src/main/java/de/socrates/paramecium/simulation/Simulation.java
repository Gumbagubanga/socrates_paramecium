package de.socrates.paramecium.simulation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Simulation {

    private static final Comparator<Performance> BEST_PERFORMER = Comparator.comparing(Performance::getTicks).reversed();
    private static final int SAMPLE_SIZE = 100_000;
    private static final int MAX_GENERATION = 1_000;

    private Performance best = null;

    public void run() {
        List<Performance> ancestors = evaluate(ProgramGenerator.randomPrograms(SAMPLE_SIZE));

        for (int generation = 1; generation <= MAX_GENERATION; generation++) {
            List<Performance> descendants = evaluate(breed(ancestors));

            List<Performance> nextGeneration = Stream.concat(ancestors.stream(), descendants.stream())
                    .sorted(BEST_PERFORMER)
                    .limit(SAMPLE_SIZE)
                    .collect(Collectors.toList());

            best = nextGeneration.get(0);

            if (best.getTicks() > ancestors.get(0).getTicks()) {
                System.out.printf("Generation %06d of %d | Best: %02d%n", generation, MAX_GENERATION, best.getTicks());
            }

            ancestors = nextGeneration;
        }
    }

    private List<Program> breed(List<Performance> ancestors) {
        List<Program> descendants = new ArrayList<>();

        for (Performance ancestor : ancestors) {
            int index = ThreadLocalRandom.current().nextInt(0, ancestors.size());

            Program mother = ancestor.getProgram();
            Program father = ancestors.get(index).getProgram();
            Program descendant = Evolution.breed(mother, father);

            descendants.add(descendant);
        }

        return descendants;
    }

    private static Performance evaluate(Program program) {
        return ProgramRunner.executeProgram(program);
    }

    private static List<Performance> evaluate(List<Program> programs) {
        return programs.stream().map(Simulation::evaluate).collect(Collectors.toList());
    }

    public Performance getBest() {
        return best;
    }
}
