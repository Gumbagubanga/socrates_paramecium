package de.socrates.paramecium.simulation;

import de.socrates.paramecium.language.Instruction;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Program {

    private final List<Instruction> code;

    Program() {
        this(new ArrayList<>());
    }

    Program(List<Instruction> code) {
        this.code = code;
    }

    void write(Instruction statement) {
        code.add(statement);
    }

    Instruction read(int line) {
        if (line >= code.size()) {
            return null;
        }

        return code.get(line);
    }

    public Program mate(Program partner, int codeBreak) {
        List<Instruction> top = code.subList(0, codeBreak);
        List<Instruction> bottom = partner.code.subList(codeBreak, partner.code.size());

        List<Instruction> descendant = new ArrayList<>(top.size() + bottom.size());
        descendant.addAll(top);
        descendant.addAll(bottom);
        return new Program(descendant);
    }

    public Program mutate(int mutationRate, ProgramGenerator programGenerator, SplittableRandom splittableRandom) {
        IntStream.range(0, code.size())
                .filter(i -> splittableRandom.nextInt(mutationRate) == 0)
                .forEach(i -> code.set(i, programGenerator.randomLine()));

        return this;
    }

    @Override
    public String toString() {
        return IntStream.range(0, code.size())
                .mapToObj(i -> String.format("%02d %s", i, code.get(i)))
                .collect(Collectors.joining("\n", "", "\n"));
    }
}
