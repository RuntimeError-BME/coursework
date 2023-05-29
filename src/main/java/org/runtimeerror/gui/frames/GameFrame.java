package org.runtimeerror.gui.frames;
import org.runtimeerror.controller.Game;
import org.runtimeerror.gui.backgrounds.SetBackgroundImage;
import org.runtimeerror.gui.buttons.*;
import org.runtimeerror.gui.controller.GuiController;
import org.runtimeerror.gui.layout.GridBagConstraintsConfig;
import org.runtimeerror.gui.players.Players;
import org.runtimeerror.gui.timer.GameTimer;
import org.runtimeerror.model.map.Element;
import org.runtimeerror.model.map.Network;
import org.runtimeerror.model.map.Pipe;
import org.runtimeerror.model.players.Player;
import org.runtimeerror.model.players.Technician;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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
    Players players;
    JPanel[] panels;

    /** Az ablakban használt layout megvalósítása Gbc-vel */
    GridBagConstraintsConfig gbc = new GridBagConstraintsConfig(20, 0, 20, 0);
    GridBagConstraintsConfig gbcTopMap = new GridBagConstraintsConfig(20, 0, 0, 0);
    GridBagConstraintsConfig gbcBottomMap = new GridBagConstraintsConfig(0, 0, 20, 0);
    GridBagConstraintsConfig gbcMidMap = new GridBagConstraintsConfig(0, 0, 0, 0);

    /** Az osztály konstruktora - inicializálja az elemeket */
    public GameFrame(int nop, String mc, String mt) throws IOException, InterruptedException {
        /** Beállítások átvetele a NewGameFrame osztálytól */
        numberOfPlayers = nop; MapComplexity = mc; MapTheme = mt;

        guiController = GuiController.GetInstance();
        guiController.SetGameFrame(this);
        setFocusable(true);

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
        panels = new JPanel[15];
        mainPanel = new JPanel(); mainPanel.setLayout(new GridBagLayout()); mainPanel.setOpaque(false);
        cardsPanel = new JPanel(); cardsPanel.setLayout(new GridBagLayout()); cardsPanel.setOpaque(false);
        timePanel = new JPanel(); timePanel.setLayout(new GridBagLayout()); timePanel.setOpaque(false);

        /** Virtuális óra beállítás és a játékosok inventory-jainak inicializálása */
        GameTimer gt = new GameTimer(this);
        timePanel.add(gt.returnPanel());

        /** pálya felépítése */
        this.BuildMap();

        /** A játékot kezdő csapat beállítása */
        players.SetCurrentTeam("Technician");

        UpdateButtons();
        /** Kezdőjátékosnak sárgán jelezzük az elemét, amin áll. */
        UpdateCurrPlayerBtnBorder(-1);


        /** Az elemek paneljeikhez adása */
        mainPanel.add(players.ReturnPanel(), gbc.gbcRemainder);
        mainPanel.add(cardsPanel, gbc.gbcRemainder);
        mainPanel.add(timePanel, gbc.gbcRemainder);

        /** A háttér beállítása és az ablak láthatóvá tétele */
        SetBackgroundImage backgroundTool = new SetBackgroundImage();
        JLabel background = new JLabel();
        switch(MapTheme) {
            case "Desert":
                background = backgroundTool.setBackgroundImage("src/main/java/org/runtimeerror/gui/backgrounds/texture/Desert.png");
                break;
            case "Forest":
                background = backgroundTool.setBackgroundImage("src/main/java/org/runtimeerror/gui/backgrounds/texture/Forest.png");
                break;
            case "Space":
                background = backgroundTool.setBackgroundImage("src/main/java/org/runtimeerror/gui/backgrounds/texture/Space.png");
                break;
        }
        background.add(mainPanel, new GridBagConstraints());
        con.add(background);
        this.setUndecorated(true);
        setVisible(true);
        addKeyListener(this);
    }

    /** Kezdőpálya építő segédfüggvények - low, medium, high complexity - és ráhelyezi a játékosokat a pályára */
    private void buildLowComplexityMap() {
        /** A backend általi pálya építése */
        guiController.BuildLowComplexityMap();

        /** A frontend általi pálya építése */
        ButtonSetterElement(259, "Source", false);
        ButtonSetterElement(234, "Pipe", false);
        ButtonSetterElement(209, "Pipe", false);
        ButtonSetterElement(184, "Cistern", false);
        ButtonSetterElement(183, "Pipe", false);
        ButtonSetterElement(182, "Pump", false);
        ButtonSetterElement(181, "Pipe", false);
        ButtonSetterElement(180, "Pipe", false);
        ButtonSetterElement(179, "Pipe", false);
        ButtonSetterElement(178, "Pump", false);
        ButtonSetterElement(159, "Pipe", false);
        ButtonSetterElement(134, "Pipe", false);
        ButtonSetterElement(109, "Pump", false);
        ButtonSetterElement(108, "Pipe", false);
        ButtonSetterElement(83, "Cistern", false);
        ButtonSetterElement(58, "Pipe", false);
        ButtonSetterElement(57, "Pipe", false);
        ButtonSetterElement(32, "Pipe", false);
        ButtonSetterElement(7, "Source", false);
        ButtonSetterElement(185, "Pipe", false);
        ButtonSetterElement(186, "Pipe", false);
        ButtonSetterElement(187, "Pipe", false);
        ButtonSetterElement(188, "Pump", false);
        ButtonSetterElement(189, "Pipe", false);
        ButtonSetterElement(214, "Cistern", false);
        ButtonSetterElement(163, "Pipe", false);
        ButtonSetterElement(138, "Pipe", false);
        ButtonSetterElement(139, "Pipe", false);
        ButtonSetterElement(140, "Pump", false);
        ButtonSetterElement(115, "Pipe", false);
        ButtonSetterElement(90, "Pipe", false);
        ButtonSetterElement(65, "Pipe", false);
        ButtonSetterElement(40, "Cistern", false);
        ButtonSetterElement(239, "Pipe", false);
        ButtonSetterElement(264, "Pipe", false);
        ButtonSetterElement(289, "Pump", false);
        ButtonSetterElement(314, "Pipe", false);
        ButtonSetterElement(339, "Source", false);
        ButtonSetterElement(290, "Pipe", false);
        ButtonSetterElement(291, "Pipe", false);
        ButtonSetterElement(292, "Pipe", false);
        ButtonSetterElement(293, "Cistern", false);
        ButtonSetterElement(215, "Pipe", false);
        ButtonSetterElement(216, "Pipe", false);
        ButtonSetterElement(191, "Pipe", false);
        ButtonSetterElement(192, "Pump", false);
        ButtonSetterElement(193, "Pipe", false);
        ButtonSetterElement(194, "Cistern", false);
        ButtonSetterElement(195, "Pipe", false);
        ButtonSetterElement(220, "Pipe", false);
        ButtonSetterElement(221, "Pipe", false);
        ButtonSetterElement(222, "Pump", false);
        ButtonSetterElement(169, "Pipe", false);
        ButtonSetterElement(144, "Pipe", false);
        ButtonSetterElement(145, "Pipe", false);
        ButtonSetterElement(120, "Pump", false);
        ButtonSetterElement(223, "Pipe", false);
        ButtonSetterElement(224, "Source", false);
        ButtonSetterElement(121, "Pipe", false);
        ButtonSetterElement(96, "Pump", false);
        ButtonSetterElement(97, "Pipe", false);
        ButtonSetterElement(98, "Pipe", false);
        ButtonSetterElement(99, "Source", false);
        ButtonSetterElement(203, "Pipe", false);
        ButtonSetterElement(202, "Pipe", false);
        ButtonSetterElement(227, "Pipe", false);
        ButtonSetterElement(252, "Cistern", false);
        ButtonSetterElement(251, "Pipe", false);
        ButtonSetterElement(250, "Source", false);

        /** Itt helyezzük el a játkosokat */
        Game.GetInstance().AddPlayer(new Technician("t1"), 252);
        Game.GetInstance().AddPlayer(new Player("s1"), 120);
        ButtonSetterPlayer(252, "Technician", false);
        buttons.get(252).setToolTipText("t1");
        ButtonSetterPlayer(120, "Saboteur", false);
        buttons.get(120).setToolTipText("s1");

        if (numberOfPlayers >= 2) {
            Game.GetInstance().AddPlayer(new Technician("t2"), 7);
            Game.GetInstance().AddPlayer(new Player("s2"), 194);
            ButtonSetterPlayer(7, "Technician", false);
            buttons.get(7).setToolTipText("t2");
            ButtonSetterPlayer(194, "Saboteur", false);
            buttons.get(194).setToolTipText("s2");
        }

        if (numberOfPlayers >= 3) {
            Game.GetInstance().AddPlayer(new Technician("t3"), 184);
            Game.GetInstance().AddPlayer(new Player("s3"), 40);
            ButtonSetterPlayer(184, "Technician", false);
            buttons.get(184).setToolTipText("t3");
            ButtonSetterPlayer(40, "Saboteur", false);
            buttons.get(40).setToolTipText("s3");
        }
        UpdateButtonTooltips();
    }
    private void buildMediumComplexityMap() {

        buildLowComplexityMap();

        /** A backend általi pálya építése */
        guiController.BuildMediumComplexityMap();

        ButtonSetterElement(153, "Pipe", false);
        ButtonSetterElement(152, "Pipe", false);
        ButtonSetterElement(127, "Pipe", false);
        ButtonSetterElement(155, "Cistern", false);
        ButtonSetterElement(205, "Pump", false);
        ButtonSetterElement(230, "Pipe", false);
        ButtonSetterElement(255, "Pipe", false);
        ButtonSetterElement(256, "Pipe", false);
        ButtonSetterElement(281, "Cistern", false);
        ButtonSetterElement(306, "Pipe", false);
        ButtonSetterElement(305, "Pipe", false);
        ButtonSetterElement(330, "Pump", false);
        ButtonSetterElement(329, "Pipe", false);
        ButtonSetterElement(354, "Source", false);
        ButtonSetterElement(245, "Pump", false);
        ButtonSetterElement(270, "Pipe", false);
        ButtonSetterElement(295, "Pipe", false);
        ButtonSetterElement(294, "Pipe", false);
        ButtonSetterElement(318, "Pipe", false);
        ButtonSetterElement(343, "Pump", false);
        ButtonSetterElement(344, "Pipe", false);
        ButtonSetterElement(369, "Source", false);
        ButtonSetterElement(89, "Pump", false);
        ButtonSetterElement(88, "Pipe", false);
        ButtonSetterElement(87, "Pipe", false);
        ButtonSetterElement(86, "Source", false);
        ButtonSetterElement(61, "Pipe", false);
        ButtonSetterElement(36, "Pipe", false);
        ButtonSetterElement(35, "Pump", false);
        ButtonSetterElement(143, "Source", false);
        ButtonSetterElement(118, "Pipe", false);
        ButtonSetterElement(93, "Pipe", false);
        ButtonSetterElement(68, "Cistern", false);
        ButtonSetterElement(43, "Pipe", false);
        ButtonSetterElement(42, "Pipe", false);
        ButtonSetterElement(41, "Pipe", false);
    }
    private void buildHighComplexityMap() {

        buildMediumComplexityMap();

        /** A backend általi pálya építése */
        guiController.BuildHighComplexityMap();

        ButtonSetterElement(352, "Pipe", false);
        ButtonSetterElement(353, "Pipe", false);
        ButtonSetterElement(355, "Pipe", false);
        ButtonSetterElement(356, "Pipe", false);
        ButtonSetterElement(357, "Pipe", false);
        ButtonSetterElement(351, "Cistern", false);
        ButtonSetterElement(358, "Cistern", false);
        ButtonSetterElement(333, "Pipe", false);
        ButtonSetterElement(334, "Pipe", false);
        ButtonSetterElement(309, "Pump", false);
        ButtonSetterElement(284, "Pipe", false);
        ButtonSetterElement(260, "Pipe", false);
        ButtonSetterElement(261, "Pump", false);
        ButtonSetterElement(262, "Pipe", false);
        ButtonSetterElement(287, "Pipe", false);
        ButtonSetterElement(288, "Pipe", false);
        ButtonSetterElement(310, "Pipe", false);
        ButtonSetterElement(311, "Pipe", false);
        ButtonSetterElement(336, "Cistern", false);
        ButtonSetterElement(335, "Pipe", false);
        ButtonSetterElement(337, "Pipe", false);
        ButtonSetterElement(338, "Pipe", false);
        ButtonSetterElement(361, "Source", false);
        ButtonSetterElement(39, "Pipe", false);
        ButtonSetterElement(14, "Pipe", false);
        ButtonSetterElement(13, "Source", false);
        ButtonSetterElement(71, "Pipe", false);
        ButtonSetterElement(46, "Pump", false);
        ButtonSetterElement(47, "Pipe", false);
        ButtonSetterElement(48, "Pipe", false);
        ButtonSetterElement(23, "Source", false);
        ButtonSetterElement(123, "Pipe", false);
        ButtonSetterElement(148, "Pipe", false);
        ButtonSetterElement(173, "Cistern", false);
        ButtonSetterElement(248, "Pipe", false);
        ButtonSetterElement(273, "Pipe", false);
        ButtonSetterElement(298, "Cistern", false);
        ButtonSetterElement(323, "Pipe", false);
        ButtonSetterElement(348, "Pipe", false);
        ButtonSetterElement(349, "Source", false);
        ButtonSetterElement(347, "Pipe", false);
        ButtonSetterElement(372, "Pump", false);
        ButtonSetterElement(277, "Pipe", false);
        ButtonSetterElement(302, "Pump", false);
        ButtonSetterElement(56, "Pipe", false);
        ButtonSetterElement(55, "Pump", false);
        ButtonSetterElement(130, "Pipe", false);
        ButtonSetterElement(105, "Pipe", false);
        ButtonSetterElement(106, "Pump", false);
        ButtonSetterElement(107, "Pipe", false);
        ButtonSetterElement(126, "Source", false);
        ButtonSetterElement(101, "Pipe", false);
        ButtonSetterElement(76, "Pipe", false);
        ButtonSetterElement(51, "Cistern", false);
        ButtonSetterElement(52, "Pipe", false);
        ButtonSetterElement(53, "Pipe", false);
        ButtonSetterElement(54, "Pipe", false);
        ButtonSetterElement(28, "Source", false);
        ButtonSetterElement(80, "Pipe", false);
        
    }

    /** Megépíti a kezdő pályát a felhasználó által megadott kompelxitás függvényében */
    public void BuildMap() {
        /** Ennek segítségével kap a pálya minden eleme egy id-t */
        int id = 0;

        /** Itt tároljuk a lépéshez kiválasztott gombokat */
        ArrayList<Integer> moveButtons = new ArrayList<Integer>();

        /** 15 panelbe helyezünk 25-25 kártyát */
        for (int i = 0; i < 15; i++) {
            panels[i] = new JPanel(); panels[i].setLayout(new GridBagLayout()); panels[i].setOpaque(false);

            for (int j = 0; j < 25; j++) {
                ElementButton current = new ElementButton(); current.SetId(id);
                current.setPreferredSize(new Dimension(80, 80));
                current.setText("" + id);
                current.addActionListener(ae -> {
                    /** IDE KÉNE MINDEN FÉLE CHECK MÉG */
                        if (moveButtons.size() == 1) {
                            moveButtons.add(current.GetId());

                            /** Itt egyenlőre mindenképpen Technician-el dolgozunk, ugyanis a játékos típusát a backend fogja megadni */
                            MovePlayer(moveButtons.get(0), moveButtons.get(1), "Technician", false);
                            moveButtons.clear();
                        }
                        else moveButtons.add(current.GetId());
                });

                current.addKeyListener(this);
                buttons.add(current);
                panels[i].add(buttons.get(id));
                id++;
            }

            if (i == 0) cardsPanel.add(panels[i], gbcTopMap.gbcRemainder);
            if (i == 14) cardsPanel.add(panels[i], gbcBottomMap.gbcRemainder);
            else cardsPanel.add(panels[i], gbcMidMap.gbcRemainder);
        }

        /** A felhasználó által megadott pálya komplexitás alkalmazása adott számú mező készítésével */
        switch (MapComplexity) {
            case "Low":
                buildLowComplexityMap();
                break;
            case "Medium":
                buildMediumComplexityMap();
                break;
            case "High":
                buildHighComplexityMap();
                break;
        }
        UpdateInventory();
    }

    /**
     * Beállítja a gombot bizonyos típusú elemnek
     * @param id - Az aktiválandó elem azonosítója
     * @param type - Az aktiválandó elem aktiválásra kívánt típusa
     */
    public void ButtonSetterElement(int id, String type, boolean repaintIt) {
        for (ElementButton eb : buttons) {
            if (eb.GetId() == id) {
                switch (type) {
                    case "Pipe":
                        eb.setIcon(new ImageIcon("src/main/java/org/runtimeerror/gui/buttons/texture/pipe.png"));
                        break;
                    case "Pump":
                        eb.setIcon(new ImageIcon("src/main/java/org/runtimeerror/gui/buttons/texture/pump.png"));
                        break;
                    case "Cistern":
                        eb.setIcon(new ImageIcon("src/main/java/org/runtimeerror/gui/buttons/texture/cistern.png"));
                        break;
                    case "Source":
                        eb.setIcon(new ImageIcon("src/main/java/org/runtimeerror/gui/buttons/texture/source.png"));
                        break;
                }
            }
        }

        /** Az ablak újrafestése, ha szükséges */
        if (repaintIt) {
            Repaint();
        }
    }

    /**
     * A megadott indexű elemre ráállít egy megadott típusú játékost
     * @param id - Az aktiválandó elem azonosítója
     * @param playerType - Az elemre ráhelyezni kívánt játékos típusa
     */
    public void ButtonSetterPlayer(int id, String playerType, boolean repaintIt) {
        for (ElementButton eb : buttons) {
            if (eb.GetId() == id) {
                switch (playerType) {
                    case "Technician":
                        eb.setBorder(BorderFactory.createLineBorder(Color.blue, 5));
                        break;
                    case "Saboteur":
                        eb.setBorder(BorderFactory.createLineBorder(Color.black, 5));
                        break;
                }
            }
        }

        /** Az ablak újrafestése, ha szükséges */
        if (repaintIt) {
            Repaint();
        }
    }

    /** Ha a felhasználó léptetné a játékost, akkor a backend pozitív visszajelzését követően az elem kerete megváltozik a függvény hatására */
    public void MovePlayer(int fromButtonId, int toButtonId, String playerType, boolean repaintIt) {
        guiController.handleMoveEvent(fromButtonId, toButtonId, playerType);
    }

    /** Az ablak újrafestését végző függvény */
    public void Repaint() {
        this.revalidate();
        this.pack();
        this.repaint();
    }

    /** Frissíti a játék összes gombját. */
    public void UpdateButtons() {
        Network network = Game.GetInstance().GetNetwork();
        JButton defBtn = new JButton();
        int i = 0;
        for (ElementButton btn : buttons) {
            btn.setBorder(defBtn.getBorder());

            Element elem = network.GetElement(i++); // a gombhoz tartozó elem
            if (elem == null)
                continue;

            if (elem.GetBroken())
                btn.setBackground(Color.RED);
            else if (elem.GetFlooded())
                btn.setBackground(new Color(33, 189, 196, 255));
            else
                btn.setBackground(null);

            if (!elem.GetPickUpAble_onlyAttribute())
                continue; // az ezutáni textúracsere csak csövekre vonatkozik


            if (elem.GetSticky()) {
                btn.setIcon(new ImageIcon("src/main/java/org/runtimeerror/gui/buttons/texture/pipe_sticky.png"));
            } else if (elem.GetSlippery()) {
                btn.setIcon(new ImageIcon("src/main/java/org/runtimeerror/gui/buttons/texture/pipe_slippy.png"));
            } else if (elem.GetNbCnt() > 0) {
                btn.setIcon(new ImageIcon("src/main/java/org/runtimeerror/gui/buttons/texture/pipe.png"));
            }
        }

        Border[] borders = new LineBorder[2];
        borders[0] = BorderFactory.createLineBorder(Color.blue, 5);
        borders[1] = BorderFactory.createLineBorder(Color.black, 5);

        i = 0;
        for (Player player : Game.GetInstance().GetPlayers()) {
            ElementButton btn = buttons.get(player.GetCurrElem().GetIdx());
            btn.setBorder(i % 2 == 0 ? borders[0] : borders[1]);
            ++i;
        }
        players.SetPoints(Game.GetInstance().GetTechnicianScore(),Game.GetInstance().GetSaboteurScore());
    }

    /**
     * A szerelők tároló tartalmához tartozó kiírás frissítése. És a jelenleg soronlevő játékos kiírása.
     */
    public void UpdateInventory(){
        Game game= Game.GetInstance();
        for (int p=0; p < numberOfPlayers; p++){
            gt.ActualiseInventory(p, game.GetInventory(p*2));
        }
        players.SetInformation(game.GetCurrPlayer().GetName() + " player turn");
        if(game.IsTechnicianTurn()) players.SetCurrentTeam("Technician");
        else players.SetCurrentTeam("Saboteur");
    }

    /**
     * Frissíti a gombok tooltipjeit aszerint, hogy milyen játékosok állnak rajtuk.
     */
    public void UpdateButtonTooltips()
    {
        for (ElementButton btn : buttons) {
            btn.setToolTipText(null);
        }

        for (Player player : Game.GetInstance().GetPlayers()) {
            ElementButton btn = buttons.get(player.GetCurrElem().GetIdx());

            String toolTip = btn.getToolTipText();
            if (toolTip == null)
                btn.setToolTipText(player.GetName() + "  ");
            else
                btn.setToolTipText(btn.getToolTipText() + player.GetName() + "  ");
        }
    }

    /** Beállítja a jelenlegi játékos eleméhez tartozó gomb keretét sárgára. */
    public void UpdateCurrPlayerBtnBorder(int prevBtnIdx) {
        Player player = Game.GetInstance().GetCurrPlayer();
        ElementButton btn = null;
        if (player != null) {
            btn = buttons.get(player.GetCurrElem().GetIdx());
            btn.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 5));
        }

        if (prevBtnIdx == -1) {
            return;
        }

        boolean toolTip_refreshNeeded = false;
        Element elem = Game.GetInstance().GetNetwork().GetElement(prevBtnIdx);
        if (elem.GetPlayerCnt() > 0) {
            toolTip_refreshNeeded = true;
            if (elem.GetPlayer(0).GetName().charAt(0) == 't')
                buttons.get(prevBtnIdx).setBorder(BorderFactory.createLineBorder(Color.blue, 5));
            else
                buttons.get(prevBtnIdx).setBorder(BorderFactory.createLineBorder(Color.black, 5));
        } else {
            buttons.get(prevBtnIdx).setBorder(new JButton().getBorder());
        }

        /** tooltip frissítése */
        buttons.get(prevBtnIdx).setToolTipText(null);
        String toolTip = btn.getToolTipText();
        if (toolTip == null)
            btn.setToolTipText(player.GetName() + "  ");
        else
            btn.setToolTipText(btn.getToolTipText() + player.GetName() + "  ");
        if (toolTip_refreshNeeded)
            UpdateButtonTooltips();


        /** Az ablak újrafestése */
        Repaint();
    }

    /** Az átadott indexű elemnek a textúráját kicseréli egy pumpa textúrára. */
    public void ReplaceTextureToPump(int idx) {
        buttons.get(idx).setIcon(new ImageIcon("src/main/java/org/runtimeerror/gui/buttons/texture/pump.png"));
    }

    /** Visszaállítja alapállapotába az adott indexű gomb kinézetét. */
    public void ResetElementButton(int idx) {
        ElementButton btn = buttons.get(idx);
        btn.setIcon(null);
        btn.setBackground(null);

    }

    /** Felajánlja a játékosnak a 'centerIdx' indexű ElementButton-tól szomszédos összes üres ElementButtont, hogy
     * válasszon közülök. A választott indexével tér vissza. */
    public int GetEmptyElementButton(int centerIdx) {
        int[] possibleIndices = { centerIdx - 25, centerIdx - 1, centerIdx + 1, centerIdx + 25 };
        List<Integer> correctIndices = new ArrayList<>();
        for (int idx : possibleIndices) {
            if (idx < 0 || idx > 374 || (Game.GetInstance().GetNetwork().GetElement(idx) != null
            && Game.GetInstance().GetCurrPlayer().GetPart().GetIdx() != idx))
                continue;

            correctIndices.add(idx);
        }
        if (correctIndices.isEmpty())
            return -1;

        Object[] options = correctIndices.toArray();
        int chosenIdx = JOptionPane.showOptionDialog(this, "Where do you want to place the pipe?", "Placing pipe",
                                                     JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        return correctIndices.get(chosenIdx);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    @Override
    public void setFocusable(boolean b) {
        super.setFocusable(b);
    }

    /** A felhasználó általi modifikációkat jelentő gombok lenyomásainak kezelését végző függvények */
    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) { guiController.handleKeyboardEvent(e.getKeyCode()); }

    @Override
    public void keyReleased(KeyEvent e) { }
}
