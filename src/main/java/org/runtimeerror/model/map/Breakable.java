package org.runtimeerror.model.map;
import org.runtimeerror.Main;
import static org.runtimeerror.skeleton.SkeletonController._Game;


/**
 * Ez az elem tönkre tud menni/kilyukadni, valamint ez által meg is lehet javítani.
 * Ilyen elemet tudnak tárolni a szerelők (más néven part).
 * (Ez egy absztrakt osztály.)
 */
public abstract class Breakable extends Element {
    /**
     * Attribútumok
     */
    private boolean broken = false; // hibás-e vagy sem, azaz át tud-e ereszteni magán vizet

    /**
     * Metódusok
     */
    /**
     *  Megjavítja az elemet.
     */
    public void Fix() {
        Main.skeleton.PrintFunctionCalled(this);
        broken = false;
        Main.skeleton.PrintFunctionReturned("Fix", "" );
    }

    /**
     * Elrontja az elemet.
     */
    public void Break() {
        Main.skeleton.PrintFunctionCalled(this);
        broken = true;
        Main.skeleton.PrintFunctionReturned("Break", "" );
    }

    /**
     * Visszaadja, hogy az elem törött-e.
     */
    @Override
    public boolean GetBroken() {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionReturned("GetBroken", broken ? "true" : "false" );
        return broken;
    }

    /**
     *  Ha a pumpa el van romolva, nem tesz semmit.
     * Ha nincs elromolva akkor vízzel tölti fel magát. Ha nincsen outputja, akkor a szabotőrök pontot kapnak,
     * de ha van outputja, akkor az outputra továbbhívja a Flood() függvényt.
     */
    @Override
    public void Flood() {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionCall(this,"SetFlooded","true");
        this.SetFlooded(true);

        Main.skeleton.PrintFunctionCall(this,"GetBroken");
        if(!this.GetBroken()){
            Main.skeleton.PrintFunctionCall(this,"GetOutput");
            Element next=this.GetOutput();
            if(next!=null){
                Main.skeleton.PrintFunctionCall(this,"Flood");
                next.Flood();
            }
            else {
                Main.skeleton.PrintFunctionCall(this,"AddSaboteurPoints","1");
                _Game.AddSaboteurPoints(1);
            }
        }
        else {
            Main.skeleton.PrintFunctionCall(this,"AddSaboteurPoints","1");
            _Game.AddSaboteurPoints(1);
        }
        Main.skeleton.PrintFunctionReturned("Flood", "");
    }

    // TODO: override in PROTO
    /** Visszaadja, hogy az elem felvehető-e.
     Az Element ősbéli megvalósításhoz hozzá vesz egy új feltételt: ne legyen törött sem, hogy felvehető legyen. */
//    @Override
//    public boolean GetPickUpAble() {
//        throw new NotImplementedException();
//    }
}