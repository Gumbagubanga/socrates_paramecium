package de.socrates.paramecium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
class ParameciumTest {

    @Test
    void positive_health() {
        Paramecium paramecium = new Paramecium(null, 1, 0, 0);

        Assertions.assertTrue(paramecium.isAlive());
    }

    @Test
    void zero_health() {
        Paramecium paramecium = new Paramecium(null, 0, 0, 0);

        Assertions.assertFalse(paramecium.isAlive());
    }

}
