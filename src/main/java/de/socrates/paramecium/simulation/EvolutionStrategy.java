package de.socrates.paramecium.simulation;

import java.util.List;

public interface EvolutionStrategy {

    List<Performance> selection(List<Performance> performances);

}
