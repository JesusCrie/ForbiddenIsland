package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.Utils;
import iut2.forbiddenisland.controller.request.Request;
import iut2.forbiddenisland.controller.request.RequestType;
import iut2.forbiddenisland.controller.request.Response;
import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.Location;
import iut2.forbiddenisland.model.cell.Cell;
import iut2.forbiddenisland.model.cell.CellState;

import java.util.ArrayList;
import java.util.List;

public class DiverPower implements Power {

    @Override
    public void alterRequest(final Request req, final Board board) {
        if (req.getType() == RequestType.PLAYER_MOVE) {
            final Cell target = null;
            final List<Cell> reachableCells = new ArrayList<>();
            final List<Cell> floodedCells = new ArrayList<>();
            for (Location loc : Utils.getCrossCells(req.getCurrentPlayer().getPosition().getLocation())){
                final Cell cell = req.getData(Request.DATA_CELL);
                if (cell != null){
                    if(cell == target){
                        return;
                    }
                    reachableCells.add(cell);
                }
            }
            if (checkDiverMoveCell(board, reachableCells, floodedCells, req.getCurrentPlayer().getPosition().getLocation(), target)){
                req.bypassChecks();
                return;
            }
        }
    }

    @Override
    public void alterResponse(final Response res, final Board board) {
        if (res.getOriginRequest().getType() == RequestType.CELLS_REACHABLE) {
            final List<Cell> cells = (List<Cell>) res.getData();
            final List<Cell> floodedCells = new ArrayList<>();
            final Location position = res.getOriginRequest().getCurrentPlayer().getPosition().getLocation();
            if (cells.size() < 4) {
                getDriverReachableCells(board, cells, floodedCells, position);
            }
        }
    }

    private void getDriverReachableCells(final Board board, final List<Cell> reachableCells, final List<Cell> floodedCells, final Location pos) {
        for (Location loc : Utils.getCrossCells(pos)) {
            final Cell cell = board.getCell(loc);
            if (cell != null && cell.getState() != CellState.FLOODED
                    && reachableCells.contains(cell)) {
                reachableCells.add(cell);

            } else if (cell != null && !floodedCells.contains(cell)) {
                floodedCells.add(cell);
                getDriverReachableCells(board, reachableCells, floodedCells, cell.getLocation());
            }
        }
    }

    private boolean checkDiverMoveCell(final Board board, final List<Cell> reachableCells, final List<Cell> floodedCells, final Location pos, final Cell target) {
        for (Location loc : Utils.getCrossCells(pos)) {
            final Cell cell = board.getCell(loc);
            if (cell != null && cell.getState() != CellState.FLOODED
                    && reachableCells.contains(cell)) {
                if (cell.equals(target)) {
                    return true;
                }
                reachableCells.add(cell);

            } else if (cell != null && !floodedCells.contains(cell)) {
                floodedCells.add(cell);
                if (checkDiverMoveCell(board, reachableCells, floodedCells, cell.getLocation(), target)){
                    return true;
                }
            }
        }
        return false;
    }
}
