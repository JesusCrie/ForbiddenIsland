package iut2.forbiddenisland.view.gui.components;

import iut2.forbiddenisland.model.adventurer.Adventurer;

public class AdventurerCardButton extends AutoResizePreserveRatioImageButton {

    public AdventurerCardButton(final Adventurer adv) {
        super(adv.getMetadata().getCardImage());
    }

}
