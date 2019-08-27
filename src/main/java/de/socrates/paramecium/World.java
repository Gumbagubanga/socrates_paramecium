package de.socrates.paramecium;

import de.socrates.paramecium.language.types.Direction;
import de.socrates.paramecium.language.types.Environment;

import java.util.Map;

public class World {

    private int xPosition;
    private int yPosition;
    private Environment[][] environment;

    public static World generate() {
        World world = new World();

        String ascii;
        ascii = "xxxxxxxxx\n" +
                "x.......x\n" +
                "x...x...x\n" +
                "x..xxx..x\n" +
                "x...x...x\n" +
                "x.......x\n" +
                "xxxxxxxxx";

        String[] split = ascii.split("\n");
        int width = split[0].length();
        int height = split.length;

        world.environment = new Environment[height][width];

        for (int y = 0; y < split.length; y++) {
            String line = split[y];
            for (int x = 0; x < line.length(); x++) {
                world.environment[y][x] = Environment.parse(line.charAt(x));
            }
        }

        world.xPosition = 1;
        world.yPosition = 1;

        return world;
    }

    Environment whatIs(Direction direction) {
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

        return environment[sensePositionY][sensePositionX];
    }

    void move(Direction direction) {
        Map<Direction, Runnable> movement = Map.of(
                Direction.NORTH, this::moveNorth,
                Direction.WEST, this::moveWest,
                Direction.SOUTH, this::moveSouth,
                Direction.EAST, this::moveEast
        );

        if (whatIs(direction) != Environment.WALL) {
            movement.entrySet().stream()
                    .filter(e -> e.getKey() == direction)
                    .map(Map.Entry::getValue)
                    .findFirst()
                    .ifPresent(Runnable::run);
        }
    }

    boolean takeFood() {
        if (environment[yPosition][xPosition] == Environment.FOOD) {
            environment[yPosition][xPosition] = Environment.EMPTY;
            return true;
        }
        return false;
    }

    int width() {
        return environment[0].length;
    }

    int height() {
        return environment.length;
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

        for (int y = 0; y < environment.length; y++) {
            Environment[] row = environment[y];

            for (int x = 0; x < row.length; x++) {
                result += row[x].getAscii();
            }

            if (y != environment.length - 1) {
                result += "\n";
            }
        }

        return result;
    }

}
