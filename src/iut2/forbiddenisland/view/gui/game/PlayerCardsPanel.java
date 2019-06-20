package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.controller.Controller;
import iut2.forbiddenisland.model.adventurer.Adventurer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerCardsPanel extends JPanel {

    private final List<PlayerCardPanel> panels = new ArrayList<>(4);

    public PlayerCardsPanel(final Controller controller, final int width, final int height) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        for (Adventurer adventurer : controller.getAdventurers().get()) {
            final PlayerCardPanel panel = new PlayerCardPanel(controller, adventurer, width, height / 4);
            panels.add(panel);
            add(panel);
        }

        controller.getAdventurers().subscribe(this::onUpdateAdventurers);

        panels.get(0).setHighlighted(false);
        panels.get(1).setHighlighted(true);
    }

    private void onUpdateAdventurers(final List<Adventurer> adventurers) {
        for (int i = 0; i < adventurers.size(); i++) {
            panels.get(i).updateCards(adventurers.get(i).getCards());
        }
    }
}
