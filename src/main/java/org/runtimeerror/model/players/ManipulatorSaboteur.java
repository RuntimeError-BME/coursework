package org.runtimeerror.model.players;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import org.runtimeerror.model.map.Pipe;
import static org.runtimeerror.skeleton.SkeletonController._Game;

public class ManipulatorSaboteur extends Manipulator {

    /* Kilyukasztja az átadott p csövet. Utána pedig véget ér a jelenlegi játékos köre. */
    @Override
    public void Manipulate(Pipe p) {
        throw new NotImplementedException();
    }
}