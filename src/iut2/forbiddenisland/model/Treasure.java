package iut2.forbiddenisland.model;

import iut2.forbiddenisland.model.adventurer.Adventurer;
import iut2.forbiddenisland.model.cell.TreasureCell;
import iut2.forbiddenisland.view.TreasureGraphicalMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Treasure {

    private final TreasureGraphicalMetadata metadata;
    private boolean claimable;

    public Treasure(final TreasureGraphicalMetadata metadata) {
        this.metadata = metadata;
        this.claimable = true;
    }

    public TreasureGraphicalMetadata getMetadata() {
        return metadata;
    }

    public String getName() {
        return metadata.getName();
    }

    public boolean isClaimable() {
        return claimable;
    }

    public void claim(final Adventurer adv) {
        adv.addTreasure(this);
        claimable = false;
    }

    // Equality methods

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Treasure && metadata == ((Treasure) obj).metadata;
    }

    @Override
    public int hashCode() {
        return Objects.hash(metadata);
    }
}
