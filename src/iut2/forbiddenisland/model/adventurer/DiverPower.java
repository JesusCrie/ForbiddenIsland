package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.Utils;
import iut2.forbiddenisland.controller.request.Request;
import iut2.forbiddenisland.controller.request.RequestType;
import iut2.forbiddenisland.controller.request.Response;
import iut2.forbiddenisland.model.Location;
import iut2.forbiddenisland.model.cell.Cell;
import iut2.forbiddenisland.model.cell.CellState;

import java.util.ArrayList;
import java.util.List;

public class DiverPower implements Power {

    @Override
    public void alterRequest(final Request req) {
        if (req.getType() == RequestType.PLAYER_MOVE) {
            // TODO
        }
    }

    @Override
    public void alterResponse(final Response res) {
        if (res.getOriginRequest().getType() == RequestType.CELLS_REACHABLE) {
            final List<Cell> cells = (List<Cell>) res.getData();
            final List<Cell> floodedCells = new ArrayList<>();
            final Location position = res.getOriginRequest().getCurrentPlayer().getPosition().getLocation();
            if (cells.size() < 4) {
                getDriverReachableCells(res, cells, floodedCells, position);
            }
        }
    }

    private void getDriverReachableCells(final Response res, final List<Cell> reachableCells, final List<Cell> floodedCells, final Location pos) {
        for (Location loc : Utils.getCrossCells(pos)) {
            final Cell cell = res.getBoard().getCell(loc);
            if (cell != null && cell.getState() != CellState.FLOODED
                    && reachableCells.contains(cell)) {
                reachableCells.add(cell);

            } else if (cell != null && !floodedCells.contains(cell)) {
                floodedCells.add(cell);
                getDriverReachableCells(res, reachableCells, floodedCells, cell.getLocation());
            }
        }
    }
}
