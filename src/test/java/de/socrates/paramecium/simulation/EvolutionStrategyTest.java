package de.socrates.paramecium.simulation;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

import de.socrates.paramecium.simulation.evolution.BestSelectEvolutionStrategy;
import de.socrates.paramecium.simulation.evolution.TournamentSelectionEvolutionStrategy;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.platform.commons.annotation.Testable;

import java.util.List;
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
    void batchEvaluationTest() {
        Population population = Population.create(SAMPLE_SIZE, PROGRAM_SIZE);

        try (Timer.Context context = metrics.timer("batchEvaluation").time()) {
            population.evaluate();
        }
    }

    @Disabled
    @RepeatedTest(10)
    void breedTest() {
        Population population = Population.create(SAMPLE_SIZE, PROGRAM_SIZE);

        try (Timer.Context context = metrics.timer("breed").time()) {
            population.breed();
        }
    }

    @Disabled
    @RepeatedTest(10)
    void bestSelectionTest() {
        List<Performance> performances = Population.create(SAMPLE_SIZE, PROGRAM_SIZE).breed().evaluate();
        BestSelectEvolutionStrategy evolutionStrategy = new BestSelectEvolutionStrategy(SAMPLE_SIZE);

        try (Timer.Context context = metrics.timer("bestSelection").time()) {
            evolutionStrategy.selection(performances);
        }
    }

    @RepeatedTest(10)
    void tournamentSelectionTest() {
        List<Performance> performances = Population.create(SAMPLE_SIZE, PROGRAM_SIZE).breed().evaluate();
        TournamentSelectionEvolutionStrategy evolutionStrategy = new TournamentSelectionEvolutionStrategy(SAMPLE_SIZE);

        try (Timer.Context context = metrics.timer("tournamentSelection").time()) {
            evolutionStrategy.selection(performances);
        }
    }
}