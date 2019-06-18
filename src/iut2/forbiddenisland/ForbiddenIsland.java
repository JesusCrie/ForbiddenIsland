package iut2.forbiddenisland;

import iut2.forbiddenisland.view.gui.game.GameFrame;

import javax.swing.*;

public class ForbiddenIsland {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            final GameFrame frame = new GameFrame();
            frame.setVisible(true);
        });
    }
}
