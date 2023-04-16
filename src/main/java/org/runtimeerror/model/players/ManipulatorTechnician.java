package org.runtimeerror.model.players;

import org.runtimeerror.Main;
import org.runtimeerror.controller.Game;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import org.runtimeerror.model.map.Pipe;
import org.runtimeerror.model.map.Pump;

import static org.runtimeerror.skeleton.SkeletonController._Game;

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
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionCall(this, "GetBroken()");
        if (p.GetBroken()) {
            Main.skeleton.PrintFunctionCall(this, "Fix()");
            p.Fix();
            Game.NextTurn();
            Main.skeleton.PrintFunctionReturned("Manipulate", "");
        } else
            throw new NotImplementedException();
    }
}