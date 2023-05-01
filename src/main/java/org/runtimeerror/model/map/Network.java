package org.runtimeerror.model.map;
import java.util.List;
import java.util.ArrayList;
import org.runtimeerror.Main;


/**
 * Tárolja a pálya elemeit. Csövek és pumpák adhatók hozzá, a csöveket pedig el is lehet távolítani róla.
 * Vizsgálja, hogy ezek a műveletek lehetségesek-e egyáltalán.
 * A benne tárolt elemek azok, amik ténylegesen meg is lesznek jelenítve a pályán.
 * Továbbá ez az osztály indítja el a vízfolyást forrásokból.
 */
public class Network {
    /**
     * Attribútumok
     */
    private final List<Element> elements = new ArrayList<>(32); // a pálya összes elemét tárolja

    /** Külön csoportosítja minden elem közül a ciszternákat. (De az "elements"-ben is szerepelnek.) */
    private final List<Cistern> cisterns = new ArrayList<>(4);

    /** Külön csoportosítja minden elem közül a forrásokat. (De az "elements"-ben is szerepelnek.) */
    private final List<Source> sources = new ArrayList<>(4);

    /** Külön csoportosítja minden elem közül a pumpákat. (De az "elements"-ben is szerepelnek.) */
    private final List<Pump> pumps = new ArrayList<>(8);

    /**
     * Metódusok
     */
    /** Megkísérli eltávolítani az átadott elemet a pályáról. Ha ennek semmi akadálya nem volt
     (pl. nem esne komponensekre a pálya, mint gráf), akkor igazzal tér vissza.
     Ellenkező esetben nem módosít semmi a pályán. */
    public boolean RemoveElem(Element e) {
        Main.skeleton.PrintFunctionCalled(this);
        boolean re=elements.remove(e);
        Main.skeleton.PrintFunctionReturned("RemoveElem",re ? "true" : "false");
        return re;
    }

    /** Az átadott csövet megkísérli hozzáadni a pályához. Ennek sikerességét adja vissza. */
    public boolean AddPipe(Pipe p) {
        Main.skeleton.PrintFunctionCalled(this);
        if(elements.size()<32){
            boolean b=elements.add(p);
            Main.skeleton.PrintFunctionReturned("AddPipe",b ? "true":"false");
            return b;
        }
        else {
            Main.skeleton.PrintFunctionReturned("AddPipe","false");
            return false;
        }
    }

    /** Az átadott pumpával kísérli meg felülírni a másik átadott elemet a pályán. Ennek sikerességét adja vissza. */
    public boolean AddPump(Pump p, Element e) {
        Main.skeleton.PrintFunctionCalled(this);

        boolean b=(elements.remove(e) && pumps.add(p) && elements.add(p));

        Main.skeleton.PrintFunctionReturned("AddPump", b ? "true" : "false");
        return b;
    }

    /** Felapasztja az összes vizet a pályáról, majd minden forrásból elindítja a vizet,
     aminek következtében el lesz árasztva vízzel a pálya, és pontokat fognak kapni a csapatok. */
    public void Flood() {
        for (Element element : elements) {
            element.Flood();
        }

        for (Source source : sources) { // végigmegyünk a pálya összes forrásán
            source.Flood(); // és elárasztjuk őket (ez majd minden további elemet eláraszt a pályán)
        }
    }

    /** Visszaadja a pálya minden elemét. */
    public final List<Element> GetElements() {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionReturned("GetElements","elements");
        return elements;
    }

    /** Visszaadja a pályán lévő pumpákat. */
    public final List<Pump> GetPumps() {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionReturned("GetPumps","pumps");
        return pumps;
    }

    /** Visszaadja a pályán lévő csöveket. */
    public final List<Pipe> GetPipes() {
        return pipes;
    }

    /** Visszaadja a pályán lévő ciszternákat. */
    public final List<Cistern> GetCisterns() {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionReturned("GetCisterns","cists");
        return cisterns;
    }

    /** --------- CSAK A PÁLYA INICIALIZÁLÁSKOR HASZNÁLT FÜGGVÉNYEK (nincs hibakezelés bennük): --------- */

    /** Az átadott ciszternát hozzáadja a pályához. */
    public boolean AddCistern(Cistern c) {
        Main.skeleton.PrintFunctionCalled(this);
        if(cisterns.size()==4){
            Main.skeleton.PrintFunctionReturned("AddCistern", "false");
            return false;
        }
        else{
            cisterns.add(c);
            Main.skeleton.PrintFunctionReturned("AddCistern", "true");
            return true;
        }
    }

    /** Az átadott forrást hozzáadja a pályához. */
    public boolean AddSource(Source s) {
        Main.skeleton.PrintFunctionCalled(this);
        if(sources.size()==4){
            Main.skeleton.PrintFunctionReturned("AddSource", "false");
            return false;
        }
        else{
            sources.add(s);
            Main.skeleton.PrintFunctionReturned("AddSource","true");
            return true;
        }

    }
}