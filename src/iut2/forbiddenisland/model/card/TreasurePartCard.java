package iut2.forbiddenisland.model.card;

import iut2.forbiddenisland.model.Treasure;

/**
 * Represent a card that can be used to claim a treasure.
 * NOT TO BE CONFUSED WITH {@link TreasureCard} !!!
 */
public class TreasurePartCard extends TreasureCard {

    private final Treasure treasure;

    public TreasurePartCard(final Treasure treasure) {
        super(treasure.getName());
        this.treasure = treasure;
    }

    public Treasure getTreasure() {
        return treasure;
    }
}
