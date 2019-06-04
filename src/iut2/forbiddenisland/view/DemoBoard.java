package iut2.forbiddenisland.view;

import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.Location;
import iut2.forbiddenisland.model.Treasure;
import iut2.forbiddenisland.model.WaterLevel;
import iut2.forbiddenisland.model.adventurer.*;
import iut2.forbiddenisland.model.cell.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DemoBoard {

    public static Board createAndGet(final List<Adventurer> adventurers) {
        final List<Treasure> treasures = Arrays.asList(
                new Treasure("La pierre sacrée"),
                new Treasure("La Statue du Zéphyr"),
                new Treasure("Le Cristal Ardent"),
                new Treasure("Le Calice de l'Onde")
        );

        final Map<Location, Cell> map = new HashMap<>();

        add(map, new Cell(CellNames.ABYSS_BRIDGE, Location.from(2, 0)));
        add(map, new StartCell(CellNames.BRONZE_DOOR, Location.from(3, 0)));

        add(map, new TreasureCell(CellNames.SHADOW_CAVERN, Location.from(1, 1), treasures.get(2)));
        add(map, new StartCell(CellNames.IRON_DOOR, Location.from(2, 1)));
        add(map, new StartCell(CellNames.GOLDEN_DOOR, Location.from(3, 1)));
        add(map, new Cell(CellNames.CLIFFS_OBLIVION, Location.from(4, 1)));

        add(map, new TreasureCell(CellNames.CORAL_PALACE, Location.from(0, 2), treasures.get(3)));
        add(map, new StartCell(CellNames.SILVER_DOOR, Location.from(1, 2)));
        add(map, new Cell(CellNames.ILLUSION_DUNE, Location.from(2, 2)));
        add(map, new HeliportCell(CellNames.HELIPORT, Location.from(3, 2)));
        add(map, new Cell(CellNames.COPPER_DOOR, Location.from(4, 2)));
        add(map, new TreasureCell(CellNames.SCREAMINGS_GARDEN, Location.from(5, 2), treasures.get(1)));

        add(map, new Cell(CellNames.PURPLE_FOREST, Location.from(0, 3)));
        add(map, new Cell(CellNames.LOST_LAGOON, Location.from(1, 3)));
        add(map, new Cell(CellNames.FOGGY_SWAMP, Location.from(2, 3)));
        add(map, new Cell(CellNames.OBSERVATORY, Location.from(3, 3)));
        add(map, new Cell(CellNames.GHOST_ROCK, Location.from(4, 3)));
        add(map, new TreasureCell(CellNames.FIRE_CAVERN, Location.from(5, 3), treasures.get(2)));

        add(map, new TreasureCell(CellNames.SUN_TEMPLE, Location.from(1, 4), treasures.get(0)));
        add(map, new TreasureCell(CellNames.MOON_TEMPLE, Location.from(2, 4), treasures.get(0)));
        add(map, new TreasureCell(CellNames.TIDAL_PALACE, Location.from(3, 4), treasures.get(3)));
        add(map, new Cell(CellNames.TWILIGHT_VAL, Location.from(4, 4)));

        add(map, new Cell(CellNames.WATCHTOWER, Location.from(2, 5)));
        add(map, new TreasureCell(CellNames.WISPERING_GARDEN, Location.from(3, 5), treasures.get(1)));

        map.get(Location.from(3, 0)).setState(CellState.WET);
        map.get(Location.from(2, 2)).setState(CellState.FLOODED);
        map.get(Location.from(1, 3)).setState(CellState.WET);
        map.get(Location.from(2, 3)).setState(CellState.FLOODED);
        map.get(Location.from(3, 3)).setState(CellState.WET);
        map.get(Location.from(4, 3)).setState(CellState.FLOODED);
        map.get(Location.from(5, 3)).setState(CellState.WET);
        map.get(Location.from(2, 4)).setState(CellState.FLOODED);
        map.get(Location.from(3, 5)).setState(CellState.WET);

        for (Adventurer adventurer : adventurers) {
            if (adventurer instanceof Pilot)
                adventurer.move(map.get(Location.from(3, 2)));

            else if (adventurer instanceof Engineer)
                adventurer.move(map.get(Location.from(3, 0)));

            else if (adventurer instanceof Explorer)
                adventurer.move(map.get(Location.from(4, 2)));

            else if (adventurer instanceof Diver)
                adventurer.move(map.get(Location.from(2, 1)));

            else if (adventurer instanceof Messenger)
                adventurer.move(map.get(Location.from(1, 2)));

            else if (adventurer instanceof Navigator)
                adventurer.move(map.get(Location.from(3, 1)));
        }

        return new Board(map, adventurers, treasures, new WaterLevel(3));
    }

    private static void add(Map<Location, Cell> map, Cell cell) {
        map.put(cell.getLocation(), cell);
    }
}
