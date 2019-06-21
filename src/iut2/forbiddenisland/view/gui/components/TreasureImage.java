package iut2.forbiddenisland.view.gui.components;

import iut2.forbiddenisland.model.Treasure;

public class TreasureImage extends AutoResizePreserveRatioImagePanel {

    private final Treasure treasure;

    public TreasureImage(final Treasure treasure) {
        super(treasure.getMetadata().getImage());
        this.treasure = treasure;
        update();
    }

    public void update() {
        setVisible(!treasure.isClaimable());
    }
}
