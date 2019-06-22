package iut2.forbiddenisland;

import iut2.forbiddenisland.controller.Controller;
import iut2.forbiddenisland.demo.DemoBoardCreator;
import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.BoardGenerator;
import iut2.forbiddenisland.model.adventurer.Adventurer;
import iut2.forbiddenisland.model.card.TreasureCard;
import iut2.forbiddenisland.model.card.TreasureDeck;
import iut2.forbiddenisland.view.gui.game.GameFrame;
import iut2.forbiddenisland.view.gui.game.WelcomeFrame;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class ForbiddenIsland {

    public static void main(String[] args) {
        // Set platform look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.err.println("Failed to load system look and feel !");
            e.printStackTrace();
        }

        startWelcomeFrame();
    }

    public static void startWelcomeFrame() {
        SwingUtilities.invokeLater(() -> {
            final WelcomeFrame welcomeFrame = new WelcomeFrame();
            //welcomeFrame.pack();
            welcomeFrame.setVisible(true);
        });
    }

    public static void startGameFrame(final List<Adventurer> adventurers, final int waterLevel) {
        final Board board = BoardGenerator.createRandomBoard(adventurers, waterLevel);
        final Controller controller = new Controller(board, adventurers);

        invokeGameFrame(controller);
    }

    public static void startDemo1(final List<String> names) {
        final Board board = DemoBoardCreator.createScenario1(names);
        final Controller controller = new Controller(board, board.getAdventurers());

        invokeGameFrame(controller);
    }

    public static void startDemo2(final List<String> names) {
        final Board board = DemoBoardCreator.createScenario2(names);
        final Controller controller = new Controller(board, board.getAdventurers());

        invokeGameFrame(controller);
    }

    private static void invokeGameFrame(final Controller controller) {
        SwingUtilities.invokeLater(() -> {
            final GameFrame frame = new GameFrame(controller);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
