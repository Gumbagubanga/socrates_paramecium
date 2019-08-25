package de.socrates.paramecium;

import de.socrates.paramecium.language.types.Sense;

public class World {

    Sense[][] environment;

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

        world.environment = new Sense[height][width];

        for (int y = 0; y < split.length; y++) {
            String line = split[y];
            for (int x = 0; x < line.length(); x++) {
                switch (line.charAt(x)) {
                    case 'x':
                        world.environment[y][x] = Sense.WALL;
                        break;
                    case ' ':
                        world.environment[y][x] = Sense.FOOD;
                    default:
                        break;
                }
            }
        }
        return world;
    }

    @Override
    public String toString() {
        String result = "";

        for (int y = 0; y < environment.length; y++) {
            Sense[] senses = environment[y];

            for (int x = 0; x < senses.length; x++) {
                Sense sens = senses[x];
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
