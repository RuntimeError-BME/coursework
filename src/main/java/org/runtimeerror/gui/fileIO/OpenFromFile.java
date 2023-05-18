package org.runtimeerror.gui.fileIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


/** TODO: CLASS INFORMATION
 - Lehetővé teszi, hogy a felhasználó elmentse, illetve folytatni tudja a játékát
 - Ehhez az osztály létrehoz egy új ablakot, amelyben a felhasználó ki tudja választani a fájlkezelőjéből
   a betöltendő játékot - Ez a funkció szálkezeléssel van megvalósítva, ahhoz hogy a háttér ablak is megfelelően működjön
 */


public class OpenFromFile extends JFrame {
    private JTextField filename = new JTextField(), dir = new JTextField();
    private JButton open = new JButton("Open"), save = new JButton("Save");

    public OpenFromFile() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(300,300));
        this.setMaximumSize(new Dimension(300,300));
        this.setMinimumSize(new Dimension(300,300));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        JPanel p = new JPanel();
        open.addActionListener(new OpenL());
        p.add(open);
        Container cp = getContentPane();
        cp.add(p, BorderLayout.SOUTH);
        dir.setEditable(false);
        filename.setEditable(false);
        p = new JPanel();
        p.setLayout(new GridLayout(2, 1));
        p.add(filename);
        p.add(dir);
        cp.add(p, BorderLayout.NORTH);
    }

    class OpenL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser c = new JFileChooser();
            c.setPreferredSize(new Dimension(1000, 400));

            int rVal = c.showOpenDialog(OpenFromFile.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                filename.setText(c.getSelectedFile().getName());
                dir.setText(c.getCurrentDirectory().toString());
            }
            if (rVal == JFileChooser.CANCEL_OPTION) {
                filename.setText("You pressed cancel");
                dir.setText("");
            }
        }
    }

    class SaveL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser c = new JFileChooser();

            int rVal = c.showSaveDialog(OpenFromFile.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                filename.setText(c.getSelectedFile().getName());
                dir.setText(c.getCurrentDirectory().toString());
            }
            if (rVal == JFileChooser.CANCEL_OPTION) {
                filename.setText("You pressed cancel");
                dir.setText("");
            }
        }
    }
}
