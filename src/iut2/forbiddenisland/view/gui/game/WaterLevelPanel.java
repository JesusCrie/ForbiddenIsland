package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.view.gui.utils.AutoResizePreserveRatioImagePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class WaterLevelPanel extends JLayeredPane {


    public WaterLevelPanel(final Component parent) {
        AutoResizePreserveRatioImagePanel fond;
        try {
             fond = new AutoResizePreserveRatioImagePanel(
                    ImageIO.read(getClass().getResourceAsStream("/Niveau.png"))
            );
        } catch (IOException e) {
            e.printStackTrace();
            fond = null;
        }
        fond.setSize(300,200);
        add(fond, 1);
    }
}
