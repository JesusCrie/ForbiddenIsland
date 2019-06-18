package iut2.forbiddenisland.view.gui.utils;

import iut2.forbiddenisland.model.adventurer.Adventurer;

public class AdventurerCard extends AutoResizeImageButton {

    public AdventurerCard(final Adventurer adv) {
        super(adv.getMetadata().getCardImage());
    }

}
