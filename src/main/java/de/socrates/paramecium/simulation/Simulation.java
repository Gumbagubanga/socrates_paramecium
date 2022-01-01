package de.socrates.paramecium.simulation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Simulation {

    private static final Comparator<Performance> BEST_PERFORMER = Comparator.comparing(Performance::getTicks).reversed();
    private static final FamilyNode ROOT = FamilyNode.EMPTY;
    private static final int SAMPLE_SIZE = 1_000_000;
    private static final int TAKE_BEST = 10_000;
    private static final int MAX_GENERATION = 1000;

    public void run() {
        List<Program> ancestors = ProgramGenerator.randomPrograms(SAMPLE_SIZE);

        ROOT.addAll(evaluate(ancestors));

        evolve(ROOT.getChildren(), 1);
    }

    private static void evolve(List<FamilyNode> children, int currentGeneration) {
        if (currentGeneration >= MAX_GENERATION) {
            return;
        }

        List<Program> descendants = new ArrayList<>();
        for (FamilyNode child : children) {
            Program mother = child.getProgram();
            Program father = children.get(ThreadLocalRandom.current().nextInt(0, children.size())).getProgram();
            Program descendant = Evolution.breed(mother, father);

            descendants.add(descendant);
        }

        List<Performance> evaluate = evaluate(descendants);
        List<FamilyNode> nextGenerations = evaluate.stream().map(FamilyNode::create).collect(Collectors.toList());

        evolve(nextGenerations, currentGeneration + 1);
    }

    private static Performance evaluate(Program program) {
        return ProgramRunner.executeProgram(program);
    }

    private static List<Performance> evaluate(List<Program> programs) {
        return programs.stream()
                .map(Simulation::evaluate)
                .sorted(BEST_PERFORMER)
                .limit(TAKE_BEST)
                .collect(Collectors.toList());
    }

    public Performance findBest() {
        return ROOT.findBest();
    }
}
