package iut2.forbiddenisland.model;

import iut2.forbiddenisland.model.cell.TreasureCell;

import java.util.List;

public class Treasure {

    private final String name;
    private boolean claimable = true;
    private final List<TreasureCell> cells;

    public Treasure(final String name, final List<TreasureCell> cells) {
        this.name = name;
        this.cells = cells;
    }

    public String getName() {
        return name;
    }

    public boolean isClaimable() {
        return claimable;
    }

    public List<TreasureCell> getCells() {
        return cells;
    }
}
