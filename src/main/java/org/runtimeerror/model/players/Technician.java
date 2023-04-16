package org.runtimeerror.model.players;

import org.runtimeerror.Main;
import org.runtimeerror.model.map.Element;
import org.runtimeerror.model.map.Network;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import org.runtimeerror.model.map.Direction;
import org.runtimeerror.model.map.Breakable;

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
        throw new NotImplementedException();
    }

    /* Visszaadja azt a Breakable-t („part” attribútumot), ami a szerelőnél van. */
    public Breakable GetPart() {
        throw new NotImplementedException();
    }

    /* Az átadott Breakable-re állítja a „part” attribútumot. */
    public void SetPart(Breakable b) {
        throw new NotImplementedException();
    }
}