package iut2.forbiddenisland.view.gui.components;

import iut2.forbiddenisland.model.card.TreasureCard;

public class CardButton extends AutoResizeImageButton {

    private final TreasureCard card;

    public CardButton(final TreasureCard card) {
        super(card.getMetadata().getImage());
        this.card = card;
    }
}
