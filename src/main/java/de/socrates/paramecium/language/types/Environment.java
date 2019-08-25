package de.socrates.paramecium.language.types;

import java.util.concurrent.ThreadLocalRandom;

public enum Environment {
    EMPTY, FOOD, WALL;

    public static Environment random() {
        int element = ThreadLocalRandom.current().nextInt(0, Environment.values().length);

        return Environment.values()[element];
    }
}
