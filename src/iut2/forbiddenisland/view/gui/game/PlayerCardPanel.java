package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.model.adventurer.Adventurer;
import iut2.forbiddenisland.view.TreasureCardGraphicalMetadata;
import iut2.forbiddenisland.view.gui.utils.AdventurerCard;
import iut2.forbiddenisland.view.gui.utils.AutoResizeImageButton;
import iut2.forbiddenisland.view.gui.utils.CardButton;

import javax.swing.*;
import java.awt.*;

public class PlayerCardPanel extends JPanel {
    private final AdventurerCard type;
    private final JLabel name;
    private final JButton[] cards;

    private final JComponent topPanel;
    private final JComponent botPanel;

    private final Font biggerFont;

    public PlayerCardPanel(final Adventurer adventurer, final int width, final int height) {
        topPanel = Box.createHorizontalBox();
        topPanel.setPreferredSize(new Dimension(width, height - 140));
        botPanel = new JPanel(new GridLayout(1, 5, 0, 5));
        botPanel.setPreferredSize(new Dimension(width, 140));

        name = new JLabel();
        name.setText(adventurer.getName());
        type = new AdventurerCard(adventurer);

        cards = new JButton[5];

        int k = 0;
        for (int i = 0; i < adventurer.getCards().size(); i++) {
            cards[i] = new CardButton(adventurer.getCards().get(i));
            k++;
        }

        for (int j = k; j < 5; j++) {
            cards[j] = new AutoResizeImageButton(TreasureCardGraphicalMetadata.EMPTY_CARD.getImage());
        }

        biggerFont = name.getFont().deriveFont(name.getFont().getSize() * 2f);
        name.setFont(biggerFont);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        topPanel.add(type);
        topPanel.add(name);

        for (int i = 0; i < 5; i++) {
            botPanel.add(cards[i]);
        }

        this.add(topPanel);
        this.add(botPanel);


    }
}
