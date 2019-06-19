package iut2.forbiddenisland;

import iut2.forbiddenisland.controller.Controller;
import iut2.forbiddenisland.controller.DummyController;
import iut2.forbiddenisland.view.gui.game.GameFrame;
import iut2.forbiddenisland.view.gui.game.StartingFrame;

import javax.swing.*;
import java.util.List;

public class ForbiddenIsland {

    public static void main(String[] args) {
        startWelcomeFrame();
    }

    public static void startWelcomeFrame() {
        SwingUtilities.invokeLater(() -> {
            final StartingFrame startingFrame = new StartingFrame();
            startingFrame.setVisible(true);
        });
    }

    public static void startGameFrame(final List<String> adventurers) {
        // TODO replace with real controller
        final Controller controller = new DummyController();

        SwingUtilities.invokeLater(() -> {
            final GameFrame frame = new GameFrame(controller);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
