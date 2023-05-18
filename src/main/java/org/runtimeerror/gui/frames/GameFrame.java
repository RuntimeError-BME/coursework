package org.runtimeerror.gui.frames;
import org.runtimeerror.gui.background.SetBackgroundImage;
import org.runtimeerror.gui.buttons.ElementButton;
import org.runtimeerror.gui.controller.GuiController;
import org.runtimeerror.gui.layout.Gbc;
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

    /** A felhasználó által megadott beállításokat átvesszük a NewGameFrame-től */
    public static int numberOfPlayers;
    public static String MapComplexity;
    public static String MapTheme;

    /** A pályán elhelyezett objektumokat (gombokat) számon tartó tároló */
    private ArrayList<ElementButton> buttons;

    /** A mindent magába foglaló konténer */
    Container con;

    /** Az ablakon használt régiók paneljai */
    JPanel mainPanel; JPanel timePanel;

    /** Az ablakon használt elemek */
    JButton exit;

    /** Az osztály konstruktora - inicializálja az elemeket */
    public GameFrame(int nop, String ts, String th, GuiController guic) throws IOException {
        /** A GUI-t kezelő objektum */
        this.guic = guic;

        /** Beállítások átvetele a NewGameFrame osztálytól */
        numberOfPlayers = nop; MapComplexity = ts; MapTheme = th;

        /** Alap ablak beállítások megadása */
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("Desert Water Network©");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /** Konténer inicializálása */
        con = getContentPane();

        /** Az ablakban használt layout megvalósítása Gbc-vel */
        Gbc gbc = new Gbc(10, 0, 10, 0);

        /** Panelok inicializálása - modifikálása */
        mainPanel = new JPanel(); mainPanel.setLayout(new GridBagLayout()); mainPanel.setOpaque(false);
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

        /** Az elemek paneljeikhez adása */
        mainPanel.add(timePanel, gbc.gbcRemainder);

        /** A háttér beállítása és az ablak láthatóvá tétele */
        SetBackgroundImage backgroundTool = new SetBackgroundImage();
        JLabel background = backgroundTool.setBackgroundImage("src/main/java/org/runtimeerror/gui/background/" + MapTheme + ".png");
        background.add(mainPanel, new GridBagConstraints());
        con.add(background);
        this.setUndecorated(true);
        setVisible(true);
    }

    /** A megadott objektumot hozzá adja a pályához */
    public void AddButton(ElementButton button) {
        buttons.add(button);
    }

    /** A megadott objektumot eltávolítja a pályáról */
    public void RemoveButton(ElementButton button) {
        buttons.remove(button);
    }
}
