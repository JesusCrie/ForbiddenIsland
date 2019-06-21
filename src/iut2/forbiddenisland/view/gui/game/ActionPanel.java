package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.controller.GameMode;
import iut2.forbiddenisland.view.IconGraphicalMetadata;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

public class ActionPanel extends JPanel {

    private final JLabel remainingActionsText;
    private final JButton buttonMove;
    private final JButton buttonDry;
    private final JButton buttonSend;
    private final JButton buttonClaim;
    private final JButton buttonEndRound;

    public ActionPanel(final int width, final int height) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final int heightPerButton = height / 6;

        remainingActionsText = new JLabel("", SwingConstants.CENTER);
        remainingActionsText.setText("3 actions restantes");
        remainingActionsText.setMaximumSize(new Dimension(width, heightPerButton));
        add(remainingActionsText);

        buttonMove = createButton("Se Déplacer", IconGraphicalMetadata.ICON_MOVE.getImage(), width, heightPerButton);
        add(buttonMove);

        buttonDry = createButton("Assecher", IconGraphicalMetadata.ICON_DRY.getImage(), width, heightPerButton);
        add(buttonDry);

        buttonSend = createButton("Envoyer Carte", IconGraphicalMetadata.ICON_SEND.getImage(), width, heightPerButton);
        add(buttonSend);

        buttonClaim = createButton("Récupérer Trésor", IconGraphicalMetadata.ICON_CLAIM.getImage(), width, heightPerButton);
        add(buttonClaim);

        buttonEndRound = createButton("Fin du tour", IconGraphicalMetadata.ICON_DONE.getImage(), width, heightPerButton);
        buttonEndRound.setBackground(Color.ORANGE);
        add(buttonEndRound);
    }

    public JButton getButtonMove() {
        return buttonMove;
    }

    public JButton getButtonDry() {
        return buttonDry;
    }

    public JButton getButtonSend() {
        return buttonSend;
    }

    public JButton getButtonClaim() {
        return buttonClaim;
    }

    public JButton getButtonEndRound() {
        return buttonEndRound;
    }

    public void setRemainingActions(final int amount) {
        remainingActionsText.setText(amount + " actions restantes");
    }

    public void highlightGameMode(final GameMode gameMode) {
        buttonMove.setBackground(Color.WHITE);
        buttonDry.setBackground(Color.WHITE);
        buttonSend.setBackground(Color.WHITE);
        buttonClaim.setBackground(Color.WHITE);

        switch (gameMode) {
            case MOVE:
                buttonMove.setBackground(Color.GREEN);
                break;
            case DRY:
                buttonDry.setBackground(Color.GREEN);
                break;
            case SEND:
                buttonSend.setBackground(Color.GREEN);
                break;
            case TREASURE:
                buttonClaim.setBackground(Color.GREEN);
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
        btn.setBackground(Color.WHITE);

        return btn;
    }
}
