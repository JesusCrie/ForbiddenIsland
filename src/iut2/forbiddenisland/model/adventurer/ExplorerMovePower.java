package iut2.forbiddenisland.model;
import iut2.forbiddenisland.Utils.Utils;
import iut2.forbiddenisland.controller.RequestType;
import iut2.forbiddenisland.controller.Response;

import java.util.ArrayList;
import java.util.List;


public class ExplorerMovePower implements Power {
    private final Board board;

    public ExplorerMovePower(Board board) {
        this.board = board;
    }

    @Override
    public void alterResponse(final Response<?> res) {
        if (res.getOriginRequest().getType() == RequestType.CELLS_REACHABLE) {
            List<Cell> cells = (List<Cell>) res.getData();
            Location position = res.getOriginRequest().getCurrentPlayer().getPosition().getLocation();
            for (Location loc : Utils.getCornerCells(position)) {
                //todo getCell(Location loc) return la cell a la location loc, si elle n'est pas coulée (donc si elle est sèche ou inondée), sinon return null
                Cell cell = /* board.getCell(loc) */ null;
                if (cell != null){
                    cells.add(cell);
                }
            }
        }
    }
}
