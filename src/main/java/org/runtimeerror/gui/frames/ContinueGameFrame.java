package org.runtimeerror.gui.frames;
import org.runtimeerror.gui.backgrounds.SetBackgroundImage;
import org.runtimeerror.gui.layout.GridBagConstraintsConfig;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


/** TODO: CLASS INFORMATION
 - Ez az osztály tölti be a felhasználó által elmentett, illetve folytatni kívánt játékot
 */


public class ContinueGameFrame extends JFrame {
    /** A mindent magába foglaló konténer */
    Container con;

    /** Az ablakon használt régiók paneljai */
    JPanel mainPanel; JPanel utp; JPanel ltp;

    /** Az ablakon használt elemek */
    JLabel upperText;
    JButton backButton;

    /** Az osztály konstruktora - inicializálja az elemeket */
    public ContinueGameFrame() throws IOException {
        /** Alap ablak beállítások megadása */
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("Continue Game");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /** Konténer inicializálása */
        con = getContentPane();

        /** Az ablakban használt layout megvalósítása Gbc-vel */
        GridBagConstraintsConfig gbc1 = new GridBagConstraintsConfig(0, 0, 340, 0);
        GridBagConstraintsConfig gbc2 = new GridBagConstraintsConfig(300, 0, 0, 0);

        /** Panelok inicializálása - modifikálása */
        mainPanel = new JPanel(); mainPanel.setLayout(new GridBagLayout()); mainPanel.setOpaque(false);
        utp = new JPanel(); utp.setLayout(new GridBagLayout()); utp.setOpaque(false);
        ltp = new JPanel(); ltp.setLayout(new GridBagLayout()); ltp.setOpaque(false);

        /** Elemek inicializálása - modifikálása */
        upperText = new JLabel("Continue Game");
        upperText.setFont(new Font("Monospaced", Font.BOLD, 80));
        upperText.setBorder(null); upperText.setBackground(new Color(0, 0, 0, 0));

        /** Az ablakot vezérlő gombok inicializálása - modifikálása - azok megnyomásával a hozzájuk tartozó ablak elindítása */
        /** Vissza a főmenübe */
        backButton = new JButton("Back"); backButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
        backButton.addActionListener(e -> {
            try {
                MenuFrame menu = new MenuFrame();
                this.setVisible(false);
                this.dispose();
            } catch (Exception error) {
                System.out.println("Error while loading menu background image!");
            }
        });

        /** Az elemek paneljeikhez adása */
        utp.add(upperText);
        ltp.add(backButton);

        mainPanel.add(utp, gbc1.gbcRemainder);
        mainPanel.add(ltp, gbc2.gbcRemainder);

        /** A háttér beállítása */
        SetBackgroundImage backgroundTool = new SetBackgroundImage();
        JLabel background = backgroundTool.setBackgroundImage("src/main/java/org/runtimeerror/gui/backgrounds/texture/Desert.png");
        background.add(mainPanel, new GridBagConstraints());
        con.add(background);
        this.setUndecorated(true);
    }
}