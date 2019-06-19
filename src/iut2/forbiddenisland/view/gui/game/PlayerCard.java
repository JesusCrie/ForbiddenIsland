package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.model.adventurer.Adventurer;
import iut2.forbiddenisland.view.TreasureCardGraphicalMetadata;
import iut2.forbiddenisland.view.gui.utils.AdventurerCard;
import iut2.forbiddenisland.view.gui.utils.AutoResizeImageButton;
import iut2.forbiddenisland.view.gui.utils.CardButton;

import javax.swing.*;

public class PlayerCard extends JPanel {
    private final AdventurerCard type;
    private final JLabel name;
    private final JButton[] cards;

    private final JComponent topPanel;
    private final JComponent botPanel;

    PlayerCard(Adventurer adventurer){
        topPanel = Box.createHorizontalBox();
        botPanel = Box.createHorizontalBox();

        name= new JLabel();
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
