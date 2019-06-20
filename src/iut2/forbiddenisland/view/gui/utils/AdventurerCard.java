package iut2.forbiddenisland.view.gui.utils;

import iut2.forbiddenisland.model.adventurer.Adventurer;
import iut2.forbiddenisland.view.gui.components.AutoResizePreserveRatioImagePanel;

public class AdventurerCard extends AutoResizePreserveRatioImagePanel {

    public AdventurerCard(final Adventurer adv) {
        super(adv.getMetadata().getCardImage());
    }
}
