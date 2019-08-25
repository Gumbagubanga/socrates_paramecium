package de.socrates.paramecium.language;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
class NopInstructionTest {

    @Test
    void nop() {
        Assertions.assertEquals("nop()", ((Instruction) new NopInstruction()).toString());
    }
}
