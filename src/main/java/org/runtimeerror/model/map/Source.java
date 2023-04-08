package org.runtimeerror.model.map;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import org.runtimeerror.model.players.Manipulator;

public class Source extends Element {

    /* Vízzel tölti fel magát, és minden nem lyukas szomszédjára hívja tovább a Flood() függvényt,
     azaz ereszti tovább a vizet. Ha nem tudja ezt megtenni az adott szomszédja felé,
     mert lyukas, vagy nincs arra szomszédja, akkor pontot ad a szabotőröknek. */
    @Override
    public void Flood() {
        throw new NotImplementedException();
    }

    /* Az átvett manipulátorral manipulálja ezt a konkrét elemet. */
    @Override
    public void Manipulate(Manipulator m) {
        throw new NotImplementedException();
    }
}