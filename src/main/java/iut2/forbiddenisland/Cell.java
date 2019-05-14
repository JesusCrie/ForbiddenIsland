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
		// TODO - implement Cell.Cell
		throw new UnsupportedOperationException();
	}

	public string getName() {
		return this.name;
	}

	public Location getLocation() {
		// TODO - implement Cell.getLocation
		throw new UnsupportedOperationException();
	}

}