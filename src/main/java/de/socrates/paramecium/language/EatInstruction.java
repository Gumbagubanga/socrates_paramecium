package de.socrates.paramecium.language;

import de.socrates.paramecium.Paramecium;

public class EatInstruction implements Instruction {

    @Override
    public void execute(Paramecium paramecium) {
        paramecium.eat(3);
        paramecium.exhaust(1);
    }

    @Override
    public String toString() {
        return "eat()";
    }
}
