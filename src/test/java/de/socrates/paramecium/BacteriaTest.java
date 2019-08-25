package de.socrates.paramecium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
class BacteriaTest {

    @Test
    void positive_health() {
        Bacteria paramecium = new Bacteria(null, 1, 0, 0);

        Assertions.assertTrue(paramecium.isAlive());
    }

    @Test
    void zero_health() {
        Bacteria paramecium = new Bacteria(null, 0, 0, 0);

        Assertions.assertFalse(paramecium.isAlive());
    }

}
