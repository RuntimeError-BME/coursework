package org.runtimeerror.model.players;

import org.runtimeerror.Main;
import java.util.List;
import org.runtimeerror.model.map.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static org.runtimeerror.skeleton.SkeletonController._Game;
import static org.runtimeerror.skeleton.SkeletonController._GameI;

public abstract class Manipulator {

    /* Absztrakt metódus a játékosok csőmanipuláló viselkedésének leírására. */
    public abstract void Manipulate(Pipe p);

    /* Átállítja az átadott pumpát (GameInput-ot használja a bemenetért). Ezután véget ér a játékos köre (turn). */
    public void Manipulate(Pump p) {
        Main.skeleton.PrintFunctionCalled(this);

        Main.skeleton.PrintFunctionCall(this, "GetNewPumpDirections");
        Direction[] dirs = _GameI.GetNewPumpDirections();

        Main.skeleton.PrintFunctionCall(this,"GetNb","dirs[0]");
        Element newInp = p.GetNb(dirs[0]);

        Main.skeleton.PrintFunctionCall(this, "SetInput", "newInp");
        p.SetInput(newInp);

        Main.skeleton.PrintFunctionCall(this,"GetNb","dirs[1]");
        Element newOut = p.GetNb(dirs[1]);

        Main.skeleton.PrintFunctionCall(this, "SetOutput", "newOut");
        p.SetOutput(newOut);

        Main.skeleton.PrintFunctionCall(this,"NexTurn");
        _Game.NextTurn();

        Main.skeleton.PrintFunctionReturned("Manipulate","");
    }

    /* Átlépteti a jelenlegi játékost a következő ciszternára. Ezzel nem ér véget a játékos köre (turn). */
    public void Manipulate(Cistern c) {
        Main.skeleton.PrintFunctionCalled(this);

        Main.skeleton.PrintFunctionCall(this, "GetNetwork");
        Network network = _Game.GetNetwork();

        Main.skeleton.PrintFunctionCall(this, "GetCisterns");
        List<Cistern> cisterns = network.GetCisterns();

        for (Cistern cistern : cisterns) {  } //ez keresné meg az input alapján
        Cistern targetElem=cisterns.get(1); //input alapján, most konstans

        Main.skeleton.PrintFunctionCall(this, "GetCurrPlayer");
        Player player = _Game.GetCurrPlayer();


        Main.skeleton.PrintFunctionCall(this, "MoveTo", "targetElem");
        player.MoveTo(targetElem);


        Main.skeleton.PrintFunctionReturned("Manipulate", "");
    }

    /* Üres törzsű függvény, jelenleg a játékosok nem tesznek semmit a forráson állva.
     (A jövőbeli bővíthetőségre fenntartva, illetve a Dynamic Dispatch hibátlan működése miatt kell,
     hogy ne kelljen Type-checking a hívóoldalon.) */
    public void Manipulate(Source s) { }
}