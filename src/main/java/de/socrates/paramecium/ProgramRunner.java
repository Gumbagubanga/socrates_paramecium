package de.socrates.paramecium;

import de.socrates.paramecium.language.GotoInstruction;
import de.socrates.paramecium.language.Instruction;

class ProgramRunner {

    private final Program program;

    private int programCounter = 0;
    private boolean debug;

    ProgramRunner(Program program, boolean debug) {
        this.program = program;
        this.debug = debug;
    }

    ProgramRunner(Program program) {
        this(program, false);
    }

    Performance execute(Paramecium paramecium) {
        Instruction action;

        int ticks = 0;

        while (paramecium.isAlive() && (action = program.read(programCounter)) != null) {
            action.execute(paramecium);
            updateProgramCounter(action);

            printDebug(paramecium, action);
            ticks++;
        }

        return new Performance(program, ticks);
    }

    private void updateProgramCounter(Instruction action) {
        if (action instanceof GotoInstruction) {
            this.programCounter = ((GotoInstruction) action).getLine();
        } else {
            this.programCounter++;
        }
    }

    private void printDebug(Paramecium paramecium, Instruction action) {
        if (debug) {
            System.out.println(paramecium.toString());
            System.out.println(action.toString());
            System.out.println(paramecium.world.toString());
            System.out.println();
        }
    }
}
