package org.runtimeerror.model.map;

import org.runtimeerror.Main;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import org.runtimeerror.model.players.Manipulator;

public class Pump extends Breakable {

    /* Ha a pumpa el van romolva, nem tesz semmit.
     Ha nincs elromolva akkor vízzel tölti fel magát. Ha nincsen outputja, akkor a szabotőrök pontot kapnak,
     de ha van outputja, akkor az outputra továbbhívja a Flood() függvényt. */
    @Override
    public void Flood() {
        throw new NotImplementedException();
    }

    /* Az átvett manipulátorral manipulálja ezt a konkrét elemet. */
    @Override
    public void Manipulate(Manipulator m) {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionCall(this, "Manipulate", this);
        m.Manipulate(this);
        Main.skeleton.PrintFunctionReturned("Manipulate", "");
    }

}