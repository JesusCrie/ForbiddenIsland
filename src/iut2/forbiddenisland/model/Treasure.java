package iut2.forbiddenisland.model;

import java.util.List;

public class Treasure {

    private final String name;
    private final List<TreasureCell> cells;

    public Treasure(final String name, final List<TreasureCell> cells) {
        this.name = name;
        this.cells = cells;
    }

    public String getName() {
        return name;
    }

    public List<TreasureCell> getCells() {
        return cells;
    }
}
