package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.controller.Controller;
import iut2.forbiddenisland.view.TreasureCardGraphicalMetadata;
import iut2.forbiddenisland.view.gui.utils.AutoResizeImageButton;
import iut2.forbiddenisland.view.gui.utils.ConstraintFactory;

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

    private final JLabel labDry;
    private final JLabel labEnd;
    private final JLabel labClear;
    private final JLabel labMove;
    private final JLabel labSend;

    public ActionPanel(final Controller controller) {
        //<editor-fold desc="*** INITIALISATION ***">
        countPanel = new JPanel(new GridBagLayout());
        actPanel = new JPanel(new GridBagLayout());

        countAction = new JLabel();
        countAction.setHorizontalAlignment((int) CENTER_ALIGNMENT);

        labDry = new JLabel("Assecher");
        labClear = new JLabel("Récuperer tresor");
        labEnd = new JLabel("Finir Tours");
        labMove = new JLabel("Se deplacer");
        labSend = new JLabel("echanger carte");

        btnDry = new AutoResizeImageButton(TreasureCardGraphicalMetadata.ICON_DRY.getImage());
        btnSend = new AutoResizeImageButton(TreasureCardGraphicalMetadata.ICON_GIVE.getImage());
        btnMove = new AutoResizeImageButton(TreasureCardGraphicalMetadata.ICON_MOVE.getImage());
        btnEnd = new AutoResizeImageButton(TreasureCardGraphicalMetadata.ICON_SHIFT.getImage());
        btnClear = new AutoResizeImageButton(TreasureCardGraphicalMetadata.ICON_GET.getImage());
        //</editor-fold>

        //<editor-fold desc="*** COUNT PANEL ***">
        countAction.setText("Nombres d'action restante : " + controller.getRemainingActions().get());
        countPanel.add(countAction);
        //</editor-fold>

        actPanel.add(labDry, ConstraintFactory.fillHorizontal(0,0,1,1));
        actPanel.add(labClear, ConstraintFactory.fillHorizontal(0,1,1,1));
        actPanel.add(labMove, ConstraintFactory.fillHorizontal(0,2,1,1));
        actPanel.add(labSend, ConstraintFactory.fillHorizontal(0,3,1,1));
        actPanel.add(labEnd, ConstraintFactory.fillHorizontal(0,4,1,1));

        actPanel.add(btnDry, ConstraintFactory.fillBoth(2,0,1,1));
        actPanel.add(btnClear, ConstraintFactory.fillBoth(2,1,1,1));
        actPanel.add(btnMove, ConstraintFactory.fillBoth(2,2,1,1));
        actPanel.add(btnSend, ConstraintFactory.fillBoth(2,3,1,1));
        actPanel.add(btnEnd, ConstraintFactory.fillBoth(2,4,1,1));

        this.setLayout(new GridBagLayout());
        this.add(countPanel, ConstraintFactory.create(1,0));
        this.add(actPanel, ConstraintFactory.fillBoth(0,1,3,5));

    }
}
