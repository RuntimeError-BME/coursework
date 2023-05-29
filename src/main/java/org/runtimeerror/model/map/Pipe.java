package org.runtimeerror.model.map;

import org.runtimeerror.controller.Game;
import org.runtimeerror.model.players.Player;
import org.runtimeerror.model.players.ManipulatorPlayer;
import org.runtimeerror.prototype.PrototypeController;

/**
 * Olyan elromolható, felvehető elem, amelyen legfeljebb egy játékos tartózkodhat.
 * Ki lehet lyukasztani, a lyukas csövet pedig meg is lehet javítani, ekkor egy (véletlenszerű) ideig nem lehet
 * kilyukasztani ismét. Ha a cső nem lyukas akkor a kimenetéhez vizet tud juttatni.
 * Ha lyukas, vagy a kimenete üres, akkor a szabotőrök pontot kapnak, amikor víz érkezik belé.
 * Ha nem lyukas, akkor csúszóssá vagy ragadóssá lehet tenni egy időre.
 * Ha csúszós a cső, akkor aki rálép véletlenszerűen az egyik végére csatlakoztatott elemre csúszik át.
 * Ilyenkor se lyukasztani, se ragadóssá tenni nem lehet az adott csövet.
 * Ha ragadós a cső, akkor aki rálép, annak a köre véget ér. Ebben az esetben se lyukasztani, se csúszóssá nem lehet
 * tenni az adott csövet. Egy cső általában kevesebb ideig ragadós, mint csúszós.
 * Tehát összegezve: egyszerre csak egy állapotban lehet a felsoroltak (törött, ragadós, csúszós) közül egy cső.
 */
public final class Pipe extends Breakable {
    /** Attribútumok */
    private boolean sticky = false; // ragadós-e a cső (aki rálép, véget fog-e érni a köre)
    private boolean slippery = false; // csúszós-e a cső (aki rálép, véletlenszerű szomszédra kerül azonnal)
    /** időszámláló, ami körökben adja meg, hogy még hány körig (turn) nem lehet lyukasztani a csövet,
     * vagy hogy még hány körig (turn) lesz ragadós vagy csúszós. */
    private int counter = 0;

    /** Konstruktor, ami beállítja az ősbeli indexetét az elemnek, illetve
     * igazra állítja az ősből örökölt protected pickUpAble attribútumot, hiszen a csövek felvehető elemek. */
    public Pipe() { super(); pickUpAble = true; }
    public Pipe(int idx){super(idx); pickUpAble = true; }

    /** Visszaadja, hogy a cső ragadós-e. Felülírja az Element ősben lévő megvalósítást, lásd: Element.GetSticky(). */
    @Override
    public boolean GetSticky() {
        return sticky;
    }

    /** Az átadott logikai értéknek megfelelően állítja be, hogy a cső ragadós-e. */
    public void SetSticky(boolean sticky){
        this.sticky = sticky;
    }

    /** Visszaadja, hogy a cső csúszós-e. */
    @Override
    public boolean GetSlippery(){
        return slippery;
    }

    /** Az átadott logikai értéknek megfelelően állítja be, hogy a cső csúszós-e. */
    public void SetSlippery(boolean slippy) {
        slippery = slippy;
    }

    /** Visszaadja az állapot visszaszámláló attribútum (counter) értékét. */
    public int GetCounter() { return counter; }

    /** Az átadott egész értékre állítja be az állapot visszaszámláló attribútumot (counter). */
    public void SetCounter(int value) { counter = value; }

