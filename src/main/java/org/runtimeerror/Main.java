package org.runtimeerror;
import org.runtimeerror.prototype.PrototypeController;


public class Main {

    public static void main(String[] args) {

       PrototypeController pc = PrototypeController.GetInstance();
        pc.ResetState();

    }
}