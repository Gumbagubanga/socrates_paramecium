package de.socrates.paramecium.language;

import de.socrates.paramecium.language.types.Direction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
class MoveStatementTest {

    @Test
    void move_north() {
        Assertions.assertEquals("move(NORTH)", ((Statement) new MoveStatement(Direction.NORTH)).toString());
    }

    @Test
    void move_south() {
        Assertions.assertEquals("move(SOUTH)", ((Statement) new MoveStatement(Direction.SOUTH)).toString());
    }

    @Test
    void move_west() {
        Assertions.assertEquals("move(WEST)", ((Statement) new MoveStatement(Direction.WEST)).toString());
    }

    @Test
    void move_east() {
        Assertions.assertEquals("move(EAST)", ((Statement) new MoveStatement(Direction.EAST)).toString());
    }

}
