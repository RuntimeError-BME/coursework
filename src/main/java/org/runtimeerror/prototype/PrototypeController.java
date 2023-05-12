package org.runtimeerror.prototype;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import org.runtimeerror.controller.Game;
import org.runtimeerror.model.map.*;
import org.runtimeerror.model.players.*;
import sun.nio.ch.Net;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;

public final class PrototypeController {

    private static Game game = Game.GetInstance(); // egy referencia a játék egyetlen példányára (Singleton)
    private static PrototypeController singleInstance; // a proto controller egyetlen példánya (Singleton)

    private static PrintStream outConsole = System.out; // referencia a konzol kimenetre
    private static InputStream inConsole = System.in; // referencia a konzol bemenetre
    private boolean printAlsoToFile = false;
    private static boolean logging; // ki lesz-e írva a parancsok kimenete

    private List<Entry<String, String>> inputFiles = new ArrayList<>(20);
    private List<String> outputFiles = new ArrayList<>(20);
    private List<String> required_outputFiles = new ArrayList<>(20);

    private static String currLine; // a jelenlegi parancs (sor) szövege

    private boolean gameOver = false; // véget ért-e a játék

    static {
        singleInstance = null;
        logging = false;
    }

    public static PrintStream GetOutConsole(){
        return outConsole;
    }

    public boolean GetPrintAlsoToFile() { return printAlsoToFile; }

    public static boolean IsLogging() { return logging; }

    /**
     * Konstruktor (privát láthatóságú, mert Singleton osztály).
     */
    private PrototypeController() {
        boolean readFromFiles = setInputOutputStream("input.txt", true);
        printAlsoToFile = setInputOutputStream("output.txt", false);
        if (printAlsoToFile)
            readOutputFiles();

        if (!readFromFiles) {
            consoleCommandLoop();
        } else {
            readInputFiles();
            fileTestingLoop();
        }
    }

    /**
     * Beállítja hogy honnan olvasson/hova írjon a prototípus, ha létezik a fájl akkor állítja át konzolról.
     * @param path - a fájl eléri útja ami a kimenet/bemenet fájlok elérési útját tartalmazza.
     * @param in - igaz ha a bemenetet állíjuk,
     * @return - visszaadja, hogy létezik-e a fájl.
     */
    private boolean setInputOutputStream(String path, boolean in) {
        File file = new File(path);
        if (!file.exists()) {
            outConsole.println("Nincs meg: "+path);
            return false;
        } else {
            try {
                if (in) System.setIn(Files.newInputStream(Paths.get(path)));
            } catch (Exception e) {
                outConsole.println("Hiba: Az adott fájl (" + path + ") elérése során!");
            }
            return true;
        }
    }

    /** A konzolról olvas parancsokat végtelen ciklusban. */
    private void consoleCommandLoop() {

    }

    /** Beolvassa a bemeneti fájlok neveit és a hozzájuk tartozó leírást. */
    private void readInputFiles() {
        Scanner s = new Scanner(System.in);
        while (s.hasNextLine()) {
            String line = s.nextLine();
            String[] splitted = line.split("\t");
            inputFiles.add(new AbstractMap.SimpleEntry(splitted[0], splitted[1]));
        }
    }

    /** Beolvassa a kimeneti fájlokat (amikbe írunk, és amikhez összehasonlítjuk őket). */
    private void readOutputFiles() {
        try {
            Scanner s = new Scanner(new FileInputStream("output.txt"));
            while (s.hasNextLine()) {
                String outputName = s.nextLine();
                String required_outputName = outputName.replace("output", "required_output");
                outputFiles.add(outputName);
                required_outputFiles.add(required_outputName);
            }
        } catch (Exception e)
        {
            System.out.println("Nem létezik output.txt!");
        }
    }

    /**
     * Kiírjuk az input.txt file-ban szereplő teszteseteket számát, melyet a magyarázata követ
     */
    private void listInputFiles() {
        int i = 1;

        for (Entry<String, String> entry : inputFiles)
            System.out.println(i++ + ": " + entry.getValue());
    }

    /** A input fájlok közül választhat a felhasználó végtelen ciklusban. */
    private void fileTestingLoop() {
        while (true) {
            System.setOut(outConsole);
            System.out.println("Válassz tesztesetet:");
            listInputFiles();

            Scanner scanner = new Scanner(inConsole);
            int requiredTest = -1;
            while (true) {
                try {
                    requiredTest = scanner.nextInt();
                    if (requiredTest >= 0 && requiredTest <= inputFiles.size())
                        break;
                } catch (Exception e) { }
                System.out.println("Adj meg érvényes teszteset számot!");
            }

            if (requiredTest == 0) break;
            testFile(requiredTest - 1);
            break;
        }
    }

