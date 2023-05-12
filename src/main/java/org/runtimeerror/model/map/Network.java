package org.runtimeerror.model.map;

import org.runtimeerror.controller.Game;
import org.runtimeerror.prototype.PrototypeController;

import java.io.PrintStream;
import java.util.List;
import java.util.ArrayList;

/**
 * Tárolja a pálya elemeit. Csövek, pumpák, ciszternák és források adhatók hozzá, a csöveket pedig el is lehet
 * távolítani róla. Vizsgálja, hogy ezek a műveletek lehetségesek-e egyáltalán.
 * A benne tárolt elemek azok, amik ténylegesen meg is lesznek jelenítve a pályán.
 * Továbbá ez az osztály indítja el a vízfolyást forrásokból.
 */
public final class Network {
    /**
     * Attribútumok
     */
    private final List<Element> elements = new ArrayList<>(32); // a pálya összes elemét tárolja

    /** Külön csoportosítja minden elem közül a forrásokat. (De az "elements"-ben is szerepelnek.) */
    private final List<Source> sources = new ArrayList<>(4);

    /** Külön csoportosítja minden elem közül a ciszternákat. (De az "elements"-ben is szerepelnek.) */
    private final List<Cistern> cisterns = new ArrayList<>(4);

    /** Külön csoportosítja minden elem közül a pumpákat. (De az "elements"-ben is szerepelnek.) */
    private final List<Pump> pumps = new ArrayList<>(8);

    /** Külön csoportosítja minden elem közül a csöveket. (De az "elements"-ben is szerepelnek.) */
    private final List<Pipe> pipes = new ArrayList<>(16);

    /** Eltávolítja az átadott elemet a pályáról. (A szortírozó gyűjtemények egyikéből ("pipes",
     "pumps", "cisterns" vagy "sources") távolítja csak el, "elements"-ből sosem lesz eltávolítva, még ha nincs is a
     pályán az elem, mert például egy szerelő tárolójában van. Az egyetlen mód egy elem "megsemmisítésére" a Game
     Reset() függvényén kívül az AddPipe() függvény, ami felülírja az adott indexen lévő csövet egy pumpával. De
     eltávolítani nem szabad "elements"-ből, mert fixnek kell lennie annak, hogy mely indexein milyen elemeket tárol. )
     FIGYELEM: a hívó felelőssége, hogy ne adjon át null-t! */
    public void RemoveElem(Element e) {
        // eltávolítjuk a típus szerint szortírozott gyűjtemények egyikéből is
        for (Pipe pipe : pipes) { // végigmegyünk a csöveket szortírozó gyűjteményen
            if (e == pipe) { // ha benne van az átadott elem
                pipes.remove(pipe); // akkor eltávolítjuk belőle
                return; // és kész is vagyunk, a többiben már biztosan nincsen
            }
        }
        for (Pump pump : pumps) { // végigmegyünk a pumpákat szortírozó gyűjteményen
            if (e == pump) { // ha benne van az átadott elem
                pumps.remove(pump); // akkor eltávolítjuk belőle
                return; // és kész is vagyunk, a többiben már biztosan nincsen
            }
        }
        for (Cistern cistern : cisterns) { // végigmegyünk a ciszternákat szortírozó gyűjteményen
            if (e == cistern) { // ha benne van az átadott elem
                cisterns.remove(cistern); // akkor eltávolítjuk belőle
                return; // és kész is vagyunk, a többiben már biztosan nincsen
            }
        }
        for (Source source : sources) { // végigmegyünk a forrásokat szortírozó gyűjteményen
            if (e == source) { // ha benne van az átadott elem
                sources.remove(source); // akkor eltávolítjuk belőle
                return; // és kész is vagyunk
            }
        }
    }

    /** Az átadott csövet megkísérli hozzáadni a pályához. A művelet sikerességével tér vissza (egy csőnek a lerakás
     * után nem lehet több, mint 2 szomszédja, vagy máshogy fogalmazva nem futhatnak párhuzamosan egymás mellett
     * csövek - ez a hibaellenőrző viselkedés még nem lesz megvalósítva a prototípusban, a tesztelő felelőssége).
     * (Az új csövet hozzáadja az "elements" és "pipes" gyűjteményhez is.) */
    public boolean AddPipe(Pipe p) {
        if (!elements.contains(p)) // erre a check-re a felvett elem újra letétele miatt van szükség
            elements.add(p);
        pipes.add(p);

        if (PrototypeController.IsLogging())
            System.out.println("a pipe was added as element " + p.GetIdx() + ".\n");
        return true;
    }

