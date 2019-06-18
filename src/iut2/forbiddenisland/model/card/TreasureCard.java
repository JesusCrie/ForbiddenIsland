package iut2.forbiddenisland.model.card;

import iut2.forbiddenisland.view.TreasureCardGraphicalMetadata;

/**
 * Represent a treasure card, drawable by any player.
 * NOT TO BE CONFUSED WITH {@link TreasurePartCard} !!!
 */
public abstract class TreasureCard extends Card {

    private final TreasureCardGraphicalMetadata metadata;

    public TreasureCard(final TreasureCardGraphicalMetadata metadata) {
        super(metadata.getName());
        this.metadata = metadata;
    }

    public TreasureCardGraphicalMetadata getMetadata() {
        return metadata;
    }
}
