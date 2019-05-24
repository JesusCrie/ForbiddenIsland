package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.controller.request.Request;
import iut2.forbiddenisland.controller.request.Response;

public interface Power {

    /**
     * Apply the power to the incoming request.
     * Can alter the request by adding/removing/modifying
     * his data.
     *
     * @param req - The incoming request.
     */
    default void alterRequest(final Request req) {
    }

    /**
     * Aplly the power to the outgoing response.
     * Can alter the response by adding/removing/modifying
     * his data.
     *
     * @param res - The outgoing response.
     */
    default void alterResponse(final Response res) {
    }

}
