package de.socrates.paramecium.simulation;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class Statistics {

    private final Deque<Performance> fittest = new ArrayDeque<>();

    public void addFittest(List<Performance> population) {
        population.stream()
                .max(Comparator.comparing(Performance::getTicks))
                .ifPresent(fittest::add);
    }

    public void print(boolean renderBest) {
        Performance best = fittest.getLast();

        if (renderBest) {
            ProgramRunner.executeProgram(best.getProgram(), renderBest);
        }
        System.out.println(best.getProgram());
        System.out.printf("Best: %s | Generation %02d%n", best, fittest.size());
        System.out.println(fittest.stream()
                .map(Performance::getTicks)
                .map(Object::toString)
                .collect(Collectors.joining(" > ", "History: ", "")));
    }
}
