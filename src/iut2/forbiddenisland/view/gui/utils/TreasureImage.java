package iut2.forbiddenisland.view.gui.utils;

import iut2.forbiddenisland.model.Treasure;

public class TreasureImage extends AutoResizePreserveRatioImagePanel {

    public TreasureImage(final Treasure treasure) {
        super(treasure.getMetadata().getImage());
    }
}
