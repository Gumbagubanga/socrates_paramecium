package de.socrates.paramecium.language;

import de.socrates.paramecium.environment.Direction;
import de.socrates.paramecium.environment.Tile;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
class IfClauseTest {

    @Test
    void if_empty() {
        Assertions.assertEquals("if(sense(SOUTH) == EMPTY) then goto(10)",
                new IfClause(Direction.SOUTH, Tile.EMPTY, new GotoInstruction(10)).toString());
    }
}
