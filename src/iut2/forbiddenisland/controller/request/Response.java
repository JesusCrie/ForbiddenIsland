package iut2.forbiddenisland.controller.request;

public class Response<T> {

    private final Request origin;
    private T data;
    private final boolean success;

    public static final Response EMPTY = new Response(null, true);

    public Response(final Request origin, final boolean success) {
        this.origin = origin;
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
     * Retrieve the data returned with the response.
     *
     * @return The bundled data.
     */
    public T getData() {
        return this.data;
    }

    /**
     * Set the data to carry along the response.
     *
     * @param d - The data to carry.
     */
    public Response<T> setData(final T d) {
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
