package iut2.forbiddenisland;

import iut2.forbiddenisland.controller.Controller;
import iut2.forbiddenisland.controller.DummyController;
import iut2.forbiddenisland.model.BoardGenerator;
import iut2.forbiddenisland.model.card.TreasureCard;
import iut2.forbiddenisland.model.card.TreasureDeck;
import iut2.forbiddenisland.view.gui.game.GameFrame;
import iut2.forbiddenisland.view.gui.game.WelcomeFrame;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class ForbiddenIsland {

    public static void main(String[] args) {
        //startWelcomeFrame();
        startGameFrame(null);
    }

    public static void startWelcomeFrame() {
        SwingUtilities.invokeLater(() -> {
            final WelcomeFrame welcomeFrame = new WelcomeFrame();
            welcomeFrame.pack();
            welcomeFrame.setVisible(true);
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

    public static void testDialog() {
        final TreasureDeck deck = new TreasureDeck(BoardGenerator.createTreasures());

        final List<TreasureCard> cards = Arrays.asList(
                deck.drawCard(),
                deck.drawCard(),
                deck.drawCard(),
                deck.drawCard(),
                deck.drawCard(),
                deck.drawCard(),
                deck.drawCard()
        );

        TreasureCard card = GameFrame.askCardToDiscard(cards);
        System.out.println(card.getName());
    }
}
