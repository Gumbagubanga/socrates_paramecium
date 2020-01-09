package de.socrates.paramecium;

public class Performance {

    private final Program program;
    private final int ticks;

    public Performance(Program program, int ticks) {
        this.program = program;
        this.ticks = ticks;
    }

    public Program getProgram() {
        return program;
    }

    public int getTicks() {
        return ticks;
    }

    @Override
    public String toString() {
        return String.format("Ticks %d", ticks);
    }
}
