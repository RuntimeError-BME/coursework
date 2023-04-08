package org.runtimeerror.model.players;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import org.runtimeerror.model.map.Pipe;
import org.runtimeerror.model.map.Pump;
import org.runtimeerror.model.map.Cistern;
import org.runtimeerror.model.map.Source;

public abstract class Manipulator {

    /* Absztrakt metódus a játékosok csőmanipuláló viselkedésének leírására. */
    public abstract void Manipulate(Pipe p);

    /* Átállítja az átadott pumpát (GameInput-ot használja a bemenetért). Ezután véget ér a játékos köre (turn). */
    public void Manipulate(Pump p) {
        throw new NotImplementedException();
    }

    /* Átlépteti a jelenlegi játékost a következő ciszternára. Ezzel nem ér véget a játékos köre (turn). */
    public void Manipulate(Cistern c) {
        throw new NotImplementedException();
    }

    /* Üres törzsű függvény, jelenleg a játékosok nem tesznek semmit a forráson állva.
     (A jövőbeli bővíthetőségre fenntartva, illetve a Dynamic Dispatch hibátlan működése miatt kell,
     hogy ne kelljen Type-checking a hívóoldalon.) */
    public void Manipulate(Source s) { }
}