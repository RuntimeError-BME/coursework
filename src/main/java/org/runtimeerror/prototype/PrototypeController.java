package org.runtimeerror.prototype;

import org.runtimeerror.controller.Game;
import org.runtimeerror.model.map.*;
import org.runtimeerror.model.players.*;


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
     * Egy elemhez hozzárak egy új bemenetet és vagy kimenetet.
     * @param connectthis - az elem amihez csatlakoztat.
     * @param inp - az új bemenet, ha null akkor nem rakja hozzá
     * @param outp - az új kimenet, ha null akkor nem rakja hozzá
     */
    private static void Connector( Element connectthis ,Element inp, Element outp ){
        if(inp!=null) {
            connectthis.AddNb(inp);
            connectthis.SetInput(inp);
        }
        if(outp!=null) {
            connectthis.AddNb(outp);
            connectthis.SetOutput(outp);
        }
    }
    /**
     * Vissza állítja a pályát alap állapotba (forrás -> cső -> pumpa -> cső -> cső -> cső -> ciszterna)
     * A függvények tesztelését egyelőre ezzel végzem, ezért mást is csinál.
     */
    public static void ResetState(){
        game.Reset();
        Network network= game.GetNetwork();

        Source s1=new Source();
        Pipe p1=new Pipe();
        Pipe p1_5=new Pipe();
        Pipe p2=new Pipe();
        Pipe p3=new Pipe();
        Pipe p4=new Pipe();
        Pump pu1=new Pump();
        Cistern c1=new Cistern();

        Connector(s1,null,p1);

        Connector(p1,s1,p1_5);

        Connector(p1_5,p1,p2);

        Connector(p2,p1_5,p3);

        Connector(p3,p2,p4);

        Connector(p4,p3,c1);

        Connector(c1,p4,null);

        network.AddSource(s1);
        network.AddPipe(p1);
        network.AddPipe(p1_5);
        network.AddPipe(p2);
        network.AddPipe(p3);
        network.AddPipe(p4);
        network.AddPump(pu1,p1_5);
        network.AddCistern(c1);

        ManipulatorPlayer mp1=new ManipulatorPlayer();
        Player player1=new Player("S1",mp1);
        player1.SetCurrElem(s1);
        s1.AddPlayer(player1);
        game.AddPlayer(player1);

        System.out.println(game.GetTurnInfo());
        network.Print();

        game.NextTurn();
        network.Print();

        PrototypeController.GetInstance().SetCurrLine("sticky");
        player1.MoveTo(p1);
        player1.ManipulateCurrElem();

        network.Print();
    }

    /**
     * Beállítja a jelenlegi bemenetet. - MÓDOSÍTÁSRA szorulhat, csak a tesztek működése okán kellett.
     * @param currline - amire beállítja (string)
     */
    private void SetCurrLine(String currline){
        this.currLine=currline;
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

     Setsitcky <elem_idx>
     SetSlippery <elem_idx>
     Break <elem_idx>
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
