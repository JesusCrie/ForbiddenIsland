package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.controller.Controller;
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

public class PlayerCardPanel extends JPanel {

    private static final double CARD_IMAGE_RATIO = 501.0 / 699.0;
    private static final Color TRANSPARENT = new Color(0, true);

    private Adventurer adventurer;
    private boolean highlighted = false;
    private final JComponent bottomPanel;

    private final Observable<TreasureCard> cardClickNotifier = new Observable<>();
    private final Observable<Adventurer> adventurerClickNotifier = new Observable<>();


    public PlayerCardPanel(final Controller controller, final Adventurer adventurer, final int width, final int height) {
        this.adventurer = adventurer;

        // Setup container
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(1, 0, 1, 0));

        // Create children
        final double widthPerCard = width / 5.0;
        final int heightBottomPanel = (int) (widthPerCard / CARD_IMAGE_RATIO);

        final JComponent topPanel = createTopPanel(adventurer, width, height - heightBottomPanel);
        topPanel.setPreferredSize(new Dimension(width, heightBottomPanel));

        bottomPanel = createBottomPanel(adventurer);
        bottomPanel.setPreferredSize(new Dimension(width, 140));

        add(topPanel);
        add(bottomPanel);

        controller.observeClickCard(cardClickNotifier);
        controller.observeClickPlayer(adventurerClickNotifier);
    }

    public void setHighlighted(final boolean highlighted) {
        if (this.highlighted != highlighted) {
            this.highlighted = highlighted;
            setBorder(highlighted ? BorderFactory.createLineBorder(Color.RED, 5) : null);
            repaint();
        }
    }

    public void updateCards() {
        bottomPanel.removeAll();

        for (JComponent btn : generateCardButtons(adventurer.getCards())) {
            bottomPanel.add(btn);
        }

        repaint();
    }

    private JComponent createTopPanel(final Adventurer adv, final int width, final int height) {
        final Box panel = Box.createHorizontalBox();
        panel.setMaximumSize(new Dimension(width, height));


        // To force the image to take the whole space available
        final JPanel cardPanel = new JPanel(new GridLayout(1, 1));
        cardPanel.setMaximumSize(new Dimension((int) (height / CARD_IMAGE_RATIO), height));
        final AdventurerCardButton cardImage = new AdventurerCardButton(adv);
        cardPanel.add(cardImage);
        cardImage.addActionListener(e -> adventurerClickNotifier.set(adv));

        final JLabel name = new JLabel(adv.getName());

        name.setFont(name.getFont().deriveFont(20f));

        panel.add(cardPanel);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(name);

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
            btn.addActionListener(e -> cardClickNotifier.set(card));

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
