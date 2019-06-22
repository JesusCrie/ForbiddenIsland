package iut2.forbiddenisland.view.gui.components;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Image;

public class AutoResizeImageButton extends JButton {

    protected Image background;

    public AutoResizeImageButton(final Image background) {
        this.background = background;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(final Graphics g) {
        // Only draw the background image
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
    }
}
