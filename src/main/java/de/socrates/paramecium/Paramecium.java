package de.socrates.paramecium;

import de.socrates.paramecium.language.types.Direction;
import de.socrates.paramecium.language.types.Sense;

import java.util.Map;

public class Paramecium {
    private final World world;

    private int health;
    private int xPosition;
    private int yPosition;

    public Paramecium(World world, int health, int xPosition, int yPosition) {
        this.world = world;
        this.health = health;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void eat(int energy) {
        if (sense(Direction.NONE) == Sense.FOOD) {
            health += energy;
            world.environment[yPosition][xPosition] = Sense.EMPTY;
        }
    }

    public void exhaust(int energy) {
        health -= energy;
    }

    public Sense sense(Direction direction) {
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
            case NONE:
                break;
        }

        return world.environment[sensePositionY][sensePositionX];
    }

    public void move(Direction direction) {
        Map<Direction, Runnable> movement = Map.of(
                Direction.NORTH, this::moveNorth,
                Direction.WEST, this::moveWest,
                Direction.SOUTH, this::moveSouth,
                Direction.EAST, this::moveEast
        );

        if (sense(direction) != Sense.WALL) {
            movement.entrySet().stream()
                    .filter(e -> e.getKey() == direction)
                    .map(Map.Entry::getValue)
                    .findFirst()
                    .ifPresent(Runnable::run);
        }

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
        return String.format("Health %3d\n%s\n", health, world.toString());
    }
}
