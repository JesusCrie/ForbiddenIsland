package iut2.forbiddenisland.model.card;

import iut2.forbiddenisland.view.TreasureCardGraphicalMetadata;

/**
 * Represent any of the 3 special cards that a player can draw.
 *
 * @see HelicopterCard
 * @see SandBagCard
 * @see RisingWatersCard
 */
public abstract class SpecialCard extends TreasureCard {

    public SpecialCard(final TreasureCardGraphicalMetadata metadata) {
        super(metadata);
    }
}
