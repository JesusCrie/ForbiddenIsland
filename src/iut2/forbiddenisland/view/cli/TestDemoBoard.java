package iut2.forbiddenisland.view.cli;

import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.view.DemoBoard;

public class TestDemoBoard {

    public static void main(String[] args) {
        final Board board = DemoBoard.createAndGet();

        System.out.println(ConsoleRenderer.buildBoard(board));
    }
}
