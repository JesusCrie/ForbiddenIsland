package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.Utils;
import iut2.forbiddenisland.controller.request.RequestType;
import iut2.forbiddenisland.controller.request.Response;
import iut2.forbiddenisland.model.Location;
import iut2.forbiddenisland.model.cell.Cell;

import java.util.ArrayList;
import java.util.List;

public class DiverPower implements Power {

    @Override
    public void alterResponse(final Response res) {
        if (res.getOriginRequest().getType() == RequestType.CELLS_REACHABLE) {
            List<Cell> cells = (List<Cell>) res.getData();
            List<Cell> floodedCells = new ArrayList<Cell>();
            Location position = res.getOriginRequest().getCurrentPlayer().getPosition().getLocation();
            if (cells.size() < 4) {
                getDriverReachableCells(res, cells, floodedCells, position);
            }
        }
    }

    public void getDriverReachableCells(final Response res, final List<Cell> reachableCells, final List<Cell> floodedCells, final Location pos) {
        for (Location loc : Utils.getCrossCells(pos)) {
            Cell cell = res.getBoard().getCell(loc);
            if (cell != null) {
                if (!reachableCells.contains(cell)) {
                    reachableCells.add(cell);
                }
            } else {
                cell = res.getBoard().getCellIfFlooded(loc);
                if (cell != null) {
                    if (!floodedCells.contains(cell)) {
                        floodedCells.add(cell);
                        getDriverReachableCells(res, reachableCells, floodedCells, cell.getLocation());
                    }
                }
            }
        }
    }
}
