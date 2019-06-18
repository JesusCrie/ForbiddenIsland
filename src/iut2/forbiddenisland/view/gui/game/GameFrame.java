package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.controller.Controller;
import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.adventurer.*;
import iut2.forbiddenisland.view.DemoBoard;
import iut2.forbiddenisland.view.gui.utils.ConstraintFactory;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.util.Arrays;
import java.util.List;

public class GameFrame extends JFrame {

    final List<Adventurer> adventurers = Arrays.asList(
            new Pilot("Jean Mich"),
            new Navigator("Jacquie"),
            new Explorer("Jeane"),
            new Messenger("Pierrot"),
            new Diver("Philippe !"),
            new Engineer("Gertrude")
    );
    final Board board = DemoBoard.createAndGet(adventurers);
    final Controller controller = new Controller(board, adventurers);

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
        add(new WaterLevelPanel(this),
                ConstraintFactory.fillBoth(3, 0, 1, 3));
        add(new PlayerCardsPanel(controller),
                ConstraintFactory.fillBoth(0, 3, 3, 1));
        add(new ActionPanel(controller),
                ConstraintFactory.fillBoth(3, 3, 1, 1));
    }
}
