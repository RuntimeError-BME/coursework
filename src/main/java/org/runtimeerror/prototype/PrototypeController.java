package org.runtimeerror.prototype;

import org.runtimeerror.controller.Game;


import java.io.InputStream;
import java.io.PrintStream;
public final class PrototypeController {

    private static Game game = Game.GetInstance(); // egy referencia a játék egyetlen példányára (Singleton)
    private static PrototypeController singleInstance; // a proto controller egyetlen példánya (Singleton)

    private static PrintStream outConsole = System.out; // referencia a konzol kimenetre
    private static InputStream inConsole = System.in; // referencia a konzol bemenetre

    private String currLine; // a jelenlegi parancs (sor) szövege
    private boolean gameOver = false; // véget ért-e a játék

    static {
        singleInstance = null;
    }

    /**
     * Konstruktor (privát láthatóságú, mert Singleton osztály).
     */
    private PrototypeController() { }

    /**
     * Visszaadja a játék egyetlen példányát. (Első híváskor hozza létre.)
     */
    public static PrototypeController GetInstance() {
        if (singleInstance == null)
            singleInstance = new PrototypeController();

        return singleInstance;
    }

    /**
     * Visszaadja a jelenleg beolvasott parancs sorát szövegként.
     */
    public String GetCurrLine() {
        return currLine;
    }

    /**
     * Az átadott logikai változó szerint beállítja, hogy a játék véget ért-e.
     */
    public void SetGameOver(boolean over) { gameOver = over; }

    /**
     * Átállítja, hogy a játék determinisztikus-e (van-e benne véletlenszerű viselkedés, vagy sem).
     * Ha determinisztikus nemdeterminisztikussá, ha pedig nemdeterminisztikus determinisztikussá teszi.
     * (Gyakorlatilag invertálja a logikai változót SetDeterministic() és GetDeterministic() fv-ek segítségével.)
     */
    private static void toggleRandom() {
        game.SetDeterministic(!game.GetDeterministic());
    }

    /**
     * Elkezdi a játék főciklusát (amíg nem éri el az egyik csapat a győzelemhez szükséges pontszámot, addig TODO!!!
     */
    private static void startGame() {
        System.out.println(game.GetTurnInfo());

    }

    /**
     * Ide kell a bemenet olvasás annak végrehajtása és a kimenet írása
     */

    /**


     add <elem_type> <nb_elem_idx>
     add <player_type> <name> <elem_idx>
     controller toggle random
     controller break <elem_idx>
     !!! controller set defaultCounter <turn_cnt>
     load state <rel_file_name>
     reset state
     start game

     move <elem_idx>
     manipulate
        stickify
        slippify
        break
        change input <dir_nr> output <dir_nr>

     pickup
     relocate <dir_nr>
     place <dir_nr>

     print inventory <technician_name>
     print currElem <name>
     print elem <elem_nr>
     print map

     ----------------------------------------------------------------
     kell:

     Setsitcky
     SetSlippery
     Break
     Next Turn // kör passzolása


     ----------------------------------------------------------------


     lépés csőre, amin már állnak
     lépés csőre, amin még nem állnak
     lépés csúszós csőre, csúszós állapot elmúlása
     lépés ragadós csőre, ragadós állapot elmúlása
     lépés pumpára, amin már állnak

     cső lyukasztása és megjavítása
     az elromlott pumpa megjavítsa
     cső csúszóssá tétele
     cső ragadóssá tétele
     pumpa bemenetének és kimenetének beállítása
     teleportálás ciszternák között

     csőfelvétel, ha lehetséges
     csőfelvétel, ha nem lehetséges
     csőlehelyezés, ha lehetséges
     csőlehelyezés, ha nem lehetséges
     pumpafeltvétel, ha lehetséges
     pumpafeltvétel, ha nem lehetséges
     pumpalehelyezés, ha lehetséges
     pumpalehelyezés, ha nem lehetséges

     játék vége

     */
}
