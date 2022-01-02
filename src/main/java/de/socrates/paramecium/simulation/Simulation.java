package de.socrates.paramecium.simulation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import picocli.CommandLine;

public class Simulation implements Runnable {
    private static final Comparator<Performance> BEST_PERFORMER = Comparator.comparing(Performance::getTicks).reversed();
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newWorkStealingPool();

    @CommandLine.Parameters
    private int programSize;
    @CommandLine.Parameters
    private int sampleSize;
    @CommandLine.Parameters
    private int maxGeneration;

    public static void main(String[] args) {
        System.exit(new CommandLine(new Simulation()).execute(args));
    }

    @Override
    public void run() {
        List<Performance> ancestors = init();

        for (int generation = 1; generation <= maxGeneration; generation++) {
            List<Performance> descendants = evaluate(breed(ancestors));

            ancestors = Stream.concat(ancestors.stream(), descendants.stream())
                    .sorted(BEST_PERFORMER)
                    .limit(sampleSize)
                    .collect(Collectors.toList());
        }

        Performance best = ancestors.get(0);

        ProgramRunner.executeProgram(best.getProgram(), true);
        System.out.println(best);
    }

    private List<Performance> init() {
        ProgramGenerator programGenerator = new ProgramGenerator(programSize);
        List<Program> init = IntStream.range(0, sampleSize)
                .mapToObj(i -> programGenerator.randomProgram())
                .collect(Collectors.toList());
        return evaluate(init);
    }

    private List<Program> breed(List<Performance> ancestors) {
        EvolutionStrategy evolutionStrategy = new EvolutionStrategy(programSize);

        List<Program> descendants = new ArrayList<>();

        for (Performance ancestor : ancestors) {
            int index = ThreadLocalRandom.current().nextInt(0, ancestors.size());

            Program mother = ancestor.getProgram();
            Program father = ancestors.get(index).getProgram();
            Program descendant = evolutionStrategy.breed(mother, father);

            descendants.add(descendant);
        }

        return descendants;
    }

    private static Performance evaluate(Program program) {
        return ProgramRunner.executeProgram(program);
    }

    private static List<Performance> evaluate(List<Program> programs) {
        List<Callable<Performance>> collect = programs.stream()
                .map(p -> (Callable<Performance>) () -> Simulation.evaluate(p))
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
}
