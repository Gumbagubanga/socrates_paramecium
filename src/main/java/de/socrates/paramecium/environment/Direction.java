package de.socrates.paramecium.environment;

import java.util.SplittableRandom;

public enum Direction {
    SOUTH, WEST, EAST, NORTH, IN_PLACE;

    public static Direction random() {
        int element = new SplittableRandom().nextInt(Direction.values().length);

        return Direction.values()[element];
    }
}
