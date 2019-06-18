package iut2.forbiddenisland.model.cell;

import iut2.forbiddenisland.model.Location;
import iut2.forbiddenisland.view.CellGraphicalMetadata;

/**
 * Represent any starting cell where a player will start.
 */
public class StartCell extends Cell {

	public StartCell(final CellGraphicalMetadata metadata, final Location loc) {
		super(metadata, loc);
	}
}
