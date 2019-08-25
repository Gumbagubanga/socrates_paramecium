package de.socrates.paramecium.language;

import de.socrates.paramecium.Bacteria;

public class EatStatement implements Statement {

    @Override
    public void execute(Bacteria paramecium) {
        paramecium.eat(3);
    }

    @Override
    public String print() {
        return "eat()";
    }
}
