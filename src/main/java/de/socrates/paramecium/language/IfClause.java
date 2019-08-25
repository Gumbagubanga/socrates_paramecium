package de.socrates.paramecium.language;

import de.socrates.paramecium.Paramecium;
import de.socrates.paramecium.language.types.Direction;
import de.socrates.paramecium.language.types.Environment;

public class IfClause implements Statement {
    private final Direction direction;
    private final Environment sense;
    private final Statement statement;

    public IfClause(Direction direction, Environment sense, Statement statement) {
        this.direction = direction;
        this.sense = sense;
        this.statement = statement;
    }

    public static Statement random(Statement randomSimpleStatement) {
        return new IfClause(Direction.random(), Environment.random(), randomSimpleStatement);
    }

    @Override
    public void execute(Paramecium paramecium) {
        if (paramecium.sense(direction) == sense) {
            statement.execute(paramecium);
            statement.exhaust(paramecium);
        }
    }

    @Override
    public String toString() {
        return String.format("if(sense(%s) == %s) then %s", direction, sense, statement.toString());
    }
}
