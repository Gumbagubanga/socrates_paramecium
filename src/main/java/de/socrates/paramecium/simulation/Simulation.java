package de.socrates.paramecium.simulation;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import picocli.CommandLine;

public class Simulation implements Runnable {

    @CommandLine.Parameters
    private int programSize;
    @CommandLine.Parameters
    private int sampleSize;
    @CommandLine.Parameters
    private int maxGeneration;

    @CommandLine.Option(names = "renderbest")
    private boolean renderBest = false;

    public static void main(String[] args) {
        System.exit(new CommandLine(new Simulation()).execute(args));
    }

    @Override
    public void run() {
        EvolutionStrategy evolutionStrategy = new EvolutionStrategy(programSize, sampleSize);

        List<Performance> ancestors = init();
        for (int generation = 1; generation <= maxGeneration; generation++) {
            List<Performance> descendants = EvolutionStrategy.evaluate(evolutionStrategy.breed(ancestors));

            ancestors = evolutionStrategy.selection(ancestors, descendants);
        }

        Performance best = ancestors.get(0);

        ProgramRunner.executeProgram(best.getProgram(), renderBest);
        System.out.println(best.getProgram());
        System.out.println(best);
    }

    private List<Performance> init() {
        ProgramGenerator programGenerator = new ProgramGenerator(programSize);
        List<Program> init = IntStream.range(0, sampleSize)
                .mapToObj(i -> programGenerator.randomProgram())
                .collect(Collectors.toList());
        return EvolutionStrategy.evaluate(init);
    }
}
