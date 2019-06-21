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
import iut2.forbiddenisland.model.cell.TreasureCell;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller {

    private final GameEngine engine;
    private final Observable<GameMode> gameMode;
    private final Observable<String> feedbackObs;
    private final Observable<List<Adventurer>> sendableAdventurers;
    private final Observable<Collection<Cell>> highlightedCells;

    private Adventurer selectedAdventurer;
    private Card selectedCard;
    private Cell selectedCell;

    public Controller(final Board board, final List<Adventurer> adventurers) {
        engine = new GameEngine(board, adventurers);
        gameMode = new NotifyOnSubscribeObservable<>(GameMode.IDLE);
        feedbackObs = new Observable<>();
        sendableAdventurers = new Observable<List<Adventurer>>() {
            @Override
            public void notifyChanges() {
                value = gameMode.get() == GameMode.SEND ? engine.getPlayersSendable() : Collections.emptyList();
                super.notifyChanges();
            }
        };
        highlightedCells = createHighlightedCellsObs();

        gameMode.subscribe(mode -> {
            // Update everything
            sendableAdventurers.notifyChanges();
            engine.getCells().notifyChanges();
            highlightedCells.notifyChanges();
            engine.getAdventurers().notifyChanges();
        });
    }

    private Observable<Collection<Cell>> createHighlightedCellsObs() {
        return new NotifyOnSubscribeObservable<Collection<Cell>>() {
            @Override
            public void notifyChanges() {
                switch (gameMode.get()) {
                    case MOVE:
                        value = engine.getReachableCells();
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
                            value = engine.getCells().get().values();
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

    public Observable<String> getFeedbackObservable() {
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

    public Observable<List<Adventurer>> getSendableAdventurers() {
        return sendableAdventurers;
    }

    public Observable<List<Treasure>> getTreasures() {
        return engine.getTreasures();
    }

    public Observable<Boolean> getEndGameObservable() {
        return engine.getEndGame();
    }

    public Observable<Adventurer> getTooManyCardsObservable() {
        return engine.getTooManyCards();
    }

    /**
     * The controller will subscribe to the provided observable
     * and enable the move mode of the engine when triggered.
     *
     * @param o - The observable to observe.
     */
    public void observeModeMove(final Observable<Void> o) {
        o.subscribe(value -> {
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
            // Check if any card to send
            if (engine.getCurrentPlayer().get().getCards().isEmpty()) {
                feedbackObs.set("Vous n'avez aucune carte à envoyer !");

            } else {
                selectedAdventurer = null;
                selectedCard = null;

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
                        feedbackObs.set("Vous n'avez pas le droit de vous deplacer ici !");

                    // Note: Stays in the movement mode unlike other modes who get back to idle mode.

                    break;

                case DRY:
                    if (!engine.dryCell(cell))
                        feedbackObs.set("Vous n'avez pas le droit d'assecher cette tuile !");
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

            // Update cells
            highlightedCells.notifyChanges();
            engine.getCells().notifyChanges();
            // Update adventurers
            engine.getAdventurers().notifyChanges();
            sendableAdventurers.notifyChanges();
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
                    selectedAdventurer = adventurer;

                    if (selectedCard != null) {
                        if (!engine.sendCard(selectedAdventurer, selectedCard))
                            feedbackObs.set("Vous ne pouvez pas envoyer cette carte à cet aventurier !");
                        else
                            gameMode.set(GameMode.IDLE);
                    }
                    break;
                case MOVE:
                    selectedAdventurer = adventurer;
                    break;
                default:
                    break;
            }

            // Update cells
            highlightedCells.notifyChanges();
            engine.getCells().notifyChanges();
            // Update adventurers
            engine.getAdventurers().notifyChanges();
            sendableAdventurers.notifyChanges();
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
            if (gameMode.get() == GameMode.SEND && adventurerCardPair.getLeft() != engine.getCurrentPlayer().get()) {
                selectedCard = adventurerCardPair.getRight();

                if (selectedAdventurer != null) {
                    if (!engine.sendCard(selectedAdventurer, selectedCard))
                        feedbackObs.set("Vous ne pouvez pas envoyer cette carte à cet aventurier !");
                }

            } else if (adventurerCardPair.getRight() instanceof SpecialCard) {
                selectedCard = adventurerCardPair.getRight();
                selectedAdventurer = adventurerCardPair.getLeft();

                final SpecialCard card = (SpecialCard) adventurerCardPair.getRight();

                if (card instanceof HelicopterCard) {
                    feedbackObs.set("Veuillez sélectionnez une tuile de départ");
                    gameMode.set(GameMode.SPECIAL_CARD_HELICOPTER);

                } else if (card instanceof SandBagCard) {
                    feedbackObs.set("Veuillez sélectionnez une tuile à assecher");
                    gameMode.set(GameMode.SPECIAL_CARD_SANDBAG);
                }
            }
        });
    }

    /**
     * The controller will subscribe to the provided observable
     * and trash the card provided for the provided player when triggered.
     *
     * @param o - The observable to observe.
     */
    public void observeTrashCard(final Observable<Pair<Adventurer, TreasureCard>> o) {
        o.subscribe(pair -> engine.trashCard(pair.getLeft(), pair.getRight()));
    }

    /**
     * The controller will subscribe to the provided observable
     * and communicate the click of the new round to the engine when triggered.
     *
     * @param o - The observable to observe.
     */
    public void observeClickEndRound(final Observable<Void> o) {
        o.subscribe(v -> {
            gameMode.set(GameMode.IDLE);
            engine.endPlayerRound();

            engine.startIslandTurn();

            engine.newPlayerRound();
            // TODO maybe more end round things
        });
    }

}
