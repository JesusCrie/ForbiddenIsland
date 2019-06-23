package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.controller.Controller;
import iut2.forbiddenisland.model.adventurer.Adventurer;

import javax.swing.*;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdventurerCardsPanel extends JPanel {

    private final Map<Adventurer, AdventurerCardPanel> panels = new HashMap<>(4);

    public AdventurerCardsPanel(final Controller controller, final int width, final int height) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        for (Adventurer adventurer : controller.getAdventurers().get()) {
            final AdventurerCardPanel panel = new AdventurerCardPanel(adventurer, width, height / 4);
            panels.put(adventurer, panel);
            add(panel);
        }

        // Fill with empty panels
        for (int i = panels.size(); i < 4; i++) {
            final JPanel filler = new JPanel();
            filler.setPreferredSize(new Dimension(width, height / 4));
            add(filler);
        }
    }

    public Map<Adventurer, AdventurerCardPanel> getPanels() {
        return panels;
    }

    public void updateCards() {
        panels.values().forEach(AdventurerCardPanel::updateCards);
        invalidate();
    }

    public void setCurrentAdventurer(final Adventurer currentAdventurer) {
        panels.forEach((adv, panel) -> panel.setIsCurrentPlayer(adv == currentAdventurer));
    }

    public void setHighlightedAdventurers(final List<Adventurer> sendableAdventurers) {
        panels.forEach((adv, panel) -> panel.setSelectable(sendableAdventurers.contains(adv)));
    }
}
