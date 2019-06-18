package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.model.adventurer.power.DiverPower;
import iut2.forbiddenisland.view.AdventurerGraphicalMetadata;

/**
 * Represent the character "diver".
 */
public class Diver extends Adventurer {

    public Diver(final String name) {
        super(name, AdventurerGraphicalMetadata.DIVER,
                new DiverPower()
        );
    }
}
