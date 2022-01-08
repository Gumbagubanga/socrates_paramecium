package de.socrates.paramecium;

import de.socrates.paramecium.simulation.Evolution;
import de.socrates.paramecium.simulation.EvolutionParameters;

import picocli.CommandLine;

public class Simulation implements Runnable {

    @CommandLine.Parameters
    private int programSize;
    @CommandLine.Parameters
    private int sampleSize;
    @CommandLine.Parameters
    private int maxGeneration;

    @CommandLine.Option(names = {"--renderbest"}, defaultValue = "true")
    private boolean renderBest;

    @CommandLine.Option(names = {"--mutationrate"}, defaultValue = "0")
    private int mutationRate;

    public static void main(String[] args) {
        System.exit(new CommandLine(new Simulation()).execute(args));
    }

    @Override
    public void run() {
        EvolutionParameters config = new EvolutionParameters(sampleSize, programSize, mutationRate, maxGeneration);

        Evolution evolution = new Evolution(config);
        evolution.evolve();

        evolution.getStatistics().print(renderBest);
    }
}
