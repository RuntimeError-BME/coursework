package org.runtimeerror.model.map;

import org.runtimeerror.controller.Game;
import org.runtimeerror.model.players.ManipulatorPlayer;
import org.runtimeerror.model.players.Player;

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
        if(output.GetPickUpAble_onlyAttribute()){
            output.SetInput(this);
            for (Element e: output.GetNbs()){
                if(e!=this) output.SetOutput(e);
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

        System.out.print("\n\tbroken: " + GetBroken());
        System.out.print("\n\tinput: " + GetInput().GetIdx());
        System.out.print("\n\toutput: " + GetOutput().GetIdx());
        System.out.print("\n");
    }

    /** Hozzáadja a pályának a pumpákat szortírozó gyűjteményéhez az adott pumpát. */
    public boolean NetworkAdd(Element e){
        return Game.GetInstance().GetNetwork().AddPump(this,e);
    }
}