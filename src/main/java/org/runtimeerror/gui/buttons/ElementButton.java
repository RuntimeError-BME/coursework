package org.runtimeerror.gui.buttons;
import org.runtimeerror.gui.controller.GuiController;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


/** TODO: CLASS INFORMATION
    - A pályán levő objektumok közös tuljadonságait megvalósító osztály
    - Minden objektum tárol egy azonosítót, amelyet a játék motorja ad neki, annak érdekében, hogy event bekövetkezése során azt kezelni tudja
    - Továbbá az objektumok elhelyezése egy speciális, GuiController osztálybeli függvénnyel történik, ehhez el kell tárolni az ehhez szükséges paramétereket
    - A leszármazottak specifikusan beállítják a szélességet, magasságot, valamint a UI-t
 */


public class ElementButton extends JButton {
    private int id; /** Az objetum pályán kapott azonosítója, ezzel történik a hivatkozás */

    private final GuiController guiController = GuiController.GetInstance();

    public ElementButton() {
        addActionListener(e -> {
            if(id < 0) return;
            guiController.handleMoveEvent(this.id);
        });
    }

    /** GETTER SETTER FÜGGVÉNYEK AZ ATTRIBÚTUMOKHOZ */

    // Visszaadja az objektum azonosítóját
    public int GetId() {
        return id;
    }

    // Beaállítja az objektum azonosítóját
    public void SetId(int id) {
        this.id = id;
    }
}