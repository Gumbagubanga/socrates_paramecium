package de.socrates.paramecium.environment;

public class Paramecium {
    public final World world;

    private int health;

    public Paramecium(int health, World world) {
        this.world = world;
        this.health = health;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void eat(int energy) {
        if (world.takeFood()) {
            health += energy;
        }
    }

    public void exhaust(int energy) {
        health -= energy;
    }

    public Tile sense(Direction direction) {
        return world.whatIs(direction);
    }

    public void move(Direction direction) {
        world.move(direction);
    }

    @Override
    public String toString() {
        return String.format("Health %3d", health);
    }
}