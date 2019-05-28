package iut2.forbiddenisland.view;

import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.Location;
import iut2.forbiddenisland.model.WaterLevel;
import iut2.forbiddenisland.model.cell.*;

import java.util.HashMap;
import java.util.Map;

public class DemoBoard {

    public static Board createAndGet() {
        final Map<Location, Cell> map = new HashMap<>();

        add(map, new Cell(CellNames.ABYSS_BRIDGE, Location.from(2, 0)));
        add(map, new StartCell(CellNames.BRONZE_DOOR, Location.from(3, 0)));

        add(map, new Cell(CellNames.SHADOW_CAVERN, Location.from(1, 1)));
        add(map, new StartCell(CellNames.IRON_DOOR, Location.from(2, 1)));
        add(map, new StartCell(CellNames.GOLDEN_DOOR, Location.from(3, 1)));
        add(map, new Cell(CellNames.CLIFFS_OBLIVION, Location.from(4, 1)));

        add(map, new Cell(CellNames.CORAL_PALACE, Location.from(0, 2)));
        add(map, new StartCell(CellNames.SILVER_DOOR, Location.from(1, 2)));
        add(map, new Cell(CellNames.ILLUSION_DUNE, Location.from(2, 2)));
        add(map, new HeliportCell(CellNames.HELIPORT, Location.from(3, 2)));
        add(map, new Cell(CellNames.COPPER_DOOR, Location.from(4, 2)));
        add(map, new Cell(CellNames.SCREAMINGS_GARDEN, Location.from(5, 2)));

        add(map, new Cell(CellNames.PURPLE_FOREST, Location.from(0, 3)));
        add(map, new Cell(CellNames.LOST_LAGOON, Location.from(1, 3)));
        add(map, new Cell(CellNames.FOGGY_SWAMP, Location.from(2, 3)));
        add(map, new Cell(CellNames.OBSERVATORY, Location.from(3, 3)));
        add(map, new Cell(CellNames.GHOST_ROCK, Location.from(4, 3)));
        add(map, new Cell(CellNames.FIRE_CAVERN, Location.from(5, 3)));

        add(map, new Cell(CellNames.SUN_TEMPLE, Location.from(1, 4)));
        add(map, new Cell(CellNames.MOON_TEMPLE, Location.from(2, 4)));
        add(map, new Cell(CellNames.TIDAL_PALACE, Location.from(3, 4)));
        add(map, new Cell(CellNames.TWILIGHT_VAL, Location.from(4, 4)));

        add(map, new Cell(CellNames.WATCHTOWER, Location.from(2, 5)));
        add(map, new Cell(CellNames.WISPERING_GARDEN, Location.from(3, 5)));

        map.get(Location.from(3, 0)).setState(CellState.WET);
        map.get(Location.from(2, 2)).setState(CellState.FLOODED);
        map.get(Location.from(1, 3)).setState(CellState.WET);
        map.get(Location.from(2, 3)).setState(CellState.FLOODED);
        map.get(Location.from(3, 3)).setState(CellState.WET);
        map.get(Location.from(4, 3)).setState(CellState.FLOODED);
        map.get(Location.from(5, 3)).setState(CellState.WET);
        map.get(Location.from(2, 4)).setState(CellState.FLOODED);
        map.get(Location.from(3, 5)).setState(CellState.WET);

        return new Board(map, new WaterLevel(3));
    }

    private static void add(Map<Location, Cell> map, Cell cell) {
        map.put(cell.getLocation(), cell);
    }
}
