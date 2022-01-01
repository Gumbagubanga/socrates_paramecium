package de.socrates.paramecium.simulation;

import de.socrates.paramecium.language.Instruction;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Evolution {
    public static Program mutation(Program program) {
        List<Instruction> mutatedProgram = program.getCode().stream()
                .map(Evolution::lineMutation)
                .collect(Collectors.toList());

        return new Program(mutatedProgram);
    }

    public static Program breed(Program mother, Program father) {
        List<Instruction> motherCode = mother.getCode();
        List<Instruction> fatherCode = father.getCode();

        List<Instruction> descendant = Stream.concat(
                motherCode.stream().limit(motherCode.size() / 2),
                fatherCode.stream().skip(fatherCode.size() / 2)
        ).collect(Collectors.toList());

        return new Program(descendant);
    }

    private static Instruction lineMutation(Instruction statement) {
        boolean isMutated = ThreadLocalRandom.current().nextInt(0, 20) == 0;
        return isMutated ? ProgramGenerator.randomStatement() : statement;
    }

}
