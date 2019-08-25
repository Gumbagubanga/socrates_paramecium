package de.socrates.paramecium;

import de.socrates.paramecium.language.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Program {

    private List<Statement> program;

    Program() {
        this(new ArrayList<>());
    }

    private Program(List<Statement> program) {
        this.program = program;
    }

    void write(Statement statement) {
        program.add(statement);
    }

    Statement read(int line) {
        return program.stream().skip(line).findFirst().orElse(null);
    }

    Program singleMutation() {
        List<Statement> mutatedProgram = program.stream()
                .map(Program::lineMutation)
                .collect(Collectors.toList());

        return new Program(mutatedProgram);
    }

    private static Statement lineMutation(Statement statement) {
        boolean isMutated = ThreadLocalRandom.current().nextInt(0, 20) == 0;
        return isMutated ? StatementGenerator.randomStatement() : statement;
    }

    @Override
    public String toString() {
        return IntStream.range(0, program.size())
                .mapToObj(i -> String.format("%2d %s", i, program.get(i).print()))
                .collect(Collectors.joining("\n"));
    }
}
