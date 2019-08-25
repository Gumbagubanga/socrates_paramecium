package de.socrates.paramecium;

import de.socrates.paramecium.language.EatStatement;
import de.socrates.paramecium.language.GotoStatement;
import de.socrates.paramecium.language.IfClause;
import de.socrates.paramecium.language.NopStatement;
import de.socrates.paramecium.language.types.Direction;
import de.socrates.paramecium.language.types.Sense;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
class ProgramTest {

    @Test
    void programm() {
        Program writer = new Program();
        writer.write(new NopStatement());

        Assertions.assertEquals(new NopStatement().print(), writer.nextAction(0).print());
    }

    @Test
    void paramecium_exhausted() {
        Program program = new Program();
        program.write(new NopStatement());

        Bacteria paramecium = new Bacteria(null, 1, 0, 0);
        program.execute(paramecium);

        Assertions.assertFalse(paramecium.isAlive());
    }

    @Test
    void paramecium_eats() {
        Program program = new Program();
        program.write(new EatStatement());

        World world = World.generate();

        Bacteria paramecium = new Bacteria(world, 1, 1, 1);
        program.execute(paramecium);

        Assertions.assertTrue(paramecium.isAlive());
    }

    @Test
    void goto_jump() {
        Program program = new Program();
        program.write(new GotoStatement(2));
        program.write(new EatStatement());
        program.write(new NopStatement());

        Bacteria paramecium = new Bacteria(null, 2, 0, 0);
        program.execute(paramecium);

        Assertions.assertFalse(paramecium.isAlive());
    }

    @Test
    void if_false_nop() {
        Program program = new Program();
        program.write(new IfClause(Direction.NONE, Sense.EMPTY, new NopStatement()));

        World world = World.generate();

        Bacteria paramecium = new Bacteria(world, 1, 1, 1);
        program.execute(paramecium);

        Assertions.assertFalse(paramecium.isAlive());
    }

    @Test
    void if_true_nop() {
        Program program = new Program();
        program.write(new IfClause(Direction.NONE, Sense.FOOD, new NopStatement()));

        World world = World.generate();

        Bacteria paramecium = new Bacteria(world, 2, 1, 1);
        program.execute(paramecium);

        Assertions.assertFalse(paramecium.isAlive());
    }
}
