package iut2.forbiddenisland.controller;

import iut2.forbiddenisland.controller.observer.NotifyOnCreateObservable;
import iut2.forbiddenisland.controller.observer.NotifyOnSubscribeObservable;
import iut2.forbiddenisland.controller.observer.Observable;
import iut2.forbiddenisland.controller.request.Request;
import iut2.forbiddenisland.controller.request.RequestType;
import iut2.forbiddenisland.controller.request.Response;
import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.Location;
import iut2.forbiddenisland.model.Treasure;
import iut2.forbiddenisland.model.WaterLevel;
import iut2.forbiddenisland.model.adventurer.Adventurer;
import iut2.forbiddenisland.model.card.*;
import iut2.forbiddenisland.model.cell.Cell;
import iut2.forbiddenisland.model.cell.TreasureCell;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GameEngine {

    private final PlayerManagement players;
    private final ModelProxy modelProxy;

    private final Observable<Map<Location, Cell>> cells;
    private final Observable<List<Adventurer>> adventurers;
    private final Observable<WaterLevel> waterLevel;
    private final Observable<List<Treasure>> treasures;
    private final Observable<Boolean> endGame;
    private final Observable<Integer> remainingActions;

    public GameEngine(final Board board, final List<Adventurer> players) {
        this.players = new PlayerManagement(players);

        modelProxy = new ModelProxy(board);

        cells = createCellsObs();
        adventurers = createAdventurersObs();
        waterLevel = createWaterLevelObs();
        treasures = createTreasureObs();
        endGame = createEndGameObs();

        remainingActions = new NotifyOnSubscribeObservable<>(0);

        newPlayerRound();
    }

    // *** Factory methods for complex observables ***

    /**
     * Create an observable that will query the list of cells
     * at each update.
     *
     * @return An observable that auto updates it self when it changes.
     */
    private Observable<Map<Location, Cell>> createCellsObs() {
        return new NotifyOnSubscribeObservable<Map<Location, Cell>>() {
            @Override
            public void notifyChanges() {
                // Query all cells
                final Response<Map<Location, Cell>> allCells = modelProxy.request(
                        new Request(RequestType.CELLS_ALL, getCurrentPlayer().get())
                );

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
        return new NotifyOnSubscribeObservable<>(players.players);
    }

    /**
     * Create an observable that will keep an updated version of the water level.
     *
     * @return An observable for the current water level.
     */
    private Observable<WaterLevel> createWaterLevelObs() {
        return new NotifyOnSubscribeObservable<WaterLevel>() {
            @Override
            public void notifyChanges() {
                // Query water level
                final Response<WaterLevel> waterLevel = modelProxy.request(
                        new Request(RequestType.ISLAND_WATER_LEVEL, getCurrentPlayer().get())
                );

                value = waterLevel.getData();

                super.notifyChanges();
            }
        };
    }

    /**
     * Create an observable that will keep an updated version of the treasures.
     *
     * @return An observable of the treasures.
     */
    private Observable<List<Treasure>> createTreasureObs() {
        return new NotifyOnSubscribeObservable<List<Treasure>>() {
            @Override
            public void notifyChanges() {
                // Query treasures
                final Response<List<Treasure>> treasures = modelProxy.request(
                        new Request(RequestType.TREASURES_ALL, getCurrentPlayer().get())
                );

                value = treasures.getData();

                super.notifyChanges();
            }
        };
    }

    /**
     * Create an observable that will keep the current win status of the game.
     * True for a win, False for a defeat, and null if the game is still running.
     *
     * @return An observable of the game state.
     */
    private Observable<Boolean> createEndGameObs() {
        return new NotifyOnCreateObservable<Boolean>() {
            @Override
            public void notifyChanges() {
                final Response<Boolean> res = modelProxy.request(
                        new Request(RequestType.GAME_CHECK_WIN, getCurrentPlayer().get())
                );

                value = res.getData();

                super.notifyChanges();
            }
        };
    }

    // *** Various getters used to expose some fields ***

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
    public Observable<Map<Location, Cell>> getCells() {
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
     * Expose the water level of the board.
     * Updated when the state of the water need a visual update.
     *
     * @return An observable of the water level of the board.
     */
    public Observable<WaterLevel> getWaterLevel() {
        return waterLevel;
    }

    /**
     * Expose the treasures of the board.
     * Updated when the state of the treasures need a visual update.
     *
     * @return An observable of each treasures of the board.
     */
    public Observable<List<Treasure>> getTreasures() {
        return treasures;
    }

    /**
     * Expose the status of the game
     *
     * @return An observable of the winning status of the game.
     */
    public Observable<Boolean> getEndGame() {
        return endGame;
    }

    // *** More getters that will trigger a request to the board ***

    /**
     * Get all cells reachable by the player from the board.
     * The player can move to any of them successfully.
     *
     * @return The list of cells where the player is able to move.
     */
    public List<Cell> getReachableCells() {
        final Response<List<Cell>> reachableCells = modelProxy.request(
                new Request(RequestType.CELLS_REACHABLE, getCurrentPlayer().get())
                        .putData(Request.DATA_CELL, getCurrentPlayer().get().getPosition())
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
                        .putData(Request.DATA_CELL, getCurrentPlayer().get().getPosition())
        );

        return dryableCells.getData();
    }

    /**
     * Get all adventurers whose you can send a card to.
     *
     * @return The list of players you can reach.
     */
    public List<Adventurer> getPlayersSendable() {
        final Response<List<Adventurer>> sendablePlayers = modelProxy.request(
                new Request(RequestType.PLAYERS_SENDABLE, getCurrentPlayer().get())
        );

        return sendablePlayers.getData();
    }

    // *** Player round related methods ***

    /**
     * Start a new player round by settings up the needed variables of the engine.
     */
    public void newPlayerRound() {
        players.next();

        // Notify new round
        modelProxy.request(new Request(RequestType.GAME_NEW_ROUND, getCurrentPlayer().get()));

        // Query AP
        final Response<Integer> res = modelProxy.request(
                new Request(RequestType.GAME_MOVE_AMOUNT, getCurrentPlayer().get())
        );
        remainingActions.set(res.getData());

        adventurers.notifyChanges();
    }

    /**
     * Try to move the desired player to the desired cell.
     *
     * @param player - The player to move.
     * @param cell   - The cell to move the player to.
     * @return True if the action was successful, otherwise false.
     */
    public boolean movePlayer(final Adventurer player, final Cell cell) {
        final Response<Integer> res = modelProxy.request(
                new Request(RequestType.PLAYER_MOVE, getCurrentPlayer().get())
                        .putData(Request.DATA_PLAYER, player)
                        .putData(Request.DATA_CELL, cell)
        );

        if (res.isOk()) {
            decrementActions(res.getData());

            cells.notifyChanges();
        }

        return res.isOk();
    }

    /**
     * Try to dry a cell.
     *
     * @param cell - The cell to dry
     */
    public boolean dryCell(final Cell cell) {
        final Response<Integer> res = modelProxy.request(
                new Request(RequestType.PLAYER_DRY, getCurrentPlayer().get())
                        .putData(Request.DATA_PLAYER, getCurrentPlayer().get())
                        .putData(Request.DATA_CELL, cell)
        );

        if (res.isOk()) {
            decrementActions(res.getData());
            cells.notifyChanges();
        }

        return res.isOk();
    }

    /**
     * Try to claim a treasure from a treasure cell.
     *
     * @param cell - The cell to claim.
     */
    public boolean claimTreasure(final TreasureCell cell) {
        final Response<Integer> res = modelProxy.request(
                new Request(RequestType.PLAYER_CLAIM, getCurrentPlayer().get())
                        .putData(Request.DATA_CELL, cell)
        );

        if (res.isOk()) {
            decrementActions(res.getData());
            adventurers.notifyChanges();
            treasures.notifyChanges();
            cells.notifyChanges();
        }

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
                        .putData(Request.DATA_PLAYER, getCurrentPlayer().get())
                        .putData(Request.DATA_PLAYER_EXTRA, to)
                        .putData(Request.DATA_CARD, card)
        );

        if (res.isOk()) {
            decrementActions(res.getData());
            adventurers.notifyChanges();
        }

        return res.isOk();
    }

    /**
     * Use a card.
     *
     * @param card - The card to use.
     */
    public boolean useCard(final SpecialCard card) {
        final Response<Void> res = modelProxy.request(
                new Request(RequestType.CARD_USE, getCurrentPlayer().get())
                        .putData(Request.DATA_PLAYER, getCurrentPlayer().get())
                        .putData(Request.DATA_CARD, card)
        );

        if (res.isOk()) {
            adventurers.notifyChanges();
            cells.notifyChanges();
        }

        return res.isOk();
    }

    /**
     * End the 'manual' part of the current player's round.
     */
    public void endPlayerRound() {
        // Draw treasure cards
        final int drawAmount = modelProxy.<Integer>request(
                new Request(RequestType.CARD_DRAW_AMOUNT, getCurrentPlayer().get())
        ).getData();

        for (int i = 0; i < drawAmount; i++) {
            final Response<TreasureCard> res = modelProxy.request(
                    new Request(RequestType.CARD_DRAW, getCurrentPlayer().get())
                            .putData(Request.DATA_PLAYER, getCurrentPlayer().get())
                            .putData(Request.DATA_AMOUNT, 1) // Only one because the rising water card need to be applied immediately
            );

            if (res.getData() instanceof RisingWatersCard) {
                modelProxy.request(new Request(RequestType.ISLAND_WATER_UP, getCurrentPlayer().get()));
                waterLevel.notifyChanges();
                startIslandTurn();
            }
        }

        cells.notifyChanges();
        adventurers.notifyChanges();
    }

    private void decrementActions(final int amount) {
        remainingActions.set(remainingActions.get() - amount);
    }

    // *** Island turn related methods ***

    public void startIslandTurn() {
        // Draw flood card
        final int drawAmount = modelProxy.<WaterLevel>request(
                new Request(RequestType.ISLAND_WATER_LEVEL, null)
        ).getData().computeAmountFloodCards();

        for (int i = 0; i < drawAmount; i++) {
            // Draw a card
            final Response<FloodCard> floodCard = modelProxy.request(new Request(RequestType.ISLAND_DRAW, null));

            // Use the card
            modelProxy.request(new Request(RequestType.ISLAND_APPLY, null).putData(Request.DATA_CARD, floodCard.getData()));
        }

        cells.notifyChanges();
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
