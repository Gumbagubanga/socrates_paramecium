package de.socrates.paramecium.simulation;

public class EvolutionParameters {
    public final int sampleSize;
    public final int programSize;
    public final int mutationRate;
    public final int maxGeneration;

    public EvolutionParameters(int sampleSize, int programSize, int mutationRate, int maxGeneration) {
        this.sampleSize = sampleSize;
        this.programSize = programSize;
        this.mutationRate = mutationRate;
        this.maxGeneration = maxGeneration;
    }
}
