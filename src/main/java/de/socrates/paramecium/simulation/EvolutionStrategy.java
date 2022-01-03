package de.socrates.paramecium.simulation;

import de.socrates.paramecium.language.Instruction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SplittableRandom;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EvolutionStrategy {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newWorkStealingPool();
    private static final Comparator<Performance> PERFORMANCE_COMPARATOR = Comparator.comparing(Performance::getTicks);
    private final int programSize;
    private final int sampleSize;

    public EvolutionStrategy(int programSize, int sampleSize) {
        this.programSize = programSize;
        this.sampleSize = sampleSize;
    }

    List<Performance> evaluate(List<Program> programs) {
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

    List<Performance> selection(List<Performance> ancestors, List<Performance> descendants) {
        return tournamentSelection(ancestors, descendants);
    }

    private List<Performance> bestSelection(List<Performance> ancestors, List<Performance> descendants) {
        return Stream.concat(ancestors.stream(), descendants.stream())
                .sorted(PERFORMANCE_COMPARATOR.reversed())
                .limit(sampleSize)
                .collect(Collectors.toList());
    }

    private List<Performance> tournamentSelection(List<Performance> ancestors, List<Performance> descendants) {
        List<Performance> collect = Stream.concat(ancestors.stream(), descendants.stream())
                .collect(Collectors.toList());

        Set<Integer> losers = new HashSet<>();

        while (losers.size() < sampleSize) {
            int i = selectRandomAncestor(sampleSize * 2);
            int i2 = selectRandomAncestor(sampleSize * 2);

            if (!losers.contains(i) && !losers.contains(i2)) {
                Performance o1 = collect.get(i);
                Performance o2 = collect.get(i2);
                int e = PERFORMANCE_COMPARATOR.compare(o1, o2) <= 0 ? i : i2;
                losers.add(e);
            }
        }

        List<Performance> result = new ArrayList<>();

        for (int i = 0; i < collect.size(); i++) {
            if (!losers.contains(i)) {
                result.add(collect.get(i));
            }
        }

        return result;
    }

    List<Program> breed(List<Performance> ancestors) {
        List<Program> descendants = new ArrayList<>();

        for (Performance ancestor : ancestors) {
            int index = selectRandomAncestor(ancestors.size());
            int codeBreak = randomProgramLine();

            Program mother = ancestor.getProgram();
            Program father = ancestors.get(index).getProgram();
            Program descendant = breed(mother, father, codeBreak);

            descendants.add(descendant);
        }

        return descendants;
    }

    private Program breed(Program mother, Program father, int codeBreak) {
        List<Instruction> motherCode = mother.getCode();
        List<Instruction> fatherCode = father.getCode();

        List<Instruction> descendant = Stream.concat(
                motherCode.stream().limit(codeBreak),
                fatherCode.stream().skip(codeBreak)
        ).collect(Collectors.toList());

        return new Program(descendant);
    }

    private static int selectRandomAncestor(int size) {
        return new SplittableRandom().nextInt(size);
    }

    private int randomProgramLine() {
        return selectRandomAncestor(programSize);
    }
}
