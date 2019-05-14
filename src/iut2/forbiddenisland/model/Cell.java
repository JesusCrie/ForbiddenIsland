package iut2.forbiddenisland.model;

import java.util.*;

public abstract class Cell {

	private List<Adventurer> adventurers;
	private CellState state;
	private String name;

	/**
	 * 
	 * @param name
	 * @param loc
	 */
	public Cell(String name, Location loc) {
		// TODO - implement iut2.forbiddenisland.model.Cell.iut2.forbiddenisland.model.Cell
		throw new UnsupportedOperationException();
	}

	public String getName() {
		return this.name;
	}

	public Location getLocation() {
		// TODO - implement iut2.forbiddenisland.model.Cell.getLocation
		throw new UnsupportedOperationException();
	}

}
