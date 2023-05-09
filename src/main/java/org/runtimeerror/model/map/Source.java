package org.runtimeerror.model.map;

import org.runtimeerror.controller.Game;
import org.runtimeerror.model.players.ManipulatorPlayer;
import org.runtimeerror.model.players.Player;

/**
 * Olyan elem, amely vizet juttat a vele szomszédos elemekbe, és soha sincsen bemenete.
 */
public final class Source extends Element {

    /** Vízzel tölti fel magát, és minden nem lyukas szomszédjára hívja tovább a Flood() függvényt,
     azaz ereszti tovább a vizet. */
    @Override
    public void Flood() {

        SetFlooded(true); // víz kerül belé
        int cnt = 0, i = 0;
        while (cnt < GetNbCnt()) { // végigmegyünk az összes szomszédján
            Element nb = GetNbs(new Direction(i));
            if (nb != null) {
                nb.Flood(); // elárasztjuk az összeset
                ++cnt;
            }
            ++i;
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
    public void Print() {
        int idx = Game.GetInstance().GetNetwork().GetElements().indexOf(this);
        System.out.print("details of element " + idx + " (source):" +
                         "\n\tflooded: " + GetFlooded() +
                         "\n\tplayers: ");
        for (Player player : players)
            System.out.print(player.GetName() + " ");

        System.out.print("\n\tnbs: ");
        int cnt = 0, i = 0;
        while (cnt < GetNbCnt()) {
            Element nb = GetNbs(new Direction(i));
            if (nb != null) {
                System.out.print(i + " ");
                ++cnt;
            }
            ++i;
        }
        System.out.print("\n");
    }
}