package iut2.forbiddenisland.controller;

import iut2.forbiddenisland.controller.observer.Observable;
import iut2.forbiddenisland.model.Adventurer;
import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.Cell;

import java.util.List;

public class Controller {

	private final GameEngine engine;

	public Controller(final Board board, final Adventurer... adventurers) {
		engine = new GameEngine(board, adventurers);
	}

	public Observable<List<Cell>> getCells() {
		return engine.getCells();
	}

	public Observable<Integer> getRemainingActions() {
		return engine.getRemainingActions();
	}

	public Observable getAdventurers() {
		// TODO
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param o
	 */
	public void observeModeMove(Observable o) {
		// TODO - implement iut2.forbiddenisland.controller.Controller.observeModeMove
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param o
	 */
	public void observeModeDry(Observable o) {
		// TODO - implement iut2.forbiddenisland.controller.Controller.observeModeDry
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param o
	 */
	public void observeModeTreasureClaim(Observable o) {
		// TODO - implement iut2.forbiddenisland.controller.Controller.observeModeTreasureClaim
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param o
	 */
	public void observeModeSend(Observable o) {
		// TODO - implement iut2.forbiddenisland.controller.Controller.observeModeSend
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param o
	 */
	public void observeClickCell(Observable o) {
		// TODO - implement iut2.forbiddenisland.controller.Controller.observeClickCell
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param o
	 */
	public void observeClickPlayer(Observable o) {
		// TODO - implement iut2.forbiddenisland.controller.Controller.observeClickPlayer
		throw new UnsupportedOperationException();
	}

}
