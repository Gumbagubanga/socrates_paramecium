package de.socrates.paramecium;

import de.socrates.paramecium.language.types.Direction;
import de.socrates.paramecium.language.types.Environment;

public class Paramecium {
    private final World world;

    private int health;

    public Paramecium(int health, World world) {
        this.world = world;
        this.health = health;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void eat(int energy) {
        if (world.hasFood()) {
            health += energy;
        }
    }

    public void exhaust(int energy) {
        health -= energy;
    }

    public Environment sense(Direction direction) {
        return world.whatIsIn(direction);
    }

    public void move(Direction direction) {
        world.move(direction);
    }

    @Override
    public String toString() {
        return String.format("Health %3d\n%s\n", health, world.toString());
    }
}
