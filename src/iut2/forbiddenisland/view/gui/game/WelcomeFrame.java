/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.ForbiddenIsland;
import iut2.forbiddenisland.model.adventurer.*;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jogak
 */
public class WelcomeFrame extends JFrame {

    private int adventurerAmount;

    private final JPanel panelCenter;
    private final JCheckBox checkBoxDemo;

    private final JComboBox<String> difficultyComboBox;
    private final JComboBox<String> adventurerAmountComboBox;

    private final JPanel[] adventurerPanels;
    private final JTextField[] adventurerNameFields;

    private final JButton start;
    private final JLabel errorLabel;

    public WelcomeFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) (screenSize.getWidth() / 3.0), (int) (screenSize.getHeight() / 2.0));
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Important components

        adventurerAmount = 4;
        panelCenter = new JPanel(new GridLayout(5, 1, 10, 10));
        checkBoxDemo = new JCheckBox();

        adventurerPanels = new JPanel[4];
        adventurerNameFields = new JTextField[4];

        errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(errorLabel.getFont().deriveFont(20f));

        difficultyComboBox = new JComboBox<>(new String[]{"Novice", "Normal", "Elite", "Légendaire"});
        difficultyComboBox.setFont(difficultyComboBox.getFont().deriveFont(20f));

        adventurerAmountComboBox = new JComboBox<>(new String[]{"2", "3", "4"});
        adventurerAmountComboBox.setSelectedIndex(2);
        adventurerAmountComboBox.setFont(adventurerAmountComboBox.getFont().deriveFont(15f));
        adventurerAmountComboBox.addItemListener(e -> {

            for (int i = 0; i < adventurerAmount; i++) {
                panelCenter.remove(adventurerPanels[i]);
            }

            adventurerAmount = adventurerAmountComboBox.getSelectedIndex() + 2;
            for (int i = 0; i < adventurerAmount; i++) {
                panelCenter.add(adventurerPanels[i]);
                adventurerNameFields[i].setText("");
            }

            repaint();
        });


        start = new JButton("Commencer la Partie");
        start.setFont(start.getFont().deriveFont(Font.BOLD));
        start.addActionListener(e -> {
            for (int i = 0; i < adventurerAmount; ++i) {
                if (adventurerNameFields[i].getText().trim().isEmpty()) {
                    errorLabel.setText("Veuillez saisir les noms des joueurs !");
                    return;
                }
            }

            dispose();

            if (checkBoxDemo.isSelected()) {
                startDemo();
            } else {
                startGame();
            }
        });
        start.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    start.doClick();
                }
            }
        });

        // Layout

        final JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);

        final JPanel mainPanel = createMainPanel();
        add(mainPanel, BorderLayout.CENTER);


        final JPanel adventurerAmountPanel = createAdventurerNumberPanel();
        panelCenter.add(adventurerAmountPanel);

        // Spawn player name panels
        for (int i = 0; i < 4; ++i) {
            final JPanel adventurerPanel = new JPanel(new GridLayout(1, 4));

            final JLabel adventurerLabel = new JLabel("Joueur " + (i + 1) + " :", JLabel.CENTER);
            adventurerLabel.setFont(adventurerLabel.getFont().deriveFont(15f));

            final JTextField adventurerNameField = new JTextField();
            adventurerNameField.setFont(adventurerNameField.getFont().deriveFont(15f));
            adventurerNameField.setColumns(10);
            adventurerNameField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                        start.doClick();
                    }
                }
            });

            adventurerPanel.add(new JLabel());
            adventurerPanel.add(adventurerLabel);
            adventurerPanel.add(adventurerNameField);
            adventurerPanel.add(new JLabel());

            adventurerPanels[i] = adventurerPanel;
            this.adventurerNameFields[i] = adventurerNameField;

            panelCenter.add(adventurerPanel);
        }

        mainPanel.add(panelCenter, BorderLayout.CENTER);

        final JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.PAGE_END);
    }

    private JPanel createTopPanel() {
        final JPanel panel = new JPanel();

        final JLabel title = new JLabel("L'île Interdite");
        title.setFont(title.getFont().deriveFont(Font.BOLD).deriveFont(80f));

        panel.add(title);

        return panel;
    }

    private JPanel createMainPanel() {
        final JPanel panel = new JPanel(new GridLayout(2, 1));

        final JPanel difficultyDemoPanel = new JPanel(new GridLayout(2, 1));

        final JPanel difficultyPanel = createDifficultyPanel();
        difficultyDemoPanel.add(difficultyPanel);

        final JPanel demoPanel = createDemoPanel();
        difficultyDemoPanel.add(demoPanel);

        panel.add(difficultyDemoPanel);

        return panel;
    }

    private JPanel createDemoPanel() {
        final JPanel panel = new JPanel();

        final JLabel label = new JLabel("Mode démo");
        label.setFont(label.getFont().deriveFont(20f));
        panel.add(label);

        panel.add(checkBoxDemo);

        return panel;
    }

    private JPanel createDifficultyPanel() {
        final JPanel panel = new JPanel();

        final JLabel label = new JLabel("Difficulté : ");
        label.setFont(label.getFont().deriveFont(20f));
        panel.add(label);

        final JPanel selectionPanel = new JPanel();
        selectionPanel.add(difficultyComboBox);

        panel.add(selectionPanel);

        return panel;
    }

    private JPanel createAdventurerNumberPanel() {
        final JPanel panel = new JPanel(new GridLayout(1, 2));

        final JLabel label = new JLabel("Nombre de joueur : ", SwingConstants.RIGHT);
        label.setFont(label.getFont().deriveFont(Font.BOLD).deriveFont(15f));
        panel.add(label);

        final JPanel comboBoxContainer = new JPanel(new GridLayout(1, 2));
        comboBoxContainer.add(adventurerAmountComboBox);
        comboBoxContainer.add(new JLabel());
        panel.add(comboBoxContainer);

        return panel;
    }

    private JPanel createBottomPanel() {
        final JPanel panel = new JPanel(new BorderLayout());

        final JPanel errorPanel = new JPanel(new BorderLayout());
        errorPanel.add(errorLabel, BorderLayout.CENTER);
        panel.add(errorPanel, BorderLayout.PAGE_START);

        final JPanel panelStartButton = new JPanel(new GridBagLayout());

        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridheight = 1;
        constraints.gridwidth = 10;
        constraints.ipadx = 150;
        constraints.ipady = 25;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(0, 0, 10, 0);

        panelStartButton.add(start, constraints);

        panel.add(panelStartButton, BorderLayout.CENTER);

        return panel;
    }

    private void startGame() {
        // Make a list of every classes of adventurers
        final List<Class<? extends Adventurer>> adventurerClasses = Arrays.asList(
                Diver.class, Engineer.class, Explorer.class, Messenger.class, Navigator.class, Pilot.class
        );

        // Shuffle it
        Collections.shuffle(adventurerClasses);
        // Create an iterator for convenience
        final Iterator<Class<? extends Adventurer>> adventurerClassProvider = adventurerClasses.iterator();

        // Create the adventurers
        final List<Adventurer> adventurers = new ArrayList<>(adventurerAmount);
        for (int i = 0; i < adventurerAmount; i++) {
            adventurers.add(instantiateAdventurer(adventurerClassProvider.next(), adventurerNameFields[i].getText()));
        }

        // Create the game window
        ForbiddenIsland.startGameFrame(adventurers, difficultyComboBox.getSelectedIndex());
    }

    private void startDemo() {
        final int choice = JOptionPane.showOptionDialog(null, "Quel scenario voulez vous utiliser ?", "Choix scénario démo",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[] {
                        "N°1 - Victoire",
                        "N°2 - Défaite"
                }, null);

        if (choice == JOptionPane.CLOSED_OPTION) {
            JOptionPane.showMessageDialog(null, "You didn't select any scenario, aborting...");
            return;
        }

        final List<String> names = Arrays.stream(adventurerNameFields)
                .map(JTextComponent::getText)
                .collect(Collectors.toList());

        if (choice == 0) {
            ForbiddenIsland.startDemo1(names);
        } else {
            ForbiddenIsland.startDemo2(names);
        }
    }

    private Adventurer instantiateAdventurer(final Class<? extends Adventurer> clazz, final String name) {
        try {
            return clazz.getConstructor(String.class).newInstance(name);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            // Nah it will never happen
            throw new IllegalStateException("Failed to instantiate adventurer !", e);
        }
    }
}
