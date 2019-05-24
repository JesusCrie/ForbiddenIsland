package iut2.forbiddenisland.model;

import iut2.forbiddenisland.controller.Request;
import iut2.forbiddenisland.controller.RequestType;
import iut2.forbiddenisland.controller.Response;

import java.util.HashSet;
import java.util.List;



public class NavigatorPower implements Power {

    private Board board;
    private Adventurer adventurer;

    private NavigatorPower(Board board){ this.board = board;}

    @Override
    public void alterRequest(Request req) {
        if(req.getType() == RequestType.PLAYER_MOVE){
           this.adventurer = req.getData(Request.DATA_PLAYER);
        }
    }

    @Override
    public void alterResponse(Response res) {
        if(res.getOriginRequest().getType() == RequestType.PLAYER_MOVE){
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
