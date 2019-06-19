package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.model.adventurer.Adventurer;
import iut2.forbiddenisland.view.TreasureCardGraphicalMetadata;
import iut2.forbiddenisland.view.gui.utils.AdventurerCard;
import iut2.forbiddenisland.view.gui.utils.AutoResizeImageButton;
import iut2.forbiddenisland.view.gui.utils.CardButton;

import javax.swing.*;
import java.awt.*;

public class PlayerCard extends JPanel {
    private final AdventurerCard type;
    private final JLabel name;
    private final JButton[] cards;

    private final JComponent topPanel;
    private final JComponent botPanel;

    private final Font biggerFont;

    PlayerCard(Adventurer adventurer){
        topPanel = new JPanel(new GridLayout(1,2));
        botPanel = new JPanel(new GridLayout(1,5));

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

        biggerFont = new Font(name.getFont().getName(), name.getFont().getStyle(), name.getFont().getSize()*2);
        name.setFont(biggerFont);
        Dimension dim = new Dimension(50,75);
        //type.setPreferredSize(dim);
        //type.setMinimumSize(dim);
        //type.setSize(dim);








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
