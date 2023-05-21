package org.runtimeerror.gui.frames;
import org.runtimeerror.gui.background.SetBackgroundImage;
import org.runtimeerror.gui.buttons.ElementButton;
import org.runtimeerror.gui.controller.GuiController;
import org.runtimeerror.gui.layout.GridBagConstraintsConfig;
import org.runtimeerror.gui.players.Players;
import org.runtimeerror.gui.timer.GameTimer;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;


/** TODO: CLASS INFORMATION
 - Ez az osztály valósítja meg a játékot, a felhasználó általi beállításokat alkalmazva
 - Szorosan össze van kapcsolva a GuiController osztállyal, mert a kontroller alkalmazza a felhasználói
   utasításokat, de ezeket közvetlen a GameFrame-en keresztül teszi, annak megfelelő metódusainak meghívásával
 */


public class GameFrame extends JFrame {
    /** A GUI-t kezelő objektum */
    GuiController guic;

    /** A játékosok kijelző méretének lehívása */
    public Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();

    /** A felhasználó által megadott beállításokat átvesszük a NewGameFrame-től */
    public static int numberOfPlayers;
    public static String MapComplexity;
    public static String MapTheme;

    /** A pályán elhelyezett objektumokat (gombokat) számon tartó tároló */
    private ArrayList<ElementButton> buttons;

    /** A mindent magába foglaló konténer */
    Container con;

    /** Az ablakon használt régiók paneljai */
    JPanel mainPanel; JPanel cardsPanel; JPanel timePanel;

    /** Az ablakon használt elemek */
    JButton exit;
    Players players;
    ImageIcon[] themeImgs;
    JButton[] cards; JPanel[] panels;
    JButton firstPick; JButton secondPick;

    /** A pálya mérete mezőkben mérve */
    int tableSize;

    /** Az osztály konstruktora - inicializálja az elemeket */
    public GameFrame(int nop, String mc, String mt, GuiController guic) throws IOException, InterruptedException {
        /** A GUI-t kezelő objektum */
        this.guic = guic;

        /** Beállítások átvetele a NewGameFrame osztálytól */
        numberOfPlayers = nop; MapComplexity = mc; MapTheme = mt;

        /** Alap ablak beállítások megadása */
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("Menu");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /** Konténer inicializálása */
        con = getContentPane();

        /** A játékosok inicializálása a GUI-n */
        players = new Players(numberOfPlayers * 2);

        /** Az ablakban használt layout megvalósítása Gbc-vel */
        GridBagConstraintsConfig gbc = new GridBagConstraintsConfig(10, 0, 10, 0);
        GridBagConstraintsConfig gbcTopMap = new GridBagConstraintsConfig(10, 0, 10, 0);
        GridBagConstraintsConfig gbcBottomMap = new GridBagConstraintsConfig(0, 0, 10, 0);
        GridBagConstraintsConfig gbcMidMap = new GridBagConstraintsConfig(0, 0, 0, 0);

        /** Panelok inicializálása - modifikálása */
        mainPanel = new JPanel(); mainPanel.setLayout(new GridBagLayout()); mainPanel.setOpaque(false);
        cardsPanel = new JPanel(); cardsPanel.setLayout(new GridBagLayout()); cardsPanel.setOpaque(false);
        timePanel = new JPanel(); timePanel.setLayout(new GridBagLayout()); timePanel.setOpaque(false);

        /** Az ablakot vezérlő gombok inicializálása - modifikálása - azok megnyomásával a hozzájuk tartozó ablak elindítása */
        /** Játék mentése, majd vissza a főmenübe */
        exit = new JButton("Save and exit");
        exit.addActionListener(ae -> {
            try {
                MenuFrame menu = new MenuFrame(guic);
                this.setVisible(false);
                this.dispose();
            } catch (Exception error) {
                System.out.println("Error while loading menu background image!");
            }
        });

        /** Virtuális óra beállítás */
        GameTimer gt = new GameTimer();
        timePanel = gt.returnPanel();
        timePanel.add(exit);

        /** A felhasználó által megadott textúra alkalmazása */
//        switch (MapTheme) {
//            //case "Desert" -> /** TODO: Adott textúra beállítása az elemen, annak megfelelő függvényének meghívásával */
//            //case "Forest" -> /** TODO: Adott textúra beállítása az elemen, annak megfelelő függvényének meghívásával */
//            //case "Space" -> /** TODO: Adott textúra beállítása az elemen, annak megfelelő függvényének meghívásával */
//        }

        /** A felhasználó által megadott pálya komplexitás alkalmazása adott számú mező készítésével */
        switch (MapComplexity) {
            case "Low":
                tableSize = 8;
                break;
            case "Medium":
                tableSize = 16;
                break;
            case "High":
                tableSize = 32;
                break;
        }

        /** A pálya kezeléséhez használt indexek kezelése TODO: NAGYON NINCS MÉG VÉGIG GONDOLVA */
        cards = new JButton[tableSize * 2];
        panels = new JPanel[((int) Math.sqrt(tableSize * 2))];

        /** TODO: NA ITT NAGYON SOK GOND LESZ MÉG!!! */
        for (int i = 0; i < ((int) Math.sqrt(tableSize * 2)); i++) {
            panels[i] = new JPanel(); panels[i].setLayout(new GridBagLayout()); panels[i].setOpaque(false);

            for (int j = 0; j < ((int) Math.sqrt(tableSize * 2)); j++) {
                JButton current = new JButton(); current.setPreferredSize(new Dimension(50, 50));
                current.setIcon(new ImageIcon("src/main/java/org/runtimeerror/gui/background/Forest.png"));

                current.addActionListener(ae -> {
                    /** Itt történnek a felhasználó általi cselekvések metódushívásai TODO: NAGYON NINCS MÉG VÉGIG GONDOLVA */
                });

                panels[i].add(current);
            }
            if (i == 0) cardsPanel.add(panels[i], gbcTopMap.gbcRemainder);
            if (i == (((int) Math.sqrt(tableSize * 2)) - 1)) cardsPanel.add(panels[i], gbcBottomMap.gbcRemainder);
            else cardsPanel.add(panels[i], gbcMidMap.gbcRemainder);
        }

        /** Az elemek paneljeikhez adása */
        mainPanel.add(players.ReturnPanel(), gbc.gbcRemainder);
        mainPanel.add(cardsPanel, gbc.gbcRemainder);
        mainPanel.add(timePanel, gbc.gbcRemainder);

        /** A háttér beállítása és az ablak láthatóvá tétele */
        SetBackgroundImage backgroundTool = new SetBackgroundImage();
        JLabel background = new JLabel();
        switch(MapTheme) {
            case "Desert":
                background = backgroundTool.setBackgroundImage("src/main/java/org/runtimeerror/gui/background/Desert.png");
                break;
            case "Forest":
                background = backgroundTool.setBackgroundImage("src/main/java/org/runtimeerror/gui/background/Forest.png");
                break;
            case "Space":
                background = backgroundTool.setBackgroundImage("src/main/java/org/runtimeerror/gui/background/Space.png");
                break;
        }
        background.add(mainPanel, new GridBagConstraints());
        con.add(background);
        this.setUndecorated(true);
        setVisible(true);
    }
}
