package de.socrates.paramecium.language;

import de.socrates.paramecium.Bacteria;
import de.socrates.paramecium.language.types.Direction;
import de.socrates.paramecium.language.types.Sense;

public class IfClause implements Statement {
    private final Direction direction;
    private final Sense sense;
    private final Statement statement;

    public IfClause(Direction direction, Sense sense, Statement statement) {
        this.direction = direction;
        this.sense = sense;
        this.statement = statement;
    }

    @Override
    public void execute(Bacteria paramecium) {
        if (paramecium.sense(direction) == sense) {
            statement.execute(paramecium);
            statement.exhaust(paramecium);
        }
    }

    @Override
    public String print() {
        return String.format("if(sense(%s) == %s) then %s", direction, sense, statement.print());
    }
}
