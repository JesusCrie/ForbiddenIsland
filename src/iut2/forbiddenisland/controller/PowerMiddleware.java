package iut2.forbiddenisland.controller;

/**
 * A middleware that will alter the requests and responses using
 * the powers of the current player.
 */
public class PowerMiddleware implements Middleware {

    @Override
    public Request handleRequest(final Request request) {
        request.getCurrentPlayer().getPowers().forEach(
                power -> power.alterRequest(request)
        );

        return request;
    }

    @Override
    public <T> Response<T> handleResponse(final Response<T> response) {
        response.getOriginRequest().getCurrentPlayer().getPowers().forEach(
                power -> power.alterResponse(response)
        );

        return response;
    }
}
