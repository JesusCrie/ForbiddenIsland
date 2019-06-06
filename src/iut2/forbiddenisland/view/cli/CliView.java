package iut2.forbiddenisland.view.cli;

import iut2.forbiddenisland.Pair;
import iut2.forbiddenisland.controller.Controller;
import iut2.forbiddenisland.controller.observer.Observable;
import iut2.forbiddenisland.model.Location;
import iut2.forbiddenisland.model.Treasure;
import iut2.forbiddenisland.model.adventurer.Adventurer;
import iut2.forbiddenisland.model.card.TreasureCard;
import iut2.forbiddenisland.model.cell.Cell;
import iut2.forbiddenisland.model.cell.CellState;

import java.util.List;
import java.util.Map;

public class CliView {

    private final Observable<Cell> obsClickCell = new Observable<>();
    private final Observable<Adventurer> obsClickPlayer = new Observable<>();
    private final Observable<TreasureCard> obsClickCard = new Observable<>();
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
    private boolean gameOn = true;

    public CliView(final Controller controller) {
        controller.observeClickCell(obsClickCell);
        controller.observeClickPlayer(obsClickPlayer);
        controller.observeClickCard(obsClickCard);
        controller.observeClickEndRound(obsEndRound);

        controller.observeModeMove(obsModeMove);
        controller.observeModeDry(obsModeDry);
        controller.observeModeSend(obsModeSend);
        controller.observeModeTreasureClaim(obsModeClaim);

        controller.getFeedbackObservable().subscribe(val -> System.out.println("\n" + val + "\n"));

        controller.getCells().subscribe(val -> cells = val);
        controller.getAdventurers().subscribe(val -> players = val);
        controller.getCurrentAdventurer().subscribe(val -> currentPlayer = val);
        controller.getTreasures().subscribe(val -> treasures = val);
        controller.getRemainingActions().subscribe(val -> remainingActions = val);
        controller.getEndGameObservable().subscribe(val -> {
            if (val == null)
                return;

            gameOn = false;

            if (val)
                System.out.println("****** Les joueurs ont gagnés ! ******");
            else
                System.out.println("****** L'Ile a gagner... ******");
        });
    }

    public void start() {
        while (gameOn) {
            roundAction();
        }
    }

    private void roundAction() {
        if (remainingActions <= 0) {
            roundEnd();
            return;
        }

        final String board = ConsoleRenderer.drawBoard(cells, treasures, players);
        System.out.println(board);
        System.out.println("\n== Tour de: " + currentPlayer.getName() + " ==\n");

        CliPrompter.createMenu("Choisissez une action [" + remainingActions + " restantes]",
                Pair.of("Bouger", this::moveMode),
                Pair.of("Assecher", this::dryMode),
                Pair.of("Envoyer une carte", this::sendMode),
                Pair.of("Récuperer un trésor", this::claimMode),
                Pair.of("Fin du tour", this::roundEnd)
        );
    }

    private void roundEnd() {
        obsEndRound.notifyChanges();
    }

    private void moveMode() {
        obsModeMove.notifyChanges();
        obsClickPlayer.set(currentPlayer);
        System.out.println("== Ou voulez vous aller ? ==");

        final Location position = currentPlayer.getPosition().getLocation();
        System.out.println("Vous êtes en X = " + position.getX() + ", Y = " + position.getY());
        Cell to;

        do {
            System.out.println("Entrez les coordonnées d'une case existante");
            final int toX = CliPrompter.askPositiveInt("X ?", 6);
            final int toY = CliPrompter.askPositiveInt("Y ?", 6);

            to = cells.get(Location.from(toX, toY));

        } while (to == null);

        obsClickCell.set(to);

        // Back to round menu
        roundAction();
    }

    private void dryMode() {
        obsModeDry.notifyChanges();
        System.out.println("== Quelle case voulez vous assecher ? ==");

        final Location position = currentPlayer.getPosition().getLocation();
        System.out.println("Vous êtes en X = " + position.getX() + ", Y = " + position.getY());
        Cell to;

        do {
            System.out.println("Entrez les coordonnées d'une case inondée");
            final int toX = CliPrompter.askPositiveInt("X ?", 6);
            final int toY = CliPrompter.askPositiveInt("Y ?", 6);

            to = cells.get(Location.from(toX, toY));

        } while (to == null || to.getState() != CellState.WET);

        obsClickCell.set(to);

        // Back to round menu
        roundAction();
    }

    @SuppressWarnings("unchecked")
    private void sendMode() {
        obsModeSend.notifyChanges();
        System.out.println("== Quelle carte voulez vous envoyer ? ==");

        final Pair<String, Runnable>[] actionCards = currentPlayer.getCards().stream()
                .map(card -> Pair.of(card.getName(), (Runnable) () -> obsClickCard.set(card)))
                .toArray(Pair[]::new);

        CliPrompter.createMenu("Selectionnez une carte", actionCards);

        final Pair<String, Runnable>[] actionPlayers = players.stream()
                .filter(pl -> pl != currentPlayer)
                .map(pl -> Pair.of(pl.getName(), (Runnable) () -> obsClickPlayer.set(pl)))
                .toArray(Pair[]::new);

        CliPrompter.createMenu("Selectionnez un destinataire", actionPlayers);

        // Back to round menu
        roundAction();
    }

    private void claimMode() {
        obsModeClaim.notifyChanges();

        System.out.println("== Vous essayer de recuperer le trésor sur votre tuile ==");
        obsClickCell.set(currentPlayer.getPosition());

        // Back to round menu
        roundAction();
    }
}
