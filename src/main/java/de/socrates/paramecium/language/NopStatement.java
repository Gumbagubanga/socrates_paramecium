package de.socrates.paramecium.language;

public class NopStatement implements Statement {

    @Override
    public String print() {
        return "nop()";
    }
}
