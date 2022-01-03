package de.socrates.paramecium.environment;

import java.util.Arrays;
import java.util.Optional;
import java.util.SplittableRandom;

public enum Tile {
    EMPTY(' '),
    FOOD('.'),
    WALL('x');

    private final char ascii;

    Tile(char ascii) {
        this.ascii = ascii;
    }

    public static Tile random() {
        int element = new SplittableRandom().nextInt(0, Tile.values().length);

        return Tile.values()[element];
    }

    public static Tile parse(char ascii) {
        Optional<Tile> first = Arrays.stream(values()).filter(e -> e.ascii == ascii).findFirst();
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
