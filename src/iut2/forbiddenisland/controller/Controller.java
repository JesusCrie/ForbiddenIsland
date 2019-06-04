package iut2.forbiddenisland.controller;

import iut2.forbiddenisland.controller.observer.NotifyOnSubscribeObservable;
import iut2.forbiddenisland.controller.observer.Observable;
import iut2.forbiddenisland.model.Board;
import iut2.forbiddenisland.model.Location;
import iut2.forbiddenisland.model.Treasure;
import iut2.forbiddenisland.model.adventurer.Adventurer;
import iut2.forbiddenisland.model.card.Card;
import iut2.forbiddenisland.model.card.SpecialCard;
import iut2.forbiddenisland.model.card.TreasureCard;
import iut2.forbiddenisland.model.cell.Cell;
import iut2.forbiddenisland.model.cell.TreasureCell;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Controller {

    private final GameEngine engine;
    private final Observable<GameMode> gameMode;
    private final Observable<String> feedbackObs;
    private final Observable<List<Adventurer>> sendableAdventurers;

    private Adventurer selectedAdventurer;
    private Card selectedCard;

    public Controller(final Board board, final List<Adventurer> adventurers) {
        engine = new GameEngine(board, adventurers);
        gameMode = new NotifyOnSubscribeObservable<>(GameMode.IDLE);
        feedbackObs = new Observable<>();
        sendableAdventurers = new Observable<List<Adventurer>>() {
            @Override
            public void notifyChanges() {
                value = engine.getPlayersSendable();
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

    public Observable<List<Cell>> getHighlightedCells() {
        return new NotifyOnSubscribeObservable<List<Cell>>() {
            @Override
            public void notifyChanges() {
                switch (gameMode.get()) {
                    case MOVE:
                        value = engine.getReachableCells();
                        break;
                    case DRY:
                        value = engine.getDryableCells();
                        break;
                    case SEND:
                        value = Collections.emptyList();
                        break;
                    case TREASURE:
                        final Cell current = getCurrentAdventurer().get().getPosition();
                        if (current instanceof TreasureCell)
                            value = Collections.singletonList(current);
                        else
                            value = Collections.emptyList();
                    case IDLE:
                    default:
                        value = Collections.emptyList();
                }

                super.notifyChanges();
            }
        };
    }

    public Observable<Integer> getRemainingActions() {
        return engine.getRemainingActions();
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

    /**
     * The controller will subscribe to the provided observable
     * and enable the move mode of the engine when triggered.
     *
     * @param o - The observable to observe.
     */
    public void observeModeMove(final Observable<Void> o) {
        o.subscribe(value -> gameMode.set(GameMode.MOVE));
    }

    /**
     * The controller will subscribe to the provided observable
     * and enable the dry mode of the engine when triggered.
     *
     * @param o - The observable to observe.
     */
    public void observeModeDry(final Observable<Void> o) {
        o.subscribe(value -> gameMode.set(GameMode.DRY));
    }

    /**
     * The controller will subscribe to the provided observable
     * and enable the treasure claiming mode of the engine when triggered.
     *
     * @param o - The observable to observe.
     */
    public void observeModeTreasureClaim(final Observable<Void> o) {
        o.subscribe(value -> gameMode.set(GameMode.TREASURE));
    }

    /**
     * The controller will subscribe to the provided observable
     * and enable the sending mode of the engine when triggered.
     *
     * @param o - The observable to observe.
     */
    public void observeModeSend(final Observable<Void> o) {
        o.subscribe(value -> gameMode.set(GameMode.SEND));
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
                    break;
                case DRY:
                    if (!engine.dryCell(cell))
                        feedbackObs.set("Vous n'avez pas le droit d'assecher cette tuile !");
                    break;
                case TREASURE:
                    if (cell instanceof TreasureCell) {
                        if (!engine.claimTreasure((TreasureCell) cell))
                            feedbackObs.set("Vous ne pouvez pas recupérer ce trésor !");
                    } else {
                        feedbackObs.set("Ce n'est pas une tuile trésor !");
                    }
                    break;
                default:
                    break;
            }
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
                    }
                    break;
                case MOVE:
                    selectedAdventurer = adventurer;
                    break;
                default:
                    break;
            }
        });
    }

    /**
     * The controller will subscribe to the provided observable
     * and communicate the click of a card to the engine when triggered.
     *
     * @param o - The observable to observe.
     */
    public void observeClickCard(final Observable<TreasureCard> o) {
        o.subscribe(card -> {
            if (gameMode.get() == GameMode.SEND) {
                selectedCard = card;

                if (selectedAdventurer != null) {
                    if (!engine.sendCard(selectedAdventurer, selectedCard))
                        feedbackObs.set("Vous ne pouvez pas envoyer cette carte à cet aventurier !");
                }
            } else if (card instanceof SpecialCard) {
                if (!engine.useCard((SpecialCard) card))
                    feedbackObs.set("Vous ne pouvez pas utiliser cette carte !");
            }
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
            engine.endPlayerRound();
            gameMode.set(GameMode.IDLE);

            engine.startIslandTurn();
            // TODO maybe more end round things
        });
    }

}
