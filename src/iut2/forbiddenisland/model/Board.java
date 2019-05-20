package iut2.forbiddenisland.model;

import iut2.forbiddenisland.controller.Request;
import iut2.forbiddenisland.controller.Response;

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
	public <T> Response<T> handleRequest(Request r) {
		// TODO - implement iut2.forbiddenisland.model.Board.handleRequest
		throw new UnsupportedOperationException();
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
		ArrayList<Cell> dryableCell = new ArrayList<>();
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
		int numberCard = 1;
		for (int i = 1; i <= getWaterLevel().computeAmountFloodCards()){
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
}
