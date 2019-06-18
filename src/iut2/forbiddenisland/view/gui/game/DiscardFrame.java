package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.model.card.TreasureCard;
import iut2.forbiddenisland.view.gui.utils.CardButton;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DiscardFrame extends JFrame {

    public DiscardFrame(final List<TreasureCard> treasureCards, final CompletableFuture<TreasureCard> future) {
        setSize(100 * treasureCards.size() + 100, 300);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setAlwaysOnTop(true);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        final JLabel label = new JLabel("Quelle carte voulez vous retiré ?", JLabel.CENTER);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = treasureCards.size();
        c.insets = new Insets(0, 0, 20, 0);
        add(label, c);

        for (int i = 0; i < treasureCards.size() - 1; i++) {
            c = new GridBagConstraints();
            final TreasureCard card = treasureCards.get(i);
            final JButton button = new CardButton(card);
            c.gridx = i;
            c.gridy = 1;
            c.insets = new Insets(0, 5, 0, 5);
            c.ipady = 100;
            c.ipadx = 50;
            add(button, c);
            button.addActionListener(e -> {
                dispose();
                future.complete(card);
            });
        }

        c = new GridBagConstraints();
        final TreasureCard card = treasureCards.get(treasureCards.size() - 1);
        final JButton button = new CardButton(card);
        c.gridx = treasureCards.size() - 1;
        c.gridy = 1;
        c.insets = new Insets(0, 25, 0, 0);
        c.ipady = 100;
        c.ipadx = 50;
        add(button, c);
        button.addActionListener(e -> {
            dispose();
            future.complete(card);
        });
    }
}
