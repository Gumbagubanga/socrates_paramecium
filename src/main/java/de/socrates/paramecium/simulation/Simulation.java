package de.socrates.paramecium.simulation;

import de.socrates.paramecium.simulation.evolution.TournamentSelectionEvolutionStrategy;

import picocli.CommandLine;

public class Simulation implements Runnable {

    @CommandLine.Parameters
    private int programSize;
    @CommandLine.Parameters
    private int sampleSize;
    @CommandLine.Parameters
    private int maxGeneration;

    @CommandLine.Option(names = {"--renderbest"})
    private boolean renderBest = true;

    public static void main(String[] args) {
        System.exit(new CommandLine(new Simulation()).execute(args));
    }

    @Override
    public void run() {
        EvolutionStrategy evolutionStrategy = new TournamentSelectionEvolutionStrategy(sampleSize);

        Population population = Population.create(sampleSize, programSize);
        population.evolve(maxGeneration, evolutionStrategy);

        Performance best = population.findFittest();

        if (renderBest) {
            ProgramRunner.executeProgram(best.getProgram(), renderBest);
        }
        System.out.println(best.getProgram());
        System.out.println(best);
    }

}
