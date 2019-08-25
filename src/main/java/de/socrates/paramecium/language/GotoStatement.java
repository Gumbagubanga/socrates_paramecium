package de.socrates.paramecium.language;

public class GotoStatement implements Statement {
    private final int line;

    public GotoStatement(int line) {
        this.line = line;
    }

    public int getLine() {
        return line;
    }

    @Override
    public String print() {
        return String.format("goto(%d)", line);
    }

}
