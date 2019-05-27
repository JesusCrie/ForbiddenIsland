package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.controller.request.Request;
import iut2.forbiddenisland.controller.request.RequestType;
import iut2.forbiddenisland.controller.request.Response;
import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.cell.Cell;

import java.util.List;

public class PilotPower implements Power {

    private final Board board;
    private boolean canUsePower;

    public PilotPower(Board board) {
        this.board = board;
    }

    @Override
    public void alterRequest(Request req) {
        if (req.getType() == RequestType.GAME_MOVE_AMOUNT) {
            canUsePower = true;
        }
    }

    @Override
    public void alterResponse(final Response res) {
        if (res.getOriginRequest().getType() == RequestType.CELLS_REACHABLE && canUsePower) {
            List<Cell> cells = /*board.getCell*/ null;
            res.setData(cells);
            canUsePower = false;
        }
    }
}
