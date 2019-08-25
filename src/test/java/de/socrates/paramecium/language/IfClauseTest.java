package de.socrates.paramecium.language;

import de.socrates.paramecium.language.types.Direction;
import de.socrates.paramecium.language.types.Environment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
class IfClauseTest {

    @Test
    void if_wall() {
        Assertions.assertEquals("if(sense(NORTH) == WALL) then eat()", ((Statement) new IfClause(Direction.NORTH, Environment.WALL, new EatStatement())).toString());
    }

    @Test
    void if_empty() {
        Assertions.assertEquals("if(sense(SOUTH) == EMPTY) then goto(10)", ((Statement) new IfClause(Direction.SOUTH, Environment.EMPTY, new GotoStatement(10))).toString());
    }

    @Test
    void if_food() {
        Assertions.assertEquals("if(sense(WEST) == FOOD) then nop()", ((Statement) new IfClause(Direction.WEST, Environment.FOOD, new NopStatement())).toString());
    }
}
