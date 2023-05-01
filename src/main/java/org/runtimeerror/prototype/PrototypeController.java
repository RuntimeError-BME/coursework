package org.runtimeerror.prototype;

import org.runtimeerror.controller.Game;

import java.io.InputStream;
import java.io.PrintStream;
public class PrototypeController {

    private Game game = Game.GetInstance(); // egy referencia a játék egyetlen példányára (Singleton)

    private static PrintStream outConsole = System.out; // referencia a konzol kimenetre
    private static InputStream inConsole = System.in; // referencia a konzol bemenetre

    private static boolean isRandom = true; // megadja, hogy a modell véletlenszerű viselkedése be van-e kapcsolva


    public static boolean GetRandom

    public static void ToggleRandom

        /**
         * Ide kell a bemenet olvasás annak végrehajtása és a kimenet írása
         */
}
