public class GameEngine {

	Observable currentPlayer;
	ModelProxy modelProxy;
	GameMode mode;

	/**
	 * 
	 * @param proxy
	 */
	public GameEngine(ModelProxy proxy) {
		// TODO - implement GameEngine.GameEngine
		throw new UnsupportedOperationException();
	}

	public Collection getCells() {
		// TODO - implement GameEngine.getCells
		throw new UnsupportedOperationException();
	}

	public void newRound() {
		// TODO - implement GameEngine.newRound
		throw new UnsupportedOperationException();
	}

	public void enableModeMove() {
		// TODO - implement GameEngine.enableModeMove
		throw new UnsupportedOperationException();
	}

	public void enableModeDry() {
		// TODO - implement GameEngine.enableModeDry
		throw new UnsupportedOperationException();
	}

	public void enableModeTreasureClaim() {
		// TODO - implement GameEngine.enableModeTreasureClaim
		throw new UnsupportedOperationException();
	}

	public void enableModeSend() {
		// TODO - implement GameEngine.enableModeSend
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param cell
	 * @param player
	 */
	public Response movePlayer(Cell cell, Adventurer player) {
		// TODO - implement GameEngine.movePlayer
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param cell
	 */
	public Response dryCell(Cell cell) {
		// TODO - implement GameEngine.dryCell
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param c
	 */
	public Response claimTreasure(TreasureCell c) {
		// TODO - implement GameEngine.claimTreasure
		throw new UnsupportedOperationException();
	}

}