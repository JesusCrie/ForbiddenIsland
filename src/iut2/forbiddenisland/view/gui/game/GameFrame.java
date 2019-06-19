package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.controller.Controller;
import iut2.forbiddenisland.model.card.TreasureCard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class GameFrame extends JFrame {

    private final Box container;

    public GameFrame(final Controller controller) {
        setSize(1900, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Yes, every size is hardcoded and its not responsive

        container = Box.createHorizontalBox();
        container.setBorder(new EmptyBorder(5, 5, 5, 5));

        // Create board panel
        final BoardPanel boardPanel = new BoardPanel(controller);
        boardPanel.setPreferredSize(new Dimension(1000, 1000));
        container.add(boardPanel);

        // Create player card panel
        final PlayerCardsPanel playerCardsPanel = new PlayerCardsPanel(controller);
        playerCardsPanel.setPreferredSize(new Dimension(500, 1000));
        container.add(playerCardsPanel);

        // Create water & action panels
        {
            final Box waterActionContainer = Box.createVerticalBox();
            waterActionContainer.setPreferredSize(new Dimension(300, 1000));

            final WaterLevelPanel waterLevelPanel = new WaterLevelPanel(controller);
            waterActionContainer.setPreferredSize(new Dimension(300, 600));
            waterActionContainer.add(waterLevelPanel);

            final ActionPanel actionPanel = new ActionPanel(controller, 300, 300);
            actionPanel.setMaximumSize(new Dimension(300, 300));
            actionPanel.setPreferredSize(new Dimension(300, 300));
            actionPanel.setup();
            waterActionContainer.add(actionPanel);

            container.add(waterActionContainer);
        }

        add(container);
    }

    public static TreasureCard askCardToDiscard(final List<TreasureCard> cards) {
        final CompletableFuture<TreasureCard> future = new CompletableFuture<>();

        final DiscardCardFrame frame = new DiscardCardFrame(cards, future);
        frame.setVisible(true);

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException("Future failed", e);
        }
    }
}
