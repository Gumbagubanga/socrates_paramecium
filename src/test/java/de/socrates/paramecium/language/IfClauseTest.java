package de.socrates.paramecium.language;

import de.socrates.paramecium.environment.Direction;
import de.socrates.paramecium.environment.Tile;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
class IfClauseTest {

    @Test
    void if_wall() {
        Assertions.assertEquals("if(sense(NORTH) == WALL) then eat()", ((Instruction) new IfClause(Direction.NORTH, Tile.WALL, new EatInstruction())).toString());
    }

    @Test
    void if_empty() {
        Assertions.assertEquals("if(sense(SOUTH) == EMPTY) then goto(10)", ((Instruction) new IfClause(Direction.SOUTH, Tile.EMPTY, new GotoInstruction(10))).toString());
    }

    @Test
    void if_food() {
        Assertions.assertEquals("if(sense(WEST) == FOOD) then nop()", ((Instruction) new IfClause(Direction.WEST, Tile.FOOD, new NopInstruction())).toString());
    }
}
