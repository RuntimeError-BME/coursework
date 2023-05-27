package org.runtimeerror.gui.timer;
import org.runtimeerror.gui.frames.GameFrame;
import org.runtimeerror.gui.frames.MenuFrame;
import org.runtimeerror.gui.layout.GridBagConstraintsConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/** TODO: CLASS INFORMATION
 - Ez az osztály valósítja meg a játék virtuális óráját,
   ennek segítségével történnek az időszakos cselekvések,
   valamint egyfajta visszajelzést ad a felhasználónak az eltelt időről
 */


public class GameTimer extends JFrame implements ActionListener {
    /** Óra, perc, másodperc inicializálása */
    private javax.swing.Timer timer;
    private int hours = 0;
    private int minutes = 0;
    private int seconds = 0;

    /** Az órát megjelenítő gomb és az azt tartalmazó panel, valamint a játékosok inventory-jai */
    private JButton button;
    JPanel timePanel;
    JButton exit;
    JButton jbTeamTechnicianInventory; JTextField tfTeamTechnicianInventory;
    JButton jbTeamSaboteurInventory; JTextField tfTeamSaboteurInventory;

    /** Az osztály konstruktora (ténylegesen nem ez az) */
    public GameTimer(GameFrame gf) {
        setTimer(gf);
    }

    /** Az osztály valódi konstruktora - inicializálja az attribútumokat */
    private void setTimer(GameFrame gf) {
        GridBagConstraintsConfig gbc1 = new GridBagConstraintsConfig(0, 100, 0, 0);
        GridBagConstraintsConfig gbc2 = new GridBagConstraintsConfig(0, 0, 0, 100);

        jbTeamTechnicianInventory = new JButton("Team Technician Inventory"); tfTeamTechnicianInventory = new JTextField("xxxxxxxxxx"); tfTeamTechnicianInventory.setEditable(false);
        jbTeamSaboteurInventory = new JButton("Team Saboteur Inventory"); tfTeamSaboteurInventory = new JTextField("yyyyyyyyyy"); tfTeamSaboteurInventory.setEditable(false);

        button = new JButton("Starting Timer...");
        timePanel = new JPanel(); timePanel.setLayout(new GridBagLayout()); timePanel.setOpaque(false);
        timePanel.add(jbTeamTechnicianInventory, gbc1.gbcCentre); timePanel.add(tfTeamTechnicianInventory, gbc2.gbcCentre);

        timePanel.add(button, gbc1.gbcCentre);

        /** Az ablakot vezérlő gombok inicializálása - modifikálása - azok megnyomásával a hozzájuk tartozó ablak elindítása */
        /** Játék mentése, majd vissza a főmenübe */
        exit = new JButton("Save and exit");
        exit.addActionListener(ae -> {
            try {
                MenuFrame menu = new MenuFrame();
                gf.setVisible(false);
                gf.dispose();
            } catch (Exception error) {
                System.out.println("Error while loading menu background image!");
            }
        });
        timePanel.add(exit, gbc2.gbcCentre);

        timePanel.add(tfTeamSaboteurInventory, gbc1.gbcCentre); timePanel.add(jbTeamSaboteurInventory, gbc2.gbcCentre);

        timer = new javax.swing.Timer(1000,this);
        timer.start();
    }

    /** Automatikus szint elérési csekkoló (ugye csak 59-ig megyünk) */
    public void actionPerformed(ActionEvent ae) {
        if (seconds == 59) {
            if (minutes == 59) {
                hours++;
                minutes = 0;
                seconds = 0;
            } else {
                minutes++;
                seconds = 0;
            }
        }
        seconds++;
        button.setText("Time (hours - minutes - seconds) : "+ hours+ " - " + minutes + " - " + seconds);
    }

    /** A csapatok inventory-ját aktualizálja */
    public void ActualiseInventory(String team, String inventory) {
        switch (team) {
            case "Technician":
                tfTeamTechnicianInventory.setText(inventory);
            case "Saboteur":
                tfTeamSaboteurInventory.setText(inventory);
        }
    }

    /** Visszaadja a hordozó ablaknak az órát tartalmazó panelt */
    public JPanel returnPanel() {
        return timePanel;
    }
}