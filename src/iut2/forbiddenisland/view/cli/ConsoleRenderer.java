package iut2.forbiddenisland.view.cli;

import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.Location;
import iut2.forbiddenisland.model.cell.CellState;

public class ConsoleRenderer {

    public static String buildBoard(final Board board) {
        StringBuilder line;
        final StringBuilder output = new StringBuilder();

        // First line

        // Padd 2 cells (4 spaces)
        line = newLine()
                .append("    ")
                .append(stateToAnsi(board.getCell(Location.from(2, 0)).getState()))
                .append(stateToAnsi(board.getCell(Location.from(3, 0)).getState()))
                .append("    \n");
        
        // Append 2 times because each cell is 2 character wide
        output.append(line);

        // Second line
        line = newLine()
                .append("  ")
                .append(stateToAnsi(board.getCell(Location.from(1, 1)).getState()))
                .append(stateToAnsi(board.getCell(Location.from(2, 1)).getState()))
                .append(stateToAnsi(board.getCell(Location.from(3, 1)).getState()))
                .append(stateToAnsi(board.getCell(Location.from(4, 1)).getState()))
                .append("  \n");
        output.append(line);

        // Third line
        line = newLine()
                .append(stateToAnsi(board.getCell(Location.from(0, 2)).getState()))
                .append(stateToAnsi(board.getCell(Location.from(1, 2)).getState()))
                .append(stateToAnsi(board.getCell(Location.from(2, 2)).getState()))
                .append(stateToAnsi(board.getCell(Location.from(3, 2)).getState()))
                .append(stateToAnsi(board.getCell(Location.from(4, 2)).getState()))
                .append(stateToAnsi(board.getCell(Location.from(5, 2)).getState()))
                .append("\n");
        output.append(line);

        // Fourth line
        line = newLine()
                .append(stateToAnsi(board.getCell(Location.from(0, 3)).getState()))
                .append(stateToAnsi(board.getCell(Location.from(1, 3)).getState()))
                .append(stateToAnsi(board.getCell(Location.from(2, 3)).getState()))
                .append(stateToAnsi(board.getCell(Location.from(3, 3)).getState()))
                .append(stateToAnsi(board.getCell(Location.from(4, 3)).getState()))
                .append(stateToAnsi(board.getCell(Location.from(5, 3)).getState()))
                .append("\n");
        output.append(line);

        // Fifth line
        line = newLine()
                .append("  ").append(stateToAnsi(board.getCell(Location.from(1, 4)).getState()))
                .append(stateToAnsi(board.getCell(Location.from(2, 4)).getState()))
                .append(stateToAnsi(board.getCell(Location.from(3, 4)).getState()))
                .append(stateToAnsi(board.getCell(Location.from(4, 4)).getState()))
                .append("  \n");
        output.append(line);

        // Sixth line
        line = newLine()
                .append("    ").append(stateToAnsi(board.getCell(Location.from(2, 5)).getState()))
                .append(stateToAnsi(board.getCell(Location.from(3, 5)).getState()))
                .append("    \n");
        output.append(line);

        return output.toString();
    }

    private static StringBuilder newLine() {
        return new StringBuilder("  ");
    }

    private static String stateToAnsi(final CellState state) {
        final String color;
        switch (state) {
            case DRY:
                color = Ansi.BG_GREEN;
                break;
            case WET:
                color = Ansi.BG_BLUE;
                break;
            default:
            case FLOODED:
                color = "";
                break;
        }

        return Ansi.colorize(color, "  ");
    }
}
