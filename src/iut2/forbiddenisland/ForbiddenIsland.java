package iut2.forbiddenisland;

import iut2.forbiddenisland.controller.Controller;
import iut2.forbiddenisland.controller.DummyController;
import iut2.forbiddenisland.view.gui.game.GameFrame;
import iut2.forbiddenisland.view.gui.game.StartingFrame;

import javax.swing.*;

public class ForbiddenIsland {

    public static void main(String[] args) {
        final Controller controller = new DummyController();

        // Delegate to swing thread
        SwingUtilities.invokeLater(() -> {
            final StartingFrame startFrame = new StartingFrame();
            startFrame.setVisible(true);

            final GameFrame frame = new GameFrame(controller);
            frame.setVisible(true);
        });
    }
}
