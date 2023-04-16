package org.runtimeerror.model.map;

import org.runtimeerror.Main;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import org.runtimeerror.model.players.Manipulator;

import static org.runtimeerror.skeleton.SkeletonController._Game;

public class Source extends Element {

    /* Vízzel tölti fel magát, és minden nem lyukas szomszédjára hívja tovább a Flood() függvényt,
     azaz ereszti tovább a vizet. Ha nem tudja ezt megtenni az adott szomszédja felé,
     mert lyukas, vagy nincs arra szomszédja, akkor pontot ad a szabotőröknek. */
    @Override
    public void Flood() {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionCall(this,"SetFlooded","true");
        this.SetFlooded(true);

        Main.skeleton.PrintFunctionCall(this,"GetNb", "0");
        Element nb = this.GetNb(new Direction(0));

        Main.skeleton.PrintFunctionCall(this, "Flood");
        nb.Flood();

        Main.skeleton.PrintFunctionCall(this,"GetNb", "1");
        this.GetNb(new Direction(1));
        Main.skeleton.PrintFunctionCall(this,"AddSaboteurPoints","1");
        _Game.AddSaboteurPoints(1);

        Main.skeleton.PrintFunctionCall(this,"GetNb", "2");
        this.GetNb(new Direction(2));
        Main.skeleton.PrintFunctionCall(this,"AddSaboteurPoints","1");
        _Game.AddSaboteurPoints(1);

        Main.skeleton.PrintFunctionCall(this,"GetNb", "3");
        this.GetNb(new Direction(3));
        Main.skeleton.PrintFunctionCall(this,"AddSaboteurPoints","1");
        _Game.AddSaboteurPoints(1);
        Main.skeleton.PrintFunctionReturned("Flood", "");
    }

    /* Az átvett manipulátorral manipulálja ezt a konkrét elemet. */
    @Override
    public void Manipulate(Manipulator m) {
        throw new NotImplementedException();
    }
}