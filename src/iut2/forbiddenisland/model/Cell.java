package iut2.forbiddenisland.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Cell {

    private final String name;
    private CellState state = CellState.DRY;
    private final Location location;

    private List<Adventurer> adventurers = new ArrayList<>();

    public Cell(final String name, final Location location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return this.name;
    }

    public Location getLocation() {
        return location;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    /**
     * @return The list of adventurers on this cell.
     */
    public List<Adventurer> getAdventurers() {
        return adventurers;
    }

    public void removeAdventurer(final Adventurer player) {
        adventurers.remove(player);
    }
}
