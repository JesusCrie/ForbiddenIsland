/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iut2.forbiddenisland.view.gui.game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;

/**
 *
 * @author jogak
 */
public class StartingFrame extends JFrame {

    public String[] adventurerName = new String[4];
    private int nbAdventurer;
    private JPanel panelCenter = new JPanel();
    private JLabel[] aventurier = new JLabel[4];
    private JTextField[] name = new JTextField[4];
    private JButton start = new JButton("COMMENCER LA PARTIE");
    private GridBagConstraints c = new GridBagConstraints();
    private JComboBox difficulty;
    private String[] setDifficulty;
    public int levelDifficulty;
    private JLabel erreur;
    private JPanel[] unAventurier = new JPanel[4];
    private JComboBox nbAventurier;
    private JLabel[] numAventurier = new JLabel[4];

    public StartingFrame() {


        setSize(800, 800);
        setLayout(new BorderLayout());

        nbAdventurer = 4;
        JPanel panelUp = new JPanel();
        JLabel title = new JLabel("L'île interdite");
        Font f = new Font("Arial", Font.BOLD, 80);
        title.setFont(f);
        panelUp.add(title);

        this.add(panelUp, BorderLayout.NORTH);
        JPanel mainPanel = new JPanel(new GridLayout(3, 1));
        this.add(mainPanel, BorderLayout.CENTER);

        String[] setDifficulty = new String[]{"Novice", "Normal", "Elite", "Légendaire"};
        difficulty = new JComboBox(setDifficulty);
        JPanel difficultyPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JLabel selecDifficulty = new JLabel("Sélection de la difficulté : ");
        f = new Font("Arial", Font.BOLD, 20);
        selecDifficulty.setFont(f);
        difficultyPanel.add(selecDifficulty);
        JPanel selecDifficultyPanel  = new JPanel(new GridLayout(3,1));
        selecDifficultyPanel.add(new JLabel(""));
        selecDifficultyPanel.add(difficulty);
        selecDifficultyPanel.add(new JLabel(""));
        difficulty.setFont(f);
        difficultyPanel.add(selecDifficultyPanel);

        mainPanel.add(difficultyPanel);

        JPanel panelCenter = new JPanel(new GridLayout(5, 1, 10, 10));
        mainPanel.add(panelCenter, BorderLayout.CENTER);
        erreur = new JLabel("");
        erreur.setForeground(Color.red);
        erreur.setFont(f);
        mainPanel.add(erreur);
        Font font = new Font("Arial", Font.BOLD, 20);
        JPanel selecNbAventurier = new JPanel(new GridLayout(1,2));
        JLabel selecNbAdv = new JLabel("Sélection du nombre d'aventurier : ");
        selecNbAdv.setFont(font);
        selecNbAventurier.add(selecNbAdv);

        String[] nbAv = new String[]{"2","3","4"};
        nbAventurier = new JComboBox(nbAv);
        nbAventurier.setSelectedIndex(2);
        nbAventurier.setFont(new Font("Arial", 3, 15    ));
        selecNbAventurier.add(nbAventurier);
        panelCenter.add(selecNbAventurier);

        nbAventurier.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                for (int i = 0 ; i< nbAdventurer;i++){
                    panelCenter.remove(unAventurier[i]);
                }

                repaint();

                nbAdventurer = nbAventurier.getSelectedIndex()+2;
                for(int i = 0 ; i < nbAdventurer;i++){
                    panelCenter.add(unAventurier[i]);
                }

            }
        });

        for (int i = 0; i < 4; i++) {
            unAventurier[i] = new JPanel();
            unAventurier[i].setLayout(new GridLayout(1,2));
            numAventurier[i] = new JLabel("Aventurier " + (i + 1) + " : ");
            numAventurier[i].setFont(font);
            unAventurier[i].add(numAventurier[i]);
            name[i] = new JTextField();
            name[i].setFont(font);
            name[i].setColumns(10);
            unAventurier[i].add(name[i]);

            panelCenter.add(unAventurier[i]);

        }


        JPanel panelBottom = new JPanel(new GridBagLayout());
        this.add(panelBottom, BorderLayout.SOUTH);

        GridBagConstraints c2 = new GridBagConstraints();

        c2.gridheight = 1;
        c2.gridwidth = 10;
        c2.ipadx = 150;
        c2.ipady = 20;
        c2.gridx = 0;
        c2.gridy = 0;
        c2.insets = new Insets(0, 0, 10, 0);
        start.setFont(new Font("Arial", Font.BOLD, 18));
        panelBottom.add(start, c2);

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int j = 0;
                boolean startgame = false;
                for (int i = 0; i < 4; i++) {
                    if (!name[i].getText().trim().equals("")) {
                        adventurerName[j] = name[i].getText();
                        System.out.println(adventurerName[j]);
                        j++;
                    }
                    if (j == nbAdventurer) {
                        startgame = true;
                    } else if (j < 2) {
                        erreur.setText("Nombre de joueur insuffisant");
                    }
                    else{
                        erreur.setText("Votre nombre d'aventurier sélectionné ne correspond pas");
                    }

                }
                if (startgame) {
                    levelDifficulty = difficulty.getSelectedIndex();
                    dispose();
                }
            }
        });

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
