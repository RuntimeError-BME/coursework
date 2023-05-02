package org.runtimeerror.model.map;

import org.runtimeerror.controller.Game;
import org.runtimeerror.model.players.ManipulatorPlayer;
import org.runtimeerror.model.players.Player;

/**
 * Olyan elromolható, felvehető elem, amely vizet képes átereszteni a bemenetként beállított csövéből a kimenetként beállított csövébe.
 * Pontot oszt a szabotőröknek, ha nincsen kimenete, amikor vizet eresztene át.
 */
public final class Pump extends Breakable {

    /** Ha a pumpa el van romolva, nem tesz semmit. Ha nincs elromolva akkor vízzel tölti
     fel magát. Ha nincsen outputja, akkor a szabotőrök pontot kapnak, de ha van outputja, akkor az
     outputra hívja tovább a Flood() függvényt. */
    @Override
    public void Flood() {
        if (GetBroken()) // ha el van romolva
            return; // akkor nem tesz semmit

        SetFlooded(true); // ha viszont nincs elromolva, víz kerül belé
        Element output = GetOutput();
        if (output == null) { // ha nincs kimenete
            Game.GetInstance().AddSaboteurPoints(1); // akkor pontot kapnak a szabotőrök
            return; // és már nem folyik tovább belőle a víz a kimenetére
        }
        output.Flood(); // ha viszont minden rendben, akkor folyatja tovább a vizet a kimenetére
    }

    /** A paraméterként kapott manipulátorral manipulálja ezt a konkrét elemet.
     * (Meghívja az átadott manipulátoron a Manipulate() fv-t, és átadja önmagát neki az elem.) */
    @Override
    public void Manipulate(ManipulatorPlayer m) {
        m.Manipulate(this);
    }

    /**
     * Csak a prototípusban használt függvény, ami kiírja a pumpa adatait a következő formátumban:
     *  details of element [idx] (pump):
     *      flooded: true/false
     *      players: [player_name] [player_name] ...
     *      nbs: [dir_nr] [dir_nr] ...
     *      broken: true/false
     *      input: [dir_nr]
     *      output: [dir_nr]
     */
    @Override
    public void Print() {
        int idx = Game.GetInstance().GetNetwork().GetElements().indexOf(this);
        System.out.print("details of element " + idx + " (pipe):" +
                         "\n\tflooded: " + GetFlooded() +
                         "\n\tplayers: ");
        for (Player player : players)
            System.out.print(player.GetName() + " ");

        System.out.print("\n\tnbs: ");
        int cnt = 0, i = 0;
        int inp_dir = -1, out_dir = -1;
        while (cnt < GetNbCnt()) {
            Element nb = GetNb(new Direction(i));
            if (nb != null) {
                if (nb == GetInput())
                    inp_dir = i;
                else if (nb == GetOutput())
                    out_dir = i;

                System.out.print(i + " ");
                ++cnt;
            }
            ++i;
        }
        System.out.print("\n\tbroken: " + GetBroken());
        System.out.print("\n\tinput: " + inp_dir);
        System.out.print("\n\toutput: " + out_dir);
        System.out.print("\n");
    }
}