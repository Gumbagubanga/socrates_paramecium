package de.socrates.paramecium.language;

import de.socrates.paramecium.language.types.Direction;
import de.socrates.paramecium.language.types.Sense;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
class IfClauseTest {

    @Test
    void if_wall() {
        Assertions.assertEquals("if(sense(NORTH) == WALL) then eat()", new IfClause(Direction.NORTH, Sense.WALL, new EatStatement()).print());
    }

    @Test
    void if_empty() {
        Assertions.assertEquals("if(sense(SOUTH) == EMPTY) then goto(10)", new IfClause(Direction.SOUTH, Sense.EMPTY, new GotoStatement(10)).print());
    }

    @Test
    void if_food() {
        Assertions.assertEquals("if(sense(WEST) == FOOD) then nop()", new IfClause(Direction.WEST, Sense.FOOD, new NopStatement()).print());
    }
}
