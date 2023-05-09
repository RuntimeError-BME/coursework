package org.runtimeerror.model.map;

import org.runtimeerror.controller.Game;
import org.runtimeerror.model.players.ManipulatorPlayer;
import org.runtimeerror.model.players.Player;

/**
 * Olyan elem, amely vizet juttat a vele szomszédos elemekbe, és soha sincsen bemenete.
 */
public final class Source extends Element {

    /** Konstruktor, ami beállítja az ősbeli indexetét az elemnek. */
    public Source() { super(); }

    /** Vízzel tölti fel magát, és minden nem lyukas szomszédjára hívja tovább a Flood() függvényt,
     azaz ereszti tovább a vizet. */
    @Override
    public void Flood() {
        if(GetFlooded()) return;
        SetFlooded(true); // víz kerül belé
        for (Element element: GetNbs()) { // végigmegyünk az összes szomszédján
            if(element.GetPickUpAble_onlyAttribute()){
                element.SetInput(this);
                for (Element e: element.GetNbs()){
                    if(e!=this) element.SetOutput(e);
                }
            }
            element.Flood();//a szomszédo(ka)t elárasztja
        }
    }

    /** A paraméterként kapott manipulátorral manipulálja ezt a konkrét elemet.
     * (Meghívja az átadott manipulátoron a Manipulate() fv-t, és átadja önmagát neki az elem.) */
    @Override
    public void Manipulate(ManipulatorPlayer m) {
        m.Manipulate(this);
    }

    /**
     * Csak a prototípusban használt függvény, ami kiírja a forrás adatait a következő formátumban:
     *  details of element [idx] (source):
     *      flooded: true/false
     *      players: [player_name] [player_name] ...
     *      nbs: [dir_nr] [dir_nr] ...
     */
    @Override
    public void Print(String part) {
        super.Print("source");
    }

    @Override
    public boolean NetworkAdd(Element e) {
        return false;
    }
}