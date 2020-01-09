package de.socrates.paramecium;

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

    @Test
    void simulate() {
        List<Program> firstGenerations = IntStream.range(0, SAMPLE_SIZE)
                .mapToObj(i -> ProgramGenerator.randomProgram())
                .collect(Collectors.toList());

        List<Performance> firstPerformances = evaluate(firstGenerations);

        Performance performance = firstPerformances.get(0);
        System.out.println(performance);
        Program program = performance.getProgram();
        // executeProgram(program, true);

        List<Program> nextGenerations = IntStream.range(0, SAMPLE_SIZE)
                .mapToObj(i -> program.singleMutation())
                .collect(Collectors.toList());

        List<Performance> nextPerformances = evaluate(nextGenerations);

        Performance nextPerformance = nextPerformances.get(0);
        System.out.println(nextPerformance);
        System.out.println();
        Program nextProgram = nextPerformance.getProgram();
        executeProgram(nextProgram, true);
    }

    private static List<Performance> evaluate(List<Program> programs) {
        return programs.stream()
                .map(SimulationTest::executeProgram)
                .sorted(BEST_PERFORMER)
                .limit(TAKE_BEST)
                .collect(Collectors.toList());
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
