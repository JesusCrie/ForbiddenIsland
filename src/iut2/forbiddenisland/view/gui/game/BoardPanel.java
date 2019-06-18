package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.controller.Controller;
import iut2.forbiddenisland.controller.observer.Observable;
import iut2.forbiddenisland.model.Location;
import iut2.forbiddenisland.model.Treasure;
import iut2.forbiddenisland.model.cell.Cell;
import iut2.forbiddenisland.view.gui.utils.GridCellButton;
import iut2.forbiddenisland.view.gui.utils.TreasureImage;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardPanel extends JPanel {

    private static final JPanel EMPTY = new JPanel();

    private final Observable<Cell> cellClickNotifier = new Observable<>();

    private final List<JPanel> treasurePanels = new ArrayList<>(4);
    private final Map<Location, JPanel> cellsPanels = new HashMap<>();

    public BoardPanel(final Controller controller) {
        setLayout(new GridBagLayout());

        // TODO when real controller
        //controller.observeClickCell(cellClickNotifier);


    }

    private void setup(final Map<Location, Cell> cells, final List<Treasure> treasures) {
        if (treasures.size() != 4)
            throw new IllegalArgumentException("There need to be exactly 4 treasures !");

        // Line by line

        // *** Line 0 ***

        for (int i = 0; i < 8; ++i)
            add(EMPTY);

        // *** Line 1 ***

        add(EMPTY);

        // First treasure
        if (!treasures.get(0).isClaimable())
            add(new TreasureImage(treasures.get(0)));

        add(EMPTY);

        add(createCellButton(cells.get(Location.from(2, 0))));
        add(createCellButton(cells.get(Location.from(3, 0))));

        add(EMPTY);

        // Second treasure
        if (!treasures.get(1).isClaimable())
            add(new TreasureImage(treasures.get(1)));

        add(EMPTY);

        // *** Line 2 ***

        add(EMPTY);
        add(EMPTY);

        for (int i = 1; i <= 4; ++i)
            add(createCellButton(cells.get(Location.from(i, 1))));

        add(EMPTY);
        add(EMPTY);

        // *** Line 3 ***

        add(EMPTY);

        for (int i = 0; i <= 5; ++i)
            add(createCellButton(cells.get(Location.from(i, 2))));

        add(EMPTY);

        // *** Line 4 ***

        add(EMPTY);

        for (int i = 0; i <= 5; ++i)
            add(createCellButton(cells.get(Location.from(i, 3))));

        add(EMPTY);

        // *** Line 5 ***

        add(EMPTY);
        add(EMPTY);

        for (int i = 1; i <= 4; ++i)
            add(createCellButton(cells.get(Location.from(i, 4))));

        add(EMPTY);
        add(EMPTY);

        // *** Line 6 ***

        add(EMPTY);

        if (!treasures.get(2).isClaimable())
            add(new TreasureImage(treasures.get(2)));

        add(EMPTY);

        add(createCellButton(cells.get(Location.from(2, 5))));
        add(createCellButton(cells.get(Location.from(3, 5))));

        add(EMPTY);

        if (!treasures.get(3).isClaimable())
            add(new TreasureImage(treasures.get(3)));

        add(EMPTY);

        // *** Line 7 ***

        for (int i = 0; i < 8; ++i)
            add(EMPTY);
    }

    private GridCellButton createCellButton(final Cell cell) {
        final GridCellButton btn = new GridCellButton(cell);
        btn.addActionListener(e -> cellClickNotifier.set(cell));

        return btn;
    }
}
