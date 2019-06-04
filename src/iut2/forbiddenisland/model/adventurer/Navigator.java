package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.model.adventurer.power.NavigatorPower;

/**
 * Represent the character "navigator".
 */
public class Navigator extends Adventurer {

    public Navigator() {
        super("Navigateur",
                new NavigatorPower()
        );
    }
}
