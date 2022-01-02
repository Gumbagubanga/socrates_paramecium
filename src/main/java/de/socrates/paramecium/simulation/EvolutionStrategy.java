package de.socrates.paramecium.simulation;

import de.socrates.paramecium.language.Instruction;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EvolutionStrategy {

    private final int programSize;

    public EvolutionStrategy(int programSize) {
        this.programSize = programSize;
    }

    public Program breed(Program mother, Program father) {
        List<Instruction> motherCode = mother.getCode();
        List<Instruction> fatherCode = father.getCode();

        int codeBreak = randomProgramLine();

        List<Instruction> descendant = Stream.concat(
                motherCode.stream().limit(codeBreak),
                fatherCode.stream().skip(codeBreak)
        ).collect(Collectors.toList());

        return new Program(descendant);
    }

    private int randomProgramLine() {
        return ThreadLocalRandom.current().nextInt(0, programSize);
    }
}
