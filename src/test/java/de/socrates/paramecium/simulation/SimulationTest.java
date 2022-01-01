package de.socrates.paramecium.simulation;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class SimulationTest {

    @Test
    void simulate() {
        Simulation simulation = new Simulation();

        simulation.run();

        Performance best = simulation.findBest();

        System.out.println(best);

        ProgramRunner.executeProgram(best.getProgram(), true);
    }
}
