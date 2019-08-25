package de.socrates.paramecium.language;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
class GotoStatementTest {

    @Test
    void goto_0() {
        Assertions.assertEquals("goto(0)", new GotoStatement(0).print());
    }

}
