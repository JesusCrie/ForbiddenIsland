package iut2.forbiddenisland.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Adventurer {

    private final String name;
    private final List<Power> powers;
    private Cell position;
    private final List<Treasure> treasures = new ArrayList<>();

    public Adventurer(final String name, final Cell position, final Power... powers) {
        this.name = name;
        this.powers = Arrays.asList(powers);
        move(position);
    }

    public String getName() {
        return this.name;
    }

    public Cell getPosition() {
        return position;
    }

    public void move(final Cell to) {
        this.position = to;
    }

    public List<Power> getPowers() {
        return powers;
    }

    public List<Treasure> getTreasures() {
        return treasures;
    }
}
