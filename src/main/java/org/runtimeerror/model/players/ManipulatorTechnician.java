package org.runtimeerror.model.players;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import org.runtimeerror.model.map.Pipe;
import org.runtimeerror.model.map.Pump;

public class ManipulatorTechnician extends Manipulator {

    /* Megjavítja az átadott p csövet. Utána pedig véget ér a jelenlegi játékos köre. */
    @Override
    public void Manipulate(Pipe p) {
        throw new NotImplementedException();
    }

    /* Megjavítja az átadott p pumpát, ha az elromlott, különben pedig átállítja
     (ez esetben meghívja az ősbéli megvalósítását a függvénynek). */
    @Override
    public void Manipulate(Pump p) {
        throw new NotImplementedException();
    }
}