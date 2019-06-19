package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.view.IconGraphicalMetadata;
import iut2.forbiddenisland.view.gui.utils.AutoResizePreserveRatioImagePanel;

import java.awt.*;

public class WaterLevelPanel extends AutoResizePreserveRatioImagePanel {

    public WaterLevelPanel() {
        super(IconGraphicalMetadata.WATER_LEVEL.getImage());
        // TODO: 19/06/2019 Mettre le controlleur en paramettre du contructeur, pour ensuite mettre le waterlvl en parametre du getYCursor dans le g.drawImage
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(IconGraphicalMetadata.CURSOR.getImage(), -10, getYCursor(5), (int) (getWidth() * 0.30), (int) (getHeight() * 0.07), null);
    }

    public int getYCursor(final int waterLevel) {
        return (int) (getHeight() * 0.785 - (waterLevel * getHeight() * 0.082));
    }
}
