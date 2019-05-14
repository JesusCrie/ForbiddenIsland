import java.util.*;

public class Board {

	Collection<Cell> cells;
	WaterLevel waterLevel;

	/**
	 * 
	 * @param r
	 */
	public Object handleRequest(Request r) {
		// TODO - implement Board.handleRequest
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param p
	 */
	public int getPlayerMoveAmount(Adventurer p) {
		// TODO - implement Board.getPlayerMoveAmount
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param p
	 * @param c
	 */
	public List<Cell> getReachableCells(Adventurer p, Cell c) {
		// TODO - implement Board.getReachableCells
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param p
	 */
	public List<Cell> getCellsDryable(Adventurer p) {
		// TODO - implement Board.getCellsDryable
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param p
	 */
	public List<Adventurer> getPlayersSendable(Adventurer p) {
		// TODO - implement Board.getPlayersSendable
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param p
	 */
	public List<TreasureCell> getTreasuresClaimable(Adventurer p) {
		// TODO - implement Board.getTreasuresClaimable
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param p
	 * @param c
	 */
	public boolean movePlayer(Adventurer p, Cell c) {
		// TODO - implement Board.movePlayer
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param p
	 * @param c
	 */
	public boolean dryCell(Adventurer p, Cell c) {
		// TODO - implement Board.dryCell
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param p
	 * @param to
	 */
	public boolean sendCard(Adventurer p, Adventurer to) {
		// TODO - implement Board.sendCard
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param p
	 * @param c
	 */
	public boolean claimTreasure(Adventurer p, TreasureCell c) {
		// TODO - implement Board.claimTreasure
		throw new UnsupportedOperationException();
	}

	public boolean flood() {
		// TODO - implement Board.flood
		throw new UnsupportedOperationException();
	}

}