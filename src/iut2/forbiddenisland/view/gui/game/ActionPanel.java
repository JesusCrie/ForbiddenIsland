package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.controller.Controller;
import iut2.forbiddenisland.controller.observer.Observable;
import iut2.forbiddenisland.view.IconGraphicalMetadata;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionPanel extends JPanel {

    private final int width;
    private final int height;

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
        this.width = width;
        this.height = height;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        controller.observeModeMove(obsModeMove);
        controller.observeModeDry(obsModeDry);
        controller.observeModeSend(obsModeSend);
        controller.observeModeTreasureClaim(obsModeClaim);

        remainingActionsText = new JLabel("", SwingConstants.CENTER);

        btnMove = new JButton();
        btnMove.addActionListener(e -> obsModeMove.notifyChanges());

        btnDry = new JButton();
        btnDry.addActionListener(e -> obsModeDry.notifyChanges());

        btnSend = new JButton();
        btnSend.addActionListener(e -> obsModeSend.notifyChanges());

        btnClaim = new JButton();
        btnClaim.addActionListener(e -> obsModeClaim.notifyChanges());

        btnEndRound = new JButton();
        btnEndRound.addActionListener((e -> obsEndRound.notifyChanges()));

    }

    public void setup() {

        final int heightPerButton = height / 6;

        remainingActionsText.setText("32 actions restantes");

        final Dimension dim = new Dimension(width, heightPerButton);
        remainingActionsText.setMinimumSize(dim);
        remainingActionsText.setPreferredSize(dim);
        remainingActionsText.setMaximumSize(dim);

        add(remainingActionsText);

        setupButton(btnMove, "Se Déplacer", IconGraphicalMetadata.ICON_MOVE.getImage(), heightPerButton);
        add(btnMove);

        setupButton(btnDry, "Assecher", IconGraphicalMetadata.ICON_DRY.getImage(), heightPerButton);
        add(btnDry);

        setupButton(btnSend, "Envoyer Carte", IconGraphicalMetadata.ICON_SEND.getImage(), heightPerButton);
        add(btnSend);

        setupButton(btnClaim, "Récupérer Trésor", IconGraphicalMetadata.ICON_CLAIM.getImage(), heightPerButton);
        add(btnClaim);

        setupButton(btnEndRound, "Fin du tour", IconGraphicalMetadata.ICON_DONE.getImage(), heightPerButton);
        btnEndRound.setBackground(Color.ORANGE);
        add(btnEndRound);
    }

    private void setupButton(final JButton btn, final String label, Image icon, final int size) {
        icon = icon.getScaledInstance((int) (size * 0.8), (int) (size * 0.8), Image.SCALE_SMOOTH);

        btn.setHorizontalAlignment(JButton.LEFT);
        btn.setMaximumSize(new Dimension(width, size));
        btn.setIconTextGap(10);
        btn.setIcon(new ImageIcon(icon));
        btn.setText(label);
    }
}
