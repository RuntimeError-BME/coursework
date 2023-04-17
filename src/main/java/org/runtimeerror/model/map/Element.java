package org.runtimeerror.model.map;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import org.runtimeerror.Main;
import org.runtimeerror.model.players.Player;
import org.runtimeerror.model.players.Manipulator;


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
    protected boolean pickUpAble = false; // fel tudják-e venni a játékosok
    protected final List<Player> players = new ArrayList<>(4); // játékosok, akik éppen rajta tartózkodnak
    private final SortedMap<Integer, Element> nbs = new TreeMap<>(); // szomszédos elemei
    private Element input = null; // a szomszédja (ha van ilyen), amiből folyhat bele víz
    private Element output = null; // a szomszédja (ha van ilyen), amibe folyhat belőle víz

    /**
     * Metódusok
     */
    /** Felrak magára egy játékos, ha ez lehetséges. A művelet sikerességével tér vissza. */
    public boolean AddPlayer(Player p) {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionCall(this, "GetPlayerCnt");
        GetPlayerCnt();
        players.add(p);
        Main.skeleton.PrintFunctionReturned("AddPlayer", "true");
        return true;
    }

    public boolean GetBroken(){
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionReturned("GetBroken", "false");
        return false;
    }

    /** Eltávolítja a rajta lévő játékosai közül az átadottat. */
    public void RemovePlayer(Player p) {
        Main.skeleton.PrintFunctionCalled(this);
        players.remove(p);
        Main.skeleton.PrintFunctionReturned("RemovePlayer", "");
    }

    /** Az átadott irányba lévő szomszédos elemét adja vissza, ha van ilyen. */
    public Element GetNb(Direction d) {
        Main.skeleton.PrintFunctionCalled(this);

        Main.skeleton.PrintFunctionReturned("GetNb", nbs.get(d.ordinal())==null?"null":"newInp/targetElem");
        return nbs.get(d.ordinal());

    }

    /** Megadott irányú szomszédjának állítja be az átadott elemet. */
    public void SetNb(Direction d, Element e) {
        Main.skeleton.PrintFunctionCalled(this);
        nbs.put(d.ordinal(), e);
        Main.skeleton.PrintFunctionReturned("SetNb", "");
    }

    /** Ha egy elem támogatni akarja azt a funkcionalitást, hogy egy szerelő rajta állva
     egy part-ot kaphasson a tárolójába, akkor ezt a függvényt kell felülírnia.
     Alapértelmezett megvalósítása üres törzsű függvény (alapjáraton nem támogatják az elemek ezt a funkcionalitást). */
    public void OnPickup() {
        // throw new NotImplementedException();
    }

    /** Absztrakt metódus, amely a származtatott osztályokban felül lesz írva a szerint,
     hogy hogyan eresztik át magukon a vizet. */
    public abstract void Flood();

    /** Absztrakt metódus, mivel az elem konkrét típusától függ a lefolyása.
     Az átadott manipulátorral manipulálja az elemet. */
    public abstract void Manipulate(Manipulator m);

    /** Visszaadja, hogy az elem felvehető-e. A származtatott elemek felüldefiniálhatják. */
    public boolean GetPickUpAble() {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionReturned("GetPickUpAble",pickUpAble && (players.size()==0) ? "true" : "false");

        return pickUpAble&&(players.size()==0);
    }

    /** A szomszédos elemek számát adja vissza. */
    public int GetNbCnt() {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionReturned("GetNbCnt",""+nbs.size());
        return nbs.size();
    }

    /** A rajta tartózkodó játékosok számát adja vissza. */
    public int GetPlayerCnt() {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionReturned("GetPlayerCnt", Integer.toString(players.size()));
        return players.size();
    }

    /** Visszaadja, hogy van-e benne víz. */
    public boolean GetFlooded() {
        Main.skeleton.PrintFunctionCalled(this);

        Main.skeleton.PrintFunctionReturned("GetFlooded", flooded ? "true" : "false");
        return flooded;

    }

    /** Visszaadja a bemeneti elemét, ha van ilyen. */
//    public Element GetInput() {
//        throw new NotImplementedException();
//    }

    /** Visszaadja a kimeneti elemét, ha van ilyen. */
    public Element GetOutput() {
        Main.skeleton.PrintFunctionCalled(this);

        Main.skeleton.PrintFunctionReturned("GetOutput",output==null ? "null" : "output");
        return output;
    }

    /** Az átadott érték szerint állítja be, hogy van-e víz jelenleg benne. */
    public void SetFlooded(boolean f) {
        Main.skeleton.PrintFunctionCalled(this);
        flooded=f;
        Main.skeleton.PrintFunctionReturned("SetFlooded","");
    }

    /**
     * A bemenetét az átadott elemre állítja be.
     * @param e - az új bemenet
     */
    public void SetInput(Element e) {
        Main.skeleton.PrintFunctionCalled(this);
        input=e;
        Main.skeleton.PrintFunctionReturned("SetInput", "");

    }

    /** A kimenetét az átadott elemre állítja be.
     * @param e - az új kimenet
     */
    public void SetOutput(Element e) {
        Main.skeleton.PrintFunctionCalled(this);
        output=e;
        Main.skeleton.PrintFunctionReturned("SetOutput", "");
    }
}