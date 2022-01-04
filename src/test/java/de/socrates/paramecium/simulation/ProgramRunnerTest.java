package de.socrates.paramecium.simulation;

import de.socrates.paramecium.environment.Direction;
import de.socrates.paramecium.environment.Paramecium;
import de.socrates.paramecium.environment.Tile;
import de.socrates.paramecium.environment.World;
import de.socrates.paramecium.language.CompareOperator;
import de.socrates.paramecium.language.EatInstruction;
import de.socrates.paramecium.language.GotoInstruction;
import de.socrates.paramecium.language.IfClause;
import de.socrates.paramecium.language.NopInstruction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
class ProgramRunnerTest {

    @Test
    void paramecium_exhausted() {
        Program program = new Program();
        program.write(new NopInstruction());

        Paramecium paramecium = new Paramecium(1, null);

        ProgramRunner programRunner = new ProgramRunner(program);
        programRunner.execute(paramecium);

        Assertions.assertFalse(paramecium.isAlive());
    }

    @Test
    void paramecium_eats() {
        Program program = new Program();
        program.write(new EatInstruction());

        World world = World.generate();

        Paramecium paramecium = new Paramecium(1, world);

        ProgramRunner programRunner = new ProgramRunner(program);
        programRunner.execute(paramecium);

        Assertions.assertTrue(paramecium.isAlive());
    }

    @Test
    void goto_jump() {
        Program program = new Program();
        program.write(new GotoInstruction(2));
        program.write(new EatInstruction());
        program.write(new NopInstruction());

        Paramecium paramecium = new Paramecium(2, null);

        ProgramRunner programRunner = new ProgramRunner(program);
        programRunner.execute(paramecium);

        Assertions.assertFalse(paramecium.isAlive());
    }

    @Test
    void if_false_nop() {
        Program program = new Program();
        program.write(new IfClause(Direction.IN_PLACE, CompareOperator.EQUAL, Tile.EMPTY, new GotoInstruction(0)));

        World world = World.generate();

        Paramecium paramecium = new Paramecium(1, world);

        ProgramRunner programRunner = new ProgramRunner(program);
        programRunner.execute(paramecium);

        Assertions.assertFalse(paramecium.isAlive());
    }

    @Test
    void if_true_nop() {
        Program program = new Program();
        program.write(new IfClause(Direction.IN_PLACE, CompareOperator.EQUAL, Tile.FOOD, new GotoInstruction(0)));

        World world = World.generate();

        Paramecium paramecium = new Paramecium(2, world);

        ProgramRunner programRunner = new ProgramRunner(program);
        programRunner.execute(paramecium);

        Assertions.assertFalse(paramecium.isAlive());
    }

}