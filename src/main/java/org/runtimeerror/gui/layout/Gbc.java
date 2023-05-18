package org.runtimeerror.gui.layout;
import java.awt.*;


/** TODO: CLASS INFORMATION
    - Az ebben az osztályban előre elkészített GBC-ket használjuk később a frame-ekbe, az alapvető játék paraméterek
    beállítása során, pontosabban azok objektumainak elrendezéséhez, egymáshoz való viszonyához
 */


/** A megjelenítési elemek tájolásáért felelős osztály */
public class Gbc {
    /** A három különböző elhelyezési attribútum (Amelyek a projektbe előfordulhatnak) */
    public GridBagConstraints gbcRemainder;
    public GridBagConstraints gbcRelative;
    public GridBagConstraints gbcCentre;

    /** - Itt történik az előre definiált attribútumok inicializálása (a konstruktorban)
        - A konstruktorban megadott paramétereket állítjuk be az inset-eknek
     */
    public Gbc(int top, int left, int bottom, int right) {
        gbcRemainder = new GridBagConstraints();
        gbcRemainder.weightx = 1;
        gbcRemainder.weighty = .25;
        gbcRemainder.insets = new Insets(top, left, bottom, right);
        gbcRemainder.gridwidth = GridBagConstraints.REMAINDER;
        gbcRemainder.fill = GridBagConstraints.BOTH;

        gbcRelative = new GridBagConstraints();
        gbcRelative.weightx = 1;
        gbcRelative.weighty = .25;
        gbcRelative.insets = new Insets(top, left, bottom, right);
        gbcRelative.gridwidth = GridBagConstraints.RELATIVE;
        gbcRelative.fill = GridBagConstraints.BOTH;

        gbcCentre = new GridBagConstraints();
        gbcCentre.weightx = 1;
        gbcCentre.weighty = .25;
        gbcCentre.insets = new Insets(top, left, bottom, right);
        gbcCentre.gridwidth = GridBagConstraints.CENTER;
        gbcCentre.fill = GridBagConstraints.BOTH;
    }
}
