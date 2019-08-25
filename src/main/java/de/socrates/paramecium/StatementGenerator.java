package de.socrates.paramecium;

import de.socrates.paramecium.language.EatStatement;
import de.socrates.paramecium.language.GotoStatement;
import de.socrates.paramecium.language.IfClause;
import de.socrates.paramecium.language.MoveStatement;
import de.socrates.paramecium.language.NopStatement;
import de.socrates.paramecium.language.Statement;
import de.socrates.paramecium.language.types.Direction;
import de.socrates.paramecium.language.types.Sense;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class StatementGenerator {

    public static final int PROGRAM_SIZE = 20;

    private static final Map<Integer, Supplier<Statement>> SIMPLE_SUPPLIER_MAP = Map.of(
            0, StatementGenerator::generateNopStatement,
            1, StatementGenerator::generateEatStatement,
            2, StatementGenerator::generateMoveStatement);

    private static final Map<Integer, Supplier<Statement>> SUPPLIER_MAP = Map.of(
            0, StatementGenerator::generateNopStatement,
            1, StatementGenerator::generateEatStatement,
            2, StatementGenerator::generateMoveStatement,
            3, StatementGenerator::generateGotoStatement,
            4, StatementGenerator::generateIfClause);

    public static Statement randomStatement() {
        int element = ThreadLocalRandom.current().nextInt(0, SUPPLIER_MAP.size());

        return SUPPLIER_MAP.get(element).get();
    }

    static Statement generateNopStatement() {
        return new NopStatement();
    }

    static Statement generateEatStatement() {
        return new EatStatement();
    }

    static Statement generateMoveStatement() {
        return new MoveStatement(randomDirection());
    }

    static Statement generateGotoStatement() {
        return new GotoStatement(randomLineNumber());
    }

    static Statement generateIfClause() {
        return new IfClause(randomDirection(), randomSense(), randomSimpleStatement());
    }

    private static Statement randomSimpleStatement() {
        int element = ThreadLocalRandom.current().nextInt(0, SIMPLE_SUPPLIER_MAP.size());

        return SIMPLE_SUPPLIER_MAP.get(element).get();
    }

    private static Direction randomDirection() {
        int element = ThreadLocalRandom.current().nextInt(0, Direction.values().length);

        return Direction.values()[element];
    }

    private static Sense randomSense() {
        int element = ThreadLocalRandom.current().nextInt(0, Sense.values().length);

        return Sense.values()[element];
    }

    private static int randomLineNumber() {
        return ThreadLocalRandom.current().nextInt(0, PROGRAM_SIZE);
    }

}
