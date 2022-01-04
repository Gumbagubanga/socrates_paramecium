package de.socrates.paramecium.language;

import de.socrates.paramecium.environment.Direction;
import de.socrates.paramecium.environment.Paramecium;
import de.socrates.paramecium.environment.Tile;

public class IfClause implements Instruction {
    private final Direction direction;
    private final CompareOperator compareOperator;
    private final Tile sense;
    private final GotoInstruction statement;

    public IfClause(Direction direction, CompareOperator compareOperator, Tile sense, GotoInstruction statement) {
        this.direction = direction;
        this.compareOperator = compareOperator;
        this.sense = sense;
        this.statement = statement;
    }

    public static Instruction random(GotoInstruction gotoInstruction) {
        return new IfClause(Direction.random(), CompareOperator.random(), Tile.random(), gotoInstruction);
    }

    @Override
    public void execute(Paramecium paramecium) {
        if (compareOperator.eval(paramecium.sense(direction), sense)) {
            statement.execute(paramecium);
        }

        paramecium.exhaust(1);
    }

    @Override
    public String toString() {
        return String.format("if(sense(%s) %s %s) then %s", direction, compareOperator, sense, statement);
    }
}
