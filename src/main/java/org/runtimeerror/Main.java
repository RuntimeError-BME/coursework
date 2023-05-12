package org.runtimeerror;

import org.runtimeerror.gui.GuiController;
import org.runtimeerror.prototype.PrototypeController;

import java.io.File;
import java.io.PrintWriter;

public class Main {

    public static void main(String[] args) {

        //GuiController guic = new GuiController();
        PrototypeController pc=PrototypeController.GetInstance();
        pc.ResetState();
        for (int i=0;i<21;i++){
            try {
                if(!(new File("input/input"+i+".txt")).exists())
                    (new File("input/input"+i+".txt")).createNewFile();

                if(!(new File("output/output"+i+".txt")).exists())
                (new File("output/output"+i+".txt")).createNewFile();

                if(!(new File("required_output/required_output"+i+".txt")).exists())
                (new File("required_output/required_output"+i+".txt")).createNewFile();
            }catch (Exception e){}
        }



    }
}


/*
    Nagyobb módosítások a projektben:
    Gráfos megoldás, index kezelés a teljes pályársa - kezdetben incializálás, utána indexek frissítése az aktuális változásokat követően (index nem változik ha felveszi a pályáról, és leteszi utána).
    GUI-ban grid helyett pozíció generátor függvény, illetve button forgatás megfelelő lehet.
    Játékos egérrel tudja irányítani a karaktert a pálya elemein, amely indexet küld a játéknak.

    --------------------------------------------------------------------------------------------
    Element.java:
    1. Az idx attribútum hozzá lett adva az Element osztályhoz.
    2. Érintett függvények paramétereinek, visszatérési értékeinek módosítása a tervekhez képest.
    3. GetNbMaxDirNumber(), GetNbMinDirNumber() függvények eltávolítása (szükségtelenek a gráfos megközelítés miatt).

    Network.java:
    1. A Network szerkezetének módosítása, az indexek implementálása céljából.
    2. AddPump lényeges egyszerüsítése az indexek használatához.
 */