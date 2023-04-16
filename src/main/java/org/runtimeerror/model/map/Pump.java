package org.runtimeerror.model.map;
import org.runtimeerror.Main;
import org.runtimeerror.model.players.Manipulator;
import static org.runtimeerror.skeleton.SkeletonController.ObjNameMap;


/**
 * Olyan elromolható, felvehető elem, amely vizet képes átereszteni a bemenetként beállított csövéből a kimenetként beállított csövébe.
 * Pontot oszt a szabotőröknek, ha nincsen kimenete, amikor vizet eresztene át.
 */
public class Pump extends Breakable {
    /**
     * Metódusok
     */
    public Pump(){
        super();
        ObjNameMap.put(this, "newPump:Pump");
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionReturned("Pump","newPump");
    }

    /** Az átvett manipulátorral manipulálja ezt a konkrét elemet. */
    @Override
    public void Manipulate(Manipulator m) {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionCall(this, "Manipulate", this);
        m.Manipulate(this);
        Main.skeleton.PrintFunctionReturned("Manipulate", "");
    }
}