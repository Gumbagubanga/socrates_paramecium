package de.socrates.paramecium.simulation;

import java.util.List;

public interface EvolutionStrategy {

    default List<Program> selection(Population population) {
        return selection(population.evaluate());
    }

    List<Program> selection(List<Performance> performances);

}
