package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.model.adventurer.Adventurer;
import iut2.forbiddenisland.model.card.TreasureCard;
import iut2.forbiddenisland.view.TreasureCardGraphicalMetadata;
import iut2.forbiddenisland.view.gui.components.AdventurerCardButton;
import iut2.forbiddenisland.view.gui.components.AutoResizePreserveRatioImagePanel;
import iut2.forbiddenisland.view.gui.components.CardButton;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

public class PlayerCardPanel extends JPanel {

    private static final double CARD_IMAGE_RATIO = 501.0 / 699.0;

    private JComponent bottomPanel;

    public PlayerCardPanel(final Adventurer adventurer, final int width, final int height) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(1, 0, 1, 0));

        final double widthPerCard = width / 5.0;
        final int heightBottomPanel = (int) (widthPerCard / CARD_IMAGE_RATIO);

        final JComponent topPanel = createTopPanel(adventurer, width, height - heightBottomPanel);
        topPanel.setPreferredSize(new Dimension(width, heightBottomPanel));

        bottomPanel = createBottomPanel(adventurer);
        bottomPanel.setPreferredSize(new Dimension(width, 140));

        add(topPanel);
        add(bottomPanel);
    }

    public void updateCards(final List<TreasureCard> cards) {
        bottomPanel.removeAll();

        for (JComponent btn : generateCardButtons(cards)) {
            bottomPanel.add(btn);
        }

        repaint();
    }

    private JComponent createTopPanel(final Adventurer adv, final int width, final int height) {
        final Box panel = Box.createHorizontalBox();
        panel.setPreferredSize(new Dimension(width, height));
        panel.setMaximumSize(new Dimension(width, height));


        // To force the image to take the whole space available
        final JPanel cardPanel = new JPanel(new GridLayout(1, 1));
        cardPanel.setPreferredSize(new Dimension((int) (height * CARD_IMAGE_RATIO), height));
        final AdventurerCardButton cardImage = new AdventurerCardButton(adv);
        cardPanel.add(cardImage);

        final JLabel name = new JLabel(adv.getName());

        name.setFont(name.getFont().deriveFont(20f));

        panel.add(cardPanel);
        panel.add(name);
        panel.add(Box.createHorizontalGlue());

        return panel;
    }

    private JComponent createBottomPanel(final Adventurer adv) {
        final JPanel panel = new JPanel(new GridLayout(1, 5));

        for (JComponent button : generateCardButtons(adv.getCards())) {
            panel.add(button);
        }

        return panel;
    }

    private List<JComponent> generateCardButtons(final List<TreasureCard> cards) {
        final List<JComponent> buttons = new ArrayList<>(5);

        for (TreasureCard card : cards) {
            final CardButton btn = new CardButton(card);
            btn.addActionListener(e -> {
                // TODO notify controller
            });

            buttons.add(btn);
        }

        for (int i = cards.size(); i < 5; ++i) {
            buttons.add(createEmptyCardPlaceholder());
        }

        return buttons;
    }

    private JComponent createEmptyCardPlaceholder() {
        return new AutoResizePreserveRatioImagePanel(TreasureCardGraphicalMetadata.EMPTY_CARD.getImage());
    }
}
