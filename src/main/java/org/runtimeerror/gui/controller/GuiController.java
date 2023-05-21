package org.runtimeerror.gui.controller;
import org.runtimeerror.gui.buttons.ElementButton;
import org.runtimeerror.gui.frames.GameFrame;
import org.runtimeerror.gui.frames.MenuFrame;
import javax.swing.*;
import java.io.IOException;


/** TODO: CLASS INFORMATION - MÉG NAGYON SOK MÓDOSÍTÁS SZÜKSÉGES EBBEN AZ OSZTÁLYBAN
    - A pálya elemeinek megjelenítését végzi
    - A tartalmazott objektumokat eltárolja és esemény hatására azok megváltozásának kiváltására képes
    - Az objektumok pályán való elhelyezéséért is felel
 */


/** A grafikus megjelenítést végző osztály */
public class GuiController {
    private static GuiController guiController;
    private GameFrame frame; /** A pálya ablaka */

    /** Az attribútumok iniciaizálása */
    public GuiController() {
        /** A keret indítása */
        try {
            /** Először csak egy menüt indítunk, később állítjuk be a kontrollálandó játék ablakot */
            JFrame mf = new MenuFrame();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static GuiController GetInstance() {
        if (guiController == null) guiController = new GuiController();
        return guiController;
    }

    /** A kontrollálandó játék ablakot beállító függvény */
    public void SetGameFrame(GameFrame gf) {
        this.frame = gf;
    }


}
