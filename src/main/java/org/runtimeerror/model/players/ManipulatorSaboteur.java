package org.runtimeerror.model.players;
import org.runtimeerror.Main;
import org.runtimeerror.model.map.Pipe;
import static org.runtimeerror.skeleton.SkeletonController._Game;


/**
 * A szabotőrök konkrét manipulátora, ami definiálja, hogy hogyan manipulálhatják az összes lehetséges „gazda” elemüket.
 * A csövek kilyukasztásának viselkedését valósítja meg. A többi viselkedés megfelel az ősbélivel.
 */
public class ManipulatorSaboteur extends Manipulator {
    /**
     * Metódusok
     */
    /** Kilyukasztja az átadott p csövet. Utána pedig véget ér a jelenlegi játékos köre. */
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