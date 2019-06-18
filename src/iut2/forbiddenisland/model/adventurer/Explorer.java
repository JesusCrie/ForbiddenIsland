package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.model.adventurer.power.ExplorerDryPower;
import iut2.forbiddenisland.model.adventurer.power.ExplorerMovePower;
import iut2.forbiddenisland.view.AdventurerGraphicalMetadata;

/**
 * Represent the character "explorer".
 */
public class Explorer extends Adventurer {

    public Explorer(final String name) {
        super(name, AdventurerGraphicalMetadata.EXPLORER,
                new ExplorerMovePower(), new ExplorerDryPower()
        );
    }
}
