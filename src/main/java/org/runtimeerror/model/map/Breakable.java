package org.runtimeerror.model.map;

import org.runtimeerror.Main;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class Breakable extends Element {
    /**
     * Attribútumok
     */
    private boolean broken = false; // hibás-e vagy sem, azaz át tud-e ereszteni magán vizet

    /**
     * Metódusok
     */
    /* Megjavítja az elemet. */
    public void Fix() {
        Main.skeleton.PrintFunctionCalled(this);
        broken = false;
        Main.skeleton.PrintFunctionReturned("Fix", "" );

    }

    /* Elrontja az elemet. */
    public void Break() {
        Main.skeleton.PrintFunctionCalled(this);
        broken = true;
        Main.skeleton.PrintFunctionReturned("Break", "" );
    }

    /* Visszaadja, hogy az elem törött-e. */
    @Override
    public boolean GetBroken() {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionReturned("GetBroken", broken ? "true" : "false" );
        return broken;
    }

    // TODO: override in PROTO
    /* Visszaadja, hogy az elem felvehető-e.
     Az Element ősbéli megvalósításhoz hozzá vesz egy új feltételt: ne legyen törött sem, hogy felvehető legyen. */
//    @Override
//    public boolean GetPickUpAble() {
//        throw new NotImplementedException();
//    }
}