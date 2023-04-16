package org.runtimeerror.model.map;

import org.runtimeerror.Main;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import org.runtimeerror.model.players.Player;
import org.runtimeerror.model.players.Manipulator;

public class Pipe extends Breakable {

    public Pipe(){
        super();
        pickUpAble=true;
    }

    /* Felhelyezi az átadott játékost magára, ha nem áll rajta már más valaki. A művelet sikerességével tér vissza. */
    @Override
    public boolean AddPlayer(Player p) {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionCall(this, "GetPlayerCnt");
        if (GetPlayerCnt() == 0) {
            players.add(p);
            Main.skeleton.PrintFunctionReturned("AddPlayer", "true");
            return true;
        } else
            Main.skeleton.PrintFunctionReturned("AddPlayer", "false");
        return false;
    }

    /* Vízzel tölti meg magát. Ha lyukas a cső vagy nincs output-ja akkor pontot kapnak a szabotőrök,
    különben pedig hívja tovább az output-ján a Flood() függvényt (ereszti tovább a vizet). */
    @Override
    public void Flood() {
        throw new NotImplementedException();
    }

    @Override
    public void SetNb(Direction d, Element e){
        if(this.GetNbCnt()<2){super.SetNb(d,e);}
    }

    /* Az átvett manipulátorral manipulálja ezt a konkrét elemet. */
    @Override
    public void Manipulate(Manipulator m) {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionCall(this,"Manipulate",this);
        m.Manipulate(this);
        Main.skeleton.PrintFunctionReturned("Manipulate","");
    }
}