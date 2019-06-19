/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iut2.forbiddenisland.view.gui.game;

import iut2.forbiddenisland.ForbiddenIsland;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;

/**
 * @author jogak
 */
public class StartingFrame extends JFrame {

    private String[] adventurerNames = new String[4];
    private int nbAdventurer;
    private JPanel panelCenter = new JPanel();
    private JLabel[] aventurier = new JLabel[4];
    private JTextField[] name = new JTextField[4];
    private JButton start = new JButton("COMMENCER LA PARTIE");
    private GridBagConstraints c = new GridBagConstraints();
    private JComboBox difficulty;
    private String[] setDifficulty;
    private int levelDifficulty;
    private JCheckBox checkBoxDemo;
    private JLabel erreur;
    private JPanel[] unAventurier = new JPanel[4];
    private JComboBox nbAventurier;
    private JLabel[] numAventurier = new JLabel[4];
    private boolean startgame = false;
    private boolean modeDemo;

    public StartingFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension dim = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) (dim.getWidth() / 3), (int) (dim.getHeight() / 2));

        setLocation((int) (dim.getWidth() / 3), (int) (dim.getHeight() / 4));
        setLayout(new BorderLayout());

        nbAdventurer = 4;
        JPanel panelUp = new JPanel();
        JLabel title = new JLabel("L'île interdite");
        Font f = new Font("Arial", Font.BOLD, 80);
        title.setFont(f);
        panelUp.add(title);

        this.add(panelUp, BorderLayout.NORTH);
        JPanel mainPanel = new JPanel(new GridLayout(2, 1));
        this.add(mainPanel, BorderLayout.CENTER);

        String[] setDifficulty = new String[]{"Novice", "Normal", "Elite", "Légendaire"};
        difficulty = new JComboBox(setDifficulty);
        JPanel difficultyPanel = new JPanel(new FlowLayout());
        JLabel selecDifficulty = new JLabel("Difficulté : ");
        f = new Font("Arial", Font.BOLD, 20);
        selecDifficulty.setFont(f);
        difficultyPanel.add(selecDifficulty);
        JPanel selecDifficultyPanel = new JPanel();
        selecDifficultyPanel.add(difficulty);
        difficulty.setFont(f);
        difficultyPanel.add(selecDifficultyPanel);


        JPanel panelDemo = new JPanel(new FlowLayout());

        JLabel demo = new JLabel("Mode démo");
        demo.setFont(f);
        panelDemo.add(demo);
        checkBoxDemo = new JCheckBox();
        panelDemo.add(checkBoxDemo);
        JPanel difficultyDemoPanel = new JPanel(new GridLayout(2, 1));
        difficultyDemoPanel.add(difficultyPanel);
        difficultyDemoPanel.add(panelDemo);

        mainPanel.add(difficultyDemoPanel);

        JPanel panelCenter = new JPanel(new GridLayout(5, 1, 10, 10));
        mainPanel.add(panelCenter, BorderLayout.CENTER);

        Font font = new Font("Arial", Font.BOLD, 15);
        JPanel selecNbAventurier = new JPanel(new GridLayout(1, 2));

        JLabel selecNbAdv = new JLabel("Nombre de joueur : ", JLabel.RIGHT);
        selecNbAdv.setFont(font);
        selecNbAventurier.add(selecNbAdv);


        String[] nbAv = new String[]{"2", "3", "4"};
        nbAventurier = new JComboBox(nbAv);
        nbAventurier.setSelectedIndex(2);
        nbAventurier.setFont(new Font("Arial", 3, 15));
        JPanel comboBoxNbAventurier = new JPanel(new GridLayout(1, 2));
        comboBoxNbAventurier.add(nbAventurier);
        comboBoxNbAventurier.add(new JLabel(""));
        selecNbAventurier.add(comboBoxNbAventurier);

        panelCenter.add(selecNbAventurier);

        nbAventurier.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                for (int i = 0; i < nbAdventurer; i++) {
                    panelCenter.remove(unAventurier[i]);
                    adventurerNames[i] = new String("");
                }

                repaint();

                nbAdventurer = nbAventurier.getSelectedIndex() + 2;
                for (int i = 0; i < nbAdventurer; i++) {
                    panelCenter.add(unAventurier[i]);
                    name[i].setText("");
                }

            }
        });

        for (int i = 0; i < 4; i++) {
            unAventurier[i] = new JPanel();
            unAventurier[i].setLayout(new GridLayout(1, 4));

            numAventurier[i] = new JLabel("Joueur " + (i + 1) + " : ", JLabel.CENTER);
            numAventurier[i].setFont(font);
            unAventurier[i].add(new JLabel(""));
            unAventurier[i].add(numAventurier[i]);
            name[i] = new JTextField();
            name[i].setFont(font);
            name[i].setColumns(10);
            unAventurier[i].add(name[i]);

            unAventurier[i].add(new JLabel(""));

            panelCenter.add(unAventurier[i]);

        }


        JPanel panelBottom = new JPanel(new BorderLayout());
        this.add(panelBottom, BorderLayout.SOUTH);
        JPanel panelErreur = new JPanel(new BorderLayout());
        erreur = new JLabel(" ", JLabel.CENTER);
        erreur.setForeground(Color.red);
        erreur.setFont(f);

        panelErreur.add(erreur, BorderLayout.CENTER);
        panelBottom.add(panelErreur, BorderLayout.NORTH);

        JPanel panelStartButton = new JPanel(new GridBagLayout());
        panelBottom.add(panelStartButton, BorderLayout.CENTER);
        GridBagConstraints c2 = new GridBagConstraints();

        c2.gridheight = 1;
        c2.gridwidth = 10;
        c2.ipadx = 150;
        c2.ipady = 25;
        c2.gridx = 0;
        c2.gridy = 0;
        c2.insets = new Insets(0, 0, 10, 0);
        start.setFont(new Font("Arial", Font.BOLD, 18));
        panelStartButton.add(start, c2);

        start.addActionListener(e -> {
            int j = 0;
            for (int i = 0; i < 4; i++) {
                if (!name[i].getText().trim().equals("")) {
                    adventurerNames[j] = name[i].getText();
                    j++;
                }
                if (j == nbAdventurer) {
                    startgame = true;
                } else if (j < 2) {
                    erreur.setText("Saisir noms des joueurs");
                } else {
                    erreur.setText("Votre nombre d'aventurier sélectionné ne correspond pas");
                }

            }

            if (isStartgame()) {
                levelDifficulty = difficulty.getSelectedIndex();
                modeDemo = checkBoxDemo.isSelected();
                dispose();
                ForbiddenIsland.startGameFrame(Arrays.asList(adventurerNames));
            }
        });
    }

    public String[] getAdventurerNames() {
        return adventurerNames;
    }

    public int getLevelDifficulty() {
        return levelDifficulty;
    }

    public boolean isStartgame() {
        return startgame;
    }

    public boolean isModeDemo() {
        return modeDemo;
    }
}
