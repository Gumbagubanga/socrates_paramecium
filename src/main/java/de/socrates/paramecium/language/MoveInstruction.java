package de.socrates.paramecium.language;

import de.socrates.paramecium.Paramecium;
import de.socrates.paramecium.language.types.Direction;

public class MoveInstruction implements Instruction {
    private final Direction direction;

    public MoveInstruction(Direction direction) {
        this.direction = direction;
    }

    public static Instruction random() {
        return new MoveInstruction(Direction.random());
    }

    @Override
    public void execute(Paramecium paramecium) {
        paramecium.move(direction);
        paramecium.exhaust(1);
    }

    @Override
    public String toString() {
        return String.format("move(%s)", direction);
    }
}
