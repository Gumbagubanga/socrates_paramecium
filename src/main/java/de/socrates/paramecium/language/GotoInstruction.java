package de.socrates.paramecium.language;

import de.socrates.paramecium.Paramecium;

public class GotoInstruction implements Instruction {
    private final int line;

    public GotoInstruction(int line) {
        this.line = line;
    }

    public int getLine() {
        return line;
    }

    @Override
    public void execute(Paramecium paramecium) {
        paramecium.exhaust(1);
    }

    @Override
    public String toString() {
        return String.format("goto(%d)", line);
    }
}
