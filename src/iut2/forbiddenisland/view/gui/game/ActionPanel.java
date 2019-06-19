package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class ActionPanel extends JPanel {
    private final JPanel countPanel;
    private final JPanel actPanel;

    private final JLabel countAction;

    private final JButton btnDry;
    private final JButton btnSend;
    private final JButton btnMove;
    private final JButton btnClear;
    private final JButton btnEnd;

    public ActionPanel(final Controller controller) {
        //<editor-fold desc="*** INITIALISATION ***">
        countPanel = new JPanel(new BorderLayout());
        actPanel = new JPanel(new GridLayout(3,3));

        countAction = new JLabel();
        countAction.setHorizontalAlignment((int) CENTER_ALIGNMENT);

        btnDry = new JButton("Assecher");
        btnClear = new JButton("Récuperer tresor");
        btnEnd = new JButton("Finir Tours");
        btnMove = new JButton("Se deplacer");
        btnSend = new JButton("echanger carte");
        //</editor-fold>

        //<editor-fold desc="*** COUNT PANEL ***">
        countAction.setText("Nombres d'action restante : " + controller.getRemainingActions().get());
        countPanel.add(countAction, BorderLayout.CENTER);
        //</editor-fold>

        actPanel.add(new JPanel());
        actPanel.add(btnDry);
        actPanel.add(new JPanel());

        actPanel.add(btnClear);
        actPanel.add(btnEnd);
        actPanel.add(btnMove);

        actPanel.add(new JPanel());
        actPanel.add(btnSend);
        actPanel.add(new JPanel());

        this.setLayout(new BorderLayout());
        this.add(countPanel, BorderLayout.NORTH);
        this.add(actPanel, BorderLayout.CENTER);

    }
}
