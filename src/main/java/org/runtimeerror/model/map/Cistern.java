package org.runtimeerror.model.map;

import org.runtimeerror.controller.Game;
import org.runtimeerror.model.players.ManipulatorPlayer;
import org.runtimeerror.model.players.Player;

/**
 * Amennyi csőből folyik víz ebbe az elembe, annyi pontot oszt a szerelők csapatának.
 * Erről az elemről a játékosok közvetlen más ciszternákra tudnak lépni.
 */
public final class Cistern extends Element {

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
        for (int i = 0; i < 4; ++i) { // végigmegyünk a(z első) 4 irányon, amerre letehetünk új csövet
            Direction d = new Direction(i); // az adott irány
            Element nb = GetNb(d); // az adott irányban lévő szomszédos elem a ciszternától
            if (nb == null) { // ha még nincs arra szomszédja
                Pipe newPipe = new Pipe(); // akkor létrehozunk egy új csövet
                SetNb(d, newPipe); // beállítjuk az adott irányba a ciszternától az új csövet szomszédnak
                newPipe.SetNb(d.Opposite(), this); // beállítjuk az új csőnek az ellentétes irányba a ciszternát is
                Game.GetInstance().GetNetwork().AddPipe(newPipe); // hozzáadjuk a pályához is
                break; // ezzel egy új cső került lehelyezésre, végeztünk, egyszerre csak egy csövet tesz le
            }
        } // ha mind a négy irányba van már szomszédja, akkor nem fog új cső teremni a szomszédjában
    }

    /**
     * Csak a prototípusban használt függvény, ami kiírja a ciszterna adatait a következő formátumban:
     *  details of element [idx] (cistern):
     *      flooded: true/false
     *      players: [player_name] [player_name] ...
     *      nbs: [dir_nr] [dir_nr] ...
     */
    @Override
    public void Print() {
        int idx = Game.GetInstance().GetNetwork().GetElements().indexOf(this);
        System.out.print("details of element " + idx + " (cistern):" +
                         "\n\tflooded: " + GetFlooded() +
                         "\n\tplayers: ");
        for (Player player : players)
            System.out.print(player.GetName() + " ");

        System.out.print("\n\tnbs: ");
        int cnt = 0, i = 0;
        while (cnt < GetNbCnt()) {
            Element nb = GetNb(new Direction(i));
            if (nb != null) {
                System.out.print(i + " ");
                ++cnt;
            }
            ++i;
        }
        System.out.print("\n");
    }
}