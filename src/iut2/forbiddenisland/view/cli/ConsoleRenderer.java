package iut2.forbiddenisland.view.cli;

import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.Location;
import iut2.forbiddenisland.model.Treasure;
import iut2.forbiddenisland.model.adventurer.Adventurer;
import iut2.forbiddenisland.model.cell.Cell;
import iut2.forbiddenisland.model.cell.CellState;
import iut2.forbiddenisland.model.cell.TreasureCell;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConsoleRenderer {

    public static String drawBoard(final Map<Location, Cell> cells, final List<Treasure> treasures,
                                   final List<Adventurer> players) {
        StringBuilder line;
        final StringBuilder output = new StringBuilder();

        // Header line
        line = newLineBoard()
                .append(" 0 1 2 3 4 5\n");
        output.append(line);

        // First line

        // Padd 2 cells (4 spaces)
        line = newLineBoard(0)
                .append("    ")
                .append(stateToAnsi(cells.get(Location.from(2, 0)).getState()))
                .append(stateToAnsi(cells.get(Location.from(3, 0)).getState()))
                .append("    \n");

        // Append 2 times because each cell is 2 character wide
        output.append(line);

        // Second line
        line = newLineBoard(1)
                .append("  ")
                .append(stateToAnsi(cells.get(Location.from(1, 1)).getState()))
                .append(stateToAnsi(cells.get(Location.from(2, 1)).getState()))
                .append(stateToAnsi(cells.get(Location.from(3, 1)).getState()))
                .append(stateToAnsi(cells.get(Location.from(4, 1)).getState()))
                .append("  \n");
        output.append(line);

        // Third line
        line = newLineBoard(2)
                .append(stateToAnsi(cells.get(Location.from(0, 2)).getState()))
                .append(stateToAnsi(cells.get(Location.from(1, 2)).getState()))
                .append(stateToAnsi(cells.get(Location.from(2, 2)).getState()))
                .append(stateToAnsi(cells.get(Location.from(3, 2)).getState()))
                .append(stateToAnsi(cells.get(Location.from(4, 2)).getState()))
                .append(stateToAnsi(cells.get(Location.from(5, 2)).getState()))
                .append("\n");
        output.append(line);

        // Fourth line
        line = newLineBoard(3)
                .append(stateToAnsi(cells.get(Location.from(0, 3)).getState()))
                .append(stateToAnsi(cells.get(Location.from(1, 3)).getState()))
                .append(stateToAnsi(cells.get(Location.from(2, 3)).getState()))
                .append(stateToAnsi(cells.get(Location.from(3, 3)).getState()))
                .append(stateToAnsi(cells.get(Location.from(4, 3)).getState()))
                .append(stateToAnsi(cells.get(Location.from(5, 3)).getState()))
                .append("\n");
        output.append(line);

        // Fifth line
        line = newLineBoard(4)
                .append("  ").append(stateToAnsi(cells.get(Location.from(1, 4)).getState()))
                .append(stateToAnsi(cells.get(Location.from(2, 4)).getState()))
                .append(stateToAnsi(cells.get(Location.from(3, 4)).getState()))
                .append(stateToAnsi(cells.get(Location.from(4, 4)).getState()))
                .append("  \n");
        output.append(line);

        // Sixth line
        line = newLineBoard(5)
                .append("    ").append(stateToAnsi(cells.get(Location.from(2, 5)).getState()))
                .append(stateToAnsi(cells.get(Location.from(3, 5)).getState()))
                .append("    \n");
        output.append(line);

        // Adventurers

        output.append("\n");

        for (final Adventurer adventurer : players) {
            line = newLine().append(adventurer.getName())
                    .append(": X = ").append(adventurer.getPosition().getLocation().getX())
                    .append(", Y = ").append(adventurer.getPosition().getLocation().getY())
                    .append("\n");

            output.append(line);
        }

        // Treasures

        output.append("\n");

        for (int i = 0; i < treasures.size(); i++) {
            final Treasure treasure = treasures.get(i);

            line = newLine().append(treasure.getName()).append("\n");

            if (treasure.isClaimable()) {
                line.append(cells.values().stream()
                        .filter(cell -> cell instanceof TreasureCell)
                        .map(cell -> (TreasureCell) cell)
                        .filter(cell -> cell.getTreasure() == treasure)
                        .map(Cell::getLocation)
                        .map(location -> "\tPosition: X = " + location.getX() + ", Y = " + location.getY())
                        .collect(Collectors.joining("\n")));
            } else {
                line.append("\tDéjà pris");
            }

            output.append(line).append("\n");
        }

        return output.toString();
    }

    private static StringBuilder newLineBoard() {
        return newLineBoard(null);
    }

    private static StringBuilder newLineBoard(final Integer lineHeader) {
        if (lineHeader == null) {
            return new StringBuilder("  ");
        } else {
            return new StringBuilder(String.valueOf(lineHeader)).append(" ");
        }
    }

    private static StringBuilder newLine() {
        return new StringBuilder();
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
