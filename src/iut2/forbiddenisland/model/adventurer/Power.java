package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.controller.request.Request;
import iut2.forbiddenisland.controller.request.Response;
import iut2.forbiddenisland.model.Board;

public interface Power {

    /**
     * Apply the power to the incoming request.
     * Can alter the request by adding/removing/modifying
     * his data.
     *
     * @param req   - The incoming request.
     * @param board - The board
     */
    default void alterRequest(final Request req, final Board board) {
    }

    /**
     * Apply the power to the outgoing response.
     * Can alter the response by adding/removing/modifying
     * his data.
     *
     * @param res   - The outgoing response.
     * @param board - The board.
     */
    default void alterResponse(final Response res, final Board board) {
    }

}
