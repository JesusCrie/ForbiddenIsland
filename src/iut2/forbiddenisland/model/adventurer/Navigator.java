package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.model.adventurer.power.NavigatorPower;
import iut2.forbiddenisland.view.AdventurerGraphicalMetadata;

/**
 * Represent the character "navigator".
 */
public class Navigator extends Adventurer {

    public Navigator(final String name) {
        super(name, AdventurerGraphicalMetadata.NAVIGATOR,
                new NavigatorPower()
        );
    }
}
