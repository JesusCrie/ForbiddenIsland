package iut2.forbiddenisland.controller.request;

import iut2.forbiddenisland.model.Board;

public class Response<T> {

    private final Request origin;
    private final Board board;
    private T data;
    private final boolean success;

    public static final Response EMPTY = new Response(null, null, true);

    public Response(final Request origin, final Board board, final boolean success) {
        this.origin = origin;
        this.board = board;
        this.success = success;
    }

    /**
     * The the request that triggered this response.
     *
     * @return The original request.
     */
    public Request getOriginRequest() {
        return origin;
    }

    /**
     * Get the board, can be used by the middleware to avoid sending useless
     * requests.
     *
     * @return The board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Retrieve the data returned with the response.
     *
     * @return The bundled data.
     */
    public T getData() {
        return this.data;
    }

    /**
     * Set the data to carry along the response.
     * Can only be called once, subsequent calls will
     * have no effects.
     *
     * @param d - The data to carry.
     */
    public Response<T> setData(final T d) {
        if (data != null)
            return this;
        this.data = d;

        return this;
    }

    /**
     * Check whether the request was successful or not.
     *
     * @return True if successful, otherwise false.
     */
    public boolean isOk() {
        return success;
    }

}
