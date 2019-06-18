package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.model.adventurer.power.EngineerPower;
import iut2.forbiddenisland.view.AdventurerGraphicalMetadata;

/**
 * Represent the character "engineer".
 */
public class Engineer extends Adventurer {

    public Engineer(final String name) {
        super(name, AdventurerGraphicalMetadata.ENGINEER,
                new EngineerPower()
        );
    }
}
