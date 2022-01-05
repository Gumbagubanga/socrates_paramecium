package de.socrates.paramecium.environment;

import java.util.Arrays;

public class World {

    private int xPosition;
    private int yPosition;
    private final Tile[][] tile;

    private static final Tile[][] template = load();

    public static World generate() {
        Tile[][] tiles = Arrays.stream(template).map(Tile[]::clone).toArray(Tile[][]::new);

        return new World(tiles);
    }

    private static Tile[][] load() {
        String ascii;
        ascii = "xxxxxxxxx\n" +
                "x.......x\n" +
                "x...x...x\n" +
                "x..xxx..x\n" +
                "x...x...x\n" +
                "x.......x\n" +
                "xxxxxxxxx";
        return parse(ascii);
    }

    private static Tile[][] parse(String ascii) {
        String[] split = ascii.split("\n");
        int width = split[0].length();
        int height = split.length;

        Tile[][] tile = new Tile[height][width];

        for (int y = 0; y < split.length; y++) {
            String line = split[y];
            for (int x = 0; x < line.length(); x++) {
                tile[y][x] = Tile.parse(line.charAt(x));
            }
        }

        return tile;
    }

    public World(Tile[][] tile) {
        this.xPosition = 1;
        this.yPosition = 1;
        this.tile = tile;
    }

    Tile whatIs(Direction direction) {
        int sensePositionX = xPosition;
        int sensePositionY = yPosition;

        switch (direction) {
            case SOUTH:
                sensePositionY++;
                break;
            case WEST:
                sensePositionX--;
                break;
            case EAST:
                sensePositionX++;
                break;
            case NORTH:
                sensePositionY--;
                break;
            case IN_PLACE:
                break;
        }

        return tile[sensePositionY][sensePositionX];
    }

    void move(Direction direction) {
        if (whatIs(direction) != Tile.WALL) {
            switch (direction) {
                case SOUTH:
                    moveSouth();
                    break;
                case WEST:
                    moveWest();
                    break;
                case EAST:
                    moveEast();
                    break;
                case NORTH:
                    moveNorth();
                    break;
                default:
                    break;
            }
        }
    }

    boolean takeFood() {
        if (tile[yPosition][xPosition] == Tile.FOOD) {
            tile[yPosition][xPosition] = Tile.EMPTY;
            return true;
        }
        return false;
    }

    int width() {
        return tile[0].length;
    }

    int height() {
        return tile.length;
    }

    private void moveEast() {
        xPosition += 1;
    }

    private void moveWest() {
        xPosition -= 1;
    }

    private void moveNorth() {
        yPosition -= 1;
    }

    private void moveSouth() {
        yPosition += 1;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int y = 0; y < tile.length; y++) {
            Tile[] row = tile[y];

            for (Tile value : row) {
                result.append(value.getAscii());
            }

            if (y != tile.length - 1) {
                result.append("\n");
            }
        }

        return result.toString();
    }

}
