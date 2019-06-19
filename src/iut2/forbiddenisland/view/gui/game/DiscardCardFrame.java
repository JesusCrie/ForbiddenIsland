package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.model.card.TreasureCard;
import iut2.forbiddenisland.view.gui.utils.CardButton;
import iut2.forbiddenisland.view.gui.utils.ConstraintFactory;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DiscardCardFrame extends JFrame {

    public DiscardCardFrame(final List<TreasureCard> treasureCards, final CompletableFuture<TreasureCard> future) {

        setSize(100 * treasureCards.size() + 185 , 250);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setAlwaysOnTop(true);

        setLayout(new GridBagLayout());
        GridBagConstraints c;

        final JLabel label = new JLabel("Choisissez une carte à jeter", JLabel.CENTER);
        c = ConstraintFactory.fillHorizontal(0, 0, treasureCards.size(), 1);
        c.insets = new Insets(0, 0, 15, 0);
        add(label, c);

        for (int i = 0; i < treasureCards.size() - 1; i++) {
            final TreasureCard card = treasureCards.get(i);
            final CardButton button = createCardButton(card, new Insets(0, 5, 0, 5), future, i);
            add(button, c);
        }

        final TreasureCard card = treasureCards.get(treasureCards.size() - 1);
        final JButton button = createCardButton(card, new Insets(0, 35, 0, 0), future, treasureCards.size() - 1);
        add(button, c);
    }

    private CardButton createCardButton(final TreasureCard card, final Insets insets,
                                        final CompletableFuture<TreasureCard> future, final int index) {

        final CardButton btn = new CardButton(card);

        final GridBagConstraints cts = ConstraintFactory.create(index, 1);
        cts.ipadx = 75;
        cts.ipady = 150;
        cts.insets = insets;

        btn.addActionListener(e -> {
            dispose();
            future.complete(card);
        });

        return btn;
    }
}
