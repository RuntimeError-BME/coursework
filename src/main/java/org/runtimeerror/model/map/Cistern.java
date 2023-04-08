package org.runtimeerror.model.map;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import org.runtimeerror.model.players.Manipulator;

public class Cistern extends Element {

    /* Megpróbál pumpát adni a soron lévő játékos tárolójába. */
    @Override
    public void OnPickup() {
        throw new NotImplementedException();
    }

    /* Vízzel tölti fel magát, és pontot ad a szerelőknek. A pálya végpontjaként nem áramoltat más elemekbe vizet. */
    @Override
    public void Flood() {
        throw new NotImplementedException();
    }

    /* A paraméterként kapott manipulátorral manipulálja ezt a konkrét elemet. */
    @Override
    public void Manipulate(Manipulator m) {
        throw new NotImplementedException();
    }
}