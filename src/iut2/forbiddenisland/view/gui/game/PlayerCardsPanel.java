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
    private final JPanel mainPanel;
    private JPanel player1;
    private JPanel player2;
    private JPanel player3;
    private JPanel player4;

    private final List<Adventurer> adventurers;




    public PlayerCardsPanel(final Controller controller) {
        //<editor-fold desc="*** INTANCIATION ***">
        adventurers = controller.getAdventurers().get();
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setPreferredSize(new Dimension(500, 800));

        int k = 0;
        for (Adventurer adv : adventurers){
            switch (k) {
                case 0:
                    player1 = new PlayerCard(adv);
                    mainPanel.add(player1);
                    k++;
                    break;
                case 1:
                    player2 = new PlayerCard(adv);
                    mainPanel.add(player2);
                    k++;
                    break;
                case 2:
                    player3 = new PlayerCard(adv);
                    mainPanel.add(player3);
                    k++;
                    break;
                case 3:
                    player4 = new PlayerCard(adv);
                    mainPanel.add(player4);
                    k++;
                    break;
            }
        }
        //</editor-fold>


        this.add(mainPanel);
    }
}
