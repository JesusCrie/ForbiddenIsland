package iut2.forbiddenisland.model.adventurer.power;

import iut2.forbiddenisland.Utils;
import iut2.forbiddenisland.controller.request.Request;
import iut2.forbiddenisland.controller.request.RequestType;
import iut2.forbiddenisland.controller.request.Response;
import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.Location;
import iut2.forbiddenisland.model.cell.Cell;
import iut2.forbiddenisland.model.cell.CellState;

import java.util.List;

/**
 * Allow the current player to dry cells in the diagonals.
 */
public class ExplorerDryPower implements Power {

    @Override
    public void alterRequest(final Request req, final Board board) {
        if (req.getType() == RequestType.PLAYER_DRY) {
            final Location position = req.getCurrentPlayer().getPosition().getLocation();

            for (Location loc : Utils.getCornerCells(position)) {
                final Cell cell = req.getData(Request.DATA_CELL);

                if (cell.getLocation().equals(loc) && cell.getState() == CellState.WET) {
                    req.bypassChecks();
                }
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void alterResponse(final Response res, final Board board) {
        if (res.getOriginRequest().getType() == RequestType.CELLS_DRAINABLE) {
            final Response<List<Cell>> castedRes = (Response<List<Cell>>) res;

            final Location position = res.getOriginRequest().getCurrentPlayer().getPosition().getLocation();

            for (Location loc : Utils.getCornerCells(position)) {
                final Cell cell = board.getCellIfWet(loc);
                if (cell != null) {
                    castedRes.getData().add(cell);
                }
            }
        }
    }
}
