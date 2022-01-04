package de.socrates.paramecium.language;

import de.socrates.paramecium.environment.Direction;
import de.socrates.paramecium.environment.Tile;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
class IfClauseTest {

    @Test
    void if_eq_goto() {
        Assertions.assertEquals("if(sense(SOUTH) == EMPTY) then goto(10)",
                new IfClause(Direction.SOUTH, CompareOperator.EQUAL, Tile.EMPTY, new GotoInstruction(10)).toString());
    }

    @Test
    void if_neq_goto() {
        Assertions.assertEquals("if(sense(SOUTH) != EMPTY) then goto(10)",
                new IfClause(Direction.SOUTH, CompareOperator.NOT_EQUAL, Tile.EMPTY, new GotoInstruction(10)).toString());
    }
}
