package iut2.forbiddenisland.model.adventurer.power;

import iut2.forbiddenisland.Utils;
import iut2.forbiddenisland.controller.request.Request;
import iut2.forbiddenisland.controller.request.RequestType;
import iut2.forbiddenisland.controller.request.Response;
import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.Location;
import iut2.forbiddenisland.model.cell.Cell;
import iut2.forbiddenisland.model.cell.CellState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Allow the current player to reach any cell as long as there is a path
 * of flooded cells between him and the desired cell.
 */
public class DiverPower implements Power {

    @Override
    public void alterRequest(final Request req, final Board board) {
        if (req.getType() == RequestType.PLAYER_MOVE) {
            final Cell target = req.getData(Request.DATA_CELL);

            // If cell is reachable, bypass board checks
            try {
                if (checkDiverMoveCell(req.getCurrentPlayer().getPosition(), target, board, new HashSet<>())) {
                    req.bypassChecks();
                }
            } catch (IllegalStateException ignore) {
                // Triggered if the targeted cell if flooded
                // It's just a way to stop the method early
                // The result of the method is considered to be 'false'
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void alterResponse(final Response res, final Board board) {
        if (res.getOriginRequest().getType() == RequestType.CELLS_REACHABLE) {
            final Response<List<Cell>> castedRes = (Response<List<Cell>>) res;

            // If 4 cells are already reachable, then assume there is no water nearby
            if (castedRes.getData().size() >= 4)
                return;

            final Set<Cell> reachableCells = new HashSet<>();
            exploreReachableCells(res.getOriginRequest().getCurrentPlayer().getPosition(), board, new HashSet<>(), reachableCells);
            castedRes.setData(new ArrayList<>(reachableCells));
        }
    }

    /**
     * Recursively collect every not flooded cell near a flooded one reachable.
     *
     * @param target         - The cell to explore in this recursion.
     * @param board          - The board.
     * @param exploredCells  - A set of already explored cells.
     * @param reachableCells - The set of cells to complete.
     */
    private void exploreReachableCells(final Cell target, final Board board, final Set<Cell> exploredCells, final Set<Cell> reachableCells) {
        for (Location cell : Utils.getCrossCells(target.getLocation())) {
            final Cell current = board.getCell(cell);

            // Ignore if out of the board
            if (current == null)
                continue;

            // If is flooded, continue the exploration on this cell
            if (current.getState() == CellState.FLOODED) {
                // If already explored, don't continue further
                if (exploredCells.contains(current))
                    continue;

                exploredCells.add(current);
                exploreReachableCells(current, board, exploredCells, reachableCells);

                // If not flooded, then it is reachable
            } else {
                // Its a set, no risks that it will appear two times
                reachableCells.add(current);
            }
        }
    }

    /**
     * A variation of {@link #exploreReachableCells(Cell, Board, Set, Set)} that will only check if a targeted cell
     * is reachable in the sense of the other method (a non-flooded cell reachable by passing through flooded cells).
     *
     * @param source        - The cell to explore in this recursion.
     * @param target        - The targeted cell to check.
     * @param board         - The board.
     * @param exploredCells - A set of already explored cells.
     * @return True if the targeted cell is found and valid, otherwise false.
     * @throws IllegalStateException If the targeted cell is found but is flooded.
     */
    private boolean checkDiverMoveCell(final Cell source, final Cell target, final Board board, final Set<Cell> exploredCells) {
        for (Location loc : Utils.getCrossCells(source.getLocation())) {
            final Cell current = board.getCell(loc);

            // If out of bounds, ignore right away
            if (current == null)
                return false;

            // If it's the targeted cell
            if (current == target) {
                // If its not reachable anyway, abort the method immediately
                if (current.getState() == CellState.FLOODED)
                    throw new IllegalStateException("The targeted cell is flooded !");

                // Otherwise we're  good
                return true;
            }

            // If it's flooded, we can continue to search
            if (current.getState() == CellState.FLOODED) {
                exploredCells.add(current);

                // If recursive call is true, stop there and propagate the result up
                if (checkDiverMoveCell(current, target, board, exploredCells)) {
                    return true;
                }
            }
        }

        // If we reach that, the target cell is not reachable
        return false;
    }
}
