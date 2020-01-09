package de.socrates.paramecium.simulation;

import de.socrates.paramecium.language.EatInstruction;
import de.socrates.paramecium.language.GotoInstruction;
import de.socrates.paramecium.language.IfClause;
import de.socrates.paramecium.language.Instruction;
import de.socrates.paramecium.language.MoveInstruction;
import de.socrates.paramecium.language.NopInstruction;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class ProgramGenerator {

    private static final int PROGRAM_SIZE = 20;

    private static final Map<Integer, Supplier<Instruction>> SIMPLE_SUPPLIER_MAP = Map.of(
            0, NopInstruction::new,
            1, EatInstruction::new,
            2, MoveInstruction::random);

    private static final Map<Integer, Supplier<Instruction>> SUPPLIER_MAP = Map.of(
            0, NopInstruction::new,
            1, EatInstruction::new,
            2, MoveInstruction::random,
            3, ProgramGenerator::generateGotoStatement,
            4, ProgramGenerator::generateIfClause);

    static Program randomProgram() {
        List<Instruction> code = IntStream.range(0, PROGRAM_SIZE)
                .mapToObj(i -> randomStatement())
                .collect(Collectors.toList());

        return new Program(code);
    }

    static Instruction randomStatement() {
        int element = ThreadLocalRandom.current().nextInt(0, SUPPLIER_MAP.size());

        return SUPPLIER_MAP.get(element).get();
    }

    private static Instruction generateGotoStatement() {
        return new GotoInstruction(randomLineNumber());
    }

    private static Instruction generateIfClause() {
        return IfClause.random(randomSimpleStatement());
    }

    private static Instruction randomSimpleStatement() {
        int element = ThreadLocalRandom.current().nextInt(0, SIMPLE_SUPPLIER_MAP.size());

        return SIMPLE_SUPPLIER_MAP.get(element).get();
    }

    private static int randomLineNumber() {
        return ThreadLocalRandom.current().nextInt(0, PROGRAM_SIZE);
    }

}
