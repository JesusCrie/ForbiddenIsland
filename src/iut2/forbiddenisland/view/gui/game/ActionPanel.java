package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.controller.Controller;
import iut2.forbiddenisland.view.IconGraphicalMetadata;
import iut2.forbiddenisland.view.gui.utils.AutoResizeImageButton;
import iut2.forbiddenisland.view.gui.utils.AutoResizePreserveRatioImagePanel;
import iut2.forbiddenisland.view.gui.utils.ConstraintFactory;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        labDry = new JLabel("Assécher");
        labClear = new JLabel("Récupérer trésor");
        labEnd = new JLabel("Finir tours");
        labMove = new JLabel("Se déplacer");
        labSend = new JLabel("Echanger carte");

        btnDry = new JButton();
        btnSend = new JButton();
        btnMove = new JButton();
        btnEnd = new JButton();
        btnClear = new JButton();

        btnDry.setLayout(new GridBagLayout());
        btnSend.setLayout(new GridBagLayout());
        btnMove.setLayout(new GridBagLayout());
        btnEnd.setLayout(new GridBagLayout());
        btnClear.setLayout(new GridBagLayout());
        //</editor-fold>

        //<editor-fold desc="*** COUNT PANEL ***">
        countAction.setText("Nombres d'action restante : " + controller.getRemainingActions().get());
        countPanel.add(countAction);
        //</editor-fold>

        //<editor-fold desc="*** ACTION PANEL ***">
        btnDry.add(new AutoResizePreserveRatioImagePanel(IconGraphicalMetadata.ICON_DRY.getImage()), ConstraintFactory.fillBoth(0,0,1,1));
        btnDry.add(labDry, ConstraintFactory.fillBoth(1,0,1,1));
        actPanel.add(btnDry, ConstraintFactory.fillHorizontal(0,0,2,1));

        btnMove.add(new AutoResizePreserveRatioImagePanel((IconGraphicalMetadata.ICON_MOVE.getImage())), ConstraintFactory.fillBoth(0,0,1,1));
        btnMove.add(labMove, ConstraintFactory.fillBoth(1,0,1,1));
        actPanel.add(btnMove, ConstraintFactory.fillHorizontal(0,1,2,1));

        btnClear.add(new AutoResizePreserveRatioImagePanel((IconGraphicalMetadata.ICON_GET.getImage())), ConstraintFactory.fillBoth(0,0,1,1));
        btnClear.add(labClear, ConstraintFactory.fillBoth(1,0,1,1));
        actPanel.add(btnClear, ConstraintFactory.fillHorizontal(0,2,2,1));

        btnSend.add(new AutoResizePreserveRatioImagePanel((IconGraphicalMetadata.ICON_GIVE.getImage())), ConstraintFactory.fillBoth(0,0,1,1));
        btnSend.add(labSend, ConstraintFactory.fillBoth(1,0,1,1));
        actPanel.add(btnSend, ConstraintFactory.fillHorizontal(0,3,2,1));

        btnEnd.add(new AutoResizePreserveRatioImagePanel((IconGraphicalMetadata.ICON_SHIFT.getImage())), ConstraintFactory.fillBoth(0,0,1,1));
        btnEnd.add(labEnd, ConstraintFactory.fillBoth(1,0,1,1));
        actPanel.add(btnEnd, ConstraintFactory.fillHorizontal(0,1,2,1));
        //</editor-fold>

        this.setLayout(new GridBagLayout());
        this.add(countPanel, ConstraintFactory.create(1, 0));
        this.add(actPanel, ConstraintFactory.fillBoth(0, 1, 4, 5));

    }
}
