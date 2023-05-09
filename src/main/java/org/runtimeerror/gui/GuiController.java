package org.runtimeerror.gui;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/** TODO: CLASS INFORMATION
    - A pálya elemeinek megjelenítését végzi
    - A tartalmazott objektumokat eltárolja és esemény hatására azok megváltozásának kiváltására képes
    - Az objektumok pályán való elhelyezéséért is felel
 */


/** A grafikus megjelenítést végző osztály */
public class GuiController {
    private JFrame frame; /** Az összes objektumot (magát a pályát) tartalmazó keret */
    private ArrayList<ElementButton> buttons; /** A pályán elhelyezett objektumokat (gombokat) számon tartó tároló */
    private Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize(); /** A játékosok kijelző méretének lehívása */

    /** Az attribútumok iniciaizálása */
    public GuiController() {
        /** A keret beállítása */
        frame = new JFrame(); /** A keret inicializálása */
        frame.setTitle("runtime_error"); /** A keret nevének beállítása */
        frame.setLayout(null); /** A keret elhelyezkedésének beállítása */

        /** A háttérkép beállítása */
        ImageIcon imageIcon = new ImageIcon("frame_wallpaper/desert.jpg"); /** Kép betöltése ImageIcon-ba */
        Image image = imageIcon.getImage(); /** ImageIcon --- Image transzformáció */
        Image scaled_img = image.getScaledInstance(ScreenSize.width, ScreenSize.height,  Image.SCALE_SMOOTH); /** A kép átméretezése */
        imageIcon = new ImageIcon(scaled_img);  /** Image --- ImageIcon transzformáció */
        frame.setContentPane(new JLabel(imageIcon)); /** A kép panel-ba ágyazása */
        frame.getContentPane().setPreferredSize(new Dimension(ScreenSize.width, ScreenSize.height)); /** A keret méretének beállítása */
        frame.pack();

        /** A keret indítása */
        frame.setVisible(true); /** A keret láthatóvá tétele */
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /** Az objektum lista inicializálása */
        buttons = new ArrayList<>();
    }

    /** A megadott objektumot hozzá adja a pályához */
    public void AddButton(ElementButton button) {
        buttons.add(button);

        RefreshFrame(); /** A keret frissítése */
    }

    /** A megadott objektumot eltávolítja a pályáról */
    public void RemoveButton(ElementButton button) {
        buttons.remove(button);

        RefreshFrame(); /** A keret frissítése */
    }

    /* TODO: NINCS MÉG VÉGIGGONDOLVA */
    /** Az objetumok pozíciójának meghatározásáért felelős függvény */
    private void PositionButton() {
        /** MÉG ÜRES */
    }

    /** A keret frissítő függvénye */
    private void RefreshFrame() {
        frame.invalidate(); /** A keret érvénytelenítése */
        frame.validate(); /** A keret érvényesítése */
        frame.repaint(); /** A keret újrarajzolása */
    }
}
