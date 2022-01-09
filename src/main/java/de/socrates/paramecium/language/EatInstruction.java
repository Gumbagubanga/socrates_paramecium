package de.socrates.paramecium.language;

import de.socrates.paramecium.environment.Paramecium;

public class EatInstruction implements Instruction {

    @Override
    public void execute(Paramecium paramecium) {
        if (!paramecium.eat(2)) {
            paramecium.exhaust(1);
        }
    }

    @Override
    public String toString() {
        return "eat()";
    }
}
