package org.runtimeerror.gui.frames;
import org.runtimeerror.gui.background.SetBackgroundImage;
import org.runtimeerror.gui.buttons.*;
import org.runtimeerror.gui.controller.GuiController;
import org.runtimeerror.gui.layout.GridBagConstraintsConfig;
import org.runtimeerror.gui.players.Players;
import org.runtimeerror.gui.timer.GameTimer;
import org.runtimeerror.model.map.Cistern;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;


/** TODO: CLASS INFORMATION
 - Ez az osztály valósítja meg a játékot, a felhasználó általi beállításokat alkalmazva
 - Szorosan össze van kapcsolva a GuiController osztállyal, mert a kontroller alkalmazza a felhasználói
   utasításokat, de ezeket közvetlen a GameFrame-en keresztül teszi, annak megfelelő metódusainak meghívásával
 */


public class GameFrame extends JFrame implements KeyListener {
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
    private static GuiController guiController;

    /** Az ablakon használt régiók paneljai */
    JPanel mainPanel; JPanel cardsPanel; JPanel timePanel;

    /** Az ablakon használt elemek */
    JButton exit;
    Players players;
    JPanel[] panels;

    /** Az ablakban használt layout megvalósítása Gbc-vel */
    GridBagConstraintsConfig gbc = new GridBagConstraintsConfig(10, 0, 10, 0);
    GridBagConstraintsConfig gbcTopMap = new GridBagConstraintsConfig(10, 0, 0, 0);
    GridBagConstraintsConfig gbcBottomMap = new GridBagConstraintsConfig(0, 0, 10, 0);
    GridBagConstraintsConfig gbcMidMap = new GridBagConstraintsConfig(0, 0, 0, 0);

