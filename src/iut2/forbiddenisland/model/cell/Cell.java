package iut2.forbiddenisland.model.cell;

import iut2.forbiddenisland.model.Location;
import iut2.forbiddenisland.model.adventurer.Adventurer;
import iut2.forbiddenisland.view.CellGraphicalMetadata;

import java.util.ArrayList;
import java.util.List;

public class Cell {

    private final CellGraphicalMetadata metadata;
    private CellState state = CellState.DRY;
    private final Location location;

    private final List<Adventurer> adventurers = new ArrayList<>();

    public Cell(final CellGraphicalMetadata metadata, final Location location) {
        this.metadata = metadata;
        this.location = location;
    }

    public String getName() {
        return metadata.getName();
    }

    public CellGraphicalMetadata getMetadata() {
        return metadata;
    }

    public Location getLocation() {
        return location;
    }

    public CellState getState() {
        return state;
    }

    public void setState(final CellState state) {
        this.state = state;
    }

    public void waterUp() {
        if (state == CellState.DRY)
            setState(CellState.WET);
        else if (state == CellState.WET)
            setState(CellState.FLOODED);
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

    public void addAdventurer(final Adventurer player) {
        adventurers.add(player);
    }
}
