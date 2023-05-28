package org.runtimeerror.model.map;

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

    /** Konstruktor, ami beállítja az ősbeli indexetét az elemnek. */
    public Breakable() { super(); }

    public Breakable(int idx) { super(idx); }

    /**
     *  Megjavítja az elemet.
     */
    public void Fix() {
        broken = false;
    }

    /**
     * Elrontja az elemet.
     */
    public void Break() {
        broken = true;
    }

    /**
     * Visszaadja, hogy az elem törött-e.
     * (Felülírja az Element-beli megvalósítását, ami mindig hamist ad vissza
     * (nem Breakable elemek sosem lehetnek töröttek, Breakable elemek viszont lehetnek töröttek és nem töröttek is -
     * a két állapot közül egyszerre az egyik)).
     */
    @Override
    public boolean GetBroken() {
        return broken;
    }

    /** Visszaadja, hogy az elem felvehető-e.
     Az Element ősbéli megvalósításhoz hozzá vesz egy új feltételt: ne legyen törött sem, hogy felvehető legyen. */
    @Override
    public boolean GetPickUpAble() {
        return !broken && super.GetPickUpAble();
    }
}