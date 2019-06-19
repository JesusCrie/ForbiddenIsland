package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.controller.Controller;
import iut2.forbiddenisland.view.IconGraphicalMetadata;
import iut2.forbiddenisland.view.gui.components.AutoResizePreserveRatioImagePanel;

import java.awt.Graphics;

public class WaterLevelPanel extends AutoResizePreserveRatioImagePanel {

    private int waterLevel = 5;

    public WaterLevelPanel(final Controller controller) {
        super(IconGraphicalMetadata.ICON_WATER_LEVEL.getImage());
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        g.drawImage(IconGraphicalMetadata.ICON_WATER_LEVEL_CURSOR.getImage(),
                20, getYCursor(waterLevel), (int) (getWidth() * 0.25), (int) (getHeight() * 0.07), null);
    }

    public int getYCursor(final int waterLevel) {
        return (int) (getHeight() * 0.785 - (waterLevel * getHeight() * 0.082));
    }
}
