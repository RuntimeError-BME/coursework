package org.runtimeerror.model.map;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class Breakable extends Element {

    private boolean broken; // hibás-e vagy sem, azaz át tud-e ereszteni magán vizet

    /* Megjavítja az elemet. */
    public void Fix() {
        throw new NotImplementedException();
    }

    /* Elrontja az elemet. */
    public void Break() {
        throw new NotImplementedException();
    }

    /* Visszaadja, hogy az elem törött-e. */
    public void GetBroken() {
        throw new NotImplementedException();
    }

    // TODO: override in PROTO
    /* Visszaadja, hogy az elem felvehető-e.
     Az Element ősbéli megvalósításhoz hozzá vesz egy új feltételt: ne legyen törött sem, hogy felvehető legyen. */
//    @Override
//    public boolean GetPickUpAble() {
//        throw new NotImplementedException();
//    }
}