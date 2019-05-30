package iut2.forbiddenisland.model;

import iut2.forbiddenisland.model.adventurer.Adventurer;
import iut2.forbiddenisland.model.cell.TreasureCell;

import java.util.List;

public class Treasure {

    private final String name;
    private boolean claimable;
    private final List<TreasureCell> cells;

    public Treasure(final String name, final List<TreasureCell> cells) {
        this.name = name;
        this.claimable = true;
        this.cells = cells;
    }

    public String getName() {
        return name;
    }

    public boolean isClaimable() {
        return claimable;
    }

    public void claim(final Adventurer adv) {
        adv.addTreasure(this);
        claimable = false;
    }

    public List<TreasureCell> getCells() {
        return cells;
    }
}