    /** Az átadott pumpával kísérli meg felülírni a másik átadott elemet a pályán. Ennek sikerességét adja vissza. */
    public boolean AddPump(Pump p, Element e) {

        Element inp = e.GetInput(); // a felülírandó elem bemenete
        Element out = e.GetOutput(); // a felülírandó elem kimenete

        if (inp == null || out == null || // ha a felülírandó elemnek nincsen cső bemenete és kimenete
           !inp.GetPickUpAble_onlyAttribute() || !out.GetPickUpAble_onlyAttribute())
            return false; // akkor nem lehetséges a pumpalehelyezés (csak két cső közé kerülhet pumpa)

        p.SetInput(inp); // beállítjuk a pumpa bemenetét a felülírt cső bemenetére
        inp.SetOutput(p); // a bemenetének a kimenete most már nem a felülírandó cső lesz, hanem a pumpa
        p.SetOutput(out); // beállítjuk a pumpa kimenetét a felülírt cső kimenetére
        out.SetInput(p); // a kimenetének a bemenete most már nem a felülírandó cső lesz, hanem a pumpa
        p.SetFlooded(e.GetFlooded()); // ha a felülírandó csőben volt víz, akkor a lehelyezendő pumpában is lesz
        // átmásoljuk belé a felülírandó elem szomszédjait
        p.AddNb(inp);
        p.AddNb(out);

        // a bemenetnek azt a szomszédját, amerre a felülírandó cső van, felül kell írnunk az új pumpával
        inp.RemoveNb(e);
        inp.AddNb(p);

        // a kimenetnek azt a szomszédját, amerre a felülírandó cső van, felül kell írnunk az új pumpával
        out.RemoveNb(e);
        out.AddNb(p);


        RemoveElem(e); // eltávolítjuk a felülírandó csövet a szortírozó gyűjtemények egyikéből
        int idx = elements.indexOf(e); // a felülírandó elem indexe a minden elemet tároló gyűjteményben
        elements.set(idx, p); // felülírjuk benne a csövet az újonnan lehelyezendő pumpával
        pumps.add(p); // hozzáadjuk az új pumpát a pumpákat szortírozó gyűjteményhez

        if (PrototypeController.IsLogging())
            System.out.println("element " + e.GetIdx() + " pipe replaced by new pump.\n");
        return true; // jelezzük a sikeres pumpalehelyezést
    }

    /** Az átadott ciszternát hozzáadja a pályához.
     * (Az új ciszternát hozzáadja az "elements" és "cisterns" gyűjteményhez is.) */
    public void AddCistern(Cistern c) {
        elements.add(c);
        cisterns.add(c);
        if (PrototypeController.IsLogging())
            System.out.println("a cistern was added as element " + c.GetIdx() + ".\n");
    }

    /** Az átadott forrást hozzáadja a pályához.
     * (Az új forrást hozzáadja a az "elements" és "sources" gyűjteményhez is.) */
    public void AddSource(Source s) {
        elements.add(s);
        sources.add(s);
        if (PrototypeController.IsLogging())
            System.out.println("a source was added as element " + s.GetIdx() + ".\n");
    }

    /** Megváltoztatja az átadott indexű pumpa be- és kimenetét az átadott indexű elemekre.
     * Csak inicializáláskor van használva, ezért nincs hibakezelés. */
    public void ChangePumpDirs(int pumpIdx, int inputIdx, int outputIdx) {
        Pump p = null;
        for (Pump pump : pumps) {
            if (pump.GetIdx() == pumpIdx) {
                p = pump;
                break;
            }
        }
        if (p == null)
            return;

        Element input = null, output = null;
        for (Element e : elements) {
            if (e.GetIdx() == inputIdx) {
                input = e;
            } else if (e.GetIdx() == outputIdx) {
                output = e;
            }
        }
        p.SetInput(input);
        p.SetOutput(output);
        if (PrototypeController.IsLogging())
            System.out.println("element " + pumpIdx + " pump new input " + inputIdx + " and output " + outputIdx +
                            ", change made by controller");
    }

