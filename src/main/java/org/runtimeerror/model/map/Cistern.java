package org.runtimeerror.model.map;

import org.runtimeerror.controller.Game;
import org.runtimeerror.model.players.ManipulatorPlayer;
import org.runtimeerror.model.players.Player;

/**
 * Amennyi csőből folyik víz ebbe az elembe, annyi pontot oszt a szerelők csapatának.
 * Erről az elemről a játékosok közvetlen más ciszternákra tudnak lépni.
 */
public final class Cistern extends Element {

    /** Konstruktor, ami beállítja az ősbeli indexetét az elemnek. */
    public Cistern() { super(); }

    /** Megpróbál pumpát adni a soron lévő játékos tárolójába.
     * (Akkor lesz sikeres, ha a jelenlegi játékos szerelő, de nem szükséges semmit ellenőrizni, mert ha szabotőr,
     * akkor a Player ősbéli SetPart() fog hívódni, ami egy üres törzsű függvény a downcast elkerülésének céljából.) */
    @Override
    public void OnPickup() {
        Player player = Game.GetInstance().GetCurrPlayer(); // a jelenlegi játékos
        Pump newPump = new Pump(); // az új pumpa, amit meg fog kapni a játékos
        player.SetPart(newPump); // betesszük a tárolójába
        // megjegyzés: a pályához csak akkor lesz hozzáadva, amikor le is lesz téve
    }

    /** Vízzel tölti fel magát (SetFlooded fv-nyel), és pontot ad a szerelőknek (AddTechnicianPoints fv-nyel).
     * A pálya végpontjaként nem áramoltat más elemekbe vizet. */
    @Override
    public void Flood() {
        SetFlooded(true);
        Game.GetInstance().AddTechnicianPoints(1);
    }

    /** A paraméterként kapott manipulátorral manipulálja ezt a konkrét elemet.
     * (Meghívja az átadott manipulátoron a Manipulate() fv-t, és átadja önmagát neki az elem.) */
    @Override
    public void Manipulate(ManipulatorPlayer m) {
        m.Manipulate(this);
    }

    /**
     * Pontosan egy csövet helyez le magától egy tőle szomszédos helyre a következő irányok egyikébe: északra (0),
     * nyugatra (1), keletre (2), délre (3). Ez prioritási sorrend is, az első irányba fog teremni, amerre üres
     * szomszédja van. Ha egyik irányba sincs ezek közül üres szomszédja, akkor más irányba már nem fog cső teremni.
     */
    public void ProducePipe() {
        if(GetNbCnt() < 4){
            Pipe newPipe = new Pipe();
            if(newPipe.NetworkAdd(null)){
                AddNb(newPipe);
                newPipe.AddNb(this);
                newPipe.SetOutput(this);
            }
        }
    }

    /**
     * Csak a prototípusban használt függvény, ami kiírja a ciszterna adatait a következő formátumban:
     *  details of element [idx] (cistern):
     *      flooded: true/false
     *      players: [player_name] [player_name] ...
     *      nbs: [dir_nr] [dir_nr] ...
     */
    @Override
    public void Print(String part) {
        super.Print("cistern");
    }

    @Override
    public boolean NetworkAdd(Element e) {
        return false;
    }
}