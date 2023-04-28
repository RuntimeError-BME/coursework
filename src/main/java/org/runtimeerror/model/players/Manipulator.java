package org.runtimeerror.model.players;
import org.runtimeerror.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import org.runtimeerror.model.map.*;

import static org.runtimeerror.skeleton.SkeletonController.*;


/**
 * Absztrakt osztály, amely a Visitor tervezési mintát valósítja meg a leszármazottaival együtt.
 * A leszármazottai egy játékos típusnak készítik el a „gazda” elem manipulálását megvalósító viselkedést minden elem esetére (pumpák, csövek, források, ciszternák).
 * A szerelők konkrét manipulátora például definiálja, hogy mit tud tenni egy szerelő, ha az előbb említett elemeken állva próbál interaktálni.
 * A függvényei gyakran a kört is léptetik.
 */
public abstract class Manipulator {
    /**
     * Metódusok
     * @param p
     */
    /** Absztrakt metódus a játékosok csőmanipuláló viselkedésének leírására. */
    //public abstract void Manipulate(Pipe p);

    /** Átállítja az átadott pumpát (GameInput-ot használja a bemenetért). Ezután véget ér a játékos köre (turn). */
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

    /** Átlépteti a jelenlegi játékost a következő ciszternára. Ezzel nem ér véget a játékos köre (turn). */
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

    /** Üres törzsű függvény, jelenleg a játékosok nem tesznek semmit a forráson állva.
     (A jövőbeli bővíthetőségre fenntartva, illetve a Dynamic Dispatch hibátlan működése miatt kell,
     hogy ne kelljen Type-checking a hívóoldalon.) */
    public void Manipulate(Source s) { }


    public void Manipulate(Pipe p) {
        ObjNameMap.put(this,":<<base>>Manipulate");
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionCall(this, "GetBroken");
        if (!p.GetBroken()) {

            String harm = null;
            boolean islogged=isLogging;
            if (islogged) isLogging=false;
            System.out.print("Harm(SLIPPY/STICKY/BROKEN): ");
            try {
                harm = new BufferedReader(new InputStreamReader(System.in)).readLine();
                System.out.print("Szerelő/Szabotőr kör(0 - Szerelő, 1 - Szabotőr): ");
                _Game.setCurrPlayerIdx(new Scanner(System.in).nextInt());
            } catch (IOException e) {

            }
            if(islogged) isLogging = true;
            //Sticky - Ragadós, Slippery - Csúszós

            switch (harm){
                case "STICKY":
                    Main.skeleton.PrintFunctionCall(this, "SetSticky","true");
                    p.SetSticky(true);
                    break;
                case "SLIPPY":
                    Main.skeleton.PrintFunctionCall(this, "IsTechnicianTurn");
                    if(!_Game.IsTechnicianTurn()){ //Ha Technikus Slipperyvé tenné akkor eltöri
                        Main.skeleton.PrintFunctionCall(this, "SetSlippery","true");
                        p.SetSlippery(true);
                        break;
                    }
                default:
                    Main.skeleton.PrintFunctionCall(this, "Break");
                    p.Break();
                    break;
            }
            Main.skeleton.PrintFunctionCall(this, "NextTurn");
            _Game.NextTurn();
        }
        Main.skeleton.PrintFunctionReturned("Manipulate", "");

    }
}