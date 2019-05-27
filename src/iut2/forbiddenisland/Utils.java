package iut2.forbiddenisland;

import iut2.forbiddenisland.model.Location;

public class Utils {

    public static Location[] getCrossCells(final Location loc) {
        return new Location[]{
                Location.from(loc.getX() + 1, loc.getY()),
                Location.from(loc.getX() - 1, loc.getY()),
                Location.from(loc.getX(), loc.getY() + 1),
                Location.from(loc.getX(), loc.getY() - 1)
        };
    }

    public static Location[] getCornerCells(final Location loc) {
        return new Location[]{
                Location.from(loc.getX() + 1, loc.getY() + 1),
                Location.from(loc.getX() + 1, loc.getY() - 1),
                Location.from(loc.getX() - 1, loc.getY() + 1),
                Location.from(loc.getX() - 1, loc.getY() - 1)
        };
    }
}
