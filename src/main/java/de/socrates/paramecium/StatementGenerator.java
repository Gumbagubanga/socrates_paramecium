package de.socrates.paramecium;

import de.socrates.paramecium.language.EatStatement;
import de.socrates.paramecium.language.GotoStatement;
import de.socrates.paramecium.language.IfClause;
import de.socrates.paramecium.language.MoveStatement;
import de.socrates.paramecium.language.NopStatement;
import de.socrates.paramecium.language.Statement;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class StatementGenerator {

    public static final int PROGRAM_SIZE = 20;

    private static final Map<Integer, Supplier<Statement>> SIMPLE_SUPPLIER_MAP = Map.of(
            0, NopStatement::new,
            1, EatStatement::new,
            2, MoveStatement::random);

    private static final Map<Integer, Supplier<Statement>> SUPPLIER_MAP = Map.of(
            0, NopStatement::new,
            1, EatStatement::new,
            2, MoveStatement::random,
            3, StatementGenerator::generateGotoStatement,
            4, StatementGenerator::generateIfClause);

    public static Statement randomStatement() {
        int element = ThreadLocalRandom.current().nextInt(0, SUPPLIER_MAP.size());

        return SUPPLIER_MAP.get(element).get();
    }

    static Statement generateGotoStatement() {
        return new GotoStatement(randomLineNumber());
    }

    static Statement generateIfClause() {
        return IfClause.random(randomSimpleStatement());
    }

    private static Statement randomSimpleStatement() {
        int element = ThreadLocalRandom.current().nextInt(0, SIMPLE_SUPPLIER_MAP.size());

        return SIMPLE_SUPPLIER_MAP.get(element).get();
    }

    private static int randomLineNumber() {
        return ThreadLocalRandom.current().nextInt(0, PROGRAM_SIZE);
    }

}
