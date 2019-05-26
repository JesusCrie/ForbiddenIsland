package iut2.forbiddenisland.model;

import iut2.forbiddenisland.controller.request.Request;
import iut2.forbiddenisland.controller.request.Response;
import iut2.forbiddenisland.model.adventurer.Adventurer;
import iut2.forbiddenisland.model.card.Card;
import iut2.forbiddenisland.model.card.FloodCard;
import iut2.forbiddenisland.model.card.FloodDeck;
import iut2.forbiddenisland.model.card.TreasureDeck;
import iut2.forbiddenisland.model.cell.Cell;
import iut2.forbiddenisland.model.cell.CellState;
import iut2.forbiddenisland.model.cell.TreasureCell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {

    private final Map<Location, Cell> cells;
    private final WaterLevel waterLevel;
    private final FloodDeck floodDeck;
    private final TreasureDeck treasureDeck;

    public Board(final Map<Location, Cell> cells, final WaterLevel waterLevel) {
        this.cells = cells;
        this.waterLevel = waterLevel;
        this.floodDeck = new FloodDeck();
        this.treasureDeck = new TreasureDeck();
    }

    /**
     * @param r
     */
    @SuppressWarnings("unchecked")
    public <T> Response<T> handleRequest(final Request r) {
        switch (r.getType()) {
            // *** Cells related requests ***
            case CELLS_ALL:
                return (Response<T>) new Response<Map<Location, Cell>>(r, true).setData(cells);
            case CELLS_REACHABLE:
                return (Response<T>) new Response<List<Cell>>(r, true)
                        .setData(getReachableCells(r.getCurrentPlayer(), r.getData(Request.DATA_CELL)));
            case CELLS_DRAINABLE:
                return (Response<T>) new Response<List<Cell>>(r, true)
                        .setData(getCellsDryable(r.getCurrentPlayer()));
            case CELLS_CLAIMABLE:
                return (Response<T>) new Response<List<Treasure>>(r, true).setData(getTreasuresClaimable(r.getCurrentPlayer()));

            // *** Cards related requests ***
            case CARD_DRAW_AMOUNT:
                break; // TODO
            case CARD_DRAW:
                break; // TODO
            case CARD_USE:
                break; // TODO
            case CARD_TRASH:
                break; // TODO

            // *** Player related requests ***
            case PLAYER_MOVE:
                return (Response<T>) new Response<Integer>(
                        r, movePlayer(r.getCurrentPlayer(), r.getData(Request.DATA_CELL))
                ).setData(1);
            case PLAYER_DRY:
                return (Response<T>) new Response<Integer>(
                        r, dryCell(r.getCurrentPlayer(), r.getData(Request.DATA_CELL))
                ).setData(1);
            case PLAYER_CLAIM:
                return (Response<T>) new Response<Integer>(r, claimTreasure(r.getCurrentPlayer(), r.getData(Request.DATA_CELL))).setData(1);
            case PLAYER_SEND:
                return (Response<T>) new Response<Integer>(
                        r, sendCard(r.getCurrentPlayer(), r.getData(Request.DATA_PLAYER), r.getData(Request.DATA_PLAYER))
                ).setData(1);
            case PLAYERS_SENDABLE:
                return (Response<T>) new Response<List<Adventurer>>(r, true)
                        .setData(getPlayersSendable(r.getCurrentPlayer()));

            // *** Island related requests ***
            case ISLAND_DRAW:
                break; // TODO
            case ISLAND_APPLY:
                break; // TODO
            case ISLAND_WATER_LEVEL:
                break; // TODO
            case ISLAND_WATER_UP:
                break; // TODO

            // *** Game related requests ***
            case GAME_NEW_ROUND:
                break; // Nothing to do
            case GAME_MOVE_AMOUNT:
                return (Response<T>) new Response<Integer>(r, true)
                        .setData(getPlayerMoveAmount());
            default:
                throw new IllegalStateException("Unknown request !");
        }

        return Response.EMPTY;
    }

    /**
     */
    public int getPlayerMoveAmount() {
        return 3;
    }

    /**
     * @param p
     * @param c
     */
    public List<Cell> getReachableCells(final Adventurer p, final Cell c) {
        return Stream.of( // TODO when utils
                Location.from(c.getLocation().getX() + 1, c.getLocation().getY()),
                Location.from(c.getLocation().getX() - 1, c.getLocation().getY()),
                Location.from(c.getLocation().getX(), c.getLocation().getY() + 1),
                Location.from(c.getLocation().getX(), c.getLocation().getY() - 1)
        )
                .map(this::getCell)
                .collect(Collectors.toList());
    }

    /**
     * @param p
     */
    public List<Cell> getCellsDryable(final Adventurer p) {
        final Location loc = p.getPosition().getLocation();

        return Stream.of( // TODO when utils
                Location.from(loc.getX() + 1, loc.getY()),
                Location.from(loc.getX() - 1, loc.getY()),
                Location.from(loc.getX(), loc.getY() + 1),
                Location.from(loc.getX(), loc.getY() - 1)
        )
                .filter(location -> cells.get(location).getState() == CellState.WET)
                .map(this::getCell)
                .collect(Collectors.toList());
    }

    /**
     * @param p
     */
    public List<Adventurer> getPlayersSendable(final Adventurer p) {
        return cells.get(p.getPosition().getLocation()).getAdventurers().stream()
                .filter(anAdventurer -> anAdventurer != p)
                .collect(Collectors.toList());
    }

    /**
     * @param p
     */
    public List<Treasure> getTreasuresClaimable(final Adventurer p) {
        return p.getPosition() instanceof TreasureCell ?
                new ArrayList<>(Collections.singletonList(((TreasureCell) p.getPosition()).getTreasure()))
                : new ArrayList<>();
    }

    /**
     * @param p
     * @param c
     */
    public boolean movePlayer(final Adventurer p, final Cell c) {
        if (getReachableCells(p, p.getPosition()).contains(c)) {
            p.move(c);
            return true;
        }

        return false;
    }

    /**
     * @param p
     * @param c
     */
    public boolean dryCell(final Adventurer p, final Cell c) {
        if (getReachableCells(p, p.getPosition()).contains(c) && c.getState() == CellState.WET) {
            c.setState(CellState.DRY);
            return true;
        }

        return false;
    }

    /**
     * @param from
     * @param to
     * @param c
     */
    public boolean sendCard(final Adventurer from, final Adventurer to, final Card c) {
        if (getReachableCells(from, from.getPosition()).contains(to.getPosition())
                && from.getCards().contains(c)) {
            from.removeCard(c);
            to.addCard(c);
            return true;
        }

        return false;
    }

    /**
     * @param p
     * @param c
     */
    public boolean claimTreasure(final Adventurer p, final TreasureCell c) {
        if (getReachableCells(p, p.getPosition()).contains(c)
                && c.getTreasure().isClaimable()) {
            p.addTrasure(c.getTreasure());
            return true;
        }

        return false;
    }

    public boolean flood() {
        final int cardAmount = getWaterLevel().computeAmountFloodCards();

        for (int i = 0; i < cardAmount; i++) {
            final FloodCard card = (FloodCard) getFloodDeck().drawCard();
            card.getTargetedCell().waterUp();
            getFloodDeck().discardCard(card);
        }

        return true;
    }

    public WaterLevel getWaterLevel() {
        return waterLevel;
    }

    public FloodDeck getFloodDeck() {
        return floodDeck;
    }

    public Cell getCell(final Location l) {
        return cells.get(l);
    }

    public Cell getCellIfDry(final Location l) {
        return cells.get(l).getState() == CellState.DRY ? cells.get(l) : null;
    }

    public Cell getCellIfWet(final Location l) {
        return cells.get(l).getState() == CellState.WET ? cells.get(l) : null;
    }

    public Cell getCellIfFlooded(final Location l) {
        return cells.get(l).getState() == CellState.FLOODED ? cells.get(l) : null;
    }
}
