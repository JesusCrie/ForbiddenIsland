package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.Pair;
import iut2.forbiddenisland.controller.observer.Observable;
import iut2.forbiddenisland.model.adventurer.Adventurer;
import iut2.forbiddenisland.model.card.TreasureCard;
import iut2.forbiddenisland.view.TreasureCardGraphicalMetadata;
import iut2.forbiddenisland.view.gui.components.AdventurerCardButton;
import iut2.forbiddenisland.view.gui.components.AutoResizePreserveRatioImagePanel;
import iut2.forbiddenisland.view.gui.components.CardButton;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

public class AdventurerCardPanel extends JPanel {

    private static final double CARD_IMAGE_RATIO = 501.0 / 699.0;

    private Adventurer adventurer;
    private boolean isCurrentPlayer = false;
    private AdventurerCardButton cardButton;
    private final JPanel bottomPanel;

    private Observable<Pair<Adventurer, TreasureCard>> cardClickNotifier = new Observable<>();

    public AdventurerCardPanel(final Adventurer adventurer, final int width, final int height) {
        this.adventurer = adventurer;

        // Setup container
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(1, 0, 1, 0));

        // Create children
        final double widthPerCard = width / 5.0;
        final int heightBottomPanel = (int) (widthPerCard / CARD_IMAGE_RATIO);

        final JComponent topPanel = createTopPanel(adventurer, width, height - heightBottomPanel);
        topPanel.setPreferredSize(new Dimension(width, heightBottomPanel));

        bottomPanel = new JPanel(new GridLayout(1, 5));
        bottomPanel.setPreferredSize(new Dimension(width, 140));

        add(topPanel);
        add(bottomPanel);
    }

    public AdventurerCardButton getCardButton() {
        return cardButton;
    }

    public void setCardClickNotifier(final Observable<Pair<Adventurer, TreasureCard>> cardClickNotifier) {
        this.cardClickNotifier = cardClickNotifier;
        updateCards();
    }

    public void setIsCurrentPlayer(final boolean isCurrentPlayer) {
        if (this.isCurrentPlayer != isCurrentPlayer) {
            this.isCurrentPlayer = isCurrentPlayer;
            setBorder(isCurrentPlayer ? BorderFactory.createLineBorder(Color.RED, 5) : null);
        }
    }

    public void setSelectable(final boolean selectable) {
        // If current player, keep disable
        cardButton.setEnabled(!isCurrentPlayer && selectable);
        cardButton.setHighlighted(!isCurrentPlayer && selectable);
    }

    public void updateCards() {
        bottomPanel.removeAll();

        // Regenerate buttons from the changed set of cards
        for (JComponent btn : generateCardButtons(adventurer.getCards())) {
            bottomPanel.add(btn);
        }
    }

    private JComponent createTopPanel(final Adventurer adv, final int width, final int height) {
        final Box panel = Box.createHorizontalBox();
        panel.setMaximumSize(new Dimension(width, height));


        // To force the image to take the whole space available
        final JPanel cardPanel = new JPanel(new GridLayout(1, 1));
        cardPanel.setMaximumSize(new Dimension((int) (height / CARD_IMAGE_RATIO), height));
        cardButton = new AdventurerCardButton(adv);
        cardPanel.add(cardButton);

        final JLabel name = new JLabel(adv.getName());

        name.setFont(name.getFont().deriveFont(20f));

        panel.add(cardPanel);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(name);

        return panel;
    }

    private List<JComponent> generateCardButtons(final List<TreasureCard> cards) {
        final List<JComponent> buttons = new ArrayList<>(5);

        for (TreasureCard card : cards) {
            final CardButton btn = new CardButton(card);
            btn.addActionListener(e -> cardClickNotifier.set(Pair.of(adventurer, card)));

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
