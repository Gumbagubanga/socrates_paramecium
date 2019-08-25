package de.socrates.paramecium.language;

import de.socrates.paramecium.Paramecium;

public class EatStatement implements Statement {

    @Override
    public void execute(Paramecium paramecium) {
        paramecium.eat(3);
    }

    @Override
    public String toString() {
        return "eat()";
    }
}
