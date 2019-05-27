package iut2.forbiddenisland.controller;

import iut2.forbiddenisland.controller.request.Request;
import iut2.forbiddenisland.controller.request.Response;
import iut2.forbiddenisland.model.Board;

/**
 * A middleware that will alter the requests and responses using
 * the powers of the current player.
 */
public class PowerMiddleware implements Middleware {

    private final Board board;

    public PowerMiddleware(final Board board) {
        this.board = board;
    }

    @Override
    public Request handleRequest(final Request request) {
        request.getCurrentPlayer().getPowers().forEach(
                power -> power.alterRequest(request, board)
        );

        return request;
    }

    @Override
    public <T> Response<T> handleResponse(final Response<T> response) {
        response.getOriginRequest().getCurrentPlayer().getPowers().forEach(
                power -> power.alterResponse(response, board)
        );

        return response;
    }
}
