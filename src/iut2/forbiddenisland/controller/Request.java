package iut2.forbiddenisland.controller;

import iut2.forbiddenisland.model.Adventurer;

import java.util.HashMap;
import java.util.Map;

public class Request {

    // *** Data constants ***

    public static final int DATA_PLAYER = 1;
    public static final int DATA_CELL = 2;

    private final RequestType type;
    private final Adventurer currentPlayer;
    private final Map<Integer, Object> data = new HashMap<>();

    public Request(final RequestType type, final Adventurer currentPlayer) {
        this.type = type;
        this.currentPlayer = currentPlayer;
    }

    /**
     * Get the type of this request, used to determine
     * which data are supposed to be present.
     *
     * @return The type of request.
     */
    public RequestType getType() {
        return type;
    }

    /**
     * Get the player which this request is for.
     *
     * @return The current player.
     */
    public Adventurer getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Add some data to carry with this Request.
     *
     * @param key - The identifier to use to identify the data.
     * @param obj - The value to assign.
     * @return This object for chaining.
     */
    public Request putData(final int key, final Object obj) {
        data.put(key, obj);
        return this;
    }

    /**
     * Used to make sure the requested data exists here.
     *
     * @param key - The identifier of the wanted data.
     * @return True if the data is present, otherwise false.
     */
    public boolean hasData(final int key) {
        return data.containsKey(key);
    }

    /**
     * Retrieve some data passed along this request.
     *
     * @param key - The identifier of the data.
     * @param <T> - The type of data, for convenience.
     * @return The request data, or null.
     */
    @SuppressWarnings("unchecked")
    public <T> T getData(final int key) {
        return (T) data.get(key);
    }

}
