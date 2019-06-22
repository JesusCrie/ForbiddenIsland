package iut2.forbiddenisland.view.cli;

import iut2.forbiddenisland.controller.Controller;
import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.adventurer.*;
import iut2.forbiddenisland.demo.DemoBoardCreator;

import java.util.Arrays;
import java.util.List;

public class TestDemoBoard {

    public static void main(String[] args) {
        final List<Adventurer> adventurers = Arrays.asList(
                new Pilot("Jean Mich"),
                new Navigator("Jacquie"),
                new Explorer("Jeane"),
                new Messenger("Pierrot"),
                new Diver("Philippe !"),
                new Engineer("Gertrude")
        );

        final Board board = DemoBoardCreator.createAndGet(adventurers, 0);

        final Controller controller = new Controller(board, adventurers);
        final CliView view = new CliView(controller);

        view.start();
    }
}
