package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.model.card.TreasureCard;
import iut2.forbiddenisland.view.gui.components.CardButton;

import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DiscardCardPopupFrame extends JDialog {

    public DiscardCardPopupFrame(final List<TreasureCard> treasureCards, final CompletableFuture<TreasureCard> future) {

        setSize(100 * treasureCards.size() + 185, 250);
        setLocationRelativeTo(null);
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
        c.gridy = 0;
        c.gridwidth = treasureCards.size();
        add(label, c);

        c.insets = new Insets(0, 5, 0, 5);
        c.ipadx = 75;
        c.ipady = 150;
        c.gridwidth = 1;
        c.gridy = 1;

        for (int i = 0; i < treasureCards.size(); i++) {
            final TreasureCard card = treasureCards.get(i);
            final CardButton button = new CardButton(card);
            button.addActionListener(e -> {
                dispose();
                future.complete(card);
            });

            c.gridx = i;
            add(button, c);
        }
    }
}
