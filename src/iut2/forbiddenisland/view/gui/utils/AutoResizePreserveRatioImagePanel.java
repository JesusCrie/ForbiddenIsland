package iut2.forbiddenisland.view.gui.utils;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Image;

public class AutoResizePreserveRatioImagePanel extends JPanel {

    private final Image image;
    private final boolean isHorizontal;
    private final double aspectRatio;

    public AutoResizePreserveRatioImagePanel(final Image image) {
        this.image = image;
        isHorizontal = image.getWidth(null) > image.getHeight(null);
        aspectRatio = (double) image.getWidth(null) / image.getHeight(null);
    }

    @Override
    protected void paintComponent(final Graphics g) {
        int x = 0, y = 0;
        double width, height;


        if (isHorizontal) {
            y = (getHeight() - image.getHeight(null)) / 2;
            width = getWidth();
            height = width * aspectRatio;

        } else {
            x = (getWidth() - image.getWidth(null)) / 2;
            height = getHeight();
            width = height / aspectRatio;
        }

        g.drawImage(image, x, y, (int) width, (int) height, null);
    }
}
