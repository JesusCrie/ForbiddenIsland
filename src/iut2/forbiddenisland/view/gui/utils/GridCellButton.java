package iut2.forbiddenisland.view.gui.utils;

import iut2.forbiddenisland.model.adventurer.Adventurer;
import iut2.forbiddenisland.model.cell.Cell;
import iut2.forbiddenisland.model.cell.CellState;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

public class GridCellButton extends AutoResizeImageButton {

    private final Cell cell;

    public GridCellButton(final Cell cell) {
        super(cell.getMetadata().getGridDryImage());
        this.cell = cell;
    }

    public void update() {
        if (cell.getState() == CellState.FLOODED) {
            setVisible(false);
        } else {
            setVisible(true);

            if (cell.getState() == CellState.DRY)
                background = cell.getMetadata().getGridDryImage();
            else
                background = cell.getMetadata().getGridWetImage();
        }
    }

    @Override
    protected void paintComponent(final Graphics g) {
        // Paint the background
        super.paintComponent(g);

        final List<Adventurer> advs = cell.getAdventurers();

        // If there are adventurers on the cell
        if (advs.size() > 0) {

            // Compute the area occupied by one adventurer
            final Rectangle zone = new Rectangle(
                    (int) (0.05 * (double) getWidth()), // 5% width
                    (int) (0.05 * (double) getHeight()), // 5% height
                    (int) (0.9 * (double) getWidth()) / 2, // 90% width / 2
                    (int) (0.9 * (double) getHeight()) / 2 // 90% height / 2
            );

            // Draw first adventurer
            g.drawImage(advs.get(0).getMetadata().getPieceImage(),
                    zone.x, zone.y, zone.width, zone.height, null);

            // If 2 or more adventurers
            if (advs.size() >= 2) {
                g.drawImage(advs.get(1).getMetadata().getPieceImage(),
                        zone.x + zone.width, zone.y, zone.width, zone.height, null);

                // If 3 or more adventurers
                if (advs.size() >= 3) {
                    g.drawImage(advs.get(2).getMetadata().getPieceImage(),
                            zone.x, zone.y + zone.height, zone.width, zone.height, null);

                    // If 4 or more adventurers
                    if (advs.size() >= 4) {
                        g.drawImage(advs.get(3).getMetadata().getPieceImage(),
                                zone.x + zone.width, zone.y + zone.height, zone.width, zone.height, null);

                        // Show a warning if more than 4 adventurers
                        if (advs.size() > 4) {
                            System.err.println("RENDER WARNING: There are more than 4 adventurers on the same cell ! " + cell.getLocation());
                        }
                    }
                }
            }
        }
    }
}
