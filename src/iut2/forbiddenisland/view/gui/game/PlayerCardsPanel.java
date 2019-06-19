package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.controller.Controller;
import iut2.forbiddenisland.model.adventurer.Adventurer;
import iut2.forbiddenisland.model.card.TreasureCard;
import iut2.forbiddenisland.view.TreasureCardGraphicalMetadata;
import iut2.forbiddenisland.view.gui.components.AdventurerCardButton;
import iut2.forbiddenisland.view.gui.components.AutoResizeImageButton;
import iut2.forbiddenisland.view.gui.components.CardButton;
import iut2.forbiddenisland.view.gui.utils.ConstraintFactory;

import javax.swing.*;
import java.util.List;

public class PlayerCardsPanel extends JPanel {
    private final JComponent mainPanel;
    private final JComponent player1Panel;
    private final JComponent player2Panel;
    private final JComponent player3Panel;
    private final JComponent player4Panel;

    private final JButton[][] cards;

    private AdventurerCardButton type1;
    private AdventurerCardButton type2;
    private AdventurerCardButton type3;
    private AdventurerCardButton type4;

    private final JLabel name1;
    private final JLabel name2;
    private final JLabel name3;
    private final JLabel name4;

    private final List<Adventurer> adventurers;


    public PlayerCardsPanel(final Controller controller) {
        //<editor-fold desc="*** INTANCIATION ***">
        adventurers = controller.getAdventurers().get();

        mainPanel = Box.createVerticalBox();

        player1Panel = Box.createVerticalBox();
        player2Panel = Box.createVerticalBox();
        player3Panel = Box.createVerticalBox();
        player4Panel = Box.createVerticalBox();

        JComponent topPanel1 = Box.createHorizontalBox();
        JComponent topPanel2 = Box.createHorizontalBox();
        JComponent topPanel3 = Box.createHorizontalBox();
        JComponent topPanel4 = Box.createHorizontalBox();

        JComponent botPanel1 = Box.createHorizontalBox();
        JComponent botPanel2 = Box.createHorizontalBox();
        JComponent botPanel3 = Box.createHorizontalBox();
        JComponent botPanel4 = Box.createHorizontalBox();

        cards = new JButton[4][5];

        name1 = new JLabel();
        name2 = new JLabel();
        name3 = new JLabel();
        name4 = new JLabel();

        if(adventurers.size() >= 1){
            type1 = new AdventurerCardButton(adventurers.get(0));
        }
        if (adventurers.size() >= 2) {
            type2 = new AdventurerCardButton(adventurers.get(1));
        }
        if (adventurers.size() >= 3) {
            type3 = new AdventurerCardButton(adventurers.get(2));
        }

        if (adventurers.size() >= 4) {
            type4 = new AdventurerCardButton(adventurers.get(3));
        }
        //</editor-fold>

        //<editor-fold desc="*** PLAYER 1 ***">
        if (adventurers.size() >= 1){
            topPanel1.add(type1);
            name1.setText(adventurers.get(0).getName());
            topPanel1.add(name1);
            player1Panel.add(topPanel1);


            java.util.List<TreasureCard> playerCards = adventurers.get(0).getCards();
            int i = 0;
            for (int j = 0; j < playerCards.size(); j++) {
                cards[0][j] = new CardButton(playerCards.get(j));
                i++;
            }
            for (int j = i; j < 5; j++) {
                cards[0][j] = new AutoResizeImageButton(TreasureCardGraphicalMetadata.EMPTY_CARD.getImage());
            }
            for (int j = 0; j < 5; j++) {
                botPanel1.add(cards[0][j], ConstraintFactory.fillVertical(j,0,1,1));
            }
            player1Panel.add(botPanel1, ConstraintFactory.fillBoth(0,1,5,1));
        }
        //</editor-fold>

        //<editor-fold desc="*** PLAYER 2 ***">
        if (adventurers.size() >= 2) {
            topPanel2.add(type2, ConstraintFactory.fillBoth(0,0,1,1));
            name2.setText(adventurers.get(1).getName());
            topPanel2.add(name2, ConstraintFactory.fillHorizontal(1,0,4,1));
            player2Panel.add(topPanel2, ConstraintFactory.fillBoth(0,0,5,1));


            java.util.List<TreasureCard> playerCards = adventurers.get(1).getCards();
            int i = 0;
            for (int j = 0; j < playerCards.size(); j++) {
                cards[1][j] = new CardButton(playerCards.get(j));
                i++;
            }
            for (int j = i; j < 5; j++) {
                cards[1][j] = new AutoResizeImageButton(TreasureCardGraphicalMetadata.EMPTY_CARD.getImage());
            }
            for (int j = 0; j < 5; j++) {
                botPanel2.add(cards[1][j], ConstraintFactory.fillVertical(j,0,1,1));
            }
            player2Panel.add(botPanel2, ConstraintFactory.fillBoth(0,1,5,1));
        }
        //</editor-fold>

        //<editor-fold desc="*** PLAYER 3 ***">
        if (adventurers.size() >= 3) {
            topPanel3.add(type3, ConstraintFactory.fillBoth(0,0,1,1));
            name3.setText(adventurers.get(2).getName());
            topPanel3.add(name3, ConstraintFactory.fillHorizontal(1,0,4,1));
            player3Panel.add(topPanel3, ConstraintFactory.fillBoth(0,0,5,1));


            java.util.List<TreasureCard> playerCards = adventurers.get(2).getCards();
            int i = 0;
            for (int j = 0; j < playerCards.size(); j++) {
                cards[2][j] = new CardButton(playerCards.get(j));
                i++;
            }
            for (int j = i; j < 5; j++) {
                cards[2][j] = new AutoResizeImageButton(TreasureCardGraphicalMetadata.EMPTY_CARD.getImage());
            }
            for (int j = 0; j < 5; j++) {
                botPanel3.add(cards[2][j], ConstraintFactory.fillVertical(j,0,1,1));
            }
            player3Panel.add(botPanel3, ConstraintFactory.fillBoth(0,1,5,1));
        }
        //</editor-fold>

        //<editor-fold desc="*** PLAYER 4 ***">
        if (adventurers.size() >= 4) {
            topPanel4.add(type4, ConstraintFactory.fillBoth(0,0,1,1));
            name4.setText(adventurers.get(3).getName());
            topPanel4.add(name4, ConstraintFactory.fillHorizontal(1,0,4,1));
            player4Panel.add(topPanel4, ConstraintFactory.fillBoth(0,0,5,1));


            List<TreasureCard> playerCards = adventurers.get(3).getCards();
            int i = 0;
            for (int j = 0; j < playerCards.size(); j++) {
                cards[3][j] = new CardButton(playerCards.get(j));
                i++;
            }
            for (int j = i; j < 5; j++) {
                cards[3][j] = new AutoResizeImageButton(TreasureCardGraphicalMetadata.EMPTY_CARD.getImage());
            }
            for (int j = 0; j < 5; j++) {
                botPanel4.add(cards[3][j], ConstraintFactory.fillVertical(j,0,1,1));
            }
            player4Panel.add(botPanel4, ConstraintFactory.fillBoth(0,1,5,1));
        }
        //</editor-fold>

        mainPanel.add(player1Panel);
        mainPanel.add(player2Panel);
        mainPanel.add(player3Panel);
        mainPanel.add(player4Panel);

        this.add(mainPanel);
    }
}
