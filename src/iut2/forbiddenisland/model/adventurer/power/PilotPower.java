package iut2.forbiddenisland.model.adventurer.power;

import iut2.forbiddenisland.Utils;
import iut2.forbiddenisland.controller.request.Request;
import iut2.forbiddenisland.controller.request.RequestType;
import iut2.forbiddenisland.controller.request.Response;
import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.cell.Cell;
import iut2.forbiddenisland.model.cell.CellState;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This power allow the current player to move anywhere on the board once per round.
 */
public class PilotPower implements Power {

    private boolean canUsePower = true;

    @Override
    public void alterRequest(final Request req, final Board board) {
        if (req.getType() == RequestType.GAME_NEW_ROUND) {
            canUsePower = true;
        } else if (req.getType() == RequestType.PLAYER_MOVE && canUsePower
                && req.<Cell>getData(Request.DATA_CELL).getState() != CellState.FLOODED) {

            final boolean needPower = Arrays.stream(Utils.getCrossCells(req.getCurrentPlayer().getPosition().getLocation()))
                    .noneMatch(loc -> loc.equals(req.<Cell>getData(Request.DATA_CELL).getLocation()));

            if (needPower) {
                canUsePower = false;
                req.bypassChecks();
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void alterResponse(final Response res, final Board board) {
        if (res.getOriginRequest().getType() == RequestType.CELLS_REACHABLE && canUsePower) {
            final Response<List<Cell>> castedRes = (Response<List<Cell>>) res;
            castedRes.setData(
                    board.getCells().values().stream()
                            .filter(cell -> cell.getState() != CellState.FLOODED)
                            .filter(cell -> res.getOriginRequest().getCurrentPlayer().getPosition() != cell)
                            .collect(Collectors.toList())
            );
        }
    }
}