    /**
     * Felhelyezi az átadott játékost magára, ha nem áll rajta már más valaki. A művelet sikerességével tér vissza.
     * (Felülírja az Element ősben lévő megvalósítást - itt már nem mindig sikerül rálépni a csőre.)
     * FONTOS MÓDOSÍTÁS: ha csúszós csőre lépne, akkor véletlenszerűen egy másik szomszédos csőre fog átkerülni (vagy
     * determinisztikus viselkedés esetén a legkisebb sorszámú irányban lévőre), ha pedig ragadósra, akkor véget fog
     * írni a köre egyből.
     */
    @Override
    public void AddPlayer(Player p) {

        if (GetPlayerCnt() > 0) // ha már áll valaki a csövön
            return; // akkor nem lehet rálépni, nem lesz felhelyezve rá az átadott játékos

        if (GetSlippery()) { // ha csúszós a cső
            if (GetNbCnt() == 1) // ha csak egy szomszédja van neki (ahonnan épp érkezik a játékos)
                return; // akkor marad ugyan ott (visszacsúszik oda, ahonnan jött)
            // azzal az esettel nem foglalkozunk, amikor egy szomszéd nélküli csövön áll - úgy sem tud mozogni ilyenkor

            super.AddPlayer(p); // rálép a csúszós csőre, viszont tovább fog róla csúszni

            // ez lesz a másik elem, ahová csúszni fog
            Element targetElem2 = null; // vagy ahonnan érkezett az az elem lesz, vagy a célpont másik szomszédja
            int try1 = 0, try2 = 1;
            if (Game.GetInstance().GetDeterministic() || Game.GetInstance().SlipToHigherNb())
                { try1 = 1; try2 = 0; } // ha a nagyobb sorszámú felé fog csúszni

            Element currElem = p.GetCurrElem(); // eddig itt volt
            targetElem2 = GetNbs().get(try1); // ide szeretnénk, hogy kerüljön
            if (targetElem2 != null) { // ha volt ilyen
                p.MoveTo(targetElem2); // átkerül a játékos az előzőekben meghatározott szomszédos elemre
                if (currElem != p.GetCurrElem())
                    return;
            } // ha arra nem sikerült, akkor az alacsonyabb indexű elemre is megpróbáljuk
            targetElem2 = GetNbs().get(try2); // különben pedig az elsőre (ami biztosan létezik)
            p.MoveTo(targetElem2);

        } else { // ha NEM csúszós a cső
            super.AddPlayer(p); // akkor biztosan rá fog kerülni erre a csőre (az ősbéli megvalósítás gondoskodik erről)
            if (GetSticky()) { // ha viszont a cső ragadós, amire lépett
                PrototypeController.PrintLine("");
                Game.GetInstance().NextTurn(); // akkor véget ér a köre
            }
        }
    }

    /** A paraméterként kapott manipulátorral manipulálja ezt a konkrét elemet.
     * (Meghívja az átadott manipulátoron a Manipulate() fv-t, és átadja önmagát neki az elem.) */
    @Override
    public void Manipulate(ManipulatorPlayer m) {
        m.Manipulate(this);
    }

    /** Vízzel tölti meg magát. Ha lyukas a cső vagy nincs output-ja akkor pontot kapnak a szabotőrök,
     * különben pedig hívja tovább az output-ján a Flood() függvényt (ereszti tovább a vizet).
     */
    @Override
    public void Flood() {
        if (GetFlooded()) return;
        SetFlooded(true); // víz kerül belé
        Element output = GetOutput();
        if (GetBroken() || output == null) { // ha lyukas vagy nincs kimenete
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
        if (!output.IsMultiInput() && output.GetInput() != this) // annak az esete, amikor egy pumpába nem tud víz folyni,
            return; // mert nem ez a cső a bemenete

        output.Flood(); // ha viszont minden rendben, akkor folyatja tovább a vizet a kimenetére
    }

    /**
     * Csak a prototípusban használt függvény, ami kiírja a cső adatait a következő formátumban:
     *  details of element [idx] (pipe):
     *      flooded: true/false
     *      players: [player_name] [player_name] ...
     *      nbs: [dir_nr] [dir_nr] ...
     *      broken: true/false
     *      sticky: true/false
     *      slippery: true/false
     *      counter: [value]
     */
    @Override
    public void Print(String part) {
        super.Print("pipe");
        System.out.print("\tbroken: " + GetBroken());
        System.out.print("\n\tsticky: " + GetSticky());
        System.out.print("\n\tslippery: " + GetSlippery());
        System.out.print("\n\tcounter: " + GetCounter());
        System.out.print("\n");
    }

    /** Felsorolja a játékosnak, hogy miket tehet az adott csővel. */
    @Override
    public void PrintManipChoice() {
        if (GetBroken())
            return;

        if (Game.GetInstance().IsTechnicianTurn()) {
            if (counter > 0)
                PrototypeController.PrintLine("choose: stickify");
            else
                PrototypeController.PrintLine("choose: stickify/break");
        } else {
            if (counter > 0)
                PrototypeController.PrintLine("choose: stickify/slippify");
            else
                PrototypeController.PrintLine("choose: stickify/slippify/break");
        }
    }

    /** Hozzáadja a pályának a csöveket szortírozó gyűjteményéhez az adott csövet. */
    public boolean NetworkAdd(Element e){
        return Game.GetInstance().GetNetwork().AddPipe(this);
    }
}