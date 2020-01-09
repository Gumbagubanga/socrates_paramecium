package de.socrates.paramecium.language;

import de.socrates.paramecium.environment.Direction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
class MoveInstructionTest {

    @Test
    void move_north() {
        Assertions.assertEquals("move(NORTH)", ((Instruction) new MoveInstruction(Direction.NORTH)).toString());
    }

    @Test
    void move_south() {
        Assertions.assertEquals("move(SOUTH)", ((Instruction) new MoveInstruction(Direction.SOUTH)).toString());
    }

    @Test
    void move_west() {
        Assertions.assertEquals("move(WEST)", ((Instruction) new MoveInstruction(Direction.WEST)).toString());
    }

    @Test
    void move_east() {
        Assertions.assertEquals("move(EAST)", ((Instruction) new MoveInstruction(Direction.EAST)).toString());
    }

}
