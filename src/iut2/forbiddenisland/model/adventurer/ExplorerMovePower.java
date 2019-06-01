package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.Utils;
import iut2.forbiddenisland.controller.request.Request;
import iut2.forbiddenisland.controller.request.RequestType;
import iut2.forbiddenisland.controller.request.Response;
import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.Location;
import iut2.forbiddenisland.model.cell.Cell;
import iut2.forbiddenisland.model.cell.CellState;

import java.util.List;


public class ExplorerMovePower implements Power {

    @Override
    public void alterRequest(final Request req, final Board board) {
        if (req.getType() == RequestType.PLAYER_MOVE) {
            final Location position = req.getCurrentPlayer().getPosition().getLocation();

            for (Location loc : Utils.getCornerCells(position)) {

                if (req.<Cell>getData(Request.DATA_CELL).getLocation().equals(loc)) {
                    req.bypassChecks();
                    return;
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void alterResponse(final Response res, final Board board) {
        if (res.getOriginRequest().getType() == RequestType.CELLS_REACHABLE) {
            final Response<List<Cell>> castedRes = (Response<List<Cell>>) res;

            final Location position = res.getOriginRequest().getCurrentPlayer().getPosition().getLocation();
            for (Location loc : Utils.getCornerCells(position)) {

                final Cell cell = board.getCell(loc);
                if (cell != null && cell.getState() != CellState.FLOODED) {
                    castedRes.getData().add(cell);
                }
            }
        }
    }
}
