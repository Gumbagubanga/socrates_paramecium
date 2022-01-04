package de.socrates.paramecium.language;

import de.socrates.paramecium.environment.Direction;
import de.socrates.paramecium.environment.Paramecium;
import de.socrates.paramecium.environment.Tile;

public class IfClause implements Instruction {
    private final Direction direction;
    private final Tile sense;
    private final GotoInstruction statement;

    public IfClause(Direction direction, Tile sense, GotoInstruction statement) {
        this.direction = direction;
        this.sense = sense;
        this.statement = statement;
    }

    public static Instruction random(GotoInstruction gotoInstruction) {
        return new IfClause(Direction.random(), Tile.random(), gotoInstruction);
    }

    @Override
    public void execute(Paramecium paramecium) {
        if (paramecium.sense(direction) == sense) {
            statement.execute(paramecium);
        }

        paramecium.exhaust(1);
    }

    @Override
    public String toString() {
        return String.format("if(sense(%s) == %s) then %s", direction, sense, statement.toString());
    }
}
