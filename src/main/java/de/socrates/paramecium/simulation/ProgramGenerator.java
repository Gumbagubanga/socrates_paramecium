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

    public ProgramGenerator(int programSize) {
        this.programSize = programSize;
        this.instructionSupplier = List.of(
                NopInstruction::new,
                EatInstruction::new,
                MoveInstruction::random,
                this::generateGotoStatement,
                this::generateIfClause);
    }

    Program randomProgram() {
        List<Instruction> code = new SplittableRandom()
                .ints(programSize, 0, instructionSupplier.size())
                .mapToObj(instructionSupplier::get)
                .map(Supplier::get)
                .collect(Collectors.toList());

        return new Program(code);
    }

    private Instruction generateGotoStatement() {
        int line = new SplittableRandom().nextInt(programSize);
        return new GotoInstruction(line);
    }

    private Instruction generateIfClause() {
        return IfClause.random((GotoInstruction) generateGotoStatement());
    }

    public Instruction randomLine() {
        int instructionIndex = new SplittableRandom().nextInt(0, instructionSupplier.size());
        return instructionSupplier.get(instructionIndex).get();
    }
}
