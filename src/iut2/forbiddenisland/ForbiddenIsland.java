package iut2.forbiddenisland;

import iut2.forbiddenisland.view.gui.game.GameFrame;
import iut2.forbiddenisland.view.gui.game.StartingFrame;

import javax.swing.*;

public class ForbiddenIsland {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            final StartingFrame Sframe = new StartingFrame();

            //final GameFrame Gframe = new GameFrame();
            //Gframe.setVisible(true);
        });
    }
}
