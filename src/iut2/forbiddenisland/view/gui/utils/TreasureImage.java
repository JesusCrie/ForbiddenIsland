package iut2.forbiddenisland.view.gui.utils;

import iut2.forbiddenisland.model.Treasure;

public class TreasureImage extends AutoResizePreserveRatioImagePanel {

    private final Treasure treasure;

    public TreasureImage(final Treasure treasure) {
        super(treasure.getMetadata().getImage());
        this.treasure = treasure;
    }

    public void update() {
        setVisible(!treasure.isClaimable());
    }
}
