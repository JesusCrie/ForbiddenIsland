package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.Utils;
import iut2.forbiddenisland.controller.request.Request;
import iut2.forbiddenisland.controller.request.RequestType;
import iut2.forbiddenisland.controller.request.Response;
import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.Location;
import iut2.forbiddenisland.model.cell.Cell;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This power allow the current player to move another player by 2 cells.
 */
public class NavigatorPower implements Power {

    @Override
    public void alterRequest(final Request req, final Board board) {
        if (req.getType() == RequestType.PLAYER_MOVE &&
                !req.getCurrentPlayer().equals(req.getData(Request.DATA_PLAYER)) &&
                isReachable(
                        req.<Adventurer>getData(Request.DATA_PLAYER).getPosition().getLocation(),
                        req.<Cell>getData(Request.DATA_CELL).getLocation()
                )
        ) {
            req.bypassChecks();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void alterResponse(final Response res, final Board board) {
        if (res.getOriginRequest().getType() == RequestType.CELLS_REACHABLE) {
            final Response<List<Cell>> castedRes = (Response<List<Cell>>) res;

            final List<Cell> newCells = castedRes.getData().stream()
                    .map(Cell::getLocation)
                    .flatMap(loc -> Arrays.stream(Utils.getCornerCells(loc)))
                    .distinct()
                    .map(board::getCell)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            castedRes.setData(newCells);
        }
    }

    private boolean isReachable(final Location origin, final Location dest) {
        return Math.abs(origin.getX() - dest.getX()) + Math.abs(origin.getY() - dest.getY()) <= 2;
    }
}
