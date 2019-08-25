package de.socrates.paramecium.language;

import de.socrates.paramecium.Paramecium;
import de.socrates.paramecium.language.types.Direction;

public class MoveStatement implements Statement {
    private final Direction direction;

    public MoveStatement(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void execute(Paramecium paramecium) {
        paramecium.move(direction);
    }

    @Override
    public String print() {
        return String.format("move(%s)", direction);
    }
}
