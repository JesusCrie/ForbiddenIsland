package iut2.forbiddenisland.view.gui.components;

import iut2.forbiddenisland.model.adventurer.Adventurer;

import javax.swing.*;
import java.awt.Color;

public class AdventurerCardButton extends AutoResizePreserveRatioImageButton {

    private boolean highlighted = false;

    public AdventurerCardButton(final Adventurer adv) {
        super(adv.getMetadata().getCardImage());
    }

    public void setHighlighted(final boolean highlighted) {
        if (this.highlighted != highlighted) {
            this.highlighted = highlighted;
            setBorder(highlighted ? BorderFactory.createLineBorder(Color.GREEN, 10) : null);
        }
    }
}
