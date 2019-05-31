package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.model.adventurer.power.ExplorerDryPower;
import iut2.forbiddenisland.model.adventurer.power.ExplorerMovePower;

/**
 * Represent the character "explorer".
 */
public class Explorer extends Adventurer {

    public Explorer() {
        super("Explorateur",
                new ExplorerMovePower(), new ExplorerDryPower()
        );
    }
}
