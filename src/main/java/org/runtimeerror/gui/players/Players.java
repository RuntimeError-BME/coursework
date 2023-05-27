package org.runtimeerror.gui.players;
import org.runtimeerror.gui.layout.GridBagConstraintsConfig;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


/** TODO: CLASS INFORMATION
 - Ez az osztály kezeli a játékosokat a GUI-n TODO: NAGYON NINCS MÉG VÉGIG GONDOLVA
 */


public class Players {
    JPanel panel;
    JButton[] buttons;
    JTextField[] texts; JTextField gameplay;
    int[] points;

    public Players(int numberOfPlayers) {
        panel = new JPanel(); panel.setLayout(new GridBagLayout()); panel.setOpaque(false);
        buttons = new JButton[2];
        texts = new JTextField[2];
        gameplay = new JTextField();
        GridBagConstraintsConfig gbc1 = new GridBagConstraintsConfig(0, 100, 0, 0);
        GridBagConstraintsConfig gbc2 = new GridBagConstraintsConfig(0, 0, 0, 100);
        GridBagConstraintsConfig gbc3 = new GridBagConstraintsConfig(0, 100, 0, 100);
        points = new int[]{0, 0};

        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                buttons[i] = new JButton("Team Technician");
                buttons[i].setBorder(BorderFactory.createLineBorder(Color.blue, 5));
                texts[i] = new JTextField("  Points - " + points[i] + "  "); texts[i].setEditable(false);
                panel.add(buttons[i], gbc1.gbcCentre); panel.add(texts[i], gbc2.gbcCentre);

                gameplay.setText("Gameplay"); gameplay.setEditable(false); panel.add(gameplay, gbc3.gbcCentre);
            }
            if (i == 1) {
                buttons[i] = new JButton("Team Saboteur");
                buttons[i].setBorder(BorderFactory.createLineBorder(Color.black, 5));
                texts[i] = new JTextField("  Points - " + points[i] + "  ");
                panel.add(buttons[i], gbc1.gbcCentre); panel.add(texts[i], gbc2.gbcCentre);
            }
        }
    }

    /** A backend által meghatározott pontok csapatok pontjaihoz való adását intézi */
    public int AddPoint(int idx, int nPoints) {
        points[idx] += nPoints;
        texts[idx].setText("  Points - " + points[idx] + "  ");
        return points[idx];
    }

    /** Beállítja az információs sávot a prototípus általi visszajelzett aktuális értékre */
    public void SetInformation(String info) {
        gameplay.setText(info);
    }

    /** Beállítja a soron következő csapat színét a gameplay keretének */
    public void SetCurrentTeam(String playerType) {
        switch (playerType) {
            case "Technician":
                gameplay.setBorder(BorderFactory.createLineBorder(Color.blue, 5));
                break;
            case "Saboteur":
                gameplay.setBorder(BorderFactory.createLineBorder(Color.black, 5));
                break;
        }
    }

    /** Visszaadja a csapatokat és a játék menedzselő elemeket tartalmazó panelt */
    public JPanel ReturnPanel() {
        return panel;
    }
}
