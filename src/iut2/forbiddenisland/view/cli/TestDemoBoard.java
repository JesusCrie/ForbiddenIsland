package iut2.forbiddenisland.view.cli;

import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.BoardGenerator;
import iut2.forbiddenisland.model.adventurer.*;
import iut2.forbiddenisland.view.DemoBoard;

import java.util.Arrays;

public class TestDemoBoard {

    public static void main(String[] args) {
        final Board board = DemoBoard.createAndGet(Arrays.asList(
                new Pilot(), new Navigator(), new Explorer(), new Messenger()
        ));

        /*final Board board = BoardGenerator.createRandomBoard(Arrays.asList(
                new Pilot(), new Navigator(), new Explorer(), new Messenger()
        ), 1);*/

        System.out.println(ConsoleRenderer.buildBoard(board));
    }
}
