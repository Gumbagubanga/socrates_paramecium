package de.socrates.paramecium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
class ParameciumTest {

    @Test
    void positive_health() {
        Paramecium paramecium = new Paramecium(1, null);

        Assertions.assertTrue(paramecium.isAlive());
    }

    @Test
    void zero_health() {
        Paramecium paramecium = new Paramecium(0, null);

        Assertions.assertFalse(paramecium.isAlive());
    }

    @Test
    void print() {
        Paramecium paramecium = new Paramecium(100, null);

        Assertions.assertEquals("Health 100", paramecium.toString());
    }
}
