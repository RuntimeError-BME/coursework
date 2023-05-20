package org.runtimeerror.gui.players;
import org.runtimeerror.gui.layout.GridBagConstraintsConfig;
import javax.swing.*;
import java.awt.*;


/** TODO: CLASS INFORMATION
 - Ez az osztály kezeli a játékosokat a GUI-n TODO: NAGYON NINCS MÉG VÉGIG GONDOLVA
 */


public class Players {
    JPanel panel;
    JButton[] buttons;
    JTextField[] texts;
    int[] points;
    int Active;

    public Players(int numberOfPlayers) {
        panel = new JPanel(); panel.setLayout(new GridBagLayout()); panel.setOpaque(false);
        buttons = new JButton[numberOfPlayers];
        texts = new JTextField[numberOfPlayers];
        GridBagConstraintsConfig gbc1 = new GridBagConstraintsConfig(0, 100, 0, 0);
        GridBagConstraintsConfig gbc2 = new GridBagConstraintsConfig(0, 0, 0, 100);
        points = new int[]{0, 0, 0, 0, 0, 0};

        for (int i = 0; i < numberOfPlayers; i++) {
            buttons[i] = new JButton("Player - " + (i + 1));
            texts[i] = new JTextField("  Points - " + points[i] + "  ");
            panel.add(buttons[i], gbc1.gbcCentre); panel.add(texts[i], gbc2.gbcCentre);
        }
    }

//    public int AddPoint(int idx) {
//        points[idx]++;
//        texts[idx].setText("  Points - " + points[idx] + "  ");
//        return points[idx];
//    }
//
//    public int GetActivate() {
//        return Active;
//    }
//
//    public boolean SetActivate(int idx) {
//        Active = idx;
//        return true;
//    }

    public JPanel ReturnPanel() {
        return panel;
    }
}
