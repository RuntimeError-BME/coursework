package org.runtimeerror.model.map;

import org.runtimeerror.Main;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import org.runtimeerror.model.players.Manipulator;
import static org.runtimeerror.skeleton.SkeletonController.ObjNameMap;
import static org.runtimeerror.skeleton.SkeletonController._Game;

public class Pump extends Breakable {

    public Pump(){
        super();
        ObjNameMap.put(this, "newPump:Pump");
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionReturned("Pump","newPump");
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