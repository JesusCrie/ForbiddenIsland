package iut2.forbiddenisland.model;

import iut2.forbiddenisland.controller.Request;
import iut2.forbiddenisland.controller.RequestType;
import iut2.forbiddenisland.controller.Response;

import java.time.format.ResolverStyle;
import java.util.*;
import java.util.stream.Collectors;

public class Board {

	private final Map<Location, Cell> cells;
	WaterLevel waterLevel;
	private FloodDeck floodDeck;
	private TreasureDeck treasureDeck;

	public Board(Map<Location, Cell> cells) {
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
				return (Response<T>) new Response<Map<Location, Cell>>(r, true).setData(cells);
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
	    List<Cell> reachable = new ArrayList<>();
	    Location reach = new Location(c.getLocation().getX()+1, c.getLocation().getY());
		reachable.add(cells.get(reach));

        reach = new Location(c.getLocation().getX()-1, c.getLocation().getY());
        reachable.add(cells.get(reach));

        reach = new Location(c.getLocation().getX(), c.getLocation().getY()+1);
        reachable.add(cells.get(reach));

        reach = new Location(c.getLocation().getX(), c.getLocation().getY()-1);
        reachable.add(cells.get(reach));

        return reachable;
	}

	/**
	 * 
	 * @param p
	 */
	public List<Cell> getCellsDryable(Adventurer p) {
        List<Cell> dryable = new ArrayList<>();
        Location reach = new Location(p.getPosition().getLocation().getX(), p.getPosition().getLocation().getY());
        if (cells.get(reach).getState() == CellState.WET){
        	dryable.add(cells.get(reach));
		}

		reach = new Location(p.getPosition().getLocation().getX()+1, p.getPosition().getLocation().getY());
		if (cells.get(reach).getState() == CellState.WET){
			dryable.add(cells.get(reach));
		}

		reach = new Location(p.getPosition().getLocation().getX()-1, p.getPosition().getLocation().getY());
		if (cells.get(reach).getState() == CellState.WET){
			dryable.add(cells.get(reach));
		}

		reach = new Location(p.getPosition().getLocation().getX(), p.getPosition().getLocation().getY()+1);
		if (cells.get(reach).getState() == CellState.WET){
			dryable.add(cells.get(reach));
		}

		reach = new Location(p.getPosition().getLocation().getX(), p.getPosition().getLocation().getY()-1);
		if (cells.get(reach).getState() == CellState.WET){
			dryable.add(cells.get(reach));
		}
	 	return dryable;
	}

	/**
	 * 
	 * @param p
	 */
	public List<Adventurer> getPlayersSendable(Adventurer p) {
		ArrayList<Adventurer> sendablePlayer = new ArrayList<>();

		Location reach = new Location(p.getPosition().getLocation().getX(), p.getPosition().getLocation().getY());
		for (Adventurer anAdventurer : cells.get(reach).getAdventurers()){
			if(anAdventurer != p && !sendablePlayer.contains(anAdventurer)){
				sendablePlayer.add(anAdventurer);
			}
		}

		return null;
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
		// TODO IL EST PAS COMPLET PUTAIN IL EST FAUX WESH
	}

	/**
	 * 
	 * @param p
	 * @param c
	 */
	public boolean movePlayer(Adventurer p, Cell c) {
		if (getReachableCells(p, p.getPosition()).contains(c)) {
			Cell oldCell = p.getPosition();
			p.getPosition().removeAdventurer(p);
			p.move(c);
			c.getAdventurers().add(p);
			Cell newCell = p.getPosition();
			return oldCell != newCell;
		}
		return false;
	}

	/**
	 * 
	 * @param p
	 * @param c
	 */
	public boolean dryCell(Adventurer p, Cell c) {
		if(getReachableCells(p, p.getPosition()).contains(c)) {
			if (c.getState() == CellState.WET) {
				c.setState(CellState.DRY);
				return c.getState() == CellState.DRY;
			}
		}
		return false;
	}

	/**
	 *
	 * @param from
	 * @param to
     * @param c
	 */
	public boolean sendCard(Adventurer from, Adventurer to, Card c) {
		if (getReachableCells(from, from.getPosition()).contains(to.getPosition())){
			if (from.getCards().contains(c)){
				from.removeCard(c);
				to.addCard(c);
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param p
	 * @param c
	 */
	public boolean claimTreasure(Adventurer p, TreasureCell c) {
		if (getReachableCells(p, p.getPosition()).contains(c)) {
			if (c.getTreasure().isClaimable()) {
				p.addTrasure(c.getTreasure());
				return true;
			}
		}
		return false;
	}

	public boolean flood() {
		//TODO JE CROIS CEST DE LA MERDE
		int numberCard = 1;
		for (int i = 1; i <= getWaterLevel().computeAmountFloodCards(); i++){
			getFloodDeck().discardCard((FloodCard) getFloodDeck().drawCard());
			numberCard++;
		}
		return numberCard == getWaterLevel().computeAmountFloodCards();
	}

	public WaterLevel getWaterLevel() {
		return waterLevel;
	}

	public FloodDeck getFloodDeck() {
		return floodDeck;
	}

	public Cell getCell(Location l){
        return cells.get(l);
	}

	public Cell getCellIfDry(Location l){
        if (cells.get(l).getState() == CellState.DRY){
            return cells.get(l);
        } else return null;
	}

	public Cell getCellIfWet(Location l){
        if (cells.get(l).getState() == CellState.WET){
            return cells.get(l);
        } else return null;
	}
}
