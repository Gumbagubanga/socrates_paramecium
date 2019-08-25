package de.socrates.paramecium;

import de.socrates.paramecium.language.NopStatement;
import de.socrates.paramecium.language.Statement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
class ProgramTest {

    @Test
    void read_write() {
        Program program = new Program();
        program.write(new NopStatement());

        Assertions.assertEquals(((Statement) new NopStatement()).toString(), program.read(0).toString());
    }

    @Test
    void print() {
        Program program = new Program();
        program.write(new NopStatement());
        program.write(new NopStatement());

        Assertions.assertEquals(
                String.format(" 0 %s\n 1 %s", ((Statement) new NopStatement()).toString(), ((Statement) new NopStatement()).toString()),
                program.toString());
    }

}
