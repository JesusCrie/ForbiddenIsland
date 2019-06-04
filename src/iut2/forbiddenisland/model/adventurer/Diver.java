package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.model.adventurer.power.DiverPower;

/**
 * Represent the character "diver".
 */
public class Diver extends Adventurer {

    public Diver() {
        super("Plongeur",
                new DiverPower()
        );
    }
}
