package org.runtimeerror.model.players;

import org.runtimeerror.Main;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import org.runtimeerror.model.map.Pipe;
import static org.runtimeerror.skeleton.SkeletonController._Game;

public class ManipulatorSaboteur extends Manipulator {

    /* Kilyukasztja az átadott p csövet. Utána pedig véget ér a jelenlegi játékos köre. */
    @Override
    public void Manipulate(Pipe p) {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionCall(this,"GetBroken");
        if(!p.GetBroken()){
            Main.skeleton.PrintFunctionCall(this,"Break");
            p.Break();
            Main.skeleton.PrintFunctionCall(this,"NextTurn");
            _Game.NextTurn();
        }

        Main.skeleton.PrintFunctionReturned("Manipulate", "" );
    }
}