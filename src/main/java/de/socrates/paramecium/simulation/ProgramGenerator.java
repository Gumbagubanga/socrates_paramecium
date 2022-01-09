package de.socrates.paramecium.simulation;

import de.socrates.paramecium.language.EatInstruction;
import de.socrates.paramecium.language.GotoInstruction;
import de.socrates.paramecium.language.IfClause;
import de.socrates.paramecium.language.Instruction;
import de.socrates.paramecium.language.MoveInstruction;
import de.socrates.paramecium.language.NopInstruction;

import java.util.List;
import java.util.SplittableRandom;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ProgramGenerator {

    private final int programSize;
    private final List<Supplier<Instruction>> instructionSupplier;
    private final SplittableRandom splittableRandom;

    public ProgramGenerator(int programSize, SplittableRandom splittableRandom) {
        this.programSize = programSize;
        this.instructionSupplier = List.of(
                NopInstruction::new,
                EatInstruction::new,
                MoveInstruction::random,
                this::generateGotoStatement,
                this::generateIfClause);
        this.splittableRandom = splittableRandom;
    }

    Program randomProgram() {
        List<Instruction> code = splittableRandom
                .ints(programSize, 0, instructionSupplier.size())
                .mapToObj(instructionSupplier::get)
                .map(Supplier::get)
                .collect(Collectors.toList());

        return new Program(code);
    }

    private Instruction generateGotoStatement() {
        int line = splittableRandom.nextInt(programSize);
        return new GotoInstruction(line);
    }

    private Instruction generateIfClause() {
        return IfClause.random((GotoInstruction) generateGotoStatement());
    }

    public Instruction randomLine() {
        int instructionIndex = splittableRandom.nextInt(0, instructionSupplier.size());
        return instructionSupplier.get(instructionIndex).get();
    }
}
