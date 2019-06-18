package iut2.forbiddenisland.model;

import iut2.forbiddenisland.model.adventurer.*;
import iut2.forbiddenisland.model.cell.Cell;
import iut2.forbiddenisland.model.cell.HeliportCell;
import iut2.forbiddenisland.model.cell.StartCell;
import iut2.forbiddenisland.model.cell.TreasureCell;
import iut2.forbiddenisland.view.CellGraphicalMetadata;
import iut2.forbiddenisland.view.TreasureCardGraphicalMetadata;
import iut2.forbiddenisland.view.TreasureGraphicalMetadata;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BoardGenerator {

    /**
     * Create a supplier that will give a different valid location each time its called
     * without duplicates.
     *
     * @return A randomized supplier of every valid location.
     */
    private static Supplier<Location> createLocationProvider() {
        // Create and initialize a list of every 24 possible locations
        final List<Location> locs = new ArrayList<>(24);

        // First & sixth column
        for (int i = 2; i <= 3; ++i) {
            locs.add(Location.from(0, i));
            locs.add(Location.from(5, i));
        }

        // Second & fifth column
        for (int i = 1; i <= 4; ++i) {
            locs.add(Location.from(1, i));
            locs.add(Location.from(4, i));
        }

        // Third & fourth column
        for (int i = 0; i <= 5; ++i) {
            locs.add(Location.from(2, i));
            locs.add(Location.from(3, i));
        }

        // Randomize the locations
        Collections.shuffle(locs);

        // Create a supplier that will give the next element of this iterator each time its called
        // thus creating a random stream of locations without any duplicates
        final Iterator<Location> iterator = locs.iterator();
        return iterator::next;
    }

    /**
     * Create the list of the available treasures.
     *
     * @return A list of the treasures.
     */
    private static List<Treasure> createTreasures() {
        return Arrays.asList(
                new Treasure(TreasureGraphicalMetadata.SACRED_STONE),
                new Treasure(TreasureGraphicalMetadata.ZEPHYR_STATUE),
                new Treasure(TreasureGraphicalMetadata.ARDENT_CRYSTAL),
                new Treasure(TreasureGraphicalMetadata.WAVE_CHALICE)
        );
    }

    /**
     * Create the cells where you can claim the treasures.
     *
     * @param treasures        - The treasures, they must come straight from {@link #createTreasures()}.
     * @param locationProvider - The current location provider.
     * @return A list of the treasure cells of the board.
     */
    private static List<TreasureCell> createTreasureCells(final List<Treasure> treasures, final Supplier<Location> locationProvider) {
        // Assumes the treasure list comes directly from #createTreasures() without alteration
        final Treasure sacredStone = treasures.get(0);
        final Treasure zephyrStatue = treasures.get(1);
        final Treasure ardentCrystal = treasures.get(2);
        final Treasure waveChalice = treasures.get(3);

        return Arrays.asList(
                new TreasureCell(CellGraphicalMetadata.MOON_TEMPLE, locationProvider.get(), sacredStone),
                new TreasureCell(CellGraphicalMetadata.SUN_TEMPLE, locationProvider.get(), sacredStone),

                new TreasureCell(CellGraphicalMetadata.WHISPERS_GARDEN, locationProvider.get(), zephyrStatue),
                new TreasureCell(CellGraphicalMetadata.SCREAMINGS_GARDEN, locationProvider.get(), zephyrStatue),

                new TreasureCell(CellGraphicalMetadata.FIRE_CAVERN, locationProvider.get(), ardentCrystal),
                new TreasureCell(CellGraphicalMetadata.SHADOW_CAVERN, locationProvider.get(), ardentCrystal),

                new TreasureCell(CellGraphicalMetadata.CORAL_PALACE, locationProvider.get(), waveChalice),
                new TreasureCell(CellGraphicalMetadata.TIDAL_PALACE, locationProvider.get(), waveChalice)
        );
    }

    /**
     * Create the starting cells of the adventurers and assign the adventurers to them.
     *
     * @param adventurers      - The adventurers to assign to their starting cell.
     * @param locationProvider - The current location provider.
     * @return A list of the starting cells of the board.
     */
    private static List<StartCell> createStartCells(final List<Adventurer> adventurers, final Supplier<Location> locationProvider) {
        final List<StartCell> cells = Arrays.asList(
                new HeliportCell(CellGraphicalMetadata.HELIPORT, locationProvider.get()),
                new StartCell(CellGraphicalMetadata.BRONZE_DOOR, locationProvider.get()),
                new StartCell(CellGraphicalMetadata.COPPER_DOOR, locationProvider.get()),
                new StartCell(CellGraphicalMetadata.IRON_DOOR, locationProvider.get()),
                new StartCell(CellGraphicalMetadata.SILVER_DOOR, locationProvider.get()),
                new StartCell(CellGraphicalMetadata.GOLDEN_DOOR, locationProvider.get())
        );

        // Move each adventurer to its starting cell
        for (Adventurer adventurer : adventurers) {
            if (adventurer instanceof Pilot)
                adventurer.move(cells.get(0));
            else if (adventurer instanceof Engineer)
                adventurer.move(cells.get(1));
            else if (adventurer instanceof Explorer)
                adventurer.move(cells.get(2));
            else if (adventurer instanceof Diver)
                adventurer.move(cells.get(3));
            else if (adventurer instanceof Messenger)
                adventurer.move(cells.get(4));
            else if (adventurer instanceof Navigator)
                adventurer.move(cells.get(5));
        }

        return cells;
    }

    /**
     * Create the cells that aren't special in any way.
     *
     * @param locationProvider - The current location provider.
     * @return A list of the common cells of the board.
     */
    private static List<Cell> createCommonCells(final Supplier<Location> locationProvider) {
        return Arrays.asList(
                new Cell(CellGraphicalMetadata.ABYSS_BRIDGE, locationProvider.get()),
                new Cell(CellGraphicalMetadata.CLIFFS_OBLIVION, locationProvider.get()),
                new Cell(CellGraphicalMetadata.ILLUSION_DUNE, locationProvider.get()),
                new Cell(CellGraphicalMetadata.PURPLE_FOREST, locationProvider.get()),
                new Cell(CellGraphicalMetadata.LOST_LAGOON, locationProvider.get()),
                new Cell(CellGraphicalMetadata.FOGGY_SWAMP, locationProvider.get()),
                new Cell(CellGraphicalMetadata.OBSERVATORY, locationProvider.get()),
                new Cell(CellGraphicalMetadata.GHOST_ROCK, locationProvider.get()),
                new Cell(CellGraphicalMetadata.TWILIGHT_VAL, locationProvider.get()),
                new Cell(CellGraphicalMetadata.WATCHTOWER, locationProvider.get())
        );
    }

    /**
     * Create a random board, giving a list of adventurers and an initial water level.
     *
     * @param adventurers       - The adventurers to put on the board.
     * @param initialWaterLevel - The initial water level of the board.
     * @return A random board with the adventurers on their start cell.
     */
    public static Board createRandomBoard(final List<Adventurer> adventurers, final int initialWaterLevel) {
        // Create the unique location provider of this build
        final Supplier<Location> locationProvider = createLocationProvider();

        final List<Treasure> treasures = createTreasures();

        // Concatenate 3 streams and collect them into a map
        final Map<Location, Cell> cells = Stream.concat( // Concatenate special cells and common cells
                Stream.concat( // Concatenate start cells and treasure cells
                        createStartCells(adventurers, locationProvider).stream(),
                        createTreasureCells(treasures, locationProvider).stream()
                ),
                createCommonCells(locationProvider).stream()
                // Collect the stream by mapping the key to the location of the cell and the value to the cell itself
        ).collect(Collectors.toMap(Cell::getLocation, cell -> cell));

        return new Board(
                cells,
                adventurers,
                treasures,
                new WaterLevel(initialWaterLevel)
        );
    }
}
