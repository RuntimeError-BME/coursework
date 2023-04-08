package org.runtimeerror.model.map;

import java.util.List;
import java.util.ArrayList;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Network {

    private final List<Element> elements = new ArrayList<>(32); // a pálya összes elemét tárolja

    /* Külön csoportosítja minden elem közül a ciszternákat. (De az "elements"-ben is szerepelnek.) */
    private final List<Cistern> cisterns = new ArrayList<>(4);

    /* Külön csoportosítja minden elem közül a forrásokat. (De az "elements"-ben is szerepelnek.) */
    private final List<Source> sources = new ArrayList<>(4);

    /* Külön csoportosítja minden elem közül a pumpákat. (De az "elements"-ben is szerepelnek.) */
    private final List<Pump> pumps = new ArrayList<>(8);

    /* Megkísérli eltávolítani az átadott elemet a pályáról. Ha ennek semmi akadálya nem volt
     (pl. nem esne komponensekre a pálya, mint gráf), akkor igazzal tér vissza.
     Ellenkező esetben nem módosít semmi a pályán. */
    public boolean RemoveElem(Element e) {
        throw new NotImplementedException();
    }

    /* Az átadott csövet megkísérli hozzáadni a pályához. Ennek sikerességét adja vissza. */
    public boolean AddPipe(Pipe p) {
        throw new NotImplementedException();
    }

    /* Az átadott pumpával kísérli meg felülírni a másik átadott elemet a pályán. Ennek sikerességét adja vissza. */
    public boolean AddPump(Pump p, Element e) {
        throw new NotImplementedException();
    }

    /* Felapasztja az összes vizet a pályáról, majd minden forrásból elindítja a vizet,
     aminek következtében el lesz árasztva vízzel a pálya, és pontokat fognak kapni a csapatok. */
    public void Flood() {
        throw new NotImplementedException();
    }

    /* Visszaadja a pálya minden elemét. */
    public final List<Element> GetElements() {
        throw new NotImplementedException();
    }

    /* Visszaadja a pályán lévő pumpákat. */
    public final List<Pump> GetPumps() {
        throw new NotImplementedException();
    }

    /* Visszaadja a pályán lévő ciszternákat. */
    public final List<Cistern> GetCisterns() {
        throw new NotImplementedException();
    }


    /* --------- CSAK A PÁLYA INICIALIZÁLÁSKOR HASZNÁLT FÜGGVÉNYEK (nincs hibakezelés bennük): --------- */

    /* Az átadott ciszternát hozzáadja a pályához. */
    public boolean AddCistern(Pipe p) {
        throw new NotImplementedException();
    }

    /* Az átadott forrást hozzáadja a pályához. */
    public boolean AddSource(Pipe p) {
        throw new NotImplementedException();
    }
}