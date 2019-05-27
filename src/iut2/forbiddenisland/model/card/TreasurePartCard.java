package iut2.forbiddenisland.model.card;

import iut2.forbiddenisland.model.Treasure;

public class TreasurePartCard extends TreasureCard {

    private final Treasure treasure;

    public TreasurePartCard(final String name, final Treasure treasure) {
        super(name);
        this.treasure = treasure;
    }

    public Treasure getTreasure() {
        return treasure;
    }
}
