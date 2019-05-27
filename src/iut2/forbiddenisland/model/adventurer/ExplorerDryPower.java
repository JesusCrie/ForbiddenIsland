package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.Utils;
import iut2.forbiddenisland.controller.request.RequestType;
import iut2.forbiddenisland.controller.request.Response;
import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.Location;
import iut2.forbiddenisland.model.cell.Cell;

import java.util.List;

public class ExplorerDryPower implements Power {
    private final Board board;

    public ExplorerDryPower(Board board) {
        this.board = board;
    }

    @Override
    public void alterResponse(final Response res) {
        if (res.getOriginRequest().getType() == RequestType.CELLS_DRAINABLE) {
            List<Cell> cells = (List<Cell>) res.getData();
            Location position = res.getOriginRequest().getCurrentPlayer().getPosition().getLocation();
            for (Location loc : Utils.getCornerCells(position)) {
                //todo getWetCell(Location loc) return la cell a la location loc, si elle est inondée (donc si elle est pas sèche ni coulée) sinon return null
                Cell cell = /*board.getWetCell(loc)*/ null;
                if (cell != null) {
                    cells.add(cell);
                }
            }
        }
    }
}
