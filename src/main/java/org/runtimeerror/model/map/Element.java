package org.runtimeerror.model.map;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.runtimeerror.model.players.ManipulatorPlayer;
import org.runtimeerror.model.players.Player;

/**
 * Player-eket tud felrakni, illetve eltávolítani magáról, és számon is tartja a rajta tartózkodókat.
 * Tárolja a bemenet/kimenetét, valamint a szomszédjait is, ezeket tudja változtatni, illetve lekérdezni.
 * Továbbá tárolja, hogy van-e víz benne.
 */
public abstract class Element {
    /**
     * Attribútumok
     */
    private boolean flooded = false; // van-e benne jelenleg víz
    protected static boolean pickUpAble = false; // fel tudják-e venni a játékosok
    protected final List<Player> players = new ArrayList<>(4); // játékosok, akik éppen rajta tartózkodnak
    private SortedMap<Integer, Element> nbs = new TreeMap<>(); // szomszédos elemei
    private Element input = null; // a szomszédja (ha van ilyen), amiből folyhat bele víz
    private Element output = null; // a szomszédja (ha van ilyen), amibe folyhat belőle víz

    /** Felrakja magára az átadott játékost. Ehhez eltávolítja a játékost a jelenlegi eleméről, hozzáadja ennek az
     * elemnek a players gyűjteményéhez, végül pedig átállítja rá a játékos jelenlegi elemét.
     * Felül kell írni azokban a származtatott elemekben, ahol nem szeretnénk megengedni, hogy bármikor rá lehessen
     * lépni az adott elemre (pl. ne lehessen több játékos egy elemen, lásd Pipe). */
    public void AddPlayer(Player p) {
        p.GetCurrElem().RemovePlayer(p); // eltávolítjuk a játékost a régi eleméről
        players.add(p); // az új elem játékosaihoz hozzáadjuk
        p.SetCurrElem(this); // átállítjuk a játékost jelenlegi elemét rá
    }

    /** Eltávolítja a rajta lévő játékosai közül az átadottat. */
    public void RemovePlayer(Player p) {
        players.remove(p);
    }

    /** Az átadott irányba lévő szomszédos elemét adja vissza, ha van ilyen. */
    public Element GetNb(Direction d) {
        return nbs.get(d.ordinal());
    }

    /** Megadott irányú szomszédjának állítja be az átadott elemet. */
    public void SetNb(Direction d, Element e) {
        nbs.put(d.ordinal(), e);
    }

    /**
     * Átmásolja a szomszédos elemek gyűjteményét (annak referenciáját állítja át) az átadott elemben lévőből.
     * AddPump() fogja egyedül hívni ezt a metódust, amikor lecserél egy csövet egy pumpára.
     */
    public void CopyNbs(Element e) {
        nbs = e.nbs;
    }

    /** Megadja a legnagyobb értékű irányt, amerre van szomszédja az elemnek. Ha egyetlen szomszédja sincs az
     * elemnek, akkor -1 -et ad vissza.
     * Használat helye: Pipe.AddPlayer függvényének a csúszós csőre lépés része. */
    public int GetNbMaxDirNumber() {
        int max = -1;
        for (int dir : nbs.keySet()) {
            if (dir > max)
                max = dir;
        }
        return max;
    }

    /** Megadja a legkisebb értékű irányt, amerre van szomszédja az elemnek. Ha egyetlen szomszédja sincs az
     * elemnek, akkor -1 -et ad vissza.
     * Használat helye: Pipe.AddPlayer függvényének a csúszós csőre lépés része. */
    public int GetNbMinDirNumber() {
        if (GetNbCnt() <= 0)
            return -1;

        int min = GetNbMaxDirNumber();
        for (int dir : nbs.keySet()) {
            if (dir < min)
                min = dir;
        }
        return min;
    }

    /** Ha egy elem támogatni akarja azt a funkcionalitást, hogy egy szerelő rajta állva
     egy part-ot kaphasson a tárolójába, akkor ezt a függvényt kell felülírnia.
     Alapértelmezett megvalósítása üres törzsű függvény (alapjáraton nem támogatják az elemek ezt a funkcionalitást). */
    public void OnPickup() { }

    /** Absztrakt metódus, amely a származtatott osztályokban felül lesz írva aszerint,
     hogy hogyan eresztik át magukon a vizet. */
    public abstract void Flood();

    /** Absztrakt metódus, mivel az elem konkrét (dinamikus) típusától függ a lefolyása.
     Az átadott manipulátorral manipulálja az elemet. */
    public abstract void Manipulate(ManipulatorPlayer m);

    /** Visszaadja, hogy az elem felvehető-e (pickUpAble attribútum igaz ÉS nem áll rajta egy játékos sem).
     * A származtatott elemek felüldefiniálhatják (célszerű összeéselni az ősben (itt) lévő megvalósítással az új
     * feltételeket, lásd Breakable.GetPickUpAble()). */
    public boolean GetPickUpAble() {
        return pickUpAble && players.isEmpty();
    }

    /**
     * Egyszerűen visszaadja pickUpAble attribútum értékét. Nem összetévesztendő a gyakrabban használt GetPickUpAble
     * fv-nyel, ami ettől többet is csinál: azt is megvizsgálja, hogy játékos se álljon az elemen.
     */
    public boolean GetPickUpAble_onlyAttribute() {
        return pickUpAble;
    }

    /** A szomszédos elemek számát adja vissza. */
    public int GetNbCnt() {
        return nbs.size();
    }

    /** A rajta tartózkodó játékosok számát adja vissza. */
    public int GetPlayerCnt() {
        return players.size();
    }

    /** Visszaadja, hogy van-e benne víz. */
    public boolean GetFlooded() {
        return flooded;
    }

    /** Az átadott érték szerint állítja be, hogy van-e víz jelenleg benne. */
    public void SetFlooded(boolean flooded) {
        this.flooded = flooded;
    }

    /** Visszaadja a bemeneti elemét, ha van ilyen. */
    public Element GetInput() {
        return input;
    }

    /**
     * A bemenetét az átadott elemre állítja be.
     */
    public void SetInput(Element e) {
        input = e;
    }

    /** Visszaadja a kimeneti elemét, ha van ilyen. */
    public Element GetOutput() {
        return output;
    }

    /** A kimenetét az átadott elemre állítja be. */
    public void SetOutput(Element e) {
        output = e;
    }

    /**
     * Visszaadja, hogy az elem törött-e. Mindig hamist ad vissza. Csak Breakable írja felül ezt a függvényt, azaz
     * csak a Breakable leszármazottak lehetnek töröttek (és nem töröttek - a két állapot közül az egyik).
     * Az Element -> Breakable downcast elkerülésének céljából van "itt fent" megvalósítva ez a függvény.
     */
    public boolean GetBroken() {
        return false;
    }

    /**
     * Visszaadja, hogy az elem ragadós-e. Mindig hamist ad vissza. Csak Pipe írja felül ezt a függvényt, azaz csak
     * a Pipe-ok lehetnek ragadósak (és nem ragadósak - a két állapot közül az egyik). Az Element -> Pipe downcast
     * elkerülésének céljából van "itt fent" megvalósítva ez a függvény, ugyanis NextTurn() függvénynek szüksége van
     * erre az információra GetCurrPlayer().GetCurrElem() által visszaadott Element-en keresztül, lásd NextTurn.
     */
    public boolean GetSticky() {
        return false;
    }

    /**
     * Csak a prototípusban használt függvény, ami kiírja az elem adatait.
     * Absztrakt függvény, amit a konkrét leszármazottakban meg kell valósítani.
     */
    public abstract void Print();
}