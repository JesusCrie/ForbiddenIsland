package iut2.forbiddenisland;

import iut2.forbiddenisland.controller.Controller;
import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.adventurer.*;
import iut2.forbiddenisland.view.DemoBoard;
import iut2.forbiddenisland.view.gui.game.GameFrame;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class ForbiddenIsland {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
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

            final GameFrame frame = new GameFrame(controller);
            frame.setVisible(true);
        });
    }
}
