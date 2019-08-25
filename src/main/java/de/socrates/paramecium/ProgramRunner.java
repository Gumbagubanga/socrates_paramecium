package de.socrates.paramecium;

import de.socrates.paramecium.language.GotoInstruction;
import de.socrates.paramecium.language.Instruction;

class ProgramRunner {

    private final Program program;

    private int programCounter = 0;
    private boolean debug;

    private ProgramRunner(Program program, boolean debug) {
        this.program = program;
        this.debug = debug;
    }

    ProgramRunner(Program program) {
        this(program, false);
    }

    void execute(Paramecium paramecium) {
        Instruction action;

        while (paramecium.isAlive() && (action = program.read(programCounter)) != null) {
            action.execute(paramecium);

            printDebug(paramecium, action);

            updateProgramCounter(action);
        }
    }

    private void printDebug(Paramecium paramecium, Instruction action) {
        if (debug) {
            System.out.println(action.toString());
            System.out.println(paramecium.toString());
        }
    }

    private void updateProgramCounter(Instruction action) {
        if (action instanceof GotoInstruction) {
            this.programCounter = ((GotoInstruction) action).getLine();
        } else {
            this.programCounter++;
        }
    }
}
