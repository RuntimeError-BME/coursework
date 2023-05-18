package org.runtimeerror.gui.timer;
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

    /** Az órát megjelenítő gomb és az azt tartalmazó panel */
    private JButton button;
    JPanel timePanel;

    /** Az osztály konstruktora (ténylegesen nem ez az) */
    public GameTimer() {
        setTimer();
    }

    /** Az osztály valódi konstruktora - inicializálja az attribútumokat */
    private void setTimer() {
        button = new JButton("Starting Timer...");
        timePanel = new JPanel(); timePanel.setLayout(new GridBagLayout()); timePanel.setOpaque(false);
        timePanel.add(button);
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

    /** Visszaadja a hordozó ablaknak az órát tartalmazó panelt */
    public JPanel returnPanel() {
        return timePanel;
    }
}