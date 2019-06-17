package iut2.forbiddenisland.view.gui;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Image;

public class AutoResizeImageButton extends JButton {

    private final Image image;

    public AutoResizeImageButton(final Image image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
}
