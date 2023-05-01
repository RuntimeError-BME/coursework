package org.runtimeerror.model.players;
import org.runtimeerror.Main;
import org.runtimeerror.controller.Game;
import org.runtimeerror.model.map.*;
import static org.runtimeerror.skeleton.SkeletonController.isLogging;
import org.runtimeerror.model.map.Element;
import static org.runtimeerror.skeleton.SkeletonController._Game;


/**
 * Olyan játékos, aki csövek lyukasztása helyett javítani tudja őket, illetve pumpákat is.
 * Emellett képes vagy egy csövet, vagy egy pumpát tárolni magánál, ami el is tud helyezni a megfelelő feltételek fennállása esetén.
 */
public class Technician extends Player {
    /**
     * Attribútumok
     */
    private Element part; // az elem, amelyet a szerelő felvett a pályáról. Ha nincs nála ilyen, akkor null.

    /**
     * Attribútumok
     */
    /** Konstruktorok */
    public Technician(String name) { super(name, new ManipulatorTechnician()); }
    public Technician(String name, Manipulator m) { super(name, m); }

    /** Megkísérel felvenni egy part-ot a jelenlegi elemtől.
      (Csak ciszterna esetén fog tényleg sikerülni egy pumpát felvennie.) */
//    public void PickUpPart() {
//        throw new NotImplementedException();
//    }

    /** Megkísérli felvenni a d irányban lévő part-ot, ha van olyan. */
    @Override
    public void PickUpPart(Direction d) {
        int NbCnt = currElem.GetNbCnt();
        Element targetElem = currElem.GetNb(d);

        boolean pickUpAble = targetElem.GetPickUpAble();
        if (pickUpAble) {
            boolean flooded = targetElem.GetFlooded();
            boolean broken = targetElem.GetBroken();

            if (!flooded && !broken) {
                if (GetPart() == null) {
                    Network network = Game.GetInstance().GetNetwork();

                    network.RemoveElem(targetElem);

                    SetPart((Breakable) targetElem);
                }
            }
        }
    }

    /** Megkísérli elhelyezni a tárolt part-ját d irányba. A művelet sikerességével tér vissza. */
    @Override
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
        Element currElem = GetCurrElem();
        boolean islogged=isLogging;
        if (islogged) isLogging = false;
        Element targetElem = new Pipe();
        boolean re;
        if (storedPart.GetPickUpAble()) {
            if (islogged) isLogging = true;
            Main.skeleton.PrintFunctionCall(this, "GetNb", "d");
            re = (currElem.GetNb(d) == null);
        } else {
            if (islogged) isLogging = true;
            Main.skeleton.PrintFunctionCall(this, "GetNb", "d");
            targetElem = currElem.GetNb(d);
            re = (targetElem != null);
            {

                if (re) {

                    if (islogged) isLogging = false;
                    if (storedPart.GetPickUpAble()) {
                        if (islogged) isLogging = true;

                        Main.skeleton.PrintFunctionCall(this, "GetNetwork");
                        Network network = _Game.GetNetwork();

                        Main.skeleton.PrintFunctionCall(this, "AddPipe", "storedPart");
                        re = network.AddPipe((Pipe) storedPart);
                    } else {
                        if (islogged) isLogging = true;
                        Main.skeleton.PrintFunctionCall(this, "GetPickUpAble");
                        if (targetElem.GetPickUpAble()) {
                            Main.skeleton.PrintFunctionCall(this, "GetNetwork");
                            Network network = _Game.GetNetwork();

                            Main.skeleton.PrintFunctionCall(this, "AddPump", "storedPart", "targetElem");
                            re = network.AddPump((Pump) storedPart, targetElem);
                        }
                    }
                }
            }
        }
        Main.skeleton.PrintFunctionReturned("PlacePart", re ? "true" : "false");
        return re;
    }

    /** Visszaadja azt a Breakable-t („part” attribútumot), ami a szerelőnél van. */
    @Override
    public Breakable GetPart() {
        return part;
    }

    /** Az átadott Breakable-re állítja a „part” attribútumot. */
    @Override
    public void SetPart(Element e) {
        part = e;
    }
}