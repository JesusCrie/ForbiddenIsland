package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.model.adventurer.power.EngineerPower;

/**
 * Represent the character "engineer".
 */
public class Engineer extends Adventurer {

    public Engineer() {
        super("Ingénieur",
                new EngineerPower()
        );
    }
}
