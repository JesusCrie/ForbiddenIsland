package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.model.card.TreasureCard;
import iut2.forbiddenisland.view.gui.utils.CardButton;
import iut2.forbiddenisland.view.gui.utils.ConstraintFactory;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DiscardCardFrame extends JDialog {

    public DiscardCardFrame(final List<TreasureCard> treasureCards, final CompletableFuture<TreasureCard> future) {

        setSize(100 * treasureCards.size() + 185 , 250);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setAlwaysOnTop(true);
        setModal(true);

        setLayout(new GridBagLayout());
        GridBagConstraints c;

        final JLabel label = new JLabel("Choisissez une carte à jeter", JLabel.CENTER);
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 15, 0);
        c.gridx = 0;
        c. gridy = 0;
        c.gridwidth = treasureCards.size();
        add(label, c);

        c.insets = new Insets(0,5,0,5);
        c.ipadx = 75;
        c.ipady = 150;
        c.gridwidth = 1;
        c.gridy = 1;
        for (int i = 0; i < treasureCards.size() - 1; i++) {
            final TreasureCard card = treasureCards.get(i);
            final CardButton button = createCardButton(card, new Insets(0, 5, 0, 5), future, i);
            c.gridx = i;
            add(button, c);
        }

        final TreasureCard card = treasureCards.get(treasureCards.size() - 1);
        final JButton button = createCardButton(card, new Insets(0, 35, 0, 0), future, treasureCards.size() - 1);
        c.gridx = treasureCards.size() - 1;
        c.insets = new Insets(0,35,0,5);
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
