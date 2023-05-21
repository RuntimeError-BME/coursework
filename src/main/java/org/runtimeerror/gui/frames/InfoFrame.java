package org.runtimeerror.gui.frames;
import org.runtimeerror.gui.background.SetBackgroundImage;
import org.runtimeerror.gui.controller.GuiController;
import org.runtimeerror.gui.layout.GridBagConstraintsConfig;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.IOException;


/** TODO: CLASS INFORMATION
 - Ez az osztály a játékunk leírásának ablakát biztosítja
 */


public class InfoFrame extends JFrame {
    /** A GUI-t kezelő objektum */
    GuiController guic;

    /** A mindent magába foglaló konténer */
    Container con;

    /** Az ablakon használt régiók paneljai */
    JPanel mainPanel; JPanel utp; JPanel menuItems; JPanel ltp;

    /** Az ablakon használt elemek */
    JLabel upperText; JTextPane info;
    JScrollPane scrollPane;
    JButton backButton;

    /** Az osztály konstruktora - inicializálja az elemeket */
    public InfoFrame(GuiController guic) throws IOException {
        /** A GUI-t kezelő objektum */
        this.guic = guic;

        /** Alap ablak beállítások megadása */
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("Information");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /** Konténer inicializálása */
        con = getContentPane();

        /** Az ablakban használt layout megvalósítása Gbc-vel */
        GridBagConstraintsConfig gbc = new GridBagConstraintsConfig(100, 0, 100, 0);

        /** Panelok inicializálása - modifikálása */
        mainPanel = new JPanel(); mainPanel.setLayout(new GridBagLayout()); mainPanel.setOpaque(false);
        utp = new JPanel(); utp.setLayout(new GridBagLayout()); utp.setOpaque(false);
        ltp = new JPanel(); ltp.setLayout(new GridBagLayout()); ltp.setOpaque(false);
        menuItems = new JPanel(); menuItems.setLayout(new GridBagLayout()); menuItems.setOpaque(false);

        /** Elemek inicializálása - modifikálása */
        upperText = new JLabel("Information");
        upperText.setFont(new Font("Monospaced", Font.BOLD, 80));
        upperText.setBorder(null); upperText.setBackground(new Color(0, 0, 0, 0));
        info = new JTextPane(); StyledDocument doc = info.getStyledDocument(); SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT); doc.setParagraphAttributes(0, doc.getLength(), center, false);
        info.setText("Feladat   -   " +
                "Sivatagi vízhálózat\n" +
                "\n" +
                "\n" +
                "Módosítás\n" +
                "   - Szerelő is tud lyukasztani.\n" +
                "   - Foltozott cső véletlen hosszúságú ideig nem lyukadhat lyukasztható ki.\n" +
                "   - A csöveknek mindkét vége egyidőben le lehet csatolva.\n" +
                "   - A szabotőr azt a csövet, amin áll, rövid időre csúszóssá tudja tenni. Ilyenkor aki rálép, véletlenszerűen a cső valamelyik végéhez kapcsolódó elemre kerül. \n" +
                "   - Mind a szabotőrök, mind a szerelők azt a csövet, amin állnak, rövid időre ragadóssá tudják tenni. Aki legközelebb rálép, egy ideig nem tud továbblépni.\n" +
                "\n" +
                "A drukmákori sivatagon át bonyolult csőrendszer szállítja a vizet a hegyi forrásokból a sivatagon túl elterülő városok ciszternáiba. A csőrendszer egyszerű, elágazás nélküli csövekből és a csövekhez csatlakozó aktív elemekből (forrás, ciszterna, napelemmel működő vízátemelő pumpa stb.) áll. Egy pumpa több (de a pumpára jellemző véges számú) csövet is összeköthet, és minden pumpán külön-külön állítható, hogy éppen melyik belekötött csőből melyik másik csőbe pumpáljon, azonban egyszerre csak egy bemenete és egy kimenete lehet. A többi rákötött cső eközben el van zárva. A pumpák véletlen időközönként el tudnak romlani, ilyenkor megszűnik az adott pumpánál a vízáramlás. A pumpák mindegyike rendelkezik egy víztartállyal, amit a víz átemelése közben használ átmeneti tárolóként. A pumpa csak akkor tud vizet pumpálni egy csőbe, ha a cső szabad kapacitása ezt lehetővé teszi.\n" +
                "A csőhálózat bővíthető, változtatható. A csövek kellően rugalmasak ahhoz, hogy az egyik végüket lecsatlakoztatva egy másik aktív elemhez elvihetők és ott felcsatlakoztathatók legyenek. A ciszternáknál folyamatosan készülnek az új csövek, amelyek egyik vége a ciszternához kapcsolódik, a másik azonban szabad. A szabad végű csövekből a csőbe betáplált víz a homokba folyik.\n" +
                "\n" +
                "A csőhálózatot a szerelők tartják karban. Ők javítják meg az elromlott pumpákat, ők állítják át a pumpákat, hogy mindig a lehető legtöbb víz tudjon áthaladni a hálózaton, és ha egy cső kilyukad, az ő dolguk a cső megfoltozása is. A kilyukadt csövekből a víz kifolyik, a csövek végén lévő pumpához már nem jut belőle. A szerelők dolga a ciszternáknál lévő szabad csövekkel a hálózat kapacitásának növelése. A szerelők a ciszternáknál magukhoz tudnak venni új pumpát is, amit egy cső közepén tudnak elhelyezni. A csövet ehhez ketté kell vágni, és a két végét a pumpához kell csatlakoztatni.\n" +
                "\n" +
                "A hálózaton élnek a nomád szabotőrök is, akik a pumpákat tudják átállítani és a csöveket szokták kilyukasztani.\n" +
                "\n" +
                "Mivel a sivatag veszélyes hely, a szerelők és a szabotőrök csak a csőhálózaton haladhatnak. A pumpáknál kikerülhetik egymást, de a csöveken már nem tudnak elmenni egymás mellett, egy csövön egyszerre csak egy ember állhat.\n" +
                "\n" +
                "A játékot a két csapat legalább 2-2 játékossal játssza. A szabotőrök dolga, hogy minél több víz folyjon el a lyukakon, a szerelők pedig azon dolgoznak, hogy minél több víz jusson a ciszternákba. Az a csapat nyer, amelyik a játék végére több vizet szerez.");
        info.setEditable(false); info.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15)); info.setCaretPosition(0);
        scrollPane = new JScrollPane(info);
        scrollPane.setPreferredSize(new Dimension(1500, 350));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        /** Az ablakot vezérlő gombok inicializálása - modifikálása - azok megnyomásával a hozzájuk tartozó ablak elindítása */
        /** Vissza a főmenübe */
        backButton = new JButton("Back"); backButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));;
        backButton.addActionListener(e -> {
            try {
                MenuFrame menu = new MenuFrame(guic);
                this.setVisible(false);
                this.dispose();
            } catch (Exception error) {
                System.out.println("Error while loading menu background image!");
            }
        });

        /** Az elemek paneljeikhez adása */
        utp.add(upperText);
        menuItems.add(scrollPane);
        ltp.add(backButton);

        mainPanel.add(utp, gbc.gbcRemainder);
        mainPanel.add(menuItems, gbc.gbcRemainder);
        mainPanel.add(ltp, gbc.gbcRemainder);

        /** A háttér beállítása és az ablak láthatóvá tétele */
        SetBackgroundImage backgroundTool = new SetBackgroundImage();
        JLabel background = backgroundTool.setBackgroundImage("src/main/java/org/runtimeerror/gui/background/texture/Desert.png");
        background.add(mainPanel, new GridBagConstraints());
        con.add(background);
        this.setUndecorated(true);
        setVisible(true);
    }
}
