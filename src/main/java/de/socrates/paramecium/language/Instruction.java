package de.socrates.paramecium.language;

import de.socrates.paramecium.environment.Paramecium;

public interface Instruction {

    void execute(Paramecium paramecium);

}
