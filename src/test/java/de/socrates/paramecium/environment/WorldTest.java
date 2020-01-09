package de.socrates.paramecium.environment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
class WorldTest {

    @Test
    void ascii_parsing() {
        World world = World.generate();

        String ascii;
        ascii = "xxxxxxxxx\n" +
                "x.......x\n" +
                "x...x...x\n" +
                "x..xxx..x\n" +
                "x...x...x\n" +
                "x.......x\n" +
                "xxxxxxxxx";

        Assertions.assertEquals(9, world.width());
        Assertions.assertEquals(7, world.height());

        Assertions.assertEquals(ascii, world.toString());
    }
}