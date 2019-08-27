package de.socrates.paramecium.language.types;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public enum Environment {
    EMPTY(' '),
    FOOD('.'),
    WALL('x');

    private final char ascii;

    Environment(char ascii) {
        this.ascii = ascii;
    }

    public static Environment random() {
        int element = ThreadLocalRandom.current().nextInt(0, Environment.values().length);

        return Environment.values()[element];
    }

    public static Environment parse(char ascii) {
        Optional<Environment> first = Arrays.stream(values()).filter(e -> e.ascii == ascii).findFirst();
        try {
            return first.orElseThrow();
        } catch (Exception e) {
            return null;
        }
    }

    public char getAscii() {
        return ascii;
    }
}
