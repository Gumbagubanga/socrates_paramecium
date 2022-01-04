package de.socrates.paramecium.language;

import de.socrates.paramecium.environment.Tile;

import java.util.SplittableRandom;

public enum CompareOperator {
    EQUAL("=="),
    NOT_EQUAL("!=");

    private final String symbol;

    CompareOperator(String symbol) {
        this.symbol = symbol;
    }

    public static CompareOperator random() {
        int element = new SplittableRandom().nextInt(CompareOperator.values().length);

        return CompareOperator.values()[element];
    }

    public boolean eval(Tile tile1, Tile tile2) {
        switch (this) {
            case EQUAL:
                return tile1 == tile2;
            case NOT_EQUAL:
                return tile1 != tile2;
            default:
                throw new UnsupportedOperationException("Not implemented");
        }
    }

    @Override
    public String toString() {
        return symbol;
    }
}
