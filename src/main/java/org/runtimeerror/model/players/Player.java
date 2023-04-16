package org.runtimeerror.model.players;

import org.runtimeerror.Main;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import org.runtimeerror.model.map.Direction;
import org.runtimeerror.model.map.Element;

public class Player {

    /* A játékos által használt manipulator objektum a „gazda” elemmel való interkaciók lekezeléséhez. */
    private final Manipulator manipulator;
    private final String name; // a játékos neve, azonosítója
    private Element currElem; // az elem, amelyen a játékos éppen tartózkodik („gazda” elem)

    /* Konstruktorok */
    public Player(String name) { this.name = name; manipulator = new ManipulatorSaboteur(); }
    public Player(String name, Manipulator m) { this.name = name; manipulator = m; }

    /* Beállítja az elemet, amelyen a játékos tartózkodik. */
    public void SetCurrElem(Element e) {
        Main.skeleton.PrintFunctionCalled(this);
        currElem = e;
        Main.skeleton.PrintFunctionReturned("SetCurrElem", "");
    }

    /* Az átadott elemre helyezi a játékost, ha ez lehetséges. */
    public void MoveTo(Element e) {
        Main.skeleton.PrintFunctionCalled(this);

        Main.skeleton.PrintFunctionCall(this, "AddPlayer", this);
        boolean playerAdded = e.AddPlayer(this);
        if (playerAdded) {
            Main.skeleton.PrintFunctionCall(this, "RemovePlayer", this);
            currElem.RemovePlayer(this);

            Main.skeleton.PrintFunctionCall(this, "SetCurrElem", e);
            SetCurrElem(e);
        }

        Main.skeleton.PrintFunctionReturned("MoveTo", "");
    }

    /*  A játékost a megadott irányban lévő elemre lépteti (a „gazda” elemétől), ha ez lehetséges. */
    public void StepOnto(Direction d) {
        throw new NotImplementedException();
    }

    /* Manipulálja azt az elemet, amelyen éppen tartózkodik.
     A manipulátora határozza meg, hogy mely „gazda” elem típus esetén mit tesz. */
    public void ManipulateCurrentElement() {
        Main.skeleton.PrintFunctionCalled(this);

        Main.skeleton.PrintFunctionCall(this, "Manipulate", manipulator);
        currElem.Manipulate(manipulator);

        Main.skeleton.PrintFunctionReturned("ManipulateCurrElem", "");
    }
}