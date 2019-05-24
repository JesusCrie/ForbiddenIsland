package iut2.forbiddenisland.controller;

import iut2.forbiddenisland.controller.request.Request;
import iut2.forbiddenisland.controller.request.Response;

public interface Middleware {

    /**
     * Action to perform before the request is issued to the model.
     *
     * @param request - The request that will continue his way.
     */
    default Request handleRequest(final Request request) {
        return request;
    }

    /**
     * Action to perform before the response returns to the engine.
     * @param response - The request that will continue his way.
     */
    default <T> Response<T> handleResponse(final Response<T> response) {
        return response;
    }

}
