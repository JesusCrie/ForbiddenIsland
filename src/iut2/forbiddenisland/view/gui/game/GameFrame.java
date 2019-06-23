package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.ForbiddenIsland;
import iut2.forbiddenisland.Pair;
import iut2.forbiddenisland.controller.Controller;
import iut2.forbiddenisland.controller.GameMode;
import iut2.forbiddenisland.controller.observer.Observable;
import iut2.forbiddenisland.controller.observer.Observer;
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
        setLocationRelativeTo(null);
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

        // Delegate the role of the main container to the box container
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
        controller.getHighlightedAdventurers().subscribe(sendableAdventurers -> invokeLater(() ->
                adventurerCardsPanel.setHighlightedAdventurers(sendableAdventurers))
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

        controller.getTooManyCards().subscribe(adventurer -> {
            while (adventurer.getCards().size() > 5) {
                final TreasureCard cardToDiscard = askCardToDiscard(adventurer.getCards());
                trashCardObservable.set(Pair.of(adventurer, cardToDiscard));
            }
        });

        // *** Notices ***

        // Engine notices
        controller.getFeedback().subscribe(msg -> invokeLater(() ->
                JOptionPane.showMessageDialog(null, msg)
        ));

        // End round/game notices
        controller.getEndGameObservable().subscribe(win -> {
            if (win == null) {
                // The new round is called in a worker thread so we need to wait to no
                // spam the user with popups
                waitForRunnable(() ->
                        JOptionPane.showMessageDialog(null, "Au joueur suivant !")
                );

            } else {
                // The win is called in the event thread so no need to wait for anything
                if (win) {
                    // Close window before
                    dispose();
                    JOptionPane.showMessageDialog(null,
                            "<html>" +
                                    "<h1>Victoire !!!</h1>" +
                                    "<p>Vous avez triomphés de l'Île !</p>" +
                                    "</html>",
                            "Victoire", JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "<html>" +
                                    "<h1>Vous avez perdus !</h1>" +
                                    "<p>L'Île a eu raison de vous.</p>" +
                                    "</html>",
                            "Défaite", JOptionPane.PLAIN_MESSAGE);
                    // Close window after
                    dispose();
                }

                // Close window
                //dispose();

                // Restart the game
                ForbiddenIsland.startWelcomeFrame();
            }
        });

        // Remaining action reminder
        controller.getRemainingActions().subscribe(actions -> invokeLater(() -> {
            // If 0 actions remaining, force the round to end
            // TODO: Improvement: support engineer power with the last action
            if (actions <= 0)
                actionPanel.getButtonEndRound().doClick();
        }));

        // Rising waters card
        controller.getRisingWatersCardDrawn().subscribe(ignoreMe -> waitForRunnable(() ->
                JOptionPane.showMessageDialog(null,
                        "Vous venez de tirer une carte Montée des Eaux !", "Montée des eaux", JOptionPane.WARNING_MESSAGE)
        ));

        // Emergency rescue notifier
        controller.getEmergencyRescue().subscribe(adv -> {
            // Do not return before the event has been treated
            final CompletableFuture<Void> future = new CompletableFuture<>();

            // Show the popup
            invokeLater(() ->
                    JOptionPane.showMessageDialog(null,
                            "<html><h3>" + adv.getName() + " est en train de se noyer !<br />Sauvez le !</h3></html>",
                            "Noyade en cours", JOptionPane.ERROR_MESSAGE)
            );

            // Will complete the future only the game mode will change, aka when the move has been made
            final Observer<GameMode> emergencyGameMode = gameMode -> {
                if (gameMode != GameMode.EMERGENCY_MOVE) {
                    future.complete(null);
                }
            };

            // Register the observer
            controller.getGameMode().subscribe(emergencyGameMode);

            try {
                // Wait for the future to complete
                future.get();
                // And unregister the observer
                controller.getGameMode().unsubscribe(emergencyGameMode);
            } catch (InterruptedException | ExecutionException ignore) {
                /* no-op */
            }
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

    private static void waitForRunnable(final Runnable runnable) {
        final CompletableFuture<Void> future = new CompletableFuture<>();

        // Schedule the task to the event thread
        invokeLater(() -> {
            runnable.run();
            future.complete(null);
        });

        try {
            // Block until the future is complete
            future.get();
        } catch (InterruptedException | ExecutionException ignore) {
            /* no-op */
        }
    }

    private static TreasureCard askCardToDiscard(final List<TreasureCard> cards) {
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
