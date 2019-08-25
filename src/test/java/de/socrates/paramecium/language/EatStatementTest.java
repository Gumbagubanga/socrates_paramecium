package de.socrates.paramecium.language;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
class EatStatementTest {

    @Test
    void eat() {
        Assertions.assertEquals("eat()", new EatStatement().print());
    }
}
