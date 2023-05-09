package org.runtimeerror.gui;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


/** A grafikus megjelenítést végző osztály */
public class GuiController {
    private JFrame frame; // Az összes objektumot (magát a pályát) tartalmazó keret
    private ArrayList<JButton> buttons; /** A pályán elhelyezett objektumokat (gombokat) számon tartó tároló */

    // Az attribútumok iniciaizálása
    public GuiController() {
        // A keret beállítása
        frame = new JFrame();

        // Az objektum lista inicializálása
        buttons = new ArrayList<>();
    }

    /** A megadott objektumot hozzá adja a pályához */
    public void AddButton(JButton button) {
        buttons.add(button);
    }

    /** A megadott objektumot eltávolítja a pályáról */
    public void RemoveButton(JButton button) {
        buttons.remove(button);
    }

    /** Az objetumok pozíciójának meghatározásáért felelős függvény */
    private List<Integer> PositionButton(int width, int height) {
        return null;
    }
}
