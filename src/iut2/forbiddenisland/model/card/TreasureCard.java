package iut2.forbiddenisland.model.card;

/**
 * Represent a treasure card, drawable by any player.
 * NOT TO BE CONFUSED WITH {@link TreasurePartCard} !!!
 */
public abstract class TreasureCard extends Card {

    public TreasureCard(final String name) {
        super(name);
    }
}
