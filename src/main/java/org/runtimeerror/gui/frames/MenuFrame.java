package org.runtimeerror.gui.frames;
import org.runtimeerror.gui.fileIO.OpenFromFile;
import org.runtimeerror.gui.fileIO.StartTwoFrames;
import org.runtimeerror.gui.backgrounds.SetBackgroundImage;
import org.runtimeerror.gui.layout.GridBagConstraintsConfig;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


/** TODO: CLASS INFORMATION - MenuFrame
 - Ez az osztály a játékunk főmenüje, innen navigálva lehet eljutni az új játék létrehozásához,
   elmentett játék betöltéséhez, a játék leírásához és innen tud a felhasználó kilépni a programból
 - A ContinueGameFrame elindításához szálkezelést használunk, ezt a StartTwoFrames osztály biztosítja
    TODO: CLASS INFORMATION - MenuFrame, NewGameFrame, ContinueGameFrame, InfoFrame
 - Az ablakok speciálisan kezelik a rajtuk elhelyezett elemeket, ennek menete:
    1. Az elemeket legyen az egy sima JButton vagy egy JTextArea, mindig szekciónként panelekhez adjuk
    2. Ha szükséges akkor ezeket hierarchikusan még bővíthetjük, tehát ha a képernyő középső paneljén összetett elemek vannak,
       pl. egy JTextField/JComboBox páros, akkor azt beletesszük még egy külön panelba, és csak utána kerül a képernyő középső paneljába
    3. Az elkészített panelokat beletesszük egy fő panelba, és végül ezt a főpanelt beletesszük egy konténerbe, így lehet megvalósítani,
       hogy a háttérkép és a panelok helyesen jelenjenek meg hierarchiáikat tekintve
    4. A panelhoz/konténerhez adáskor mindig megadunk Gbc-t (GridBagContraints), ez felelős az elhelyezésért
    5. Végül a SetBackgroundImage osztály által előkészített képet is hozzáadjuk a konténerhez, ezzel hétteret beállítva az ablaknak.
 */


public class MenuFrame extends JFrame {
    /** A mindent magába foglaló konténer */
    Container con;

    /** Az ablakon használt régiók paneljai */
    JPanel mainPanel; JPanel utp; JPanel menuItems; JPanel ltp;

    /** Az ablakon használt elemek */
    JLabel upperText; JLabel lowerText;
    JButton newGameButton; JButton continueGameButton; JButton infoButton;JButton exitButton;

    /** Az osztály konstruktora - inicializálja az elemeket */
    public MenuFrame() throws IOException {
        /** Alap ablak beállítások megadása */
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("Menu");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /** Konténer inicializálása */
        con = getContentPane();

        /** Az ablakban használt layout megvalósítása Gbc-vel */
        GridBagConstraintsConfig gbc1 = new GridBagConstraintsConfig(0, 0, 160, 0);
        GridBagConstraintsConfig gbc2 = new GridBagConstraintsConfig(20, 0, 20, 0);
        GridBagConstraintsConfig gbc3 = new GridBagConstraintsConfig(160, 0, 0, 0);

        /** Panelok inicializálása - modifikálása */
        mainPanel = new JPanel(); mainPanel.setLayout(new GridBagLayout()); mainPanel.setOpaque(false);
        utp = new JPanel(); utp.setLayout(new GridBagLayout()); utp.setOpaque(false);
        ltp = new JPanel(); ltp.setLayout(new GridBagLayout()); ltp.setOpaque(false);
        menuItems = new JPanel(); menuItems.setLayout(new GridBagLayout()); menuItems.setOpaque(false);

        /** Elemek inicializálása - modifikálása */
        upperText = new JLabel("Desert Water Network©");
        upperText.setFont(new Font("Monospaced", Font.BOLD, 80));
        upperText.setBorder(null); upperText.setBackground(new Color(0, 0, 0, 0));
        lowerText = new JLabel("Made by runtime_error");
        lowerText.setFont(new Font("Monospaced", Font.BOLD, 20));
        lowerText.setForeground(Color.white);
        lowerText.setBorder(null); lowerText.setBackground(new Color(0, 0, 0, 0));

        /** Az ablakot vezérlő gombok inicializálása - modifikálása - azok megnyomásával a hozzájuk tartozó ablak elindítása */
        /** Új játék létrehozása */
        newGameButton = new JButton("New Game"); newGameButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
        newGameButton.addActionListener(e -> {
            try {
                NewGameFrame newGame = new NewGameFrame();
                this.setVisible(false);
                this.dispose();
            } catch (Exception error) {
                System.out.println("Error while loading menu background image!");
            }
        });
        /** Mentett játék folytatása */
        continueGameButton = new JButton("Continue Game"); continueGameButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
        continueGameButton.addActionListener(e -> {
            try {
                ContinueGameFrame mainFrame = new ContinueGameFrame();
                OpenFromFile off = new OpenFromFile();

                Thread t1 = new Thread(new StartTwoFrames(mainFrame));
                Thread t2 = new Thread(new StartTwoFrames(off));
                t1.start();
                Thread.sleep(10);
                t2.start();

                this.setVisible(false);
                this.dispose();

            } catch (Exception error) {
                System.out.println("Error while loading information background image!");
            }
        });
        /** Játék leírása */
        infoButton = new JButton("Information"); infoButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
        infoButton.addActionListener(e -> {
            try {
                InfoFrame info = new InfoFrame();
                this.setVisible(false);
                this.dispose();
            } catch (Exception error) {
                System.out.println("Error while loading information background image!");
            }
        });
        /** Játékból való kilépés */
        exitButton = new JButton("Exit"); exitButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        /** Az elemek paneljeikhez adása */
        utp.add(upperText);

        menuItems.add(newGameButton, gbc2.gbcRemainder);
        menuItems.add(continueGameButton, gbc2.gbcRemainder);
        menuItems.add(infoButton, gbc2.gbcRemainder);
        menuItems.add(exitButton, gbc2.gbcRemainder);

        ltp.add(lowerText);

        mainPanel.add(utp, gbc1.gbcRemainder);
        mainPanel.add(menuItems, gbc2.gbcRemainder);
        mainPanel.add(ltp, gbc3.gbcRemainder);

        /** A háttér beállítása és az ablak láthatóvá tétele */
        SetBackgroundImage backgroundTool = new SetBackgroundImage();
        JLabel background = backgroundTool.setBackgroundImage("src/main/java/org/runtimeerror/gui/backgrounds/texture/Desert.png");
        background.add(mainPanel, new GridBagConstraints());
        con.add(background);
        this.setUndecorated(true);
        setVisible(true);
    }
}