    /** Felapasztja az összes vizet a pályáról, majd minden forrásból elindítja a vizet,
     aminek következtében el lesz árasztva vízzel a pálya, és pontokat fognak kapni a csapatok. */
    public void Flood() {
        for (Element element : elements) // végigmegyünk a pálya összes elemén
            element.SetFlooded(false); // mindegyikben felapasztjuk a vizet

        for (Source source : sources) // végigmegyünk a pálya összes forrásán
            source.Flood(); // és elárasztjuk őket (ez majd minden további elemet eláraszt a pályán, és pontokat oszt)
    }

    /** Visszaadja a pálya minden elemét tároló ("elements") gyűjteményt. */
    public List<Element> GetElements() {
        return elements;
    }

    /** Visszaadja a pályán lévő csöveket ("pipes" gyűjteményt). */
    public List<Pipe> GetPipes() {
        return pipes;
    }

    /** Visszaadja a pályán lévő pumpákat ("pumps" gyűjteményt). */
    public List<Pump> GetPumps() {
        return pumps;
    }

    /** Visszaadja a pályán lévő ciszternákat ("cisterns" gyűjteményt). */
    public List<Cistern> GetCisterns() {
        return cisterns;
    }

    /** A pálya minden ciszternája körül megpróbál egy-egy új csövet teremteni
     * (hívja "cisterns" gyűjtemény elemein a ProducePipe() metódust). */
    public void ProducePipesAroundCisterns() {
        for (Cistern cistern : cisterns)
            cistern.ProducePipe();
    }

    /** Visszaadja a megadott indexű elemet a pályáról, ha van olyan. */
    public Element GetElement(int idx){
        for (Element element : elements) {
            if(element.GetIdx() == idx)
                return element;
        }
        return null;
    }

    /** Hozzáad a pályához egy pumpát. Csak az inicializálásnál használt függvény. */
    public void AddPump(Pump p) {
        elements.add(p);
        pumps.add(p);

        if (PrototypeController.IsLogging())
            System.out.println("a pump was added as element " + p.GetIdx() + ".\n");
    }

    /**
     * Egy elemhez hozzárak egy új bemenetet és vagy kimenetet.
     * @param connectthis - az elem amihez csatlakoztat.
     * @param inp - az új bemenet, ha null akkor nem rakja hozzá
     * @param outp - az új kimenet, ha null akkor nem rakja hozzá
     */
    public static void Connect(Element connectthis, Element inp, Element outp) {
        if(inp!=null) {
            connectthis.AddNb(inp);
            connectthis.SetInput(inp);
        }
        if(outp!=null) {
            connectthis.AddNb(outp);
            connectthis.SetOutput(outp);
        }
    }

    /** Hozzáad a pályához egy új csövet az átadott e elem mellé. */
    public void AddNewPipe(Element e) {
        Pipe p = new Pipe();
        
    }

    /**
     * Meghívja minden elemének a Print fügvényét - ami kírja az elemek tulajdonságait.
     */
    public void Print(){
        for (Element e: GetElements()){
            if(PrototypeController.GetInstance().GetPrintAlsoToFile()) {
                PrintStream ps = System.out;
                System.setOut(PrototypeController.GetInstance().GetOutConsole());
                e.Print("");
                System.setOut(ps);
            }
            e.Print("");
        }
    }

    /**
     * Kiírja az átadott indexű elem adatait.
     * @param idx - az átadott index
     */
    public void PrintElement(int idx) {
        if(PrototypeController.GetInstance().GetPrintAlsoToFile()) {
            PrintStream ps = System.out;
            System.setOut(PrototypeController.GetInstance().GetOutConsole());
            elements.get(idx).Print("");
            System.setOut(ps);
        }
        elements.get(idx).Print("");
    }

    /**
     * Kiírja a megadott nevű játékos jelenlegi elemének (amin áll) jellemzőit
     */
    public void PrintCurrElem() {
        Element currElem = Game.GetInstance().GetCurrPlayer().GetCurrElem();
        PrintElement(currElem.GetIdx());
    }
}