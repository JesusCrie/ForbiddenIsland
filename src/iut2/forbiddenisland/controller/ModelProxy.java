package iut2.forbiddenisland.controller;

import iut2.forbiddenisland.controller.request.Request;
import iut2.forbiddenisland.controller.request.Response;
import iut2.forbiddenisland.model.Board;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class ModelProxy {

    private final List<Middleware> middlewares;
    private final Board board;

    public ModelProxy(final Board board) {
        this.board = board;

        // Configure middleware
        middlewares = Arrays.asList(
                new PowerMiddleware(board)
        );
    }

    /**
     * Issue a request to the model.
     * The request and response will first travel through every registered
     * middleware.
     *
     * @param request - The request to issue.
     */
    public <T> Response<T> request(Request request) {
        final ListIterator<Middleware> iterator = middlewares.listIterator();

        // Pass through every middleware in the order
        // they were registered
        while (iterator.hasNext()) {
            request = iterator.next().handleRequest(request);
        }

        // Call the board
        Response<T> response = board.handleRequest(request);

        // Pass through every middleware in the reverse order
        while (iterator.hasPrevious()) {
            response = iterator.previous().handleResponse(response);
        }

        return response;
    }

}
