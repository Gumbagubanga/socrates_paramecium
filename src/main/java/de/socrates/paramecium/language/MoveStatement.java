package de.socrates.paramecium.language;

import de.socrates.paramecium.Paramecium;
import de.socrates.paramecium.language.types.Direction;

public class MoveStatement implements Statement {
    private final Direction direction;

    public MoveStatement(Direction direction) {
        this.direction = direction;
    }

    public static Statement random() {
        return new MoveStatement(Direction.random());
    }

    @Override
    public void execute(Paramecium paramecium) {
        paramecium.move(direction);
    }

    @Override
    public String toString() {
        return String.format("move(%s)", direction);
    }
}
