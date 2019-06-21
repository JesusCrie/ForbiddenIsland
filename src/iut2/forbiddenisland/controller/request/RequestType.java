package iut2.forbiddenisland.controller.request;

/**
 * Every request possible.
 *
 * In the documentation of each value you can find a list of the datas that
 * are carried with the associated request in the form:
 * - KEY: Type
 * = Response Type
 */
public enum RequestType {

    // *** Cells related requests ***

    /**
     * Used to query every cells on the board.
     * = Map&lt;Location, Cell&gt;
     */
    CELLS_ALL,

    /**
     * Used to query the reachable cells of another cell (aka the cells on top, left, down, right).
     * - DATA_CELL: Cell
     * = List&lt;Cell&gt;
     */
    CELLS_REACHABLE,

    /**
     * Used to query the cells drainable from another cell.
     * - DATA_CELL: Cell
     * = List&lt;Cell&gt;
     *
     * @see #CELLS_REACHABLE
     */
    CELLS_DRAINABLE,

    /**
     * Used to query the cells where the current player can claim a treasure.
     * - DATA_PLAYER: Adventurer
     * = List&lt;TreasureCell&gt;
     */
    CELLS_CLAIMABLE,

    // *** Card related requests ***

    /**
     * Used to query the amount of cards the player should draw this round.
     * = int
     */
    CARD_DRAW_AMOUNT,

    /**
     * Used to draw a card from the treasure deck and place it in the given
     * player's hand.
     * - DATA_PLAYER: Adventurer
     * = TreasureCard
     */
    CARD_DRAW,

    /**
     * Used to put the card out of the adventurer's deck and put it in the discarding deck.
     * - DATA_PLAYER: Adventurer
     * - DATA_CARD: TreasureCard
     * = void
     */
    CARD_TRASH,

    // *** Player related requests ***,

    /**
     * Used to move a player to another cell.
     * - DATA_PLAYER: Adventurer
     * - DATA_CELL: Cell
     * = int
     */
    PLAYER_MOVE,

    /**
     * Used to allow the current player to dry a cell.
     * - DATA_CELL: Cell
     * = int
     */
    PLAYER_DRY,

    /**
     * Used to allow the current player to claim the treasure
     * associated to a treasure cell.
     * - DATA_CELL: TreasureCell
     * = int
     */
    PLAYER_CLAIM,

    /**
     * Used to allow a player to send a card to another player.
     * - DATA_PLAYER: Adventurer
     * - DATA_PLAYER_EXTRA: Adventurer
     * - DATA_CARD: TreasureCard
     * = int
     */
    PLAYER_SEND,

    /**
     * Used to query the players in range of the current player
     * to receive a card.
     * = List&lt;Adventurer&gt;
     */
    PLAYERS_SENDABLE,

    // *** Island related requests ***

    /**
     * Used to query the treasures of the board.
     * = List&lt;Treasure&gt;
     */
    TREASURES_ALL,

    /**
     * Used to make the island draw a flood card.
     * = FloodCard
     */
    ISLAND_DRAW,

    /**
     * Used to apply a flood card onto the board.
     * - DATA_CARD: FloodCard
     * = boolean
     */
    ISLAND_APPLY,

    /**
     * Used to query the current water level of the island.
     * = WaterLevel
     */
    ISLAND_WATER_LEVEL,

    /**
     * Used to increase the water level of the island and reset the flood deck.
     * - DATA_AMOUNT: int
     * = boolean
     */
    ISLAND_WATER_UP,

    // *** Game related requests ***

    /**
     * Used to notify that a new round has started (and thus the previous one has ended).
     * = void
     */
    GAME_NEW_ROUND,

    /**
     * Used to get the amount of movements allowed per player in one round.
     * = int
     */
    GAME_MOVE_AMOUNT,

    /**
     * Used to check whether the game is over or not.
     * = boolean
     */
    GAME_CHECK_WIN
}
