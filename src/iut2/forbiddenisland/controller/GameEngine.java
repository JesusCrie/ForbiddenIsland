package iut2.forbiddenisland.controller;

import iut2.forbiddenisland.controller.observer.NotifyOnSubscribeObservable;
import iut2.forbiddenisland.controller.observer.Observable;
import iut2.forbiddenisland.controller.request.Request;
import iut2.forbiddenisland.controller.request.RequestType;
import iut2.forbiddenisland.controller.request.Response;
import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.adventurer.Adventurer;
import iut2.forbiddenisland.model.card.Card;
import iut2.forbiddenisland.model.cell.Cell;
import iut2.forbiddenisland.model.cell.TreasureCell;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class GameEngine {

    private final PlayerManagement players;
    private final ModelProxy modelProxy;

    private final Observable<List<Cell>> cells;
    private final Observable<List<Adventurer>> adventurers;
    private final Observable<Integer> remainingActions;

    public GameEngine(final Board board, final Adventurer... players) {
        this.players = new PlayerManagement(Arrays.asList(players));

        modelProxy = new ModelProxy(board);

        cells = createCellsObs();
        adventurers = createAdventurersObs();

        remainingActions = new NotifyOnSubscribeObservable<>(0);
    }

    /**
     * Create an observable that will query the list of cells
     * at each update.
     *
     * @return An observable that auto updates it self when it changes.
     */
    private Observable<List<Cell>> createCellsObs() {
        return new Observable<>(null) {
            @Override
            public void notifyChanges() {
                // Query all cells
                final Response<List<Cell>> allCells = modelProxy.request(
                        new Request(RequestType.CELLS_ALL, getCurrentPlayer().get())
                );

                // Should always be successful
                value = allCells.getData();

                super.notifyChanges();
            }
        };
    }

    /**
     * Create an observable that will be notify each time an adventurer changes.
     *
     * @return An observable that will be notified when an adventurer changes.
     */
    private Observable<List<Adventurer>> createAdventurersObs() {
        return new Observable<>(players.players);
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
     * Expose the updated number of actions left for the current player
     * this turn.
     *
     * @return The amount of actions left updated via an observable.
     */
    public Observable<Integer> getRemainingActions() {
        return remainingActions;
    }

    /**
     * Expose the cells of the board.
     * Updated when the state of a cell need a visual update.
     *
     * @return An observable of every cells currently on the board.
     */
    public Observable<List<Cell>> getCells() {
        return cells;
    }

    /**
     * Expose the adventurers of the board.
     * Updated when the state of an adventurer need a visual update.
     *
     * @return An observable of every adventurer of the board.
     */
    public Observable<List<Adventurer>> getAdventurers() {
        return adventurers;
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
                new Request(RequestType.CELLS_DRAINABLE, getCurrentPlayer().get())
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
        final Response<Integer> res = modelProxy.request(
                new Request(RequestType.PLAYER_MOVE, getCurrentPlayer().get())
                        .putData(Request.DATA_REMAINING_ACTIONS, remainingActions.get())
                        .putData(Request.DATA_CELL, cell)
                        .putData(Request.DATA_PLAYER, player)
        );

        decrementActions(res.getData());
        cells.notifyChanges();
        return res.isOk();
    }

    /**
     * Try to dry a cell.
     *
     * @param cell - The cell to dry
     */
    public boolean dryCell(final Cell cell) {
        final Response<Integer> res = modelProxy.request(
                new Request(RequestType.CELL_DRY, getCurrentPlayer().get())
                        .putData(Request.DATA_REMAINING_ACTIONS, remainingActions.get())
                        .putData(Request.DATA_CELL, cell)
        );

        cells.notifyChanges();
        return res.isOk();
    }

    /**
     * Try to claim a treasure from a treasure cell.
     *
     * @param cell - The cell to claim.
     */
    public boolean claimTreasure(final TreasureCell cell) {
        final Response<Integer> res = modelProxy.request(
                new Request(RequestType.CELL_CLAIM_TREASURE, getCurrentPlayer().get())
                        .putData(Request.DATA_REMAINING_ACTIONS, remainingActions.get())
                        .putData(Request.DATA_CELL, cell)
        );

        decrementActions(res.getData());
        adventurers.notifyChanges();
        cells.notifyChanges();
        return res.isOk();
    }

    /**
     * Try to send a card to another adventurer.
     *
     * @param to   - The target adventurer.
     * @param card - The card to send.
     */
    public boolean sendCard(final Adventurer to, final Card card) {
        final Response<Integer> res = modelProxy.request(
                new Request(RequestType.PLAYER_SEND, getCurrentPlayer().get())
                        .putData(Request.DATA_REMAINING_ACTIONS, remainingActions.get())
                        .putData(Request.DATA_PLAYER, to)
                        .putData(Request.DATA_CARD, card)
        );

        decrementActions(res.getData());
        adventurers.notifyChanges();
        return res.isOk();
    }

    /**
     * Use a card.
     *
     * @param card - The card to use.
     */
    public boolean useCard(final Card card) {
        final Response<Void> res = modelProxy.request(
                new Request(RequestType.PLAYER_USE_CARD, getCurrentPlayer().get())
                        .putData(Request.DATA_REMAINING_ACTIONS, remainingActions.get())
                        .putData(Request.DATA_CARD, card)
        );

        adventurers.notifyChanges();
        cells.notifyChanges();
        return res.isOk();
    }

    /**
     * End the round and start another one.
     */
    public void newRound() {
        players.next();

        // Query AP
        final Response<Integer> res = modelProxy.request(
                new Request(RequestType.PLAYER_MOVE_AMOUNT, getCurrentPlayer().get())
        );
        remainingActions.set(res.getData());

        adventurers.notifyChanges();
    }

    public void finishRound() {
        // Draw treasure cards
        final Response<Void> resDraw = modelProxy.request(
                new Request(RequestType.PLAYER_DRAW_CARD, getCurrentPlayer().get())
        );

        if (!resDraw.isOk())
            throw new IllegalStateException("Failed to draw cards !");

        // Island turn
        final Response<Void> resFlood = modelProxy.request(
                new Request(RequestType.FLOODING, getCurrentPlayer().get())
        );

        if (!resDraw.isOk())
            throw new IllegalStateException("Can't flood the board !");

        // TODO check victory somewhere here

        cells.notifyChanges();
        players.notify();
    }

    private void decrementActions(final int amount) {
        remainingActions.set(remainingActions.get() - amount);
    }

    /**
     * Manage the players of this game.
     * Manage who's gonna play next.
     */
    private class PlayerManagement {

        private final List<Adventurer> players;
        private final Observable<Adventurer> current;
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
