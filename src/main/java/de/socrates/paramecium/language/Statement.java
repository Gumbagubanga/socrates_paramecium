package de.socrates.paramecium.language;

import de.socrates.paramecium.Bacteria;

public interface Statement {

    default void execute(Bacteria paramecium) {
    }

    default void exhaust(Bacteria paramecium) {
        paramecium.exhaust(1);
    }

    String print();
}
