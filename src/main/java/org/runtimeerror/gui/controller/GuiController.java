package org.runtimeerror.gui.controller;
import org.runtimeerror.gui.buttons.ElementButton;
import org.runtimeerror.gui.frames.MenuFrame;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;


/** TODO: CLASS INFORMATION - MÉG NAGYON SOK MÓDOSÍTÁS SZÜKSÉGES EBBEN AZ OSZTÁLYBAN
    - A pálya elemeinek megjelenítését végzi
    - A tartalmazott objektumokat eltárolja és esemény hatására azok megváltozásának kiváltására képes
    - Az objektumok pályán való elhelyezéséért is felel
 */


/** A grafikus megjelenítést végző osztály */
public class GuiController {
    private JFrame frame; /** A pálya ablaka */

    private ArrayList<ElementButton> buttons; /** A pályán elhelyezett objektumokat (gombokat) számon tartó tároló */

    /** Az attribútumok iniciaizálása */
    public GuiController() {
        /** A keret indítása */
        try {
            frame = new MenuFrame();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
