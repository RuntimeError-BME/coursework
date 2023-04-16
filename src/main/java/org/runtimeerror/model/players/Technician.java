package org.runtimeerror.model.players;

import org.runtimeerror.Main;
import org.runtimeerror.model.map.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import static org.runtimeerror.skeleton.SkeletonController.isLogging;

import org.runtimeerror.model.map.Element;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import static org.runtimeerror.skeleton.SkeletonController._Game;

public class Technician extends Player {

    private Breakable part; // az elem, amelyet a szerelő felvett a pályáról. Ha nincs nála ilyen, akkor null.

    /* Konstruktorok */
    public Technician(String name) { super(name, new ManipulatorTechnician()); }
    public Technician(String name, Manipulator m) { super(name, m); }

    /* Megkísérel felvenni egy part-ot a jelenlegi elemtől.
      (Csak ciszterna esetén fog tényleg sikerülni egy pumpát felvennie.) */
    public void PickUpPart() {
        throw new NotImplementedException();
    }

    /* Megkísérli felvenni a d irányban lévő part-ot, ha van olyan. */
    public void PickUpPart(Direction d) {
        Main.skeleton.PrintFunctionCalled(this);


        Main.skeleton.PrintFunctionCall(this, "GetNbCnt");
        int NbCnt = currElem.GetNbCnt();

        Main.skeleton.PrintFunctionCall(this, "GetNb", "d");
        Element targetElem = currElem.GetNb(d);

        Main.skeleton.PrintFunctionCall(this, "GetPickUpAble");
        boolean pickUpAble = targetElem.GetPickUpAble();
        if(pickUpAble) {
            Main.skeleton.PrintFunctionCall(this, "GetFlooded");
            boolean flooded = targetElem.GetFlooded();

            Main.skeleton.PrintFunctionCall(this, "GetBroken");
            boolean broken = targetElem.GetBroken();

            if (!flooded && !broken) {
                Main.skeleton.PrintFunctionCall(this, "GetPart");
                if (GetPart() == null) {

                    Main.skeleton.PrintFunctionCall(this, "GetNetwork");
                    Network network = _Game.GetNetwork();


                    Main.skeleton.PrintFunctionCall(this, "RemoveElem", "targetElem");
                    network.RemoveElem(targetElem);

                    Main.skeleton.PrintFunctionCall(this, "SetPart", "targetElem");
                    SetPart((Breakable) targetElem);
                }
            }
        }
        Main.skeleton.PrintFunctionReturned("PickUpPart","");


    }

    /* Megkísérli elhelyezni a tárolt part-ját d irányba. A művelet sikerességével tér vissza. */
    public boolean PlacePart(Direction d) {
        Main.skeleton.PrintFunctionCalled(this);

        Main.skeleton.PrintFunctionCall(this,"PlacePart", "part", "d");
        boolean re=PlacePart(part,d);

        Main.skeleton.PrintFunctionReturned("PlacePart", re ? "true" : "false");
        return re;
    }

    private boolean PlacePart(Breakable storedPart,Direction d) {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionCall(this, "GetCurrElem");
        Element currElem=GetCurrElem();
        isLogging=false;
        Element targetElem=new Pipe();
        isLogging=true;
        boolean re;
        if(storedPart.GetPickUpAble()) {
            Main.skeleton.PrintFunctionCall(this, "GetNb", "d");
            re = (currElem.GetNb(d) == null);
        }else {
            Main.skeleton.PrintFunctionCall(this, "GetNb", "d");
            targetElem= currElem.GetNb(d);
            re=(targetElem!=null);
        }

        if(re){

            isLogging=false;
            if(storedPart.GetPickUpAble()) {
                isLogging=true;

                Main.skeleton.PrintFunctionCall(this, "GetNetwork");
                Network network = _Game.GetNetwork();

                Main.skeleton.PrintFunctionCall(this, "AddPipe", "storedPart");
                re = network.AddPipe((Pipe) storedPart);
            }
            else {
                isLogging=true;
                Main.skeleton.PrintFunctionCall(this, "GetPickUpAble");
                if(targetElem.GetPickUpAble()){
                    Main.skeleton.PrintFunctionCall(this, "GetNetwork");
                    Network network = _Game.GetNetwork();

                    Main.skeleton.PrintFunctionCall(this, "AddPump", "storedPart");
                    re=network.AddPump((Pump) storedPart,targetElem);
                }


            }

        }


        Main.skeleton.PrintFunctionReturned("PlacePart", re ? "true" : "false");
        return re;

    }

    /* Visszaadja azt a Breakable-t („part” attribútumot), ami a szerelőnél van. */
    public Breakable GetPart() {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionReturned("GetPart",part==null ? "null" : "part");
        return part;
    }

    /* Az átadott Breakable-re állítja a „part” attribútumot. */
    public void SetPart(Breakable b) {
        Main.skeleton.PrintFunctionCalled(this);
        part=b;
        Main.skeleton.PrintFunctionReturned("SetPart","");
    }
}