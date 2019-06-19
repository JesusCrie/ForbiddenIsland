package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.controller.Controller;
import iut2.forbiddenisland.controller.observer.Observable;
import iut2.forbiddenisland.model.adventurer.Adventurer;
import iut2.forbiddenisland.model.card.TreasureCard;
import iut2.forbiddenisland.view.TreasureCardGraphicalMetadata;
import iut2.forbiddenisland.view.gui.utils.AdventurerCard;
import iut2.forbiddenisland.view.gui.utils.AutoResizeImageButton;
import iut2.forbiddenisland.view.gui.utils.CardButton;
import iut2.forbiddenisland.view.gui.utils.ConstraintFactory;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PlayerCardsPanel extends JPanel {
    private final JPanel player1Panel;
    private final JPanel player2Panel;
    private final JPanel player3Panel;
    private final JPanel player4Panel;

    private final JButton[][] cards;

    private final AdventurerCard type1;
    private final AdventurerCard type2;
    private final AdventurerCard type3;
    private final AdventurerCard type4;

    private final JLabel name1;
    private final JLabel name2;
    private final JLabel name3;
    private final JLabel name4;

    private final List<Adventurer> adventurers;


    public PlayerCardsPanel(final Controller controller) {
        //<editor-fold desc="*** INTANCIATION ***">
        adventurers = controller.getAdventurers().get();



        player1Panel = new JPanel(new GridBagLayout());
        player2Panel = new JPanel(new GridBagLayout());
        player3Panel = new JPanel(new GridBagLayout());
        player4Panel = new JPanel(new GridBagLayout());

        JPanel topPanel1 = new JPanel(new GridBagLayout());
        JPanel topPanel2 = new JPanel(new GridBagLayout());
        JPanel topPanel3 = new JPanel(new GridBagLayout());
        JPanel topPanel4 = new JPanel(new GridBagLayout());

        JPanel botPanel1 = new JPanel(new GridBagLayout());
        JPanel botPanel2 = new JPanel(new GridBagLayout());
        JPanel botPanel3 = new JPanel(new GridBagLayout());
        JPanel botPanel4 = new JPanel(new GridBagLayout());

        cards = new JButton[4][5];

        name1 = new JLabel();
        name2 = new JLabel();
        name3 = new JLabel();
        name4 = new JLabel();

        type1 = new AdventurerCard(adventurers.get(0));
        type2 = new AdventurerCard(adventurers.get(1));
        type3 = new AdventurerCard(adventurers.get(2));
        type4 = new AdventurerCard(adventurers.get(3));
        //</editor-fold>

        //<editor-fold desc="*** PLAYER 1 ***">
        if (adventurers.size() >= 1){
            topPanel1.add(type1, ConstraintFactory.fillBoth(0,0,1,1));
            name1.setText(adventurers.get(0).getName());
            topPanel1.add(name1, ConstraintFactory.fillHorizontal(2,0,4,1));
            player1Panel.add(topPanel1, ConstraintFactory.fillBoth(0,0,5,1));


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

        this.setLayout(new GridBagLayout());
        this.add(player1Panel, ConstraintFactory.fillBoth(0,0,3,2));
        this.add(player2Panel, ConstraintFactory.fillBoth(0,2,3,2));
        this.add(player3Panel, ConstraintFactory.fillBoth(0,4,3,2));
        this.add(player4Panel, ConstraintFactory.fillBoth(0,6,3,2));
    }
}
