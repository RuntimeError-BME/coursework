package org.runtimeerror.gui.controller;
import org.runtimeerror.controller.Game;
import org.runtimeerror.gui.frames.GameFrame;
import org.runtimeerror.gui.frames.MenuFrame;
import org.runtimeerror.model.map.*;
import org.runtimeerror.prototype.PrototypeController;

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
    /** Kezdőpálya építő segédfüggvények - low, medium, high complexity - és ráhelyezi a játékosokat a pályára */
    private void buildLowComplexityMap() {

        /*
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
        */
        /** Itt helyezzük el a játkosokat */
        /*
        switch (numberOfPlayers) {
            case 1:
                ButtonSetterPlayer(252, "Technician", false);
                ButtonSetterPlayer(68, "Saboteur", false);
                break;
            case 2:
                ButtonSetterPlayer(252, "Technician", false);
                ButtonSetterPlayer(68, "Saboteur", false);
                ButtonSetterPlayer(155, "Technician", false);
                ButtonSetterPlayer(194, "Saboteur", false);
                break;
            case 3:
                ButtonSetterPlayer(252, "Technician", false);
                ButtonSetterPlayer(68, "Saboteur", false);
                ButtonSetterPlayer(155, "Technician", false);
                ButtonSetterPlayer(194, "Saboteur", false);
                ButtonSetterPlayer(184, "Technician", false);
                ButtonSetterPlayer(40, "Saboteur", false);
                break;
        }

         */
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
