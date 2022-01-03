package de.socrates.paramecium.simulation;

import com.codahale.metrics.Timer;

import de.socrates.paramecium.language.Instruction;

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
import java.util.stream.Stream;

import static com.codahale.metrics.MetricRegistry.name;

public class EvolutionStrategy {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newWorkStealingPool();
    private static final Comparator<Performance> PERFORMANCE_COMPARATOR = Comparator.comparing(Performance::getTicks);
    private final int programSize;
    private final int sampleSize;

    private final Timer evaluationTimer = Simulation.metrics.timer(name(EvolutionStrategy.class, "evaluation"));
    private final Timer selectionTimer = Simulation.metrics.timer(name(EvolutionStrategy.class, "selection"));

    public EvolutionStrategy(int programSize, int sampleSize) {
        this.programSize = programSize;
        this.sampleSize = sampleSize;
    }

    List<Performance> evaluate(List<Program> programs) {
        try (Timer.Context context = evaluationTimer.time()) {
            List<Callable<Performance>> collect = programs.stream()
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
    }

    List<Performance> selection(List<Performance> ancestors, List<Performance> descendants) {
        try (Timer.Context context = selectionTimer.time()) {
            return tournamentSelection(ancestors, descendants);
        }
    }

    private List<Performance> bestSelection(List<Performance> ancestors, List<Performance> descendants) {
        return Stream.concat(ancestors.stream(), descendants.stream())
                .sorted(PERFORMANCE_COMPARATOR.reversed())
                .limit(sampleSize)
                .collect(Collectors.toList());
    }

    private List<Performance> tournamentSelection(List<Performance> ancestors, List<Performance> descendants) {
        List<Performance> collect = Stream.concat(ancestors.stream(), descendants.stream()).collect(Collectors.toList());

        while (collect.size() > sampleSize) {
            int i = selectRandomAncestor(collect);
            int i2 = selectRandomAncestor(collect);

            Performance o1 = collect.get(i);
            Performance o2 = collect.get(i2);
            collect.remove(PERFORMANCE_COMPARATOR.compare(o1, o2) <= 0 ? i : i2);
        }
        return collect;
    }

    List<Program> breed(List<Performance> ancestors) {
        List<Program> descendants = new ArrayList<>();

        for (Performance ancestor : ancestors) {
            int index = selectRandomAncestor(ancestors);

            Program mother = ancestor.getProgram();
            Program father = ancestors.get(index).getProgram();
            Program descendant = breed(mother, father);

            descendants.add(descendant);
        }

        return descendants;
    }

    private Program breed(Program mother, Program father) {
        List<Instruction> motherCode = mother.getCode();
        List<Instruction> fatherCode = father.getCode();

        int codeBreak = randomProgramLine();

        List<Instruction> descendant = Stream.concat(
                motherCode.stream().limit(codeBreak),
                fatherCode.stream().skip(codeBreak)
        ).collect(Collectors.toList());

        return new Program(descendant);
    }

    private static int selectRandomAncestor(List<Performance> ancestors) {
        return ThreadLocalRandom.current().nextInt(0, ancestors.size());
    }

    private int randomProgramLine() {
        return ThreadLocalRandom.current().nextInt(0, programSize);
    }
}
