package de.socrates.paramecium.simulation;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.platform.commons.annotation.Testable;

import java.util.concurrent.TimeUnit;

@Testable
class EvolutionStrategyTest {

    public static final int PROGRAM_SIZE = 40;
    public static final int SAMPLE_SIZE = 100_000;

    private static final MetricRegistry metrics = new MetricRegistry();

    @AfterAll
    static void cleanup() {
        ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build()
                .report();
    }

    @Disabled
    @RepeatedTest(10)
    void evaluationBenchmark() {
        Population population = Population.create(SAMPLE_SIZE, PROGRAM_SIZE);

        try (Timer.Context context = metrics.timer("evaluationBenchmark").time()) {
            Population.evaluate(population.individuals());
        }
    }

    @Disabled
    @RepeatedTest(10)
    void breedBenchmark() {
        Population population = Population.create(SAMPLE_SIZE, PROGRAM_SIZE);

        try (Timer.Context context = metrics.timer("breed").time()) {
            population.breed();
        }
    }
}