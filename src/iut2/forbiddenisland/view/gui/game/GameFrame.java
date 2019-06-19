package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.model.card.TreasureCard;
import iut2.forbiddenisland.controller.Controller;
import iut2.forbiddenisland.view.gui.utils.ConstraintFactory;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class GameFrame extends JFrame {

    public GameFrame(final Controller controller) {
        setSize(1000, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new GridBagLayout());

        /*
         *  BBB W
         *  BBB W
         *  BBB W
         *
         *  PPP A
         */

        add(new BoardPanel(null),
                ConstraintFactory.fillBoth(0, 0, 3, 3));
        add(new WaterLevelPanel(this),
                ConstraintFactory.fillBoth(3, 0, 1, 3));
        add(new PlayerCardsPanel(this),
                ConstraintFactory.fillBoth(0, 3, 3, 1));
        add(new ActionPanel(this),
                ConstraintFactory.fillBoth(3, 3, 1, 1));
    }

    public TreasureCard askCardToDiscard(final List<TreasureCard> list) {
        final CompletableFuture<TreasureCard> future = new CompletableFuture<>();

        final DiscardCardFrame frame = new DiscardCardFrame(list, future);
        frame.setVisible(true);

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException("Future failed", e);
        }
    }
}
