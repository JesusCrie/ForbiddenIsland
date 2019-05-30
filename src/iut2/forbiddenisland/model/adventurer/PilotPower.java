package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.controller.request.Request;
import iut2.forbiddenisland.controller.request.RequestType;
import iut2.forbiddenisland.controller.request.Response;
import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.cell.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * This power allow the current player to move anywhere on the board once per round.
 */
public class PilotPower implements Power {

    private boolean canUsePower;

    @Override
    public void alterRequest(final Request req, final Board board) {
        if (req.getType() == RequestType.GAME_NEW_ROUND) {
            canUsePower = true;
        } else if (req.getType() == RequestType.PLAYER_MOVE && canUsePower) {
            canUsePower = false;
            req.bypassChecks();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void alterResponse(final Response res, final Board board) {
        if (res.getOriginRequest().getType() == RequestType.CELLS_REACHABLE && canUsePower) {
            final Response<List<Cell>> castedRes = (Response<List<Cell>>) res;
            castedRes.setData(new ArrayList<>(board.getCells().values()));
        }
    }
}
