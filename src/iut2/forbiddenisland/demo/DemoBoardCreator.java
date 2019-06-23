package iut2.forbiddenisland.demo;

import iut2.forbiddenisland.demo.scenario1.FloodDeckDemo1;
import iut2.forbiddenisland.demo.scenario1.TreasureDeckDemo1;
import iut2.forbiddenisland.demo.scenario2.FloodDeckDemo2;
import iut2.forbiddenisland.demo.scenario2.TreasureDeckDemo2;
import iut2.forbiddenisland.model.*;
import iut2.forbiddenisland.model.adventurer.*;
import iut2.forbiddenisland.model.card.FloodDeck;
import iut2.forbiddenisland.model.card.TreasureDeck;
import iut2.forbiddenisland.model.cell.*;
import iut2.forbiddenisland.view.CellGraphicalMetadata;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DemoBoardCreator {

    public static Board createAndGet(final List<Adventurer> adventurers, final int waterLevel) {
        final List<Treasure> treasures = BoardGenerator.createTreasures();

        final Map<Location, Cell> map = createMap(treasures);
        spawnPlayers(map, adventurers);

        return new Board(map, adventurers, treasures, new WaterLevel(waterLevel));
    }

    public static Board createScenario1(final List<String> names) {
        final List<Adventurer> adventurers = Arrays.asList(
                new Pilot(names.get(0)),
                new Diver(names.get(1)),
                new Messenger(names.get(3)),
                new Engineer(names.get(2))
        );

        final List<Treasure> treasures = BoardGenerator.createTreasures();

        final Map<Location, Cell> map = createMap(treasures);

        final FloodDeck floodDeck = new FloodDeckDemo1(map);
        final TreasureDeck treasureDeck = new TreasureDeckDemo1(treasures.get(0), treasures.get(1), treasures.get(2), treasures.get(3));

        // Some players need to start with more than 2 cards

        // Pilot
        for (int i = 0; i < 2; i++)
            adventurers.get(0).addCard(treasureDeck.drawCard());

        // Diver
        for (int i = 0; i < 2; ++i)
            adventurers.get(1).addCard(treasureDeck.drawCard());

        // Messenger
        for (int i = 0; i < 3; ++i)
            adventurers.get(2).addCard(treasureDeck.drawCard());

        // Engineer
        for (int i = 0; i < 2; ++i)
            adventurers.get(3).addCard(treasureDeck.drawCard());

        // Spawn players
        spawnPlayers(map, adventurers);

        return new Board(map, adventurers, treasures, new WaterLevel(0), floodDeck, treasureDeck);
    }

    public static Board createScenario2(final List<String> names) {
        final List<Adventurer> adventurers = Arrays.asList(
                new Navigator(names.get(0)),
                new Explorer(names.get(1))
        );

        final List<Treasure> treasures = BoardGenerator.createTreasures();

        final Map<Location, Cell> map = createMap(treasures);

        final FloodDeck floodDeck = new FloodDeckDemo2(map);
        final TreasureDeck treasureDeck = new TreasureDeckDemo2();

        spawnPlayers(map, adventurers);

        return new Board(map, adventurers, treasures, new WaterLevel(1), floodDeck, treasureDeck);
    }

    private static Map<Location, Cell> createMap(final List<Treasure> treasures) {
        final Map<Location, Cell> map = new HashMap<>();

        add(map, new Cell(CellGraphicalMetadata.ABYSS_BRIDGE, Location.from(2, 0)));
        add(map, new StartCell(CellGraphicalMetadata.BRONZE_DOOR, Location.from(3, 0)));

        add(map, new TreasureCell(CellGraphicalMetadata.SHADOW_CAVERN, Location.from(1, 1), treasures.get(2)));
        add(map, new StartCell(CellGraphicalMetadata.IRON_DOOR, Location.from(2, 1)));
        add(map, new StartCell(CellGraphicalMetadata.GOLDEN_DOOR, Location.from(3, 1)));
        add(map, new Cell(CellGraphicalMetadata.CLIFFS_OBLIVION, Location.from(4, 1)));

        add(map, new TreasureCell(CellGraphicalMetadata.CORAL_PALACE, Location.from(0, 2), treasures.get(3)));
        add(map, new StartCell(CellGraphicalMetadata.SILVER_DOOR, Location.from(1, 2)));
        add(map, new Cell(CellGraphicalMetadata.ILLUSION_DUNE, Location.from(2, 2)));
        add(map, new HeliportCell(CellGraphicalMetadata.HELIPORT, Location.from(3, 2)));
        add(map, new Cell(CellGraphicalMetadata.COPPER_DOOR, Location.from(4, 2)));
        add(map, new TreasureCell(CellGraphicalMetadata.SCREAMINGS_GARDEN, Location.from(5, 2), treasures.get(1)));

        add(map, new Cell(CellGraphicalMetadata.PURPLE_FOREST, Location.from(0, 3)));
        add(map, new Cell(CellGraphicalMetadata.LOST_LAGOON, Location.from(1, 3)));
        add(map, new Cell(CellGraphicalMetadata.FOGGY_SWAMP, Location.from(2, 3)));
        add(map, new Cell(CellGraphicalMetadata.OBSERVATORY, Location.from(3, 3)));
        add(map, new Cell(CellGraphicalMetadata.GHOST_ROCK, Location.from(4, 3)));
        add(map, new TreasureCell(CellGraphicalMetadata.FIRE_CAVERN, Location.from(5, 3), treasures.get(2)));

        add(map, new TreasureCell(CellGraphicalMetadata.SUN_TEMPLE, Location.from(1, 4), treasures.get(0)));
        add(map, new TreasureCell(CellGraphicalMetadata.MOON_TEMPLE, Location.from(2, 4), treasures.get(0)));
        add(map, new TreasureCell(CellGraphicalMetadata.TIDAL_PALACE, Location.from(3, 4), treasures.get(3)));
        add(map, new Cell(CellGraphicalMetadata.TWILIGHT_VAL, Location.from(4, 4)));

        add(map, new Cell(CellGraphicalMetadata.WATCHTOWER, Location.from(2, 5)));
        add(map, new TreasureCell(CellGraphicalMetadata.WHISPERS_GARDEN, Location.from(3, 5), treasures.get(1)));

        map.get(Location.from(3, 0)).setState(CellState.WET);
        map.get(Location.from(2, 2)).setState(CellState.FLOODED);
        map.get(Location.from(1, 3)).setState(CellState.WET);
        map.get(Location.from(2, 3)).setState(CellState.FLOODED);
        map.get(Location.from(3, 3)).setState(CellState.WET);
        map.get(Location.from(4, 3)).setState(CellState.FLOODED);
        map.get(Location.from(5, 3)).setState(CellState.WET);
        map.get(Location.from(2, 4)).setState(CellState.FLOODED);
        map.get(Location.from(3, 5)).setState(CellState.WET);

        return map;
    }

    private static void spawnPlayers(final Map<Location, Cell> map, final List<Adventurer> adventurers) {
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
    }

    private static void add(final Map<Location, Cell> map, final Cell cell) {
        map.put(cell.getLocation(), cell);
    }
}
