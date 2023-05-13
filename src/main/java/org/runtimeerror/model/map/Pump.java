package org.runtimeerror.model.map;

import org.runtimeerror.controller.Game;
import org.runtimeerror.model.players.ManipulatorPlayer;
import org.runtimeerror.model.players.Player;
import org.runtimeerror.prototype.PrototypeController;

/**
 * Olyan elromolható, felvehető elem, amely vizet képes átereszteni a bemenetként beállított csövéből a kimenetként beállított csövébe.
 * Pontot oszt a szabotőröknek, ha nincsen kimenete, amikor vizet eresztene át.
 */
public final class Pump extends Breakable {

    /** Konstruktor, ami beállítja az ősbeli indexetét az elemnek. */
    public Pump() { super(); }

    /** Ha a pumpa el van romolva, nem tesz semmit. Ha nincs elromolva akkor vízzel tölti
     fel magát. Ha nincsen outputja, akkor a szabotőrök pontot kapnak, de ha van outputja, akkor az
     outputra hívja tovább a Flood() függvényt. */
    @Override
    public void Flood() {
        if(GetFlooded()) return;
        if (GetBroken()) // ha el van romolva
            return; // akkor nem tesz semmit

        SetFlooded(true); // ha viszont nincs elromolva, víz kerül belé
        Element output = GetOutput();
        if (output == null) { // ha nincs kimenete
            Game.GetInstance().AddSaboteurPoints(1); // akkor pontot kapnak a szabotőrök
            return; // és már nem folyik tovább belőle a víz a kimenetére
        }
        if (output.GetPickUpAble_onlyAttribute()) { // ha cső a kimenet, akkor előfordulhat,
            if (output.GetInput() != this) { // hogy rosszul van beállítva az input és output
                Element tmp = output.GetInput(); // ilyenkor fel kel cserélnünk az inputot és az outputot
                output.SetInput(output.GetOutput());
                output.SetOutput(tmp);
            }
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
    public void Print(String part) {
        super.Print("pump");

        System.out.print("\tbroken: " + GetBroken());

        Element inp = GetInput(), out = GetOutput();
        System.out.print("\n\tinput: " + (inp == null ? "" : inp.GetIdx()));
        System.out.print("\n\toutput: " + (out == null ? "" : out.GetIdx()));
        System.out.print("\n");
    }

    /** Felszólítja a játékost, hogy használja a "change input 'elem_idx' output 'elem_idx>' parancsot.
     * (Kivéve, ha szerelő jön, és törött a cső - ekkor nincs választása, meg fogja javítani.) */
    @Override
    public void PrintManipChoice() {
        if (Game.GetInstance().IsTechnicianTurn() && GetBroken())
            return;

        PrototypeController.PrintLine("use the following command: " +
                                      "change input <elem_idx> output <elem_idx>");
    }

    /** Hozzáadja a pályának a pumpákat szortírozó gyűjteményéhez az adott pumpát. */
    public boolean NetworkAdd(Element e){
        return Game.GetInstance().GetNetwork().AddPump(this, e);
    }
}