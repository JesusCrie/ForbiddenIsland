package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.model.adventurer.power.PilotPower;

/**
 * Represent the character "pilot".
 */
public class Pilot extends Adventurer {

    public Pilot() {
        super("Pilote",
                new PilotPower()
        );
    }
}
