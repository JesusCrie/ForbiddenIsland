package iut2.forbiddenisland.view.cli;

import iut2.forbiddenisland.Pair;
import iut2.forbiddenisland.controller.Controller;
import iut2.forbiddenisland.controller.observer.Observable;
import iut2.forbiddenisland.model.Location;
import iut2.forbiddenisland.model.Treasure;
import iut2.forbiddenisland.model.adventurer.Adventurer;
import iut2.forbiddenisland.model.card.SpecialCard;
import iut2.forbiddenisland.model.cell.Cell;
import iut2.forbiddenisland.model.cell.CellState;

import java.util.List;
import java.util.Map;

public class CliView {

    private final Observable<Cell> obsClickCell = new Observable<>();
    private final Observable<Adventurer> obsClickPlayer = new Observable<>();
    private final Observable<SpecialCard> obsClickCard = new Observable<>();
    private final Observable<Void> obsEndRound = new Observable<>();

    private final Observable<Void> obsModeMove = new Observable<>();
    private final Observable<Void> obsModeDry = new Observable<>();
    private final Observable<Void> obsModeSend = new Observable<>();
    private final Observable<Void> obsModeClaim = new Observable<>();

    private Map<Location, Cell> cells;
    private List<Adventurer> players;
    private Adventurer currentPlayer;
    private List<Treasure> treasures;
    private int remainingActions;

    public CliView(final Controller controller) {
        controller.observeClickCell(obsClickCell);
        controller.observeClickPlayer(obsClickPlayer);
        controller.observeClickCard(obsClickCard);
        controller.observeClickEndRound(obsEndRound);

        controller.observeModeMove(obsModeMove);
        controller.observeModeDry(obsModeDry);
        controller.observeModeSend(obsModeSend);
        controller.observeModeTreasureClaim(obsModeClaim);

        controller.getCells().subscribe(val -> cells = val);
        controller.getAdventurers().subscribe(val -> players = val);
        controller.getCurrentAdventurer().subscribe(val -> currentPlayer = val);
        controller.getTreasures().subscribe(val -> treasures = val);
        controller.getRemainingActions().subscribe(val -> remainingActions = val);
    }

    public void start() {

    }

    private void round() {
        final String board = ConsoleRenderer.drawBoard(cells, treasures, players);
        System.out.println(board);
        System.out.println("\n== Tour de: " + currentPlayer.getName() + " ==\n");

        CliPrompter.createMenu("Choisissez une action [" + remainingActions + " restantes]",
                Pair.of("Bouger", this::moveMode),
                Pair.of("Assecher", this::dryMode),
                Pair.of("Envoyer une carte", this::sendMode),
                Pair.of("Récuperer un trésor", this::claimMode)
        );
    }

    private void moveMode() {
        obsModeMove.notifyChanges();
        System.out.println("== Ou voulez vous aller ? ==");

        final Location position = currentPlayer.getPosition().getLocation();
        System.out.println("Vous êtes en X = " + position.getX() + ", Y = " + position.getY());
        Cell to;

        do {
            System.out.println("Entrez les coordonnées d'une case existante");
            final int toX = CliPrompter.askPositiveInt("X ?", 5);
            final int toY = CliPrompter.askPositiveInt("Y ?", 5);

            to = cells.get(Location.from(toX, toY));

        } while (to == null);

        obsClickCell.set(to);
    }

    private void dryMode() {
        obsModeDry.notifyChanges();
        System.out.println("== Quelle case voulez vous assecher ? ==");

        final Location position = currentPlayer.getPosition().getLocation();
        System.out.println("Vous êtes en X = " + position.getX() + ", Y = " + position.getY());
        Cell to;

        do {
            System.out.println("Entrez les coordonnées d'une case inondée");
            final int toX = CliPrompter.askPositiveInt("X ?", 5);
            final int toY = CliPrompter.askPositiveInt("Y ?", 5);

            to = cells.get(Location.from(toX, toY))

        } while (to == null || to.getState() == CellState.WET);

        obsClickCell.set(to);
    }

    private void sendMode() {
        obsModeSend.notifyChanges();
        System.out.println("== Quelle carte voulez vous envoyer ? ==");
        // TODO
    }

    private void claimMode() {

    }
}
