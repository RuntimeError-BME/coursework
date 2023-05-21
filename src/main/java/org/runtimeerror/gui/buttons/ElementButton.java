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

    /** Az objektum paraméterei (x/y koordináták, szélesség, magasság, forgatási szög) */
    private int PosX;
    private int PosY;
    private int Width;
    private int Height;
    private int Rotation;
    private final GuiController guiController = GuiController.GetInstance();

    /* TODO: EZT MÉG MEG KELL CSINÁLNI - NINCS GRAFIKA MÖGÖTTE - DE IDE KELL TENNI ICON FORMÁBAN */
    private Icon ButtonUI; /** Az objetum UI-ja */


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

    // Visszaadja az objektum x koordinátáját
    public int GetPosX() {
        return PosX;
    }

    // Beaállítja az objektum x koordinátáját
    public void SetPosX(int PosX) {
        this.PosX = PosX;
    }

    // Visszaadja az objektum y koordinátáját
    public int GetPosY() {
        return PosY;
    }

    // Beaállítja az objektum y koordinátáját
    public void SetPosY(int PosY) {
        this.PosY = PosY;
    }

    // Visszaadja az objektum szélességét
    public int GetWidth() {
        return Width;
    }

    // Beaállítja az objektum szélességét
    public void SetWidth(int Width) {
        this.Width = Width;
    }

    // Visszaadja az objektum magasságát
    public int GetHeight() {
        return Height;
    }

    // Beaállítja az objektum magasságát
    public void SetHeight(int Height) {
        this.Height = Height;
    }

    // Visszaadja az objektum forgatási szögét
    public int GetRotation() {
        return Rotation;
    }

    // Beaállítja az objektum forgatási szögét
    public void SetRotation(int Rotation) {
        this.Rotation = Rotation;
    }
}