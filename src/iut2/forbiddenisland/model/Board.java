package iut2.forbiddenisland.model;

import iut2.forbiddenisland.controller.Request;
import iut2.forbiddenisland.controller.RequestType;
import iut2.forbiddenisland.controller.Response;

import java.time.format.ResolverStyle;
import java.util.*;
import java.util.stream.Collectors;

public class Board {

	private final List<Cell> cells;
	WaterLevel waterLevel;
	private FloodDeck floodDeck;
	private TreasureDeck treasureDeck;

	public Board(List<Cell> cells) {
		this.cells = cells;
	}

	/**
	 * 
	 * @param r
	 */
	@SuppressWarnings("unchecked")
	public <T> Response<T> handleRequest(Request r) {
		switch (r.getType()) {
			case PLAYER_MOVE:
				return (Response<T>) new Response<Integer>(r, this.movePlayer(r.getCurrentPlayer(), r.getData(Request.DATA_CELL))).setData(1);
			case PLAYER_MOVE_AMOUNT:
				return (Response<T>) new Response<Integer>(r, true).setData(this.getPlayerMoveAmount(r.getCurrentPlayer()));
			case PLAYER_SEND:
				return (Response<T>) new Response<Integer>(r, sendCard(r.getCurrentPlayer(), r.getData(Request.DATA_PLAYER), r.getData(Request.DATA_PLAYER))).setData(1);
			case PLAYERS_SENDABLE:
				return (Response<T>) new Response<List<Adventurer>>(r, true).setData(this.getPlayersSendable(r.getCurrentPlayer()));
			case CELLS_ALL:
				return (Response<T>) new Response<List<Cell>>(r, true).setData(cells);
			case CELLS_REACHABLE:
				return (Response<T>) new Response<List<Cell>>(r, true).setData(this.getReachableCells(r.getCurrentPlayer(), r.getData(Request.DATA_CELL)));
			case CELLS_DRYABLE:
				return (Response<T>) new Response<List<Cell>>(r, true).setData(this.getCellsDryable(r.getCurrentPlayer()));
			case CELL_DRY:
				return (Response<T>) new Response<Integer>(r, this.dryCell(r.getCurrentPlayer(), r.getData(Request.DATA_CELL))).setData(1);
			case CELL_CLAIM_TREASURE:
				return (Response<T>) new Response<Integer>(r, this.claimTreasure(r.getCurrentPlayer(), r.getData(Request.DATA_CELL))).setData(1);
			case TREASURES_CLAIMABLE:
				return (Response<T>) new Response<List<TreasureCell>>(r, true).setData(this.getTreasuresClaimable(r.getCurrentPlayer()));
			case FLOODING:
				return (Response<T>) new Response<>(r, this.flood()).setData(null);
			default:
				throw new IllegalStateException();
		}
	}

	/**
	 * 
	 * @param p
	 */
	public int getPlayerMoveAmount(Adventurer p) {
		return 3;
	}

	/**
	 * 
	 * @param p
	 * @param c
	 */
	public List<Cell> getReachableCells(Adventurer p, Cell c) {
		return cells.stream()
				.filter(cell -> {
					if (c.getLocation().getX() - 1 == cell.getLocation().getX() && c.getLocation().getY() == cell.getLocation().getY()){
						return true;
					} else if (c.getLocation().getX() + 1 == cell.getLocation().getX() && c.getLocation().getY() == cell.getLocation().getY()){
						return true;
					} else if (c.getLocation().getX() == cell.getLocation().getX() && c.getLocation().getY() + 1 == cell.getLocation().getY()){
						return true;
					} else return c.getLocation().getX() == cell.getLocation().getX() && c.getLocation().getY() - 1 == cell.getLocation().getY();
				})
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * @param p
	 */
	public List<Cell> getCellsDryable(Adventurer p) {
		List<Cell> recheableCell = getReachableCells(p, p.getPosition());
		List<Cell> dryableCell = new ArrayList<>();
		for(Cell aCell : recheableCell){
			if (aCell.getState() == CellState.WET){
				dryableCell.add(aCell);
			}
		}
		return dryableCell;
	}

	/**
	 * 
	 * @param p
	 */
	public List<Adventurer> getPlayersSendable(Adventurer p) {
		List<Cell> recheableCell = getReachableCells(p, p.getPosition());
		ArrayList<Adventurer> sendablePlayer = new ArrayList<>();
		for (Cell aCell : recheableCell){
			sendablePlayer.addAll(aCell.getAdventurers());
		}
		return sendablePlayer;
	}

	/**
	 * 
	 * @param p
	 */
	public List<TreasureCell> getTreasuresClaimable(Adventurer p) {
		List<Cell> recheableCell = getReachableCells(p, p.getPosition());
		ArrayList<TreasureCell> claimableTreasure = new ArrayList<>();
		for (Cell aCell : recheableCell){
			if (aCell instanceof TreasureCell){
				claimableTreasure.add((TreasureCell) aCell);
			}
		}
		return claimableTreasure;
		// TODO IL EST PAS COMPLET PUTAIN
	}

	/**
	 * 
	 * @param p
	 * @param c
	 */
	public boolean movePlayer(Adventurer p, Cell c) {
		Cell oldCell = p.getPosition();
		p.getPosition().removeAdventurer(p);
		p.move(c);
		c.getAdventurers().add(p);
		Cell newCell = p.getPosition();
		return oldCell != newCell;
	}

	/**
	 * 
	 * @param p
	 * @param c
	 */
	public boolean dryCell(Adventurer p, Cell c) {
		if (c.getState() == CellState.WET){
			c.setState(CellState.DRY);
			return c.getState() == CellState.DRY;
		} else return false;
	}

	/**
	 * 
	 * @param p
	 * @param to
	 */
	public boolean sendCard(Adventurer from, Adventurer to, Card c) {
		if (from.getCards().contains(c)){
			from.removeCard(c);
			to.addCard(c);
			return true;
		} else return false;
	}

	/**
	 * 
	 * @param p
	 * @param c
	 */
	public boolean claimTreasure(Adventurer p, TreasureCell c) {
		if (c.getTreasure().isClaimable()){
			p.addTrasure(c.getTreasure());
			return true;
		} else return false;
	}

	public boolean flood() {
		//TODO JE CROIS CEST DE LA MERDE
		int numberCard = 1;
		for (int i = 1; i <= /*getWaterLevel().computeAmountFloodCards()*/ 2; i++){
			getFloodDeck().discardCard((FloodCard) getFloodDeck().drawCard());
			numberCard++;
		}
		return numberCard == /*getWaterLevel().computeAmountFloodCards()*/ 2;
	}

	public WaterLevel getWaterLevel() {
		return waterLevel;
	}

	public FloodDeck getFloodDeck() {
		return floodDeck;
	}

	public Cell getCell(Location l){
        for(Cell aCell : cells){
            if (aCell.getLocation() == l){
                return aCell;
            }
        }
        return null;
	}

	public Cell getCellIfDry(Location l){
        for(Cell aCell : cells){
            if (aCell.getLocation() == l && aCell.getState()==CellState.DRY){
                return aCell;
            }
        }
        return null;
	}

	public Cell getCellIfWet(Location l){
        for(Cell aCell : cells){
            if (aCell.getLocation() == l && aCell.getState()==CellState.WET){
                return aCell;
            }
        }
        return null;
	}
}
