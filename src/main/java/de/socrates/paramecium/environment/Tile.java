package de.socrates.paramecium.environment;

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
        switch (ascii) {
            case ' ':
                return EMPTY;
            case '.':
                return FOOD;
            case 'x':
                return WALL;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public char getAscii() {
        return ascii;
    }
}
