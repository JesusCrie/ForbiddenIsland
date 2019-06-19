package iut2.forbiddenisland.controller;

import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.adventurer.*;
import iut2.forbiddenisland.view.DemoBoard;

import java.util.Arrays;
import java.util.List;

public class DummyController extends Controller {

    private static List<Adventurer> DUMMY_ADVENTURERS = Arrays.asList(
            new Pilot("Gégé"),
            new Diver("Pedro"),
            new Engineer("Mc Gyver"),
            new Messenger("Batman")
    );

    //private static Board DUMMY_BOARD = BoardGenerator.createRandomBoard(DUMMY_ADVENTURERS, 0);
    private static Board DUMMY_BOARD = DemoBoard.createAndGet(DUMMY_ADVENTURERS);

    public DummyController() {
        super(DUMMY_BOARD, DUMMY_ADVENTURERS);
    }
}
