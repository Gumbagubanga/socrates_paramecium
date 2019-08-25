package de.socrates.paramecium.language;

import de.socrates.paramecium.language.types.Direction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
class MoveStatementTest {

    @Test
    void move_north() {
        Assertions.assertEquals("move(NORTH)", new MoveStatement(Direction.NORTH).print());
    }

    @Test
    void move_south() {
        Assertions.assertEquals("move(SOUTH)", new MoveStatement(Direction.SOUTH).print());
    }

    @Test
    void move_west() {
        Assertions.assertEquals("move(WEST)", new MoveStatement(Direction.WEST).print());
    }

    @Test
    void move_east() {
        Assertions.assertEquals("move(EAST)", new MoveStatement(Direction.EAST).print());
    }

}
