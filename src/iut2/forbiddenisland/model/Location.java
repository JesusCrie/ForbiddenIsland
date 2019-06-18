package iut2.forbiddenisland.model;

import java.util.Objects;

/**
 * Represent a pair of (x, y) coordinates.
 * Two locations are equals if they have the same coordinates.
 */
public class Location {

    private final int x;
    private final int y;

    /**
     * Create a new location from the given coordinates.
     *
     * @param x - Horizontal position.
     * @param y - Vertical position.
     * @return The associated location.
     */
    public static Location from(final int x, final int y) {
        return new Location(x, y);
    }

    private Location(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Equality methods

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Location))
            return false;
        return ((Location) obj).x == x && ((Location) obj).y == y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "{x: " + x + ", y: " + y + "}";
    }
}
