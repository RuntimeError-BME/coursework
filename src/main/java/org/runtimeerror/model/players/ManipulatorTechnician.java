package org.runtimeerror.model.players;
import org.runtimeerror.Main;
import org.runtimeerror.controller.Game;
import org.runtimeerror.model.map.Pipe;
import org.runtimeerror.model.map.Pump;
import static org.runtimeerror.skeleton.SkeletonController._Game;
import static org.runtimeerror.skeleton.SkeletonController._GameI;


/**
 * A szerelők konkrét manipulátora, ami definiálja, hogy hogyan manipulálhatják az összes lehetséges „gazda” elemüket.
 * A csövek javításának viselkedését valósítja meg, illetve a pumpák megjavításának viselkedésével helyettesíti a pumpaátállítást, ha az adott pumpa rossz.
 * A többi viselkedés megfelel az ősbélivel.
 */
public class ManipulatorTechnician extends Manipulator {
    /**
     * Metódusok
     */
    /** Megjavítja az átadott p csövet. Utána pedig véget ér a jelenlegi játékos köre. */
    @Override
    public void Manipulate(Pipe p) {
        if (p.GetBroken()) {
            p.Fix();
            Game.GetInstance().NextTurn();
        } else {
            super.Manipulate(p);
        }
    }

    /** Megjavítja az átadott p pumpát, ha az elromlott, különben pedig átállítja
     (ez esetben meghívja az ősbéli megvalósítását a függvénynek). */
    @Override
    public void Manipulate(Pump p) {
        if (p.GetBroken()) {
            p.Fix();
            Game.GetInstance().NextTurn();
        } else {
            super.Manipulate(p);
        }
    }
}