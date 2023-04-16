package org.runtimeerror.model.players;
import org.runtimeerror.Main;
import org.runtimeerror.model.map.Pipe;
import org.runtimeerror.model.map.Pump;
import static org.runtimeerror.skeleton.SkeletonController._Game;


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
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionCall(this, "GetBroken");
        if(p.GetBroken()){
            Main.skeleton.PrintFunctionCall(this, "Fix");
            p.Fix();
            Main.skeleton.PrintFunctionCall(this, "NextTurn");
            _Game.NextTurn();
        }
        Main.skeleton.PrintFunctionReturned("Manipulate", "");
    }

    /** Megjavítja az átadott p pumpát, ha az elromlott, különben pedig átállítja
     (ez esetben meghívja az ősbéli megvalósítását a függvénynek). */
    @Override
    public void Manipulate(Pump p) {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionCall(this, "GetBroken");
        if (p.GetBroken()) {
            Main.skeleton.PrintFunctionCall(this, "Fix");
            p.Fix();
            Main.skeleton.PrintFunctionCall(this, "NextTurn");
            _Game.NextTurn();
            Main.skeleton.PrintFunctionReturned("Manipulate", "");
        } else {
            Main.skeleton.PrintFunctionCall(this, "<<base>>Manipulate",p);
            super.Manipulate(p);
            Main.skeleton.PrintFunctionReturned("Manipulate", "");
        }
    }
}