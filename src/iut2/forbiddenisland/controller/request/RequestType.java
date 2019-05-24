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
     * = List&lt;Cell&gt;
     */
    CELLS_CLAIMABLE,

    // *** Card related requests ***

    /**
     * Used to draw a card from the treasure deck and place it in the given
     * player's hand.
     * - DATA_PLAYER: Adventurer
     * - DATA_AMOUNT: int
     * = boolean
     */
    CARD_DRAW,

    // *** Player related requests ***
    PLAYERS_ALL,
    PLAYER_MOVE_AMOUNT,
    PLAYERS_SENDABLE,
    PLAYER_MOVE,
    PLAYER_SEND,
    PLAYER_USE_CARD,
    PLAYER_DRAW_CARD,


    CELL_CLAIM_TREASURE,

    // Game related requests,
    FLOODING
}
