package iut2.forbiddenisland.model.cell;

import iut2.forbiddenisland.model.Location;
import iut2.forbiddenisland.model.Treasure;
import iut2.forbiddenisland.view.CellGraphicalMetadata;

/**
 * Represent a cell where we can claim a treasure.
 */
public class TreasureCell extends Cell {

	private final Treasure treasure;

	public TreasureCell(final CellGraphicalMetadata metadata, final Location loc, final Treasure treasure) {
		super(metadata, loc);
		this.treasure = treasure;
	}

	public Treasure getTreasure() {
		return treasure;
	}
}
