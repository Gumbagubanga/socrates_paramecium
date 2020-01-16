package de.socrates.paramecium.simulation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class FamilyNode {
    public static final FamilyNode EMPTY = new FamilyNode(null);

    private final Performance performance;
    private final List<FamilyNode> children = new ArrayList<>();

    public FamilyNode(Performance performance) {
        this.performance = performance;
    }

    public static FamilyNode create(Performance performance) {
        return new FamilyNode(performance);
    }

    public Stream<FamilyNode> streamOrders() {
        return Stream.concat(
                Stream.of(this),
                children.stream().flatMap(FamilyNode::streamOrders));
    }

    public Performance findBest() {
        return this.streamOrders()
                .map(FamilyNode::getChildren)
                .flatMap(Collection::stream)
                .map(n -> n.performance)
                .max(Comparator.comparing(Performance::getTicks))
                .orElse(null);
    }

    public Program findPartner() {
        return null;
    }

    public void addAll(List<Performance> performances) {
        performances.stream().map(FamilyNode::create).forEach(children::add);
    }

    public Program getProgram() {
        return performance.getProgram();
    }

    public int getTicks() {
        return performance.getTicks();
    }

    public List<FamilyNode> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "FamilyNode{" +
                "performance=" + performance +
                '}';
    }
}
