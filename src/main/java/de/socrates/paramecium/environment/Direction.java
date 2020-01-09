package de.socrates.paramecium.environment;

import java.util.concurrent.ThreadLocalRandom;

public enum Direction {
    SOUTH, WEST, EAST, NORTH, IN_PLACE;

    public static Direction random() {
        int element = ThreadLocalRandom.current().nextInt(0, Direction.values().length);

        return Direction.values()[element];
    }
}
