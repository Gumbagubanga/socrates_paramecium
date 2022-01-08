package de.socrates.paramecium.simulation;

import de.socrates.paramecium.language.Instruction;
import de.socrates.paramecium.language.NopInstruction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
class ProgramTest {

    @Test
    void read_write() {
        Program program = new Program();
        program.write(new NopInstruction());

        Assertions.assertEquals(((Instruction) new NopInstruction()).toString(), program.read(0).toString());
    }

    @Test
    void print() {
        Program program = new Program();
        program.write(new NopInstruction());
        program.write(new NopInstruction());

        Assertions.assertEquals(
                String.format("00 %s\n01 %s\n", new NopInstruction(), new NopInstruction()),
                program.toString());
    }

}
