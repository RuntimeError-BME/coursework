package org.runtimeerror.model.players;
import org.runtimeerror.Main;
import org.runtimeerror.model.map.Direction;
import org.runtimeerror.model.map.Element;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


/**
 * A Player osztály a szabotőr osztállyal ekvivalens, tehát a szabotőrök csapatának játékosai
 * Player típusúak. Eltárolja a játékos nevét, az elemet, amelyen éppen áll, és a manipulátorát.
 * Tud mozogni irányokba, és manipulálni tudja a „gazda” elemét.
 */
public class Player {
    /**
     * Attribútumok
     */
    /** A játékos által használt manipulator objektum a „gazda” elemmel való interkaciók lekezeléséhez. */
    private final Manipulator manipulator;
    private final String name; // a játékos neve, azonosítója
    protected Element currElem; // az elem, amelyen a játékos éppen tartózkodik („gazda” elem)

    /**
     * Metódusok
     */
    /** Konstruktorok */
    public Player(String name) { this.name = name; manipulator = new ManipulatorSaboteur(); }
    public Player(String name, Manipulator m) { this.name = name; manipulator = m; }

    /**
     * Visszaadja a játékos nevét.
     */
    public String GetName() { return name; }

    /** Beállítja az elemet, amelyen a játékos tartózkodik. */
    public void SetCurrElem(Element e) {
        Main.skeleton.PrintFunctionCalled(this);
        currElem = e;
        Main.skeleton.PrintFunctionReturned("SetCurrElem", "");
    }

    /** Lekéri az elemet, amelyen a játékos tartózkodik. */
    public Element GetCurrElem() {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionReturned("GetCurrElem", "currElem");
        return currElem;
    }

    /** Az átadott elemre helyezi a játékost, ha ez lehetséges. */
    public void MoveTo(Element e) {
        Main.skeleton.PrintFunctionCalled(this);

        Main.skeleton.PrintFunctionCall(this, "AddPlayer", this);
        /*
        boolean playerAdded = e.AddPlayer(this);
        if (playerAdded) {
            Main.skeleton.PrintFunctionCall(this, "RemovePlayer", this);
            currElem.RemovePlayer(this);

            Main.skeleton.PrintFunctionCall(this, "SetCurrElem", e);
            SetCurrElem(e);
        }
         */
        e.AddPlayer(this);

        Main.skeleton.PrintFunctionReturned("MoveTo", "");
    }

    /** A játékost a megadott irányban lévő elemre lépteti (a „gazda” elemétől), ha ez lehetséges. */
//    public void StepOnto(Direction d) {
//        throw new NotImplementedException();
//    }

    /** Manipulálja azt az elemet, amelyen éppen tartózkodik.
     A manipulátora határozza meg, hogy mely „gazda” elem típus esetén mit tesz. */
    public void ManipulateCurrElem() {
        Main.skeleton.PrintFunctionCalled(this);

        Main.skeleton.PrintFunctionCall(this, "Manipulate", manipulator);
        currElem.Manipulate(manipulator);

        Main.skeleton.PrintFunctionReturned("ManipulateCurrElem", "");
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
    public void PickUpPart(Direction d) {
    }
    public Element GetPart() {
        return null;
    }
    public boolean PlacePart(Direction d) {
        return false;
    }
    public void SetPart(Element e) {
    }
}