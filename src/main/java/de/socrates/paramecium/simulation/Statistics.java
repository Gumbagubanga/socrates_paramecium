package de.socrates.paramecium.simulation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Statistics {

    private Performance fittest;
    private final List<Integer> history = new ArrayList<>();

    public void addFittest(List<Performance> population) {
        fittest = population.get(0);
        history.add(fittest.getTicks());
    }

    public void print(boolean renderBest) {
        if (renderBest) {
            ProgramRunner.executeProgram(fittest.getProgram(), renderBest);
        }
        System.out.println(fittest.getProgram());
        System.out.printf("Best: %s | Generation %02d%n", fittest, history.size());
        System.out.println("History: " + history.stream()
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting())));
    }
}
