package de.socrates.paramecium.language;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
class EatInstructionTest {

    @Test
    void eat() {
        Assertions.assertEquals("eat()", ((Instruction) new EatInstruction()).toString());
    }
}
