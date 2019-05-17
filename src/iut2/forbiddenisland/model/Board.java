package iut2.forbiddenisland.model;

import iut2.forbiddenisland.controller.Request;
import iut2.forbiddenisland.controller.Response;

import java.util.*;

public class Board {

	Collection<Cell> cells;
	WaterLevel waterLevel;

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
		// TODO - implement iut2.forbiddenisland.model.Board.getPlayerMoveAmount
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param p
	 * @param c
	 */
	public List<Cell> getReachableCells(Adventurer p, Cell c) {
		// TODO - implement iut2.forbiddenisland.model.Board.getReachableCells
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param p
	 */
	public List<Cell> getCellsDryable(Adventurer p) {
		// TODO - implement iut2.forbiddenisland.model.Board.getCellsDryable
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param p
	 */
	public List<Adventurer> getPlayersSendable(Adventurer p) {
		// TODO - implement iut2.forbiddenisland.model.Board.getPlayersSendable
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param p
	 */
	public List<TreasureCell> getTreasuresClaimable(Adventurer p) {
		// TODO - implement iut2.forbiddenisland.model.Board.getTreasuresClaimable
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param p
	 * @param c
	 */
	public boolean movePlayer(Adventurer p, Cell c) {
		// TODO - implement iut2.forbiddenisland.model.Board.movePlayer
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param p
	 * @param c
	 */
	public boolean dryCell(Adventurer p, Cell c) {
		// TODO - implement iut2.forbiddenisland.model.Board.dryCell
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param p
	 * @param to
	 */
	public boolean sendCard(Adventurer p, Adventurer to) {
		// TODO - implement iut2.forbiddenisland.model.Board.sendCard
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param p
	 * @param c
	 */
	public boolean claimTreasure(Adventurer p, TreasureCell c) {
		// TODO - implement iut2.forbiddenisland.model.Board.claimTreasure
		throw new UnsupportedOperationException();
	}

	public boolean flood() {
		// TODO - implement iut2.forbiddenisland.model.Board.flood
		throw new UnsupportedOperationException();
	}

}
