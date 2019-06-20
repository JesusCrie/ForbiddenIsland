package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.controller.Controller;
import iut2.forbiddenisland.model.adventurer.Adventurer;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class PlayerCardsPanel extends JPanel {

    private final Map<Adventurer, PlayerCardPanel> panels = new HashMap<>(5);

    public PlayerCardsPanel(final Controller controller, final int width, final int height) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        for (Adventurer adventurer : controller.getAdventurers().get()) {
            final PlayerCardPanel panel = new PlayerCardPanel(controller, adventurer, width, height / 4);
            panels.put(adventurer, panel);
            add(panel);
        }

        controller.getAdventurers().subscribe(adventurers -> onUpdateAdventurers());
        controller.getCurrentAdventurer().subscribe(currentAdv ->
                panels.forEach((adv, panel) -> panel.setHighlighted(adv.equals(currentAdv)))
        );
    }

    private void onUpdateAdventurers() {
        panels.values().forEach(PlayerCardPanel::updateCards);
    }
}
