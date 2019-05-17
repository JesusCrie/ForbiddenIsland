package iut2.forbiddenisland.controller;

import iut2.forbiddenisland.controller.observer.NotifyOnSubscribeObservable;
import iut2.forbiddenisland.controller.observer.Observable;
import iut2.forbiddenisland.model.Adventurer;
import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.Cell;
import iut2.forbiddenisland.model.TreasureCell;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class GameEngine {

    private final PlayerManagement players;
    private final ModelProxy modelProxy;
    private final Observable<GameMode> mode;

    public GameEngine(final Board board, final Adventurer... players) {
        this.players = new PlayerManagement(Arrays.asList(players));

        modelProxy = new ModelProxy(board);
        mode = new NotifyOnSubscribeObservable<>(GameMode.IDLE);
    }

    /**
     * Expose the current player.
     *
     * @return The current player's observable.
     */
    public Observable<Adventurer> getCurrentPlayer() {
        return players.current;
    }

    /**
     * Get all cells from board.
     *
     * @return A list of every cells currently on the board.
     */
    public List<Cell> getCells() {
        // Query all cells
        final Response<List<Cell>> allCells = modelProxy.request(
                new Request(RequestType.CELLS_ALL, getCurrentPlayer().get())
        );

        // Should always be successful
        return allCells.getData();
    }

    /**
     * Get all cells reachable by the player from the board.
     * The player can move to any of them successfully.
     *
     * @return The list of cells where the player is able to move.
     */
    public List<Cell> getReachableCells() {
        final Response<List<Cell>> reachableCells = modelProxy.request(
                new Request(RequestType.CELLS_REACHABLE, getCurrentPlayer().get())
        );

        return reachableCells.getData();
    }

    /**
     * Get all cells dryable by the player from the board.
     *
     * @return The list of dryable cells.
     */
    public List<Cell> getDryableCells() {
        final Response<List<Cell>> dryableCells = modelProxy.request(
                new Request(RequestType.CELLS_DRYABLE, getCurrentPlayer().get())
        );

        return dryableCells.getData();
    }

    /**
     * Try to move the desired player to the desired cell.
     *
     * @param cell   - The cell to move the player to.
     * @param player - The player to move.
     * @return True if the action was successful, otherwise false.
     */
    public boolean movePlayer(final Cell cell, final Adventurer player) {
        final Response<Void> res = modelProxy.request(
                new Request(RequestType.PLAYER_MOVE, getCurrentPlayer().get())
                        .putData(Request.DATA_CELL, cell)
                        .putData(Request.DATA_PLAYER, player)
        );

        return res.isOk();
    }

    /**
     * Try to dry a cell.
     *
     * @param cell - The cell to dry
     */
    public boolean dryCell(final Cell cell) {
        final Response<Void> res = modelProxy.request(
                new Request(RequestType.CELL_DRY, getCurrentPlayer().get())
                        .putData(Request.DATA_CELL, cell)
        );

        return res.isOk();
    }

    /**
     * Try to claim a treasure from a treasure cell.
     *
     * @param cell - The cell to claim.
     */
    public boolean claimTreasure(final TreasureCell cell) {
        final Response<Void> res = modelProxy.request(
                new Request(RequestType.CELL_CLAIM_TREASURE, getCurrentPlayer().get())
                        .putData(Request.DATA_CELL, cell)
        );

        return res.isOk();
    }

    /**
     * End the round and start another one.
     */
    public void newRound() {
        players.next();
        mode.set(GameMode.IDLE);
    }

    public void enableModeMove() {
        mode.set(GameMode.MOVE);
    }

    public void enableModeDry() {
        mode.set(GameMode.DRY);
    }

    public void enableModeTreasureClaim() {
        mode.set(GameMode.TREASURE);
    }

    public void enableModeSend() {
        mode.set(GameMode.SEND);
    }

    /**
     * Manage the players of this game.
     * Manage who's gonna play next.
     */
    private class PlayerManagement {

        private List<Adventurer> players;
        private Observable<Adventurer> current;
        private Iterator<Adventurer> playersIterator;

        public PlayerManagement(final List<Adventurer> players) {
            this.players = players;
            current = new NotifyOnSubscribeObservable<>(null);
            reset();
        }

        public void reset() {
            playersIterator = players.iterator();
        }

        public void next() {
            if (!playersIterator.hasNext()) reset();
            current.set(playersIterator.next());
        }
    }

}
