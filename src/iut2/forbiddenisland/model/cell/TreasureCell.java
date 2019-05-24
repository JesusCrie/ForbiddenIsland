package iut2.forbiddenisland.model.cell;

import iut2.forbiddenisland.model.Location;
import iut2.forbiddenisland.model.Treasure;

public class TreasureCell extends Cell {

	private Treasure treasure;

	/**
	 * @param name
	 * @param loc
	 */
	public TreasureCell(String name, Location loc) {
		super(name, loc);
	}

	/**
	 * 
	 * @param name
	 * @param loc
	 */
	public void Cell(String name, Location loc) {
		// TODO - implement iut2.forbiddenisland.model.cell.TreasureCell.iut2.forbiddenisland.model.cell.Cell
		throw new UnsupportedOperationException();
	}

	public Treasure getTreasure() {
		return treasure;
	}
}
