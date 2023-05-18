package org.runtimeerror.gui.frames;
import org.runtimeerror.gui.background.SetBackgroundImage;
import org.runtimeerror.gui.controller.GuiController;
import org.runtimeerror.gui.layout.Gbc;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


/** TODO: CLASS INFORMATION - MenuFrame
 - Ez az osztály indítja el az új játékot a felhasználó által megadott beállításokkal
 */


public class NewGameFrame extends JFrame {
    /** A GUI-t kezelő objektum */
    GuiController guic;

    /** A mindent magába foglaló konténer */
    Container con;

    /** Az ablakon használt régiók paneljai */
    JPanel mainPanel; JPanel utp; JPanel menuItems; JPanel ltp;
    JPanel tfcb1; JPanel tfcb2; JPanel tfcb3;

    /** Az ablakon használt elemek */
    JLabel upperText;
    JTextField tfNumberOfPlayers; JComboBox cbNumberOfPlayers;
    JTextField tfMapComplexity; JComboBox cbMapComplexity;
    JTextField tfMapTheme; JComboBox cbMapTheme;
    JButton backButton; JButton startGameButton;

    /** A felhasználó által megadott beállítások */
    Object numberOfPlayers;
    Object mapComplexity;
    Object mapTheme;

    /** Az osztály konstruktora - inicializálja az elemeket */
    public NewGameFrame(GuiController guic) throws IOException {
        /** A GUI-t kezelő objektum */
        this.guic = guic;

        /** Alap ablak beállítások megadása */
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("New Game");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /** Konténer inicializálása */
        con = getContentPane();

        /** Az ablakban használt layout megvalósítása Gbc-vel */
        Gbc gbc1 = new Gbc(0, 0, 200, 0);
        Gbc gbc2 = new Gbc(200, 0, 0, 0);
        Gbc gbc3 = new Gbc(20, 0, 20, 0);
        Gbc gbc4 = new Gbc(0, 10, 0, 10);

        /** Panelok inicializálása - modifikálása */
        mainPanel = new JPanel(); mainPanel.setLayout(new GridBagLayout()); mainPanel.setOpaque(false);
        utp = new JPanel(); utp.setLayout(new GridBagLayout()); utp.setOpaque(false);
        ltp = new JPanel(); ltp.setLayout(new GridBagLayout()); ltp.setOpaque(false);
        menuItems = new JPanel(); menuItems.setLayout(new GridBagLayout()); menuItems.setOpaque(false);
        tfcb1 = new JPanel(); tfcb1.setLayout(new GridBagLayout()); tfcb1.setOpaque(false);
        tfcb2 = new JPanel(); tfcb2.setLayout(new GridBagLayout()); tfcb2.setOpaque(false);
        tfcb3 = new JPanel(); tfcb3.setLayout(new GridBagLayout()); tfcb3.setOpaque(false);

        /** Elemek inicializálása - modifikálása */
        upperText = new JLabel("New Game");
        upperText.setFont(new Font("Monospaced", Font.BOLD, 80));
        upperText.setBorder(null); upperText.setBackground(new Color(0, 0, 0, 0));
        Integer[] optionPlayers = {1, 2, 3}; cbNumberOfPlayers = new JComboBox<>(optionPlayers); tfNumberOfPlayers = new JTextField("Choose the number of players (the technician/saboteur pairs):"); tfNumberOfPlayers.setEditable(false);
        String[] optionSize = {"Low", "Medium", "High"}; cbMapComplexity = new JComboBox<>(optionSize); tfMapComplexity = new JTextField("Choose the map's complexity (the quantity of elements):"); tfMapComplexity.setEditable(false);
        String[] optionTheme = {"Desert", "Forest", "Space"}; cbMapTheme = new JComboBox<>(optionTheme); tfMapTheme = new JTextField("Choose the game's theme (the appearance of the game):"); tfMapTheme.setEditable(false);

        /** A felhasználó beállítja az elindítani kívánt játék paramétereit */
        cbNumberOfPlayers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numberOfPlayers = cbNumberOfPlayers.getSelectedItem();
            }
        });
        cbMapComplexity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapComplexity = cbMapComplexity.getSelectedItem();
            }
        });
        cbMapTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapTheme = cbMapTheme.getSelectedItem();
            }
        });
        mapComplexity = cbMapTheme.getItemAt(cbMapTheme.getSelectedIndex());
        mapTheme = cbMapComplexity.getItemAt(cbMapComplexity.getSelectedIndex());

        /** Az ablakot vezérlő gombok inicializálása - modifikálása - azok megnyomásával a hozzájuk tartozó ablak elindítása */
        /** Vissza a főmenübe */
        backButton = new JButton("Back"); backButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
        backButton.addActionListener(e -> {
            try {
                MenuFrame menu = new MenuFrame(guic);
                this.setVisible(false);
                this.dispose();
            } catch (Exception error) {
                System.out.println("Error while loading menu background image!");
            }
        });
        /** Játék indítása */
        startGameButton = new JButton("Start game"); startGameButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
        startGameButton.addActionListener(e -> {
            try {
                GameFrame gf = new GameFrame((int) numberOfPlayers, (String) mapComplexity, (String) mapTheme, guic);
                this.setVisible(false);
                this.dispose();
            } catch (Exception error) {
                System.out.println("Error while loading menu background image!");
            }
        });

        /** Az elemek paneljeikhez adása */
        utp.add(upperText);
        tfcb1.add(tfNumberOfPlayers, gbc4.gbcRelative); tfcb1.add(cbNumberOfPlayers, gbc4.gbcRelative);
        tfcb2.add(tfMapComplexity, gbc4.gbcRelative); tfcb2.add(cbMapComplexity, gbc4.gbcRelative);
        tfcb3.add(tfMapTheme, gbc4.gbcRelative); tfcb3.add(cbMapTheme, gbc4.gbcRelative);
        menuItems.add(tfcb1, gbc3.gbcRemainder); menuItems.add(tfcb2, gbc3.gbcRemainder); menuItems.add(tfcb3, gbc3.gbcRemainder);
        ltp.add(backButton, gbc4.gbcRelative); ltp.add(startGameButton, gbc4.gbcRelative);

        mainPanel.add(utp, gbc1.gbcRemainder);
        mainPanel.add(menuItems, gbc3.gbcRemainder);
        mainPanel.add(ltp, gbc2.gbcRemainder);

        /** A háttér beállítása és az ablak láthatóvá tétele */
        SetBackgroundImage backgroundTool = new SetBackgroundImage();
        JLabel background = backgroundTool.setBackgroundImage("src/main/java/org/runtimeerror/gui/background/Desert.png");
        background.add(mainPanel, new GridBagConstraints());
        con.add(background);
        this.setUndecorated(true);
        setVisible(true);
    }
}
