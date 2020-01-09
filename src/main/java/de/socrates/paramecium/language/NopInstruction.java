package de.socrates.paramecium.language;

import de.socrates.paramecium.environment.Paramecium;

public class NopInstruction implements Instruction {

    @Override
    public void execute(Paramecium paramecium) {
        paramecium.exhaust(1);
    }

    @Override
    public String toString() {
        return "nop()";
    }
}
