package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.model.adventurer.power.PilotPower;
import iut2.forbiddenisland.view.AdventurerGraphicalMetadata;

/**
 * Represent the character "pilot".
 */
public class Pilot extends Adventurer {

    public Pilot(final String name) {
        super(name, AdventurerGraphicalMetadata.PILOT,
                new PilotPower()
        );
    }
}
