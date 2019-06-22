package iut2.forbiddenisland.model;

import iut2.forbiddenisland.Utils;
import iut2.forbiddenisland.controller.request.Request;
import iut2.forbiddenisland.controller.request.Response;
import iut2.forbiddenisland.model.adventurer.Adventurer;
import iut2.forbiddenisland.model.card.*;
import iut2.forbiddenisland.model.cell.Cell;
import iut2.forbiddenisland.model.cell.CellState;
import iut2.forbiddenisland.model.cell.HeliportCell;
import iut2.forbiddenisland.model.cell.TreasureCell;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {

    private final Map<Location, Cell> cells;
    private final List<Adventurer> adventurers;
    private final List<Treasure> treasures;

    private final WaterLevel waterLevel;

    private final FloodDeck floodDeck;
    private final TreasureDeck treasureDeck;

    public Board(final Map<Location, Cell> cells,
                 final List<Adventurer> adventurers,
                 final List<Treasure> treasures,
                 final WaterLevel waterLevel) {

        this(cells, adventurers, treasures, waterLevel,
                new FloodDeck(cells.values()), new TreasureDeck(treasures));
    }

    public Board(final Map<Location, Cell> cells,
                 final List<Adventurer> adventurers,
                 final List<Treasure> treasures,
                 final WaterLevel waterLevel,
                 final FloodDeck floodDeck,
                 final TreasureDeck treasureDeck) {

        this.cells = cells;
        this.adventurers = adventurers;
        this.treasures = treasures;
        this.waterLevel = waterLevel;
        this.floodDeck = floodDeck;
        this.treasureDeck = treasureDeck;
    }

    /**
     * Handle the incoming requests from the proxy and process them.
     * The data returned with the response MUST be mutable.
     * (no {@code Collections.emptyList(...)} or {@code Arrays.asList(...)} for example).
     *
     * @param r - The request supplied by the proxy
     */
    @SuppressWarnings("unchecked")
    public <T> Response<T> handleRequest(final Request r) {
        switch (r.getType()) {
            // *** Cells related requests ***
            case CELLS_ALL:
                return (Response<T>) new Response<Map<Location, Cell>>(r, true)
                        .setData(getCells());
            case CELLS_REACHABLE:
                return (Response<T>) new Response<List<Cell>>(r, true)
                        .setData(getReachableCells(r));
            case CELLS_DRAINABLE:
                return (Response<T>) new Response<List<Cell>>(r, true)
                        .setData(getCellsDryable(r));
            case CELLS_CLAIMABLE:
                return (Response<T>) new Response<List<Treasure>>(r, true)
                        .setData(getTreasuresClaimable(r));

            // *** Cards related requests ***
            case CARD_DRAW_AMOUNT:
                return (Response<T>) new Response<Integer>(r, true)
                        .setData(getDrawAmount());
            case CARD_DRAW:
                return (Response<T>) new Response<TreasureCard>(r, true)
                        .setData(drawCard(r));
            case CARD_TRASH:
                return (Response<T>) new Response<Void>(r, useCard(r));

            // *** Player related requests ***
            case PLAYER_MOVE:
                return (Response<T>) new Response<Integer>(r, movePlayer(r)).setData(1);
            case PLAYER_DRY:
                return (Response<T>) new Response<Integer>(r, dryCell(r)).setData(1);
            case PLAYER_CLAIM:
                return (Response<T>) new Response<Integer>(r, claimTreasure(r)).setData(1);
            case PLAYER_SEND:
                return (Response<T>) new Response<Integer>(r, sendCard(r)).setData(1);
            case PLAYERS_SENDABLE:
                return (Response<T>) new Response<List<Adventurer>>(r, true)
                        .setData(getPlayersSendable(r));

            // *** Island related requests ***
            case TREASURES_ALL:
                return (Response<T>) new Response<List<Treasure>>(r, true)
                        .setData(getTreasures());
            case ISLAND_DRAW:
                return (Response<T>) new Response<FloodCard>(r, true)
                        .setData(islandDrawCard());
            case ISLAND_APPLY:
                return (Response<T>) new Response<Void>(r, islandUseCard(r));
            case ISLAND_WATER_LEVEL:
                return (Response<T>) new Response<WaterLevel>(r, true)
                        .setData(getWaterLevel());
            case ISLAND_WATER_UP:
                return (Response<T>) new Response<Void>(r, incrementWaterLevel(r));

            // *** Game related requests ***
            case GAME_NEW_ROUND:
                break; // Nothing to do
            case GAME_MOVE_AMOUNT:
                return (Response<T>) new Response<Integer>(r, true)
                        .setData(getPlayerMoveAmount());
            case GAME_CHECK_WIN:
                return (Response<T>) new Response<Boolean>(r, true)
                        .setData(checkWin(r));

            default:
                throw new IllegalStateException("Unknown request !");
        }

        return new Response<>(r, true);
    }

    // *** Responses logic ***

    // Getter like requests

    public int getPlayerMoveAmount() {
        return 3;
    }

    public int getDrawAmount() {
        return 2;
    }

    public List<Cell> getReachableCells(final Request r) {
        final Cell c = r.getData(Request.DATA_CELL);

        return Stream.of(
                Utils.getCrossCells(
                        c.getLocation()))
                .map(this::getCellIfNotFlooded)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<Cell> getCellsDryable(final Request r) {
        final Cell c = r.getData(Request.DATA_CELL);
        final Location loc = c.getLocation();

        final List<Cell> data = Stream.of(Utils.getCrossCells(loc))
                .map(this::getCellIfWet)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (c.getState() == CellState.WET)
            data.add(c);
        return data;
    }

    public List<Adventurer> getPlayersSendable(final Request r) {
        final Adventurer p = r.getCurrentPlayer();

        return p.getPosition().getAdventurers().stream()
                .filter(anAdventurer -> anAdventurer != p)
                .collect(Collectors.toList());
    }

    public List<Treasure> getTreasuresClaimable(final Request r) {
        final Adventurer p = r.getData(Request.DATA_PLAYER);
        final List<Treasure> treasures = new ArrayList<>(1);

        if (p.getPosition() instanceof TreasureCell) {
            final Treasure treasure = ((TreasureCell) p.getPosition()).getTreasure();
            final long cardCount = p.getCards().stream()
                    .filter(card -> card instanceof TreasurePartCard)
                    .map(card -> (TreasurePartCard) card)
                    .filter(card -> card.getTreasure().equals(treasure))
                    .count();

            if (cardCount >= 4)
                treasures.add(treasure);
        }

        return treasures;
    }

    // Action requests

    public boolean movePlayer(final Request r) {
        final Adventurer adv = r.getData(Request.DATA_PLAYER);
        final Cell cell = r.getData(Request.DATA_CELL);

        if (r.canBypass()
                || (Utils.isAdjacent(adv.getPosition().getLocation(), cell.getLocation())
                && cell.getState() != CellState.FLOODED)) {
            adv.move(cell);
            return true;
        }

        return false;
    }

    public boolean dryCell(final Request r) {
        final Adventurer adv = r.getData(Request.DATA_PLAYER);
        final Cell cell = r.getData(Request.DATA_CELL);

        if (r.canBypass() || Utils.isAdjacent(adv.getPosition().getLocation(), cell.getLocation())) {
            cell.setState(CellState.DRY);
            return true;
        }

        return false;
    }

    public boolean sendCard(final Request r) {
        final Adventurer from = r.getData(Request.DATA_PLAYER);
        final Adventurer to = r.getData(Request.DATA_PLAYER_EXTRA);
        final TreasureCard card = r.getData(Request.DATA_CARD);

        if (r.canBypass() ||
                from.getPosition().equals(to.getPosition()) && from.getCards().contains(card)) {
            from.removeCard(card);
            to.addCard(card);
            return true;
        }

        return false;
    }

    public boolean claimTreasure(final Request r) {
        final Adventurer adv = r.getCurrentPlayer();
        final Treasure treasure = r.<TreasureCell>getData(Request.DATA_CELL).getTreasure();

        if (!r.canBypass() && !treasure.isClaimable())
            return false;

        final List<TreasurePartCard> cards = adv.getCards().stream()
                .filter(card -> card instanceof TreasurePartCard)
                .map(card -> (TreasurePartCard) card)
                .filter(card -> card.getTreasure().equals(treasure))
                .collect(Collectors.toList());

        if (!r.canBypass() && cards.size() < 4)
            return false;

        cards.forEach(adv::removeCard);
        treasure.claim(adv);

        return true;
    }

    public TreasureCard drawCard(final Request r) {
        final Adventurer adv = r.getData(Request.DATA_PLAYER);

        final TreasureCard card = treasureDeck.drawCard();
        adv.addCard(card);

        return card;
    }

    public boolean useCard(final Request r) {
        final Adventurer adv = r.getData(Request.DATA_PLAYER);
        final TreasureCard card = r.getData(Request.DATA_CARD);

        if (r.canBypass() || adv.getCards().contains(card)) {
            adv.removeCard(card);
            treasureDeck.discardCard(card);
            return true;
        }

        return false;
    }

    public FloodCard islandDrawCard() {
        return floodDeck.drawCard();
    }

    public boolean islandUseCard(final Request r) {
        final FloodCard card = r.getData(Request.DATA_CARD);

        card.getTargetedCell().waterUp();

        // If cell is not yet flooded, keep the card in the discarding deck
        if (card.getTargetedCell().getState() != CellState.FLOODED) {
            floodDeck.discardCard(card);
        }

        return true;
    }

    public boolean incrementWaterLevel(final Request r) {
        final int amount = r.getData(Request.DATA_AMOUNT);

        for (int i = 0; i < amount; ++i)
            getWaterLevel().incrementWater();

        floodDeck.reset();

        return true;
    }

    public Boolean checkWin(final Request request) {
        if (request.canBypass())
            return true;

        if (waterLevel.getLevel() == 10)
            return false;

        // Check for heliport
        final Cell heliport = cells.values().stream()
                .filter(cell -> cell instanceof HeliportCell)
                .findFirst()
                .filter(cell -> cell.getState() != CellState.FLOODED)
                .orElse(null);

        if (heliport == null)
            return false;

        // TODO check if someone is isolated

        return null;
    }

    // *** Game state related getters ***


    public List<Adventurer> getAdventurers() {
        return adventurers;
    }

    public List<Treasure> getTreasures() {
        return treasures;
    }

    public WaterLevel getWaterLevel() {
        return waterLevel;
    }

    public Map<Location, Cell> getCells() {
        return cells;
    }

    public Cell getCell(final Location l) {
        return cells.get(l);
    }

    public Cell getCellIfDry(final Location l) {
        final Cell c = getCell(l);
        if (c != null && c.getState() == CellState.DRY)
            return c;
        return null;
    }

    public Cell getCellIfWet(final Location l) {
        final Cell c = getCell(l);
        if (c != null && c.getState() == CellState.WET)
            return c;
        return null;
    }

    public Cell getCellIfFlooded(final Location l) {
        final Cell c = getCell(l);
        if (c != null && c.getState() == CellState.FLOODED)
            return c;
        return null;
    }

    public Cell getCellIfNotFlooded(final Location l) {
        final Cell c = getCell(l);
        if (c != null && c.getState() != CellState.FLOODED)
            return c;
        return null;
    }

}
