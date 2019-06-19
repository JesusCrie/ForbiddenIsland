package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.view.gui.utils.ConstraintFactory;

import javax.swing.*;
import java.awt.GridBagLayout;

public class GameFrame extends JFrame {

    public GameFrame() {
        setSize(1000, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new GridBagLayout());

        /*
         *  BBB W
         *  BBB W
         *  BBB W
         *
         *  PPP A
         */

        add(new BoardPanel(this),
                ConstraintFactory.fillBoth(0, 0, 3, 3));
        add(new WaterLevelPanel(),
                ConstraintFactory.fillBoth(3, 0, 1, 3));
        add(new PlayerCardsPanel(this),
                ConstraintFactory.fillBoth(0, 3, 3, 1));
        add(new ActionPanel(this),
                ConstraintFactory.fillBoth(3, 3, 1, 1));
    }
}
