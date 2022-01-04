package de.socrates.paramecium.simulation;

import de.socrates.paramecium.language.Instruction;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    Program mate(Program father) {
        int codeBreak = new SplittableRandom().nextInt(code.size());

        List<Instruction> descendant = Stream.concat(
                code.stream().limit(codeBreak),
                father.code.stream().skip(codeBreak)
        ).collect(Collectors.toList());

        return new Program(descendant);
    }

    @Override
    public String toString() {
        return IntStream.range(0, code.size())
                .mapToObj(i -> String.format("%2d %s", i, code.get(i)))
                .collect(Collectors.joining("\n"));
    }
}
