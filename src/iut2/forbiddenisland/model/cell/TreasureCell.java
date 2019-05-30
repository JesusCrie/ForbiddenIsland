package iut2.forbiddenisland.model.cell;

import iut2.forbiddenisland.model.Location;
import iut2.forbiddenisland.model.Treasure;

public class TreasureCell extends Cell {

	private final Treasure treasure;

	public TreasureCell(final String name, final Location loc, final Treasure treasure) {
		super(name, loc);
		this.treasure = treasure;
	}

	public Treasure getTreasure() {
		return treasure;
	}
}
