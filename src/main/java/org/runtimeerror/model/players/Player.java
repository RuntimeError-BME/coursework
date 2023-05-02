package org.runtimeerror.model.players;

import org.runtimeerror.Main;
import org.runtimeerror.model.map.Direction;
import org.runtimeerror.model.map.Element;

/**
 * A Player osztály a szabotőr osztállyal ekvivalens, tehát a szabotőrök csapatának játékosai
 * Player típusúak. Eltárolja a játékos nevét, az elemet, amelyen éppen áll, és a manipulátorát.
 * Tud mozogni irányokba, és manipulálni tudja a „gazda” elemét.
 */
public class Player {
    /** Attribútumok */
    /** A játékos által használt manipulator objektum a „gazda” elemmel való interkaciók lekezeléséhez. */
    private final ManipulatorPlayer manipulator;
    private final String name; // a játékos neve, azonosítója
    private Element currElem; // az elem, amelyen a játékos éppen tartózkodik (a „gazda” elem)

    /** Konstruktorok */
    public Player(String name) { this.name = name; manipulator = new ManipulatorPlayer(); }
    public Player(String name, ManipulatorPlayer m) { this.name = name; manipulator = m; }

    /**
     * Visszaadja a játékos nevét.
     */
    public String GetName() { return name; }

    /** Visszaadja az elemet, amelyen a játékos éppen tartózkodik (a „gazda” elemet). */
    public Element GetCurrElem() {
        return currElem;
    }

    /** Beállítja az elemet, amelyen a játékos tartózkodik. */
    public void SetCurrElem(Element e) {
        currElem = e;
    }

    /** Az átadott elemre helyezi a játékost, ha ez lehetséges.
     * (Hívja az átadott elem AddPlayer() metódusát, és átadja paraméterül önmagát a játékos.
     * Megjegyzés: Pipe esetén van csak felülírva AddPlayer(), és csak ott nem biztos, hogy sikeres lesz.) */
    public void MoveTo(Element e) {
        e.AddPlayer(this);
    }

    /**
     * Az átadott irányba próbálja léptetni a játékost az őt tartalmazó elemről.
     * Ha az adott irányba nincs szomszédos elem, nem történik semmi. Ha van, akkor hívja MoveTo()-t, és átadja neki.
     */
    public void StepOnto(Direction d) {
        Element targetElem = currElem.GetNb(d);
        if (targetElem != null)
            MoveTo(targetElem);
    }


    /** Manipulálja (vagy manipulálni próbálja) azt az elemet, amelyen éppen tartózkodik.
     (Meghívja a currElem-jén a Manipulate metódust, és átadja neki a manipulatort.) */
    public void ManipulateCurrElem() {
        currElem.Manipulate(manipulator);
    }

    /** Ezt a függvényt kell felülírniuk a leszármazottaknak, amelyek támogatni akarják azt a viselkedést, hogy
     * a játékos képes legyen felvenni a d irányban lévő part-ot, ha van olyan. A szabotőrök erre nem képesek, ezért
     * ez egy üres törzsű függvény. Azért kell mégis megvalósítani, mert így elkerülhető a Player -> Technician
     * downcast oly módon, hogy nem kell feleslegesen eltárolni a szabotőröknek is egy invetory-t, ha úgysem képesek
     * elemek tárolására. */
    public void PickUpPart(Direction d) { }

    /** Ezt a függvényt kell felülírniuk a leszármazottaknak, amelyek támogatni akarják azt a viselkedést, hogy
     * a játékos képes legyen visszaadni a tárolójában lévő elemet. A szabotőrök erre nem képesek, ezért
     * ez a függvény mindig null-t ad vissza. Azért kell mégis megvalósítani, mert így elkerülhető a Player ->
     * Technician downcast oly módon, hogy nem kell feleslegesen eltárolni a szabotőröknek is egy invetory-t, ha
     * úgysem képesek elemek tárolására. */
    public Element GetPart() {
        return null;
    }

    /** Ezt a függvényt kell felülírniuk a leszármazottaknak, amelyek támogatni akarják azt a viselkedést, hogy
     * a játékos képes legyen beállítani a tárolójában lévő elemet. A szabotőrök erre nem képesek, ezért
     * ez egy üres törzsű függvény. Azért kell mégis megvalósítani, mert így elkerülhető a Player ->
     * Technician downcast oly módon, hogy nem kell feleslegesen eltárolni a szabotőröknek is egy invetory-t, ha
     * úgysem képesek elemek tárolására. */
    public void SetPart(Element e) { }

    /** Ezt a függvényt kell felülírniuk a leszármazottaknak, amelyek támogatni akarják azt a viselkedést, hogy
     * a játékos képes legyen lehelyezni a pályára a tárolójában lévő elemet. A szabotőrök erre nem képesek, ezért
     * ez a függvény mindig false-t ad vissza, jelezve, hogy a lehelyezés mindig sikertelen. Azért kell mégis
     * megvalósítani, mert így elkerülhető a Player -> Technician downcast oly módon, hogy nem kell feleslegesen
     * eltárolni a szabotőröknek is egy invetory-t, ha úgysem képesek elemek tárolására. */
    public boolean PlacePart(Direction d) {
        return false;
    }
}