package org.runtimeerror.model.players;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import org.runtimeerror.model.map.Direction;
import org.runtimeerror.model.map.Breakable;

public class Technician extends Player {

    private Breakable part; // az elem, amelyet a szerelő felvett a pályáról. Ha nincs nála ilyen, akkor null.

    /* Konstruktorok */
    public Technician(String name) { super(name, new ManipulatorTechnician()); }
    public Technician(String name, Manipulator m) { super(name, m); }

    /* Megkísérel felvenni egy part-ot a jelenlegi elemtől.
      (Csak ciszterna esetén fog tényleg sikerülni egy pumpát felvennie.) */
    public void PickUpPart() {
        throw new NotImplementedException();
    }

    /* Megkísérli felvenni a d irányban lévő part-ot, ha van olyan. */
    public void PickUpPart(Direction d) {
        throw new NotImplementedException();
    }

    /* Megkísérli elhelyezni a tárolt part-ját d irányba. A művelet sikerességével tér vissza. */
    public boolean PlacePart(Direction d) {
        throw new NotImplementedException();
    }

    /* Visszaadja azt a Breakable-t („part” attribútumot), ami a szerelőnél van. */
    public Breakable GetPart() {
        throw new NotImplementedException();
    }

    /* Az átadott Breakable-re állítja a „part” attribútumot. */
    public void SetPart(Breakable b) {
        throw new NotImplementedException();
    }
}