    /** Az osztály konstruktora - inicializálja az elemeket */
    public GameFrame(int nop, String mc, String mt) throws IOException, InterruptedException {
        /** Beállítások átvetele a NewGameFrame osztálytól */
        numberOfPlayers = nop; MapComplexity = mc; MapTheme = mt;

        guiController = GuiController.GetInstance();

        /** Az elemek gombjainak inicializálása */
        buttons = new ArrayList<ElementButton>();

        /** Alap ablak beállítások megadása */
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("Menu");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /** Konténer inicializálása */
        con = getContentPane();

        /** A játékosok inicializálása a GUI-n */
        players = new Players(numberOfPlayers * 2);

        /** Panelok inicializálása - modifikálása */
        panels = new JPanel[11];
        mainPanel = new JPanel(); mainPanel.setLayout(new GridBagLayout()); mainPanel.setOpaque(false);
        cardsPanel = new JPanel(); cardsPanel.setLayout(new GridBagLayout()); cardsPanel.setOpaque(false);
        timePanel = new JPanel(); timePanel.setLayout(new GridBagLayout()); timePanel.setOpaque(false);

        /** Az ablakot vezérlő gombok inicializálása - modifikálása - azok megnyomásával a hozzájuk tartozó ablak elindítása */
        /** Játék mentése, majd vissza a főmenübe */
        exit = new JButton("Save and exit");
        exit.addActionListener(ae -> {
            try {
                MenuFrame menu = new MenuFrame();
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

        /** TODO: NA ITT NAGYON SOK GOND LESZ MÉG!!! */
        this.BuildMap();

        /** Az elemek paneljeikhez adása */
        mainPanel.add(players.ReturnPanel(), gbc.gbcRemainder);
        mainPanel.add(cardsPanel, gbc.gbcRemainder);
        mainPanel.add(timePanel, gbc.gbcRemainder);

        /** A háttér beállítása és az ablak láthatóvá tétele */
        SetBackgroundImage backgroundTool = new SetBackgroundImage();
        JLabel background = new JLabel();
        switch(MapTheme) {
            case "Desert":
                background = backgroundTool.setBackgroundImage("src/main/java/org/runtimeerror/gui/background/texture/Desert.png");
                break;
            case "Forest":
                background = backgroundTool.setBackgroundImage("src/main/java/org/runtimeerror/gui/background/texture/Forest.png");
                break;
            case "Space":
                background = backgroundTool.setBackgroundImage("src/main/java/org/runtimeerror/gui/background/texture/Space.png");
                break;
        }
        background.add(mainPanel, new GridBagConstraints());
        con.add(background);
        this.setUndecorated(true);
        setVisible(true);
    }

    /** A pályakészítéshez készített speciális osztály, mely tartalmazza
        a sor, oszlop, és elem típus attribútumokat
     */
    class Tuple {
        public String type;
        public int row, col;

        public Tuple(String t, int r, int c) {
            this.type = t; this.row = r; this.col = c;
        }
    }

    /** Megépíti a kezdő pályát a felhasználó által megadott kompelxitás függvényében */
    public void BuildMap() {
        /** Ebben tároljuk a pályához kszítendő elemeket */
        ArrayList<Tuple> map = new ArrayList<>();

        /** A felhasználó által megadott pálya komplexitás alkalmazása adott számú mező készítésével */
        switch (MapComplexity) {
            case "Low":
//                pipe: 9-5, 8-5, 7-5, 6-4, 5-5, 3-5, 6-6, 6-7, 6-8,
//                pump: 6-5, 4-5, 6-9
//                cistern: 6-3
//                source: 10-5,
                map.add(new Tuple("Pipe", 9, 5));
                map.add(new Tuple("Pipe", 8, 5));
                map.add(new Tuple("Pipe", 7, 5));
                map.add(new Tuple("Pipe", 6, 4));
                map.add(new Tuple("Pipe", 5, 5));
                map.add(new Tuple("Pipe", 3, 5));
                map.add(new Tuple("Pipe", 6, 6));
                map.add(new Tuple("Pipe", 6, 7));
                map.add(new Tuple("Pipe", 6, 8));
                map.add(new Tuple("Pump", 6, 5));
                map.add(new Tuple("Pump", 4, 5));
                map.add(new Tuple("Pump", 6, 9));
                map.add(new Tuple("Cistern", 6, 3));
                map.add(new Tuple("Source", 10, 5));

                break;
            case "Medium":

                break;
            case "High":

                break;
        }

        for (int i = 0; i < 11; i++) {
            panels[i] = new JPanel(); panels[i].setLayout(new GridBagLayout()); panels[i].setOpaque(false);

            for (int j = 0; j < 21; j++) {
                for (int k = 0; k < map.size(); k++) {
                    if (map.get(k).row == i) {
                        if (map.get(k).col == j) {
                            switch (map.get(k).type) {
                                case "Pipe":
                                    PipeButton currentPipe = new PipeButton(); currentPipe.setPreferredSize(new Dimension(80, 80)); currentPipe.setText(""+j);
                                    currentPipe.setIcon(new ImageIcon("src/main/java/org/runtimeerror/gui/buttons/texture/Pipe.png"));
                                    currentPipe.addActionListener(ae -> {
                                        /** Itt történnek a felhasználó általi cselekvések metódushívásai TODO: NAGYON NINCS MÉG VÉGIG GONDOLVA */
                                    });
                                    panels[i].add(currentPipe);
                                    break;
                                case "Pump":
                                    PumpButton currentPump = new PumpButton(); currentPump.setPreferredSize(new Dimension(80, 80)); currentPump.setText(""+j);
                                    currentPump.setIcon(new ImageIcon("src/main/java/org/runtimeerror/gui/buttons/texture/Pump.png"));
                                    currentPump.addActionListener(ae -> {
                                        /** Itt történnek a felhasználó általi cselekvések metódushívásai TODO: NAGYON NINCS MÉG VÉGIG GONDOLVA */
                                    });
                                    panels[i].add(currentPump);
                                    break;
                                case "Cistern":
                                    CisternButton currentCistern = new CisternButton(); currentCistern.setPreferredSize(new Dimension(80, 80)); currentCistern.setText(""+j);
                                    currentCistern.setIcon(new ImageIcon("src/main/java/org/runtimeerror/gui/buttons/texture/Cistern.png"));
                                    currentCistern.addActionListener(ae -> {
                                        /** Itt történnek a felhasználó általi cselekvések metódushívásai TODO: NAGYON NINCS MÉG VÉGIG GONDOLVA */
                                    });
                                    panels[i].add(currentCistern);
                                    break;
                                case "Source":
                                    SourceButton currentSource = new SourceButton(); currentSource.setPreferredSize(new Dimension(80, 80)); currentSource.setText(""+j);
                                    currentSource.setIcon(new ImageIcon("src/main/java/org/runtimeerror/gui/buttons/texture/Source.png"));
                                    currentSource.addActionListener(ae -> {
                                        /** Itt történnek a felhasználó általi cselekvések metódushívásai TODO: NAGYON NINCS MÉG VÉGIG GONDOLVA */
                                    });
                                    panels[i].add(currentSource);
                                    break;
                            }
                        }
                    }
                    else {
                        JButton current = new JButton();
                        current.setPreferredSize(new Dimension(80, 80));
                        current.setText("" + j);
                        current.addActionListener(ae -> {
                            /** Itt történnek a felhasználó általi cselekvések metódushívásai TODO: NAGYON NINCS MÉG VÉGIG GONDOLVA */
                        });
                        panels[i].add(current);
                    }
                }
            }
            if (i == 0) cardsPanel.add(panels[i], gbcTopMap.gbcRemainder);
            if (i == 10) cardsPanel.add(panels[i], gbcBottomMap.gbcRemainder);
            else cardsPanel.add(panels[i], gbcMidMap.gbcRemainder);
        }
    }

    /** Frissíti a pályát valamilyen felhasználói interakció függvényében */
    public void RefreshMap() {

    }
    @Override
    public void keyTyped(KeyEvent e) {
        guiController.handleKeyboardEvent(e.getKeyCode());
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) { }

}
