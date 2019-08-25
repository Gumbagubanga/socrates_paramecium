package de.socrates.paramecium.language;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
class NopStatementTest {

    @Test
    void nop() {
        Assertions.assertEquals("nop()", ((Statement) new NopStatement()).toString());
    }
}
