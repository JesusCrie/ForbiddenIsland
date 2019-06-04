package iut2.forbiddenisland.view.cli;

import iut2.forbiddenisland.controller.Controller;
import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.BoardGenerator;
import iut2.forbiddenisland.model.adventurer.*;
import iut2.forbiddenisland.view.DemoBoard;

import java.util.Arrays;
import java.util.List;

public class TestDemoBoard {

    public static void main(String[] args) {
        final List<Adventurer> adventurers = Arrays.asList(
                new Pilot(), new Navigator(), new Explorer(), new Messenger()
        );

        final Board board = DemoBoard.createAndGet(adventurers);

        final Controller controller = new Controller(board, adventurers);
        final CliView view = new CliView(controller);

        view.start();
    }
}
