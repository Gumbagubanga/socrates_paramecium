package de.socrates.paramecium;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.Comparator;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Testable
public class SimulationTest {

    public static final int SAMPLE_SIZE = 1000;
    public static final int TAKE_BEST = 10;

    public static final Comparator<Program> BEST_TICKS = Comparator.comparing(Program::getTicks).reversed();

    @Test
    void simulate() {
        List<Program> bestPrograms = mutate(i -> generateRandomProgram()).sorted(BEST_TICKS).limit(TAKE_BEST)
                .flatMap(p -> mutate(sample -> p.singleMutation())).sorted(BEST_TICKS).limit(TAKE_BEST)
                .flatMap(p -> mutate(sample -> p.singleMutation())).sorted(BEST_TICKS).limit(TAKE_BEST)
                .flatMap(p -> mutate(sample -> p.singleMutation())).sorted(BEST_TICKS).limit(TAKE_BEST)
                .flatMap(p -> mutate(sample -> p.singleMutation())).sorted(BEST_TICKS).limit(TAKE_BEST)
                .flatMap(p -> mutate(sample -> p.singleMutation())).sorted(BEST_TICKS).limit(TAKE_BEST)
                .flatMap(p -> mutate(sample -> p.singleMutation())).sorted(BEST_TICKS).limit(TAKE_BEST)
                .flatMap(p -> mutate(sample -> p.singleMutation())).sorted(BEST_TICKS).limit(TAKE_BEST)
                .flatMap(p -> mutate(sample -> p.singleMutation())).sorted(BEST_TICKS).limit(TAKE_BEST)
                .flatMap(p -> mutate(sample -> p.singleMutation())).sorted(BEST_TICKS).limit(TAKE_BEST)
                .flatMap(p -> mutate(sample -> p.singleMutation())).sorted(BEST_TICKS).limit(TAKE_BEST)
                .flatMap(p -> mutate(sample -> p.singleMutation())).sorted(BEST_TICKS).limit(TAKE_BEST)
                .flatMap(p -> mutate(sample -> p.singleMutation())).sorted(BEST_TICKS).limit(TAKE_BEST)
                .flatMap(p -> mutate(sample -> p.singleMutation())).sorted(BEST_TICKS).limit(TAKE_BEST)
                .flatMap(p -> mutate(sample -> p.singleMutation())).sorted(BEST_TICKS).limit(TAKE_BEST)
                .flatMap(p -> mutate(sample -> p.singleMutation())).sorted(BEST_TICKS).limit(TAKE_BEST)
                .flatMap(p -> mutate(sample -> p.singleMutation())).sorted(BEST_TICKS).limit(TAKE_BEST)
                .flatMap(p -> mutate(sample -> p.singleMutation())).sorted(BEST_TICKS).limit(TAKE_BEST)
                .collect(Collectors.toList());

        Program bestProgram = bestPrograms.stream().findFirst().get();

        playback(bestProgram);
    }

    private Stream<Program> mutate(IntFunction<Program> programIntFunction) {
        return IntStream.range(0, SAMPLE_SIZE)
                .mapToObj(programIntFunction)
                .map(SimulationTest::executeProgram);
    }

    private static Program executeProgram(Program program) {
        World world = World.generate();
        program.execute(new Bacteria(world, 10, 1, 1));
        return program;
    }

    private static Program generateRandomProgram() {
        Program program = new Program();
        IntStream.range(0, StatementGenerator.PROGRAM_SIZE)
                .mapToObj(i -> StatementGenerator.randomStatement())
                .forEach(program::write);
        return program;
    }

    private static void playback(Program program) {
        System.out.println("Ticks " + program.getTicks());
        program.reset();
        program.debug = true;

        System.out.println(program);
        System.out.println();

        World world = World.generate();
        program.execute(new Bacteria(world, 10, 1, 1));
    }
}
