package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.controller.Controller;
import iut2.forbiddenisland.model.adventurer.Adventurer;

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


        int k = 0;
        for (Adventurer adv : adventurers){
            switch (k) {
                case 0:
                    player1 = new PlayerCardPanel(adv, 500, 200);
                    mainPanel.add(player1);
                    k++;
                    break;
                case 1:
                    player2 = new PlayerCardPanel(adv, 500, 200);
                    mainPanel.add(player2);
                    k++;
                    break;
                case 2:
                    player3 = new PlayerCardPanel(adv, 500, 200);
                    mainPanel.add(player3);
                    k++;
                    break;
                case 3:
                    player4 = new PlayerCardPanel(adv, 500, 200);
                    mainPanel.add(player4);
                    k++;
                    break;
            }
        }
        //</editor-fold>


        this.add(mainPanel);
    }
}
