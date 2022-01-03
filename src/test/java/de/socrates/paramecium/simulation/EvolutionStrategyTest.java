package de.socrates.paramecium.simulation;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.codahale.metrics.MetricRegistry.name;

@Testable
class EvolutionStrategyTest {

    public static final int PROGRAM_SIZE = 40;
    public static final int SAMPLE_SIZE = 1_000_000;

    private static final MetricRegistry metrics = new MetricRegistry();

    private static final ProgramGenerator programGenerator = new ProgramGenerator(PROGRAM_SIZE);
    private static final List<Program> init = IntStream.range(0, SAMPLE_SIZE)
            .mapToObj(i -> programGenerator.randomProgram())
            .collect(Collectors.toList());

    @AfterAll
    static void cleanup() {
        ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build()
                .report();
    }

    @Disabled
    @Test
    void batchEvaluationTest() {
        EvolutionStrategy evolutionStrategy = new EvolutionStrategy(PROGRAM_SIZE, SAMPLE_SIZE);
        Timer timer = metrics.timer(name(EvolutionStrategy.class, "batchEvaluation"));

        try (Timer.Context context = timer.time()) {
            evolutionStrategy.evaluate(init);
        }
    }

    @Test
    void bestSelectionTest() {
        EvolutionStrategy evolutionStrategy = new EvolutionStrategy(PROGRAM_SIZE, SAMPLE_SIZE / 2);
        List<Performance> evaluate = evolutionStrategy.evaluate(init);

        List<Performance> ancestors = evaluate.stream().limit(SAMPLE_SIZE / 2).collect(Collectors.toList());
        List<Performance> descendants = evaluate.stream().skip(SAMPLE_SIZE / 2).collect(Collectors.toList());

        Timer timer = metrics.timer(name(EvolutionStrategy.class, "bestSelection"));

        try (Timer.Context context = timer.time()) {
            List<Performance> performances = evolutionStrategy.bestSelection(ancestors, descendants);
        }
    }

    @Test
    void tournamentSelectionTest() {
        EvolutionStrategy evolutionStrategy = new EvolutionStrategy(PROGRAM_SIZE, SAMPLE_SIZE / 2);
        List<Performance> evaluate = evolutionStrategy.evaluate(init);

        List<Performance> ancestors = evaluate.stream().limit(SAMPLE_SIZE / 2).collect(Collectors.toList());
        List<Performance> descendants = evaluate.stream().skip(SAMPLE_SIZE / 2).collect(Collectors.toList());

        Timer timer = metrics.timer(name(EvolutionStrategy.class, "tournamentSelection"));

        try (Timer.Context context = timer.time()) {
            List<Performance> performances = evolutionStrategy.tournamentSelection(ancestors, descendants);
        }
    }
}