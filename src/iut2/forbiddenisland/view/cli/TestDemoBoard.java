package iut2.forbiddenisland.view.cli;

import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.BoardGenerator;
import iut2.forbiddenisland.model.adventurer.Explorer;
import iut2.forbiddenisland.model.adventurer.Messenger;
import iut2.forbiddenisland.model.adventurer.Navigator;
import iut2.forbiddenisland.model.adventurer.Pilot;

import java.util.Arrays;

public class TestDemoBoard {

    public static void main(String[] args) {
        /*final Board board = DemoBoard.createAndGet(Arrays.asList(
                new Pilot(), new Navigator(), new Explorer(), new Messenger()
        ));*/

        final Board board = BoardGenerator.createRandomBoard(Arrays.asList(
                new Pilot(), new Navigator(), new Explorer(), new Messenger()
        ), 1);

        System.out.println(ConsoleRenderer.drawBoard(board));
    }
}
