package de.socrates.paramecium.simulation;

import de.socrates.paramecium.language.Instruction;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Evolution {
    public static Program mutation(Program program) {
        List<Instruction> mutatedProgram = program.getCode().stream()
                .map(Evolution::lineMutation)
                .collect(Collectors.toList());

        return new Program(mutatedProgram);
    }

    private static Instruction lineMutation(Instruction statement) {
        boolean isMutated = ThreadLocalRandom.current().nextInt(0, 20) == 0;
        return isMutated ? ProgramGenerator.randomStatement() : statement;
    }

}
