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

    private final int programSize;
    private final Map<Integer, Supplier<Instruction>> supplierMap;
    private final Map<Integer, Supplier<Instruction>> simpleSupplierMap;

    ProgramGenerator(int programSize) {
        this.programSize = programSize;
        this.supplierMap = Map.of(
                0, NopInstruction::new,
                1, EatInstruction::new,
                2, MoveInstruction::random,
                3, this::generateGotoStatement,
                4, this::generateIfClause);
        this.simpleSupplierMap = Map.of(
                0, NopInstruction::new,
                1, EatInstruction::new,
                2, MoveInstruction::random);
    }

    Program randomProgram() {
        List<Instruction> code = IntStream.range(0, programSize)
                .mapToObj(i -> randomStatement())
                .collect(Collectors.toList());

        return new Program(code);
    }

    Instruction randomStatement() {
        int element = ThreadLocalRandom.current().nextInt(0, supplierMap.size());

        return supplierMap.get(element).get();
    }

    private Instruction generateGotoStatement() {
        return new GotoInstruction(randomLineNumber());
    }

    private Instruction generateIfClause() {
        return IfClause.random(randomSimpleStatement());
    }

    private Instruction randomSimpleStatement() {
        int element = ThreadLocalRandom.current().nextInt(0, simpleSupplierMap.size());

        return simpleSupplierMap.get(element).get();
    }

    private int randomLineNumber() {
        return ThreadLocalRandom.current().nextInt(0, programSize);
    }
}
