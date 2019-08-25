package de.socrates.paramecium;

import de.socrates.paramecium.language.Instruction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Program {

    private final List<Instruction> code;

    Program() {
        this(new ArrayList<>());
    }

    Program(List<Instruction> code) {
        this.code = code;
    }

    void write(Instruction statement) {
        code.add(statement);
    }

    Instruction read(int line) {
        return code.stream().skip(line).findFirst().orElse(null);
    }

    Program singleMutation() {
        List<Instruction> mutatedProgram = code.stream()
                .map(Program::lineMutation)
                .collect(Collectors.toList());

        return new Program(mutatedProgram);
    }

    private static Instruction lineMutation(Instruction statement) {
        boolean isMutated = ThreadLocalRandom.current().nextInt(0, 20) == 0;
        return isMutated ? ProgramGenerator.randomStatement() : statement;
    }

    @Override
    public String toString() {
        return IntStream.range(0, code.size())
                .mapToObj(i -> String.format("%2d %s", i, code.get(i).toString()))
                .collect(Collectors.joining("\n"));
    }
}
