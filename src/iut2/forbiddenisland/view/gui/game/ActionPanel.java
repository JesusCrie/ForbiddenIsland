package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.controller.Controller;
import iut2.forbiddenisland.controller.GameMode;
import iut2.forbiddenisland.controller.observer.Observable;
import iut2.forbiddenisland.view.IconGraphicalMetadata;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

public class ActionPanel extends JPanel {

    private final JLabel remainingActionsText;
    private final JButton btnMove;
    private final JButton btnDry;
    private final JButton btnSend;
    private final JButton btnClaim;
    private final JButton btnEndRound;

    private final Observable<Void> obsModeMove = new Observable<>();
    private final Observable<Void> obsModeDry = new Observable<>();
    private final Observable<Void> obsModeSend = new Observable<>();
    private final Observable<Void> obsModeClaim = new Observable<>();
    private final Observable<Void> obsEndRound = new Observable<>();

    public ActionPanel(final Controller controller, final int width, final int height) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        remainingActionsText = new JLabel("", SwingConstants.CENTER);


        final int heightPerButton = height / 6;

        remainingActionsText.setText("3 actions restantes");

        final Dimension dim = new Dimension(width, heightPerButton);
        remainingActionsText.setMinimumSize(dim);
        remainingActionsText.setPreferredSize(dim);
        remainingActionsText.setMaximumSize(dim);

        add(remainingActionsText);

        btnMove = createButton("Se Déplacer", IconGraphicalMetadata.ICON_MOVE.getImage(), width, heightPerButton);
        btnMove.addActionListener(e -> obsModeMove.notifyChanges());
        add(btnMove);

        btnDry = createButton("Assecher", IconGraphicalMetadata.ICON_DRY.getImage(), width, heightPerButton);
        btnDry.addActionListener(e -> obsModeDry.notifyChanges());
        add(btnDry);

        btnSend = createButton("Envoyer Carte", IconGraphicalMetadata.ICON_SEND.getImage(), width, heightPerButton);
        btnSend.addActionListener(e -> obsModeSend.notifyChanges());
        add(btnSend);

        btnClaim = createButton("Récupérer Trésor", IconGraphicalMetadata.ICON_CLAIM.getImage(), width, heightPerButton);
        btnClaim.addActionListener(e -> obsModeClaim.notifyChanges());
        add(btnClaim);

        btnEndRound = createButton("Fin du tour", IconGraphicalMetadata.ICON_DONE.getImage(), width, heightPerButton);
        btnEndRound.addActionListener((e -> obsEndRound.notifyChanges()));
        btnEndRound.setBackground(Color.ORANGE);
        add(btnEndRound);

        controller.observeModeMove(obsModeMove);
        controller.observeModeDry(obsModeDry);
        controller.observeModeSend(obsModeSend);
        controller.observeModeTreasureClaim(obsModeClaim);
        controller.observeClickEndRound(obsEndRound);

        controller.getRemainingActions().subscribe(remaining ->
                SwingUtilities.invokeLater(() -> remainingActionsText.setText(remaining + " actions restantes"))
        );
        controller.getGameMode().subscribe(gameMode ->
            SwingUtilities.invokeLater(() -> highlightGameMode(gameMode))
        );
    }

    private void highlightGameMode(final GameMode gameMode) {
        btnMove.setBackground(Color.WHITE);
        btnDry.setBackground(Color.WHITE);
        btnSend.setBackground(Color.WHITE);
        btnClaim.setBackground(Color.WHITE);

        switch (gameMode) {
            case MOVE:
                btnMove.setBackground(Color.GREEN);
                break;
            case DRY:
                btnDry.setBackground(Color.GREEN);
                break;
            case SEND:
                btnSend.setBackground(Color.GREEN);
                break;
            case TREASURE:
                btnClaim.setBackground(Color.GREEN);
                break;
            case IDLE:
            default:
                break;
        }
    }

    private JButton createButton(final String label, Image icon, final int width, final int height) {
        final JButton btn = new JButton();

        icon = icon.getScaledInstance((int) (height * 0.8), (int) (height * 0.8), Image.SCALE_SMOOTH);

        btn.setHorizontalAlignment(JButton.LEFT);
        btn.setMaximumSize(new Dimension(width, height));
        btn.setIconTextGap(10);
        btn.setIcon(new ImageIcon(icon));
        btn.setText(label);

        return btn;
    }
}
