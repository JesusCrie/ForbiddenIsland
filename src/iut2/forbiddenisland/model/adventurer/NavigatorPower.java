package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.controller.request.Request;
import iut2.forbiddenisland.controller.request.RequestType;
import iut2.forbiddenisland.controller.request.Response;
import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.cell.Cell;

import java.util.List;


public class NavigatorPower implements Power {

    private Board board;
    private Adventurer adventurer;

    private NavigatorPower(Board board) {
        this.board = board;
    }

    @Override
    public void alterRequest(final Request req, final Board board) {
        if (req.getType() == RequestType.PLAYER_MOVE) {
            this.adventurer = req.getData(Request.DATA_PLAYER);
        }
    }

    @Override
    public void alterResponse(final Response res, final Board board) {
        if (res.getOriginRequest().getType() == RequestType.PLAYER_MOVE) {
            List<Cell> cells;
            List<Cell> tmpCells;
            /*
            cells = board.getReachableCells(adventurer.getPosition());
            tmpCells = cells;
            for (Cell cell : tmpCells){
                cells.add(board.getReachableCells(cell);
            }
               res.setData(cells);

            */
        }
    }
}
