package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.model.adventurer.power.MessengerPower;
import iut2.forbiddenisland.view.AdventurerGraphicalMetadata;

/**
 * Represent the character "messenger".
 */
public class Messenger extends Adventurer {

    public Messenger(final String name) {
        super(name, AdventurerGraphicalMetadata.MESSENGER,
                new MessengerPower()
        );
    }
}
