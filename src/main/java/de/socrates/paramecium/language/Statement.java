package de.socrates.paramecium.language;

import de.socrates.paramecium.Paramecium;

public interface Statement {

    default void execute(Paramecium paramecium) {
    }

    default void exhaust(Paramecium paramecium) {
        paramecium.exhaust(1);
    }

    String print();
}
