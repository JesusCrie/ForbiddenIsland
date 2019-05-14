package iut2.forbiddenisland.controller;

import iut2.forbiddenisland.controller.observer.Observable;
import iut2.forbiddenisland.model.Adventurer;
import iut2.forbiddenisland.model.Cell;
import iut2.forbiddenisland.model.TreasureCell;

import java.util.List;

public class GameEngine {

	Observable currentPlayer;
	ModelProxy modelProxy;
	GameMode mode;

	/**
	 * 
	 * @param proxy
	 */
	public GameEngine(ModelProxy proxy) {
		// TODO - implement iut2.forbiddenisland.controller.GameEngine.iut2.forbiddenisland.controller.GameEngine
		throw new UnsupportedOperationException();
	}

	public List<Cell> getCells() {
		// TODO - implement iut2.forbiddenisland.controller.GameEngine.getCells
		throw new UnsupportedOperationException();
	}

	public void newRound() {
		// TODO - implement iut2.forbiddenisland.controller.GameEngine.newRound
		throw new UnsupportedOperationException();
	}

	public void enableModeMove() {
		// TODO - implement iut2.forbiddenisland.controller.GameEngine.enableModeMove
		throw new UnsupportedOperationException();
	}

	public void enableModeDry() {
		// TODO - implement iut2.forbiddenisland.controller.GameEngine.enableModeDry
		throw new UnsupportedOperationException();
	}

	public void enableModeTreasureClaim() {
		// TODO - implement iut2.forbiddenisland.controller.GameEngine.enableModeTreasureClaim
		throw new UnsupportedOperationException();
	}

	public void enableModeSend() {
		// TODO - implement iut2.forbiddenisland.controller.GameEngine.enableModeSend
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param cell
	 * @param player
	 */
	public Response movePlayer(Cell cell, Adventurer player) {
		// TODO - implement iut2.forbiddenisland.controller.GameEngine.movePlayer
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param cell
	 */
	public Response dryCell(Cell cell) {
		// TODO - implement iut2.forbiddenisland.controller.GameEngine.dryCell
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param c
	 */
	public Response claimTreasure(TreasureCell c) {
		// TODO - implement iut2.forbiddenisland.controller.GameEngine.claimTreasure
		throw new UnsupportedOperationException();
	}

}
