package iut2.forbiddenisland.model;

import iut2.forbiddenisland.controller.Request;
import iut2.forbiddenisland.controller.RequestType;
import iut2.forbiddenisland.controller.Response;

import java.util.List;

public class PilotPower implements Power {

    private final Board board;
    private boolean canUsePower;

    public PilotPower(Board board) {
        this.board = board;
    }

    @Override
    public void alterRequest(Request req) {
        if (req.getType() == RequestType.PLAYER_MOVE_AMOUNT) {
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