    /**
     * Elindítja a megadott indexen szereplő teszt futtatását
     * @param testIdx - A futtatni kívánt teszteset indexe
     */
    private void testFile(int testIdx) {
        // inputFiles.get(testIdx).getValue()
    }

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
     * Vissza állítja a pályát alap állapotba (forrás -> cső -> pumpa -> cső -> cső -> cső -> ciszterna)
     * A függvények tesztelését egyelőre ezzel végzem, ezért mást is csinál.
     */
    public static void ResetState(){
        logging = false;
        game.Reset();
        Network network = game.GetNetwork();
        Element.Reset();

        Source s1 = new Source();
        Pipe p1 = new Pipe();
        Pump pu1 = new Pump();
        Pipe p2 = new Pipe();
        network.ChangePumpDirs(pu1.GetIdx(), p1.GetIdx(), p2.GetIdx());
        Pipe p3 = new Pipe();
        Pipe p4 = new Pipe();
        Cistern c1 = new Cistern();

        network.Connect(s1,null,p1);
        network.Connect(p1,s1,pu1);
        network.Connect(pu1,p1,p2);
        network.Connect(p2,pu1,p3);
        network.Connect(p3,p2,p4);
        network.Connect(p4,p3,c1);
        network.Connect(c1,p4,null);

        network.AddSource(s1);
        network.AddPipe(p1);
        network.AddPump(pu1);
        network.AddPipe(p2);
        network.AddPipe(p3);
        network.AddPipe(p4);
        network.AddCistern(c1);

        //Tesztelés rész inenntől:
        Player player1 = new Player("S1");
        player1.SetCurrElem(s1);
        s1.AddPlayer(player1);
        game.AddPlayer(player1);

        System.out.println(game.GetTurnInfo());
        network.Print();

        game.NextTurn();
        network.Print();

        player1.MoveTo(p1);
        currLine = "sticky";
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
     * Végrehajtja a felhasználó által megadott parancsot
     * @param command - Maga a parancs
     */
    private void interpretCommand(String command) {

        String[] splitted = command.split(" ");
        Game game = Game.GetInstance();
        Network network = game.GetNetwork();

        switch(splitted[0]) {
            /** Add parancs végrehajtása */
            case "add":
                switch (splitted[1]) {
                    /** <elem_type> -ra */
                    case "cistern":
                    case "pipe": network.AddPipe()
                    case "pump":
                    case "source":
                        /** TODO: megkapjuk <nb_elem_idx> és ezzel dolgozunk tovább */
                        break;

                    /** <player_type> -ra */
                    case "player":
                    case "technician":
                        /** TODO: megkapjuk <name> <elem_idx> és ezzel dolgozunk tovább */
                        break;
                }
                break;

            /** Controller parancs végrehajtása */
            case "controller":
                switch (splitted[1]) {
                    /** <toggle> -re */
                    case "toggle":
                        /** TODO: megkapjuk <random> és ezzel dolgozunk tovább */
                        break;

                    /** <break> -re */
                    case "break":
                        /** TODO: megkapjuk <elem_idx> és ezzel dolgozunk tovább */
                        break;

                    /** <set> -re */
                    case "set":
                        /** TODO: !!! megkapjuk <defaultCounter> <turn_cnt> és ezzel dolgozunk tovább */
                        break;

                    /** <pipe> -re */
                    case "pipe":
                        /** TODO: megkapjuk <pump_idx> <change> <input> <dir_nr> <output> <dir_nr> és ezzel dolgozunk tovább */
                        break;
                }
                break;

            /** Load parancs végrehajtása */
            case "load":
                /** TODO: megkapjuk <state> <rel_file_name> és ezzel dolgozunk tovább */
                break;

            /** Reset parancs végrehajtása */
            case "reset":
                /** TODO: megkapjuk <state> és ezzel dolgozunk tovább */
                break;

            /** Start parancs végrehajtása */
            case "start":
                /** TODO: megkapjuk <game> és ezzel dolgozunk tovább */
                break;

            /** Move parancs végrehajtása */
            case "move":
                /** TODO: megkapjuk <elem_idx> és ezzel dolgozunk tovább */
                break;

            /** Manipulate parancs végrehajtása */
            case "manipulate":
                switch (splitted[1]) {
                    /** <stickify> -ra */
                    case "stickify":
                        break;

                    /** <slippify> -ra */
                    case "slippify":
                        break;

                    /** <break> -re */
                    case "break":
                        break;

                    /** <change> -re */
                    case "change":
                        /** TODO: megkapjuk <input> <dir_nr> <output> <dir_nr> és ezzel dolgozunk tovább */
                        break;
                }
                break;

            /** Pickup parancs végrehajtása */
            case "pickup":
                break;

            /** Relocate parancs végrehajtása */
            case "relocate":
                /** TODO: megkapjuk <dir_nr> és ezzel dolgozunk tovább */
                break;

            /** Place parancs végrehajtása */
            case "place":
                /** TODO: megkapjuk <dir_nr> és ezzel dolgozunk tovább */
                break;

            /** Print parancs végrehajtása */
            case "print":
                switch (splitted[1]) {
                    /** <inventory> -ra */
                    case "inventory":
                        /** TODO: megkapjuk <technician_name> és ezzel dolgozunk tovább */
                        break;

                    /** <currElem> -re */
                    case "currElem":
                        /** TODO: megkapjuk <name> és ezzel dolgozunk tovább */
                        break;

                    /** <elem> -re */
                    case "elem":
                        /** TODO: megkapjuk <elem_idx> és ezzel dolgozunk tovább */
                        break;

                    /** <map> -re */
                    case "map":
                        break;
                }
                break;

            /** Set parancs végrehajtása */
            case "set":
                switch (splitted[1]) {
                    /** <sitcky> -re */
                    case "sitcky":
                        /** TODO: megkapjuk <elem_idx> és ezzel dolgozunk tovább */
                        break;

                    /** <slippy> -re */
                    case "slippy":
                        /** TODO: megkapjuk <elem_idx> és ezzel dolgozunk tovább */
                        break;
                }
                break;

            /** Break parancs végrehajtása */
            case "break":
                /** TODO: megkapjuk <elem_idx> és ezzel dolgozunk tovább */
                break;

            /** Next parancs végrehajtása */
            case "next":
                /** TODO: megkapjuk <turn> <pass> és ezzel dolgozunk tovább */
                break;

            /** TODO: VALAMILYEN DEFAULT ÉRTÉK BEÁLLÍTÁSA */
            default:
                break;
        }
    }

    private void interpretAddCommand(String command) {

    }

    /**
     * Ide kell a bemenet olvasás annak végrehajtása és a kimenet írása
     */

    /**

     TODO: MEGVAN
     add <elem_type> <nb_elem_idx>
     add <player_type> <name> <elem_idx>
     TODO: MEGVAN
     controller toggle random
     controller break <elem_idx>
     controller set defaultCounter <turn_cnt>
     TODO: MEGVAN
     load state <rel_file_name>
     TODO: MEGVAN
     reset state
     TODO: MEGVAN
     start game
     TODO: MEGVAN
     move <elem_idx>
     TODO: MEGVAN
     manipulate
        stickify
        slippify
        break
        change input <dir_nr> output <dir_nr>
     TODO: MEGVAN
     pickup
     TODO: MEGVAN
     relocate <dir_nr>
     TODO: MEGVAN
     place <dir_nr>
     TODO: MEGVAN
     print inventory <technician_name>
     print currElem <name>
     print elem <elem_idx>
     print map

     ----------------------------------------------------------------


     kell:

     TODO: MEGVAN
     set sitcky <elem_idx>
     set slippy <elem_idx>
     TODO: MEGVAN
     break <elem_idx>
     TODO: MEGVAN
     next turn pass
     TODO: MEGVAN
     controller pipe <pump_idx> change input <dir_nr> output <dir_nr>


     ----------------------------------------------------------------


     1) lépés csőre, amin már állnak
     2) lépés csőre, amin még nem állnak
     3) lépés csúszós csőre, csúszós állapot elmúlása
     4) lépés ragadós csőre, ragadós állapot elmúlása
     5) lépés pumpára, amin már állnak

     6) cső lyukasztása és megjavítása
     7) az elromlott pumpa megjavítsa
     8) cső csúszóssá tétele
     9) cső ragadóssá tétele
     10) pumpa bemenetének és kimenetének beállítása
     11) teleportálás ciszternák között

     12) csőfelvétel, ha lehetséges
     13) csőfelvétel, ha nem lehetséges
     14) csőlehelyezés, ha lehetséges
     15) csőlehelyezés, ha nem lehetséges
     16) pumpafeltvétel, ha lehetséges
     17) pumpafeltvétel, ha nem lehetséges
     18) pumpalehelyezés, ha lehetséges
     19) pumpalehelyezés, ha nem lehetséges

     20) játék vége

     */
}
