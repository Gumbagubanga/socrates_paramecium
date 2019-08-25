package de.socrates.paramecium;

import de.socrates.paramecium.language.GotoStatement;
import de.socrates.paramecium.language.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Program {

    public boolean debug = false;
    private int programCounter = 0;
    private int ticks = 0;

    private List<Statement> program = new ArrayList<>();

    public void write(Statement statement) {
        program.add(statement);
    }

    public void execute(Paramecium paramecium) {
        while (paramecium.isAlive() && hasNextAction()) {
            Statement action = nextAction(programCounter);

            action.execute(paramecium);
            action.exhaust(paramecium);

            if (debug) {
                System.out.println(action.print());
                System.out.println(paramecium.toString().replaceAll(" ", ".").replaceAll("_", " "));
            }

            if (action instanceof GotoStatement) {
                this.programCounter = ((GotoStatement) action).getLine();
            } else {
                this.programCounter++;
            }

            ticks++;
        }
    }

    public Statement nextAction(int line) {
        return program.stream().skip(line).findFirst().orElse(null);
    }

    private boolean hasNextAction() {
        return nextAction(programCounter) != null;
    }

    public int getTicks() {
        return ticks;
    }

    public void reset() {
        programCounter = 0;
        ticks = 0;
    }

    public Program singleMutation() {
        Program result = new Program();

        for (Statement statement : program) {
            int change = ThreadLocalRandom.current().nextInt(0, 20);

            result.write(change == 0 ? StatementGenerator.randomStatement() : statement);
        }

        return result;
    }


    @Override
    public String toString() {
        return IntStream.range(0, program.size())
                .mapToObj(i -> String.format("%2d %s", i, program.get(i).print()))
                .collect(Collectors.joining("\n"));
    }
}
