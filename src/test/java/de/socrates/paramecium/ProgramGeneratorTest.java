package de.socrates.paramecium;

import de.socrates.paramecium.language.EatStatement;
import de.socrates.paramecium.language.GotoStatement;
import de.socrates.paramecium.language.IfClause;
import de.socrates.paramecium.language.MoveStatement;
import de.socrates.paramecium.language.NopStatement;
import de.socrates.paramecium.language.Statement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.Set;

@Testable
class ProgramGeneratorTest {

    @Test
    void nop_statement_generator() {
        Assertions.assertTrue(StatementGenerator.generateNopStatement().getClass().isAssignableFrom(NopStatement.class));
    }

    @Test
    void eat_statement_generator() {
        Assertions.assertTrue(StatementGenerator.generateEatStatement().getClass().isAssignableFrom(EatStatement.class));
    }

    @Test
    void move_statement_generator() {
        Assertions.assertTrue(StatementGenerator.generateMoveStatement().getClass().isAssignableFrom(MoveStatement.class));
    }

    @Test
    void goto_statement_generator() {
        Assertions.assertTrue(StatementGenerator.generateGotoStatement().getClass().isAssignableFrom(GotoStatement.class));
    }

    @Test
    void if_clause_generator() {
        Assertions.assertTrue(StatementGenerator.generateIfClause().getClass().isAssignableFrom(IfClause.class));
    }

    @Test
    void statement_generator() {
        Set<Class<? extends Statement>> classes = Set.of(NopStatement.class, EatStatement.class, MoveStatement.class, GotoStatement.class, IfClause.class);

        Assertions.assertTrue(classes.contains(StatementGenerator.randomStatement().getClass()));
    }


}
