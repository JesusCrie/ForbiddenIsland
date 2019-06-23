package iut2.forbiddenisland.controller;

import iut2.forbiddenisland.Pair;
import iut2.forbiddenisland.controller.observer.NotifyOnSubscribeObservable;
import iut2.forbiddenisland.controller.observer.Observable;
import iut2.forbiddenisland.controller.observer.OneTimeObservable;
import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.Location;
import iut2.forbiddenisland.model.Treasure;
import iut2.forbiddenisland.model.WaterLevel;
import iut2.forbiddenisland.model.adventurer.Adventurer;
import iut2.forbiddenisland.model.card.*;
import iut2.forbiddenisland.model.cell.Cell;
import iut2.forbiddenisland.model.cell.CellState;
import iut2.forbiddenisland.model.cell.HeliportCell;
import iut2.forbiddenisland.model.cell.TreasureCell;

import javax.swing.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller {

    private final GameEngine engine;
    private final Observable<GameMode> gameMode;
    private final Observable<String> feedbackObs;
    private final Observable<List<Adventurer>> highlightedAdventurers;
    private final Observable<Collection<Cell>> highlightedCells;

    private Adventurer selectedAdventurer;
    private Card selectedCard;
    private Cell selectedCell;

    public Controller(final Board board, final List<Adventurer> adventurers) {
        engine = new GameEngine(board, adventurers);
        gameMode = new NotifyOnSubscribeObservable<>(GameMode.IDLE);
        feedbackObs = new Observable<>();
        highlightedAdventurers = createHighlightedAdventurersObs();
        highlightedCells = createHighlightedCellsObs();

        gameMode.subscribe(mode -> triggerUpdate());

        // Emergency mode
        engine.getEmergencyRescue().subscribe(adv -> {
            selectedAdventurer = adv;
            gameMode.set(GameMode.EMERGENCY_MOVE);
        });
    }

    private void triggerUpdate() {
        // TODO: Improvement: Update only the necessary in the corresponding method
        highlightedCells.notifyChanges();
        highlightedAdventurers.notifyChanges();
        engine.getAdventurers().notifyChanges();
        engine.getCells().notifyChanges();
    }

    private Observable<List<Adventurer>> createHighlightedAdventurersObs() {
        return new NotifyOnSubscribeObservable<List<Adventurer>>() {
            @Override
            public void notifyChanges() {
                switch (gameMode.get()) {
                    case MOVE:
                        value = engine.getPlayersMoveable();
                        break;
                    case SEND:
                        value = engine.getPlayersSendable();
                        break;
                    default:
                        value = Collections.emptyList();
                        break;
                }

                super.notifyChanges();
            }
        };
    }

    private Observable<Collection<Cell>> createHighlightedCellsObs() {
        return new NotifyOnSubscribeObservable<Collection<Cell>>() {
            @Override
            public void notifyChanges() {
                switch (gameMode.get()) {
                    case MOVE:
                    case EMERGENCY_MOVE:
                        value = engine.getReachableCells(selectedAdventurer);
                        break;

                    case DRY:
                        value = engine.getDryableCells();
                        break;

                    case TREASURE:
                        final Cell current = getCurrentAdventurer().get().getPosition();
                        if (current instanceof TreasureCell)
                            value = Collections.singletonList(current);
                        else
                            value = Collections.emptyList();
                        break;

                    case SPECIAL_CARD_HELICOPTER:
                        // Selecting the departure cell
                        if (selectedCell == null) {
                            // Get every cell with an adventurer
                            value = engine.getAdventurers().get().stream()
                                    .map(Adventurer::getPosition)
                                    .distinct()
                                    .collect(Collectors.toList());

                            // Selecting the destination cell
                        } else {
                            // Every cell
                            value = engine.getCells().get().values().stream()
                                    .filter(cell -> cell.getState() != CellState.FLOODED)
                                    .collect(Collectors.toList());
                        }

                        break;

                    case SPECIAL_CARD_SANDBAG:
                        // Get every wet cell
                        value = engine.getCells().get().values().stream()
                                .filter(cell -> cell.getState() == CellState.WET)
                                .collect(Collectors.toList());
                        break;

                    case SEND:
                    case IDLE:
                    default:
                        value = Collections.emptyList();
                }

                super.notifyChanges();
            }
        };
    }

    public Observable<String> getFeedback() {
        return feedbackObs;
    }

    public Observable<GameMode> getGameMode() {
        return gameMode;
    }

    public Observable<Map<Location, Cell>> getCells() {
        return engine.getCells();
    }

    public Observable<Collection<Cell>> getHighlightedCells() {
        return highlightedCells;
    }

    public Observable<Integer> getRemainingActions() {
        return engine.getRemainingActions();
    }

    public Observable<WaterLevel> getWaterLevel() {
        return engine.getWaterLevel();
    }

    public Observable<List<Adventurer>> getAdventurers() {
        return engine.getAdventurers();
    }

    public Observable<Adventurer> getCurrentAdventurer() {
        return engine.getCurrentPlayer();
    }

    public Observable<List<Adventurer>> getHighlightedAdventurers() {
        return highlightedAdventurers;
    }

    public Observable<List<Treasure>> getTreasures() {
        return engine.getTreasures();
    }

    public Observable<Boolean> getEndGameObservable() {
        return engine.getEndGame();
    }

    public Observable<Void> getRisingWatersCardDrawn() {
        return engine.getRisingWatersCardDrawn();
    }

    public Observable<Adventurer> getTooManyCards() {
        return engine.getTooManyCards();
    }

    public Observable<Adventurer> getEmergencyRescue() {
        return engine.getEmergencyRescue();
    }

    /**
     * The controller will subscribe to the provided observable
     * and enable the move mode of the engine when triggered.
     *
     * @param o - The observable to observe.
     */
    public void observeModeMove(final Observable<Void> o) {
        o.subscribe(value -> {
            if (gameMode.get() == GameMode.EMERGENCY_MOVE)
                return;

            selectedAdventurer = engine.getCurrentPlayer().get();
            gameMode.set(GameMode.MOVE);
        });
    }

    /**
     * The controller will subscribe to the provided observable
     * and enable the dry mode of the engine when triggered.
     *
     * @param o - The observable to observe.
     */
    public void observeModeDry(final Observable<Void> o) {
        o.subscribe(value -> {
            if (gameMode.get() == GameMode.EMERGENCY_MOVE)
                return;

            selectedAdventurer = engine.getCurrentPlayer().get();
            gameMode.set(GameMode.DRY);
        });
    }

    /**
     * The controller will subscribe to the provided observable
     * and enable the treasure claiming mode of the engine when triggered.
     *
     * @param o - The observable to observe.
     */
    public void observeModeTreasureClaim(final Observable<Void> o) {
        o.subscribe(value -> {
            if (gameMode.get() == GameMode.EMERGENCY_MOVE)
                return;

            // With the current set of rules, the only way to claim a treasure
            // is to be exactly on his cell so this gamemode is pretty useless
            gameMode.set(GameMode.TREASURE);

            // Act like the player clicked the cell he is standing on
            observeClickCell(new OneTimeObservable<>(engine.getCurrentPlayer().get().getPosition()));

            // Reset game mode
            gameMode.set(GameMode.IDLE);
        });
    }

    /**
     * The controller will subscribe to the provided observable
     * and enable the sending mode of the engine when triggered.
     *
     * @param o - The observable to observe.
     */
    public void observeModeSend(final Observable<Void> o) {
        o.subscribe(value -> {
            if (gameMode.get() == GameMode.EMERGENCY_MOVE)
                return;

            // Check if any card to send
            if (engine.getCurrentPlayer().get().getCards().isEmpty()) {
                feedbackObs.set("Vous n'avez aucune carte à envoyer !");

            } else if (engine.getPlayersSendable().isEmpty()) {
                feedbackObs.set("Aucun joueur n'est à portée !");

            } else {
                selectedAdventurer = null;
                selectedCard = null;

                feedbackObs.set("Veuillez choisir un joueur");

                gameMode.set(GameMode.SEND);
            }
        });
    }

    /**
     * The controller will subscribe to the provided observable
     * and communicate the click of a cell to the engine when triggered.
     *
     * @param o - The observable to observe.
     */
    public void observeClickCell(final Observable<Cell> o) {
        o.subscribe(cell -> {
            switch (gameMode.get()) {
                case MOVE:
                    if (!engine.movePlayer(selectedAdventurer, cell))
                        feedbackObs.set("Vous ne pouvez pas vous deplacer ici !");

                    // Note: Stays in the movement mode unlike other modes who get back to idle mode.

                    break;

                case EMERGENCY_MOVE:
                    // If this mode has been triggered in the first place, there is a valid cell to click
                    // so no going back to idle mode until the move is successful

                    if (!engine.emergencyMovePlayer(selectedAdventurer, cell)) {
                        feedbackObs.set("Vous ne pouvez pas vous déplacer ici !");
                    } else {
                        gameMode.set(GameMode.IDLE);
                    }

                    break;

                case DRY:
                    if (!engine.dryCell(cell))
                        feedbackObs.set("Vous ne pouvez pas assecher cette tuile !");
                    else
                        gameMode.set(GameMode.IDLE);
                    break;

                case TREASURE:
                    // If someone was able to claim a treasure from another cell
                    if (cell instanceof TreasureCell) {
                        if (!engine.claimTreasure((TreasureCell) cell))
                            feedbackObs.set("Vous ne pouvez pas recupérer ce trésor !");
                    } else {
                        feedbackObs.set("Ce n'est pas une tuile trésor !");
                    }
                    break;

                case SPECIAL_CARD_HELICOPTER:
                    // If selecting the start cell
                    if (selectedCell == null) {
                        if (cell.getAdventurers().isEmpty()) {
                            feedbackObs.set("Il n'y a aucun joueur a déplacer ici !");
                            break;
                        }

                        selectedCell = cell;

                    } else { // If selecting the destination cell

                        if (cell.getState() == CellState.FLOODED) {
                            feedbackObs.set("Vous ne pouvez pas vous déplacer sur une case inondée !");
                            break;
                        }

                        // Apply the card
                        if (!engine.useCardHelicopter(selectedAdventurer, (HelicopterCard) selectedCard, selectedCell, cell)) {
                            feedbackObs.set("Vous ne pouvez pas utiliser cette carte !");
                            break;
                        }

                        gameMode.set(GameMode.IDLE);
                    }
                    break;

                case SPECIAL_CARD_SANDBAG:
                    if (!engine.useCardSandbag(selectedAdventurer, (SandBagCard) selectedCard, cell)) {
                        feedbackObs.set("Vous ne pouvez pas utiliser cette carte !");
                    }

                    gameMode.set(GameMode.IDLE);
                    break;
            }

            // TODO 22/06/2019 notify ?
            triggerUpdate();
        });
    }

    /**
     * The controller will subscribe to the provided observable
     * and communicate the click of a player to the engine when triggered.
     *
     * @param o - The observable to observe.
     */
    public void observeClickPlayer(final Observable<Adventurer> o) {
        o.subscribe(adventurer -> {
            switch (gameMode.get()) {
                case SEND:
                    // If it was the first adventurer selected
                    if (selectedAdventurer == null)
                        feedbackObs.set("Apres avoir choisi un aventurier, choisissez une carte à envoyer");

                    selectedAdventurer = adventurer;
                    break;
                case MOVE:
                    selectedAdventurer = adventurer;
                    break;
                default:
                    break;
            }

            // TODO 22/06/2019 notify ?
            triggerUpdate();
        });
    }

    /**
     * The controller will subscribe to the provided observable
     * and communicate the click of a card to the engine when triggered.
     *
     * @param o - The observable to observe.
     */
    public void observeClickCard(final Observable<Pair<Adventurer, TreasureCard>> o) {
        o.subscribe(adventurerCardPair -> {
            if (gameMode.get() == GameMode.SEND && adventurerCardPair.getLeft() == engine.getCurrentPlayer().get()) {
                selectedCard = adventurerCardPair.getRight();

                if (selectedCard instanceof SpecialCard) {
                    feedbackObs.set("Vous ne pouvez pas envoyer de carte spéciale !");
                    return;
                }

                if (selectedAdventurer != null) {

                    if (!engine.sendCard(selectedAdventurer, selectedCard))
                        feedbackObs.set("Vous ne pouvez pas envoyer cette carte à cet aventurier !");
                    else {
                        gameMode.set(GameMode.IDLE);
                    }

                } else {
                    feedbackObs.set("Veuillez selectionnez un aventurier");
                }

            } else if (adventurerCardPair.getRight() instanceof SpecialCard) {
                selectedCard = adventurerCardPair.getRight();
                selectedAdventurer = adventurerCardPair.getLeft();

                final SpecialCard card = (SpecialCard) adventurerCardPair.getRight();

                if (card instanceof HelicopterCard) {
                    // Special case: check for win
                    if (selectedAdventurer.getPosition() instanceof HeliportCell
                            && selectedAdventurer.getPosition().getAdventurers().size() == engine.getAdventurers().get().size()
                            && engine.getTreasures().get().stream().noneMatch(Treasure::isClaimable)) {

                        // Heliport cell && everybody is here && no treasure is claimable
                        // Guess its a win
                        engine.getEndGame().set(true);
                        return;
                    }

                    feedbackObs.set("Veuillez sélectionnez une tuile de départ");
                    selectedCell = null;
                    gameMode.set(GameMode.SPECIAL_CARD_HELICOPTER);

                } else if (card instanceof SandBagCard) {
                    feedbackObs.set("Veuillez sélectionnez une tuile à assecher");
                    selectedCell = null;
                    gameMode.set(GameMode.SPECIAL_CARD_SANDBAG);
                }

                engine.getAdventurers().notifyChanges();
            }

            // TODO 22/06/2019 notify ? (beware of the return)
            triggerUpdate();
        });
    }

    /**
     * The controller will subscribe to the provided observable
     * and trash the card provided for the provided player when triggered.
     *
     * @param o - The observable to observe.
     */
    public void observeTrashCard(final Observable<Pair<Adventurer, TreasureCard>> o) {
        o.subscribe(pair -> {
            engine.trashCard(pair.getLeft(), pair.getRight());
            engine.getAdventurers().notifyChanges();
        });
    }

    /**
     * The controller will subscribe to the provided observable
     * and communicate the click of the new round to the engine when triggered.
     *
     * @param o - The observable to observe.
     */
    public void observeClickEndRound(final Observable<Void> o) {
        o.subscribe(v -> {
            if (gameMode.get() == GameMode.EMERGENCY_MOVE)
                return;

            // Execute everything in a worker to avoid blocking the thread

            final SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() {
                    gameMode.set(GameMode.IDLE);

                    // End the current player's round
                    if (engine.endPlayerRound()) {

                        // Do the island turn, aka drawing flood cards
                        if (engine.startIslandTurn()) {

                            // New round
                            engine.newPlayerRound();

                            // TODO 22/06/2019 notify ?
                            triggerUpdate();
                        }
                    }

                    return null;
                }
            };

            worker.execute();
        });
    }

}
