package de.socrates.paramecium.language;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
class GotoInstructionTest {

    @Test
    void goto_0() {
        Assertions.assertEquals("goto(0)", ((Instruction) new GotoInstruction(0)).toString());
    }

}
