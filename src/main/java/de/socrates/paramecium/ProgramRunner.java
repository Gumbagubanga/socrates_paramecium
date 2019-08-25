package de.socrates.paramecium;

import de.socrates.paramecium.language.GotoStatement;
import de.socrates.paramecium.language.Statement;

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
        Statement action;

        while (paramecium.isAlive() && (action = program.read(programCounter)) != null) {
            action.execute(paramecium);
            action.exhaust(paramecium);

            printDebug(paramecium, action);

            updateProgramCounter(action);
        }
    }

    private void printDebug(Paramecium paramecium, Statement action) {
        if (debug) {
            System.out.println(action.print());
            System.out.println(paramecium.toString());
        }
    }

    private void updateProgramCounter(Statement action) {
        if (action instanceof GotoStatement) {
            this.programCounter = ((GotoStatement) action).getLine();
        } else {
            this.programCounter++;
        }
    }
}
