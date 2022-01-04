package de.socrates.paramecium.environment;

public class World {

    private int xPosition;
    private int yPosition;
    private Tile[][] tile;

    public static World generate() {
        String ascii;
        ascii = "xxxxxxxxx\n" +
                "x.......x\n" +
                "x...x...x\n" +
                "x..xxx..x\n" +
                "x...x...x\n" +
                "x.......x\n" +
                "xxxxxxxxx";

        return generate(ascii);
    }

    public static World generate(String ascii) {
        World world = new World();

        String[] split = ascii.split("\n");
        int width = split[0].length();
        int height = split.length;

        world.tile = new Tile[height][width];

        for (int y = 0; y < split.length; y++) {
            String line = split[y];
            for (int x = 0; x < line.length(); x++) {
                world.tile[y][x] = Tile.parse(line.charAt(x));
            }
        }

        world.xPosition = 1;
        world.yPosition = 1;

        return world;
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
        String result = "";

        for (int y = 0; y < tile.length; y++) {
            Tile[] row = tile[y];

            for (int x = 0; x < row.length; x++) {
                result += row[x].getAscii();
            }

            if (y != tile.length - 1) {
                result += "\n";
            }
        }

        return result;
    }

}
