package org.runtimeerror.gui;
import javax.swing.*;


// A pumpát reprezentáló gomb osztály megvalósítása
public class PumpButton extends JButton {
    private int id; // Az objetum a pályán kapott azonosítója, ezzel történik a hivatkozás

    // Visszaadja az objektum azonosítóját
    public int GetId() {
        return id;
    }

    // Beaállítja az objektum azonosítóját
    public void SetId(int id) {
        this.id = id;
    }
}