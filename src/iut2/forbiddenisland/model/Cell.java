package iut2.forbiddenisland.model;

import java.util.*;

public abstract class Cell {

	Collection<Adventurer> adventurers;
	CellState state;
	private string name;

	/**
	 * 
	 * @param name
	 * @param loc
	 */
	public Cell(string name, Location loc) {
		// TODO - implement iut2.forbiddenisland.model.Cell.iut2.forbiddenisland.model.Cell
		throw new UnsupportedOperationException();
	}

	public string getName() {
		return this.name;
	}

	public Location getLocation() {
		// TODO - implement iut2.forbiddenisland.model.Cell.getLocation
		throw new UnsupportedOperationException();
	}

}
