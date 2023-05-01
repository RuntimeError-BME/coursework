package org.runtimeerror.model.players;
import org.runtimeerror.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import org.runtimeerror.controller.Game;
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
        Direction[] dirs = Game.Input.GetNewPumpDirections();

        Element newInp = p.GetNb(dirs[0]);

        p.SetInput(newInp);

        Element newOut = p.GetNb(dirs[1]);

        p.SetOutput(newOut);

        Game.GetInstance().NextTurn();
    }

    /** Átlépteti a jelenlegi játékost a következő ciszternára. Ezzel nem ér véget a játékos köre (turn). */
    public void Manipulate(Cistern c) {
        Network network = Game.GetInstance().GetNetwork();

        List<Cistern> cists = network.GetCisterns();

        int i = 0;
        for (Cistern cistern : cists) {
            if (cistern == Game.GetInstance().GetCurrPlayer().GetCurrElem()) {
                break;
            }
            i++;
        }
        Cistern targetElem = cists.get(i + 1);

        Player player = Game.GetInstance().GetCurrPlayer();

        player.MoveTo(targetElem);
    }

    /** Üres törzsű függvény, jelenleg a játékosok nem tesznek semmit a forráson állva.
     (A jövőbeli bővíthetőségre fenntartva, illetve a Dynamic Dispatch hibátlan működése miatt kell,
     hogy ne kelljen Type-checking a hívóoldalon.) */
    public void Manipulate(Source s) { }


    public void Manipulate(Pipe p) {
/*        if (!p.GetBroken()) {

            String harm = null;
            System.out.print("Harm(SLIPPY/STICKY/BROKEN): ");
            try {
                harm = new BufferedReader(new InputStreamReader(System.in)).readLine();
                System.out.print("Szerelő/Szabotőr kör(0 - Szerelő, 1 - Szabotőr): ");
                _Game.setCurrPlayerIdx(new Scanner(System.in).nextInt());
            } catch (IOException e) {

            }

            //Sticky - Ragadós, Slippery - Csúszós
            switch (harm){
                case "STICKY":
                    p.SetSticky(true);
                    break;
                case "SLIPPY":
                    if(!_Game.IsTechnicianTurn()){ //Ha Technikus Slipperyvé tenné akkor eltöri
                        p.SetSlippery(true);
                        break;
                    }
                default:
                    p.Break();
                    break;
            }
            _Game.NextTurn();
        }*/
    }
}