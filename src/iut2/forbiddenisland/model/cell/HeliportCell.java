package iut2.forbiddenisland.model.cell;

import iut2.forbiddenisland.model.Location;
import iut2.forbiddenisland.view.CellGraphicalMetadata;

/**
 * Represent the heliport, the exit cell.
 */
public class HeliportCell extends StartCell {

    public HeliportCell(final CellGraphicalMetadata metadata, final Location loc) {
        super(metadata, loc);
    }
}
