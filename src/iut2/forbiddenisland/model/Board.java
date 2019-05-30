package iut2.forbiddenisland.model;

import iut2.forbiddenisland.Utils;
import iut2.forbiddenisland.controller.request.Request;
import iut2.forbiddenisland.controller.request.Response;
import iut2.forbiddenisland.model.adventurer.Adventurer;
import iut2.forbiddenisland.model.card.*;
import iut2.forbiddenisland.model.cell.Cell;
import iut2.forbiddenisland.model.cell.CellState;
import iut2.forbiddenisland.model.cell.TreasureCell;

import java.util.ArrayList;
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
                return (Response<T>) new Response<Map<Location, Cell>>(r, true).setData(getCells());
            case CELLS_REACHABLE:
                return (Response<T>) new Response<List<Cell>>(r, true)
                        .setData(getReachableCells(r.getData(Request.DATA_CELL)));
            case CELLS_DRAINABLE:
                return (Response<T>) new Response<List<Cell>>(r, true)
                        .setData(getCellsDryable(r.getData(Request.DATA_CELL)));
            case CELLS_CLAIMABLE:
                return (Response<T>) new Response<List<Treasure>>(r, true)
                        .setData(getTreasuresClaimable(r.getCurrentPlayer()));

            // *** Cards related requests ***
            case CARD_DRAW_AMOUNT:
                return (Response<T>) new Response<Integer>(r, true)
                        .setData(getDrawAmount());
            case CARD_DRAW:
                return (Response<T>) new Response<Boolean>(r, drawCard(r.getData(Request.DATA_PLAYER), r.getData(Request.DATA_AMOUNT)));
            case CARD_USE:
                return (Response<T>) new Response<Boolean>(r, useCard(r.getData(Request.DATA_PLAYER), r.getData(Request.DATA_CARD), r));
            case CARD_TRASH:
                return (Response<T>) new Response<Boolean>(r, trashCard(r.getData(Request.DATA_PLAYER), r.getData(Request.DATA_CARD)));

            // *** Player related requests ***
            case PLAYER_MOVE:
                return (Response<T>) new Response<Integer>(
                        r, movePlayer(r.getCurrentPlayer(), r.getData(Request.DATA_CELL), r))
                        .setData(1);
            case PLAYER_DRY:
                return (Response<T>) new Response<Integer>(
                        r, dryCell(r.getCurrentPlayer(), r.getData(Request.DATA_CELL), r))
                        .setData(1);
            case PLAYER_CLAIM:
                return (Response<T>) new Response<Integer>(r, claimTreasure(r.getCurrentPlayer(), r.getData(Request.DATA_CELL), r))
                        .setData(1);
            case PLAYER_SEND:
                return (Response<T>) new Response<Integer>(
                        r, sendCard(r.getCurrentPlayer(), r.getData(Request.DATA_PLAYER), r.getData(Request.DATA_PLAYER), r))
                        .setData(1);
            case PLAYERS_SENDABLE:
                return (Response<T>) new Response<List<Adventurer>>(r, true)
                        .setData(getPlayersSendable(r.getCurrentPlayer()));

            // *** Island related requests ***
            case ISLAND_DRAW:
                return (Response<T>) new Response<FloodCard>(r, true)
                        .setData(islandDrawCard());
            case ISLAND_APPLY:
                break; // TODO c'est quoi ce truc je comprend pas
            case ISLAND_WATER_LEVEL:
                return (Response<T>) new Response<Integer>(r, true)
                        .setData(getWaterLevel().computeAmountFloodCards());
            case ISLAND_WATER_UP:
                return (Response<T>) new Response<Boolean>(r, true)
                        .setData(incrementWaterLevel(r.getData(Request.DATA_AMOUNT)));

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

    // *** Responses logic ***

    public int getPlayerMoveAmount() {
        return 3;
    }

    /**
     * @param c
     */
    public List<Cell> getReachableCells(final Cell c) {
        return Stream.of(
                Utils.getCrossCells(c.getLocation())
        )
                .map(this::getCell)
                .collect(Collectors.toList());
    }

    public List<Cell> getCellsDryable(final Cell c) {
        final Location loc = c.getLocation();

        return Stream.of(
                Utils.getCrossCells(loc)
        )
                .filter(location -> cells.get(location).getState() == CellState.WET)
                .map(this::getCell)
                .collect(Collectors.toList());
    }

    public List<Adventurer> getPlayersSendable(final Adventurer p) {
        return cells.get(p.getPosition().getLocation()).getAdventurers().stream()
                .filter(anAdventurer -> anAdventurer != p)
                .collect(Collectors.toList());
    }

    public List<Treasure> getTreasuresClaimable(final Adventurer p) {
        List<Treasure> treasures = new ArrayList<>();
        if (p.getPosition() instanceof TreasureCell) {
            TreasureCell c = (TreasureCell) p.getPosition();
            for (Card card : p.getCards()) {
                if (card instanceof TreasurePartCard) {
                    if (((TreasurePartCard) card).getTreasure() == c.getTreasure()) {
                        treasures.add(c.getTreasure());
                    }
                }
            }
        }
        return treasures;
    }

    public boolean movePlayer(final Adventurer p, final Cell c, final Request r) {
        if (r.canBypass()) {
            p.move(c);
            return true;
        }

        if (getReachableCells(p.getPosition()).contains(c)) {
            p.move(c);
            return true;
        }

        return false;
    }

    public boolean dryCell(final Adventurer p, final Cell c, final Request r) {
        if (r.canBypass() && c.getState() == CellState.WET) {
            c.setState(CellState.DRY);
            return true;
        }

        if (getReachableCells(p.getPosition()).contains(c) && c.getState() == CellState.WET) {
            c.setState(CellState.DRY);
            return true;
        }

        return false;
    }

    public boolean sendCard(final Adventurer from, final Adventurer to, final Card c, final Request r) {
        if (r.canBypass() && from.getCards().contains(c)) {
            from.removeCard(c);
            to.addCard(c);
            return true;
        }


        if (getReachableCells(from.getPosition()).contains(to.getPosition())
                && from.getCards().contains(c)) {
            from.removeCard(c);
            to.addCard(c);
            return true;
        }

        return false;
    }

    public boolean claimTreasure(final Adventurer p, final TreasureCell c, final Request r) {
        if (r.canBypass() && c.getTreasure().isClaimable()) {
            p.addTreasure(c.getTreasure());
            c.getTreasure().setClaimable(false);
            return true;
        }

        if (getTreasuresClaimable(p).contains(c)
                && c.getTreasure().isClaimable()) {
            p.addTreasure(c.getTreasure());
            c.getTreasure().setClaimable(false);
            return true;
        }

        return false;
    }

    // *** Game state related getters ***

    public WaterLevel getWaterLevel() {
        return waterLevel;
    }

    public FloodDeck getFloodDeck() {
        return floodDeck;
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

    public int getDrawAmount() {
        return 2;
    }

    public boolean drawCard(final Adventurer p, final int drawAmount) {
        for (int i = 1; i <= drawAmount; i++) {
            p.addCard(treasureDeck.drawCard());
        }
        return true;
    }

    public boolean useCard(final Adventurer p, final SpecialCard card, final Request r) {
        if (r.canBypass()) {
            treasureDeck.discardCard(card);
            p.removeCard(card);
            return true;
        }


        if (p.getCards().contains(card)) {
            treasureDeck.discardCard(card);
            p.removeCard(card);
            return true;
        } else return false;
    }

    public boolean trashCard(final Adventurer p, final Card c) {
        if (p.getCards().contains(c)) {
            p.removeCard(c);
            treasureDeck.discardCard((TreasureCard) c);
            return true;
        }
        return false;
    }

    public FloodCard islandDrawCard() {
        return floodDeck.drawCard();
    }

    public boolean incrementWaterLevel(int i) {
        for (int j = 0; j < i; i++) {
            getWaterLevel().incrementWater();
        }
        return true;
    }

}
