package de.socrates.paramecium;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Testable
public class SimulationTest {

    private static final Comparator<Performance> BEST_PERFORMER = Comparator.comparing(Performance::getTicks).reversed();

    private static final int SAMPLE_SIZE = 10_000;
    private static final int TAKE_BEST = 1;

    @Disabled
    @Test
    void simulate() {
        List<Performance> collect = IntStream.range(0, SAMPLE_SIZE)
                .mapToObj(i -> ProgramGenerator.randomProgram())
                .map(SimulationTest::executeProgram)
                .sorted(BEST_PERFORMER)
                .limit(TAKE_BEST)
                .collect(Collectors.toList());

        Performance performance = collect.get(0);
        System.out.println(performance);
        System.out.println();
        executeProgram(performance.getProgram(), true);
    }

    private static Performance executeProgram(Program program, boolean debug) {
        World world = World.generate();
        Paramecium paramecium = new Paramecium(10, world);
        return new ProgramRunner(program, debug).execute(paramecium);
    }

    private static Performance executeProgram(Program program) {
        return executeProgram(program, false);
    }
}
