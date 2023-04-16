package org.runtimeerror.model.map;

import org.runtimeerror.Main;
import org.runtimeerror.model.players.Technician;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import org.runtimeerror.model.players.Manipulator;
import static org.runtimeerror.skeleton.SkeletonController._Game;


/**
 * Amennyi csőből folyik víz ebbe az elembe, annyi pontot oszt a szerelők csapatának.
 * Erről az elemről a játékosok közvetlen más ciszternákra tudnak lépni.
 */
public class Cistern extends Element {
    /**
     * Metódusok
     */
    /* Megpróbál pumpát adni a soron lévő játékos tárolójába. */
    @Override
    public void OnPickup() {
        Main.skeleton.PrintFunctionCalled(this);

        Main.skeleton.PrintFunctionCall(this,"GetCurrPlayer");
        Technician player= (Technician) _Game.GetCurrPlayer();

        Main.skeleton.PrintFunctionCall(this, "<<create>>Pump");
        Pump newPump= new Pump();

        Main.skeleton.PrintFunctionCall(this,"SetPart");
        player.SetPart(newPump);

        Main.skeleton.PrintFunctionReturned("OnPickup","");
    }

    /* Vízzel tölti fel magát, és pontot ad a szerelőknek. A pálya végpontjaként nem áramoltat más elemekbe vizet. */
    @Override
    public void Flood() {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionCall(this,"SetFlooded","true");
        this.SetFlooded(true);

        Main.skeleton.PrintFunctionCall(this,"AddTechnicianPoints","1");
        _Game.AddTechnicianPoints(1);
        Main.skeleton.PrintFunctionReturned("Flood", "");
    }

    /* A paraméterként kapott manipulátorral manipulálja ezt a konkrét elemet. */
    @Override
    public void Manipulate(Manipulator m) {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionCall(this,"Manipulate",this);
        m.Manipulate(this);
        Main.skeleton.PrintFunctionReturned("Manipulate","");
    }
}