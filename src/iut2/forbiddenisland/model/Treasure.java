package iut2.forbiddenisland.model;

import iut2.forbiddenisland.model.adventurer.Adventurer;
import iut2.forbiddenisland.model.cell.TreasureCell;

import java.util.ArrayList;
import java.util.List;

public class Treasure {

    private final String name;
    private boolean claimable;

    public Treasure(final String name) {
        this.name = name;
        this.claimable = true;
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
}
