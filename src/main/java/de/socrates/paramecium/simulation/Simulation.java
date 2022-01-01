package de.socrates.paramecium.simulation;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Simulation {

    private static final Comparator<Performance> BEST_PERFORMER = Comparator.comparing(Performance::getTicks).reversed();
    private static final FamilyNode ROOT = FamilyNode.EMPTY;
    private static final int SAMPLE_SIZE = 100;
    private static final int TAKE_BEST = 5;
    private static final int MAX_GENERATION = 2;

    public void run() {
        List<Program> ancestors = ProgramGenerator.randomPrograms(SAMPLE_SIZE);

        ROOT.addAll(evaluate(ancestors));

        evolve(ROOT.getChildren(), 1);
    }

    private static void evolve(List<FamilyNode> children, int currentGeneration) {
        if (currentGeneration >= MAX_GENERATION) {
            return;
        }

        for (FamilyNode child : children) {
            Program program = child.getProgram();
            Program partnerProgram = ROOT.findPartner();

            List<Program> nextGenerations = IntStream.range(0, SAMPLE_SIZE)
                    .mapToObj(i -> Evolution.mutation(program))
                    .collect(Collectors.toList());

            List<Performance> nextPerformances = evaluate(nextGenerations);
            child.addAll(nextPerformances);

            evolve(child.getChildren(), currentGeneration + 1);
        }
    }

    private static List<Performance> evaluate(List<Program> programs) {
        return programs.stream()
                .map(ProgramRunner::executeProgram)
                .sorted(BEST_PERFORMER)
                .limit(TAKE_BEST)
                .collect(Collectors.toList());
    }

    public Performance findBest() {
        return ROOT.findBest();
    }
}
