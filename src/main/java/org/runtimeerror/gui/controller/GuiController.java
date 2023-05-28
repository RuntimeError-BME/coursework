package org.runtimeerror.gui.controller;
import org.runtimeerror.controller.Game;
import org.runtimeerror.gui.frames.GameFrame;
import org.runtimeerror.gui.frames.MenuFrame;
import org.runtimeerror.model.map.*;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.IOException;


/** TODO: CLASS INFORMATION - MÉG NAGYON SOK MÓDOSÍTÁS SZÜKSÉGES EBBEN AZ OSZTÁLYBAN
    - A pálya elemeinek megjelenítését végzi
    - A tartalmazott objektumokat eltárolja és esemény hatására azok megváltozásának kiváltására képes
    - Az objektumok pályán való elhelyezéséért is felel
 */


/** A grafikus megjelenítést végző osztály */
public class GuiController {
    private static GuiController guiController;
    private Game game = Game.GetInstance();
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

    public void handleKeyboardEvent(int pressedKeyCode) {
        int currentElementIndex = game.GetCurrPlayer().GetCurrElem().GetIdx();
        Network network = game.GetNetwork();
        Element currentElement = network.GetElement(currentElementIndex);
        switch(pressedKeyCode) {
            case KeyEvent.VK_T:
                game.SetDeterministic(!game.GetDeterministic());
                break;

            case KeyEvent.VK_B:
                game.BreakElementByController(currentElementIndex);
                break;
            case KeyEvent.VK_P:
                Game.Input.TryPartPlacement(currentElement);
                break;
            case KeyEvent.VK_U:
                Game.Input.Pickup();
                break;
            case KeyEvent.VK_R:
                Game.Input.TryPartRelocation(currentElement);
                break;
            case KeyEvent.VK_1:
                game.StickifyPipeByController(currentElementIndex);
                break;
            case KeyEvent.VK_2:
                game.SlippifyPipeByController(currentElementIndex);
                break;

            default:
                break;
        }
    }

    /** A GUIController egérkattintásra mozgatja a soron lévő játékost a lenyomott gombhoz tartozó pályaelemre */
    public boolean handleMoveEvent(int fromButtonId, int toButtonId, String playerType) {
        //Game.Input.MoveCurrPlayer(targetComponentIndex);
        return true;
    }

    /**
        TODO: FONTOS!!!
        Az irányításon kívül, még három függvény összeköttetést kell megvalósítani a backend és frontend között, ezek pedig:
            1. A fenti információs sáv feltöltése a prototípus visszajelzéseivel (SetInformation(String info) függvény a Players osztályban)
            2. A csapatok által kapott pontok kiírása a GUI-ban (AddPoint(int idx, int nPoints) függvény a Players osztályban)
            3. A csapatok inventory-jainak kiírása a GUI-ba (ActualiseInventory(String team, String inventory) függvény a GameTimer osztályban)
     */
}
