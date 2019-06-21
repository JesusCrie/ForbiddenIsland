package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.ForbiddenIsland;
import iut2.forbiddenisland.Pair;
import iut2.forbiddenisland.controller.Controller;
import iut2.forbiddenisland.controller.observer.Observable;
import iut2.forbiddenisland.model.adventurer.Adventurer;
import iut2.forbiddenisland.model.card.TreasureCard;
import iut2.forbiddenisland.model.cell.Cell;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static javax.swing.SwingUtilities.invokeLater;

public class GameFrame extends JFrame {

    private Observable<Pair<Adventurer, TreasureCard>> trashCardObservable = new Observable<>();

    private final BoardPanel boardPanel;
    private final AdventurerCardsPanel adventurerCardsPanel;
    private final WaterLevelPanel waterLevelPanel;
    private final ActionPanel actionPanel;

    public GameFrame(final Controller controller) {
        setSize(1900, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Yes, every size is hardcoded and its not responsive

        final Box container = Box.createHorizontalBox();
        container.setBorder(new EmptyBorder(5, 5, 5, 5));

        // Create board panel
        boardPanel = new BoardPanel();
        boardPanel.setPreferredSize(new Dimension(1000, 1000));
        container.add(boardPanel);

        // Create player card panel
        adventurerCardsPanel = new AdventurerCardsPanel(controller, 500, 1000);
        adventurerCardsPanel.setPreferredSize(new Dimension(500, 1000));
        container.add(adventurerCardsPanel);

        // Create water & action container
        final Box waterActionContainer = Box.createVerticalBox();
        waterActionContainer.setPreferredSize(new Dimension(300, 1000));

        // Water level panel
        waterLevelPanel = new WaterLevelPanel();
        waterActionContainer.setPreferredSize(new Dimension(300, 600));
        waterActionContainer.add(waterLevelPanel);

        // Create action panel
        actionPanel = new ActionPanel(300, 300);
        actionPanel.setMaximumSize(new Dimension(300, 300));
        actionPanel.setPreferredSize(new Dimension(300, 300));
        waterActionContainer.add(actionPanel);

        container.add(waterActionContainer);

        // Controller 1-way bindings
        setupControllerToViewBindings(controller);
        setupViewToControllerBindings(controller);

        // Setup board
        boardPanel.setup(controller.getCells().get(), controller.getTreasures().get());

        setContentPane(container);
    }

    private void setupControllerToViewBindings(final Controller controller) {

        // *** Board display ***

        // Cell update
        controller.getCells().subscribe(updatedCells -> invokeLater(boardPanel::update));

        // Cell highlighting update
        controller.getHighlightedCells().subscribe(newHighlightedCells -> invokeLater(() ->
                boardPanel.highlightedCells(newHighlightedCells))
        );

        // Treasure update
        controller.getTreasures().subscribe(updatedTreasures -> invokeLater(boardPanel::update));

        // End round cell update
        controller.getEndGameObservable().subscribe(isWin -> invokeLater(boardPanel::update));

        // *** Adventurer display ***

        // Adventurer cards
        controller.getAdventurers().subscribe(updatedAdventurers -> invokeLater(adventurerCardsPanel::updateCards));

        // Current adventurer
        controller.getCurrentAdventurer().subscribe(currentAdventurer -> invokeLater(() ->
                adventurerCardsPanel.setCurrentAdventurer(currentAdventurer))
        );

        // Sendable adventurer
        controller.getSendableAdventurers().subscribe(sendableAdventurers -> invokeLater(() ->
                adventurerCardsPanel.setSendableAdventurers(sendableAdventurers))
        );

        // *** Water level display ***

        // WaterLevel
        controller.getWaterLevel().subscribe(newWaterLevel ->
                waterLevelPanel.setWaterLevel(newWaterLevel.getLevel())
        );

        // *** Actions display ***

        // Remaining actions
        controller.getRemainingActions().subscribe(amount -> invokeLater(() ->
                actionPanel.setRemainingActions(amount))
        );

        // Game mode highlighter
        controller.getGameMode().subscribe(gameMode -> invokeLater(() ->
                actionPanel.highlightGameMode(gameMode))
        );

        // *** Too many cards trigger ***

        controller.getTooManyCardsObservable().subscribe(adventurer -> {
            final TreasureCard cardToDiscard = askCardToDiscard(adventurer.getCards());
            trashCardObservable.set(Pair.of(adventurer, cardToDiscard));
        });

        // *** Notices ***

        // Engine notices
        controller.getFeedbackObservable().subscribe(msg ->
                invokeLater(() -> JOptionPane.showMessageDialog(null, msg))
        );

        // End round/game notices
        controller.getEndGameObservable().subscribe(win -> invokeLater(() -> {
            if (win == null) {
                JOptionPane.showMessageDialog(null, "Au joueur suivant !");
            } else {
                if (win) {
                    JOptionPane.showMessageDialog(null, "Vous avez gagner !", "Victoire", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Vous avez perdu !", "Défaite", JOptionPane.ERROR_MESSAGE);
                }

                // If game is over, discard the frame and restart the game
                dispose();
                ForbiddenIsland.startWelcomeFrame();
            }
        }));

        // Remaining action reminder
        controller.getRemainingActions().subscribe(actions -> {
            if (actions <= 0)
                JOptionPane.showMessageDialog(null, "C'était votre dernière action, pensez à finir votre tour.");
        });
    }

    private void setupViewToControllerBindings(final Controller controller) {
        // *** Board click ***

        final Observable<Cell> obsCellClick = new Observable<>();
        boardPanel.setCellClickNotifier(obsCellClick);
        controller.observeClickCell(obsCellClick);

        // *** Adventurers click ***

        // Each adventurer panel
        adventurerCardsPanel.getPanels().forEach((adv, panel) -> {

            // Click player
            final Observable<Adventurer> obsClickAdventurer = new Observable<>();
            panel.getCardButton().addActionListener(e -> obsClickAdventurer.set(adv));
            controller.observeClickPlayer(obsClickAdventurer);

            // Click card
            final Observable<Pair<Adventurer, TreasureCard>> obsClickCard = new Observable<>();
            panel.setCardClickNotifier(obsClickCard);
            controller.observeClickCard(obsClickCard);
        });

        // *** GameMode changes ***

        // Mode move
        final Observable<Void> obsModeMove = new Observable<>();
        actionPanel.getButtonMove().addActionListener(e -> obsModeMove.notifyChanges());
        controller.observeModeMove(obsModeMove);

        // Mode dry
        final Observable<Void> obsModeDry = new Observable<>();
        actionPanel.getButtonDry().addActionListener(e -> obsModeDry.notifyChanges());
        controller.observeModeDry(obsModeDry);

        // Mode send
        final Observable<Void> obsModeSend = new Observable<>();
        actionPanel.getButtonSend().addActionListener(e -> obsModeSend.notifyChanges());
        controller.observeModeSend(obsModeSend);

        // Mode claim
        final Observable<Void> obsModeClaim = new Observable<>();
        actionPanel.getButtonClaim().addActionListener(e -> obsModeClaim.notifyChanges());
        controller.observeModeTreasureClaim(obsModeClaim);

        // *** End round ***

        final Observable<Void> obsEndRound = new Observable<>();
        actionPanel.getButtonEndRound().addActionListener(e -> obsEndRound.notifyChanges());
        controller.observeClickEndRound(obsEndRound);

        // *** Trash card ***

        controller.observeTrashCard(trashCardObservable);
    }

    public static TreasureCard askCardToDiscard(final List<TreasureCard> cards) {
        final CompletableFuture<TreasureCard> future = new CompletableFuture<>();

        invokeLater(() -> {
            final DiscardCardPopupFrame frame = new DiscardCardPopupFrame(cards, future);
            frame.setVisible(true);
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException("Future failed", e);
        }
    }
}
