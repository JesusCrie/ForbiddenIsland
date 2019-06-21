package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.view.IconGraphicalMetadata;
import iut2.forbiddenisland.view.gui.components.AutoResizePreserveRatioImagePanel;

import java.awt.Graphics;

public class WaterLevelPanel extends AutoResizePreserveRatioImagePanel {

    private int waterLevel;

    public WaterLevelPanel() {
        super(IconGraphicalMetadata.ICON_WATER_LEVEL.getImage());
    }

    public void setWaterLevel(int waterLevel) {
        if (this.waterLevel != waterLevel) {
            this.waterLevel = waterLevel;
            repaint();
        }
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        g.drawImage(IconGraphicalMetadata.ICON_WATER_LEVEL_CURSOR.getImage(),
                20, getYCursor(waterLevel), (int) (getWidth() * 0.25), (int) (getHeight() * 0.07), null);
    }

    private int getYCursor(final int waterLevel) {
        return (int) (getHeight() * 0.785 - (waterLevel * getHeight() * 0.082));
    }
}
