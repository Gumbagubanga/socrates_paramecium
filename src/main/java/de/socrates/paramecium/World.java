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
                "x       x\n" +
                "x   x   x\n" +
                "x  xxx  x\n" +
                "x   x   x\n" +
                "x       x\n" +
                "xxxxxxxxx";

        String[] split = ascii.split("\n");
        int width = split[0].length();
        int height = split.length;

        world.environment = new Environment[height][width];

        for (int y = 0; y < split.length; y++) {
            String line = split[y];
            for (int x = 0; x < line.length(); x++) {
                switch (line.charAt(x)) {
                    case 'x':
                        world.environment[y][x] = Environment.WALL;
                        break;
                    case ' ':
                        world.environment[y][x] = Environment.FOOD;
                    default:
                        break;
                }
            }
        }

        world.xPosition = 1;
        world.yPosition = 1;

        return world;
    }

    Environment whatIsIn(Direction direction) {
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

        if (whatIsIn(direction) != Environment.WALL) {
            movement.entrySet().stream()
                    .filter(e -> e.getKey() == direction)
                    .map(Map.Entry::getValue)
                    .findFirst()
                    .ifPresent(Runnable::run);
        }
    }

    boolean hasFood() {
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
            Environment[] senses = environment[y];

            for (int x = 0; x < senses.length; x++) {
                Environment sens = senses[x];
                switch (sens) {
                    case EMPTY:
                        result += "_";
                        break;
                    case FOOD:
                        result += " ";
                        break;
                    case WALL:
                        result += "x";
                        break;
                    default:
                        break;
                }
            }

            if (y != environment.length - 1) {
                result += "\n";
            }
        }

        return result;
    }

}
