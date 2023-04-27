package org.runtimeerror.model.map;
import org.runtimeerror.Main;
import org.runtimeerror.model.players.Player;
import org.runtimeerror.model.players.Manipulator;

import java.util.Random;
import java.util.Scanner;

import static org.runtimeerror.skeleton.SkeletonController.*;


/**
 * Olyan elromolható, felvehető elem, amelyen legfeljebb egy játékos tartózkodhat.
 * Ki lehet lyukasztani, a lyukas csövet pedig meg is lehet javítani.
 * Ha a cső nem lyukas akkor a kimenetéhez vizet tud juttatni.
 * Ha lyukas, vagy a kimenete üres, akkor a szabotőrök pontot kapnak, amikor víz érkezik belé.
 */
public class Pipe extends Breakable {
    private boolean sticky=false;

    private boolean slippery=false;

    //private int counter;


    public boolean GetSplippery(){
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionReturned("GetSplippery",slippery?"true":"false");
        return slippery;

    }

    public boolean GetSticky() {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionReturned("GetSticky",sticky?"true":"false");
        return sticky;
    }

    public void SetSticky(boolean stick){
        Main.skeleton.PrintFunctionCalled(this);
        if(stick) {
            sticky = true;
            slippery = false;
            counter = 2;
        }
        else sticky=false;
        Main.skeleton.PrintFunctionReturned("SetSticky","");
    }

    public void SetSlippery(boolean slippy){
        Main.skeleton.PrintFunctionCalled(this);
        if(slippy) {
            slippery = true;
            sticky = false;
            counter = 2;
        }
        else {
            if(slippery) counter=0;
            slippery=false;
        }
        Main.skeleton.PrintFunctionReturned("SetSlippery","");

    }

    /**
     * Metódusok
     */
    public Pipe(){
        super();
        ObjNameMap.put(this,"newPipe:Pipe");
        Main.skeleton.PrintFunctionCalled(this);
        pickUpAble=true;
        Main.skeleton.PrintFunctionReturned("<<create>>Pipe","newPipe");
    }

    /**
     *Felhelyezi az átadott játékost magára, ha nem áll rajta már más valaki. A művelet sikerességével tér vissza.
     */
    @Override
    public void AddPlayer(Player p) {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionCall(this, "GetPlayerCnt");
        if (GetPlayerCnt() == 0) {
            //Újitás innentől



            Main.skeleton.PrintFunctionCall(this, "GetSplippery");
            if(GetSplippery()){
                Main.skeleton.PrintFunctionCall(this, "GetNbCnt");
                if(GetNbCnt()>1){
                    Element targetElem2=null;
                    while (targetElem2==null){
                        Main.skeleton.PrintFunctionCall(this, "GetNb","random");
                        int random= new Random().nextInt()%3;//Ez egyelőre csak teszt miatt
                        if(random<0) random*=-1;
                        System.out.print("\tRandom value:"+random+"\t"); //Random nézésére
                        targetElem2=GetNb(new Direction(random));
                    }
                    isLogging=false;
                    if(targetElem2!=p.GetCurrElem()) {
                        isLogging=true;
                        Main.skeleton.PrintFunctionCall(this, "AddPlayer", p);
                        targetElem2.AddPlayer(p);
                    }else isLogging=true;

                }
            }
            else {
                Main.skeleton.PrintFunctionCall(this, "GetCurrElem");
                Element currElem=p.GetCurrElem();
                Main.skeleton.PrintFunctionCall(this, "RemovePlayer",p);
                RemovePlayer(p);
                Main.skeleton.PrintFunctionCall(this, "SetCurrElem",this);
                p.SetCurrElem(this);

                players.add(p);

                Main.skeleton.PrintFunctionCall(this, "GetSticky");
                if (GetSticky()){
                    Main.skeleton.PrintFunctionCall(this, "NextTurn");
                    _Game.NextTurn();
                }
            }

            Main.skeleton.PrintFunctionReturned("AddPlayer", "");
            return;
        }
        Main.skeleton.PrintFunctionReturned("AddPlayer", "");
        return;
    }


    /**Vízzel tölti meg magát. Ha lyukas a cső vagy nincs output-ja akkor pontot kapnak a szabotőrök,
     *különben pedig hívja tovább az output-ján a Flood() függvényt (ereszti tovább a vizet).
     *Flood volt itt.
     *
     */

    /**
     * Beállítja, d irányba a Pipe szomszédjának e-t ha még nincs 2 szomszédja.
     * @param d - irány
     * @param e - új szomszéd
     */
    @Override
    public void SetNb(Direction d, Element e){
        if(this.GetNbCnt()<2){super.SetNb(d,e);}
    }

    /**
     * Az átvett manipulátorral manipulálja ezt a konkrét elemet.
     * @param m - manipulátor
     */
    @Override
    public void Manipulate(Manipulator m) {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionCall(this,"Manipulate",this);
        m.Manipulate(this);
        Main.skeleton.PrintFunctionReturned("Manipulate","");
    }
}