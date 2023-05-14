package org.runtimeerror.prototype;

import org.runtimeerror.controller.Game;
import org.runtimeerror.model.map.*;
import org.runtimeerror.model.players.*;


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
    private static boolean readFromFiles = false;
    private static boolean printAlsoToFile = false;
    private static boolean logging; // ki lesz-e írva a parancsok kimenete

    private List<Entry<String, String>> inputFiles = new ArrayList<>(20);
    private List<String> outputFiles = new ArrayList<>(20);
    private List<String> required_outputFiles = new ArrayList<>(20);

    private static Scanner inpScanner = new Scanner(inConsole);
    private static Scanner lastScanner = new Scanner(inConsole);
    private static String currLine; // a jelenlegi parancs (sor) szövege
    private boolean gameOver = false; // véget ért-e a játék

    static {
        singleInstance = null;
        logging = true;
    }

    public static PrintStream GetOutConsole(){
        return outConsole;
    }

    public boolean GetPrintAlsoToFile() { return printAlsoToFile; }

    public static boolean IsLogging() { return logging; }

    /**
     * "Konstruktor" - a végtelen ciklusok miatt kihelyezzük publikusra - muszáj meghívni.
     */
    public void Start()
    {
        if (!readFromFiles) {
            consoleCommandLoop();
        } else {
            readInputFiles();
            fileTestingLoop();
        }
    }

    /** privát konstruktor */
    private PrototypeController() {
        readFromFiles = setInputOutputStream("input.txt", true);
        printAlsoToFile = setInputOutputStream("output.txt", false);
        if (!readFromFiles)
            printAlsoToFile = false;
        if (printAlsoToFile)
            readOutputFiles();
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
        inpScanner = new Scanner(inConsole);
        System.out.println("Adj meg parancsokat, vagy 0-át a kilépéshez!");
        while (true) {
            String line = inpScanner.nextLine();
            try {
                if (Integer.parseInt(line) == 0)
                    break;
            } catch (Exception Ignored) {
                try {
                    interpretCommand(line);
                } catch (Exception ex) { outConsole.println(ex.getStackTrace()); }
            }
        }
    }

    /** Beolvassa a bemeneti fájlok neveit és a hozzájuk tartozó leírást. */
    private void readInputFiles() {
        inpScanner = new Scanner(System.in);
        while (inpScanner.hasNextLine()) {
            String line = inpScanner.nextLine();
            String[] splitted = line.split("\t");
            inputFiles.add(new AbstractMap.SimpleEntry(splitted[0], splitted[1]));
        }
    }

    /** Beolvassa a kimeneti fájlokat (amikbe írunk, és amikhez összehasonlítjuk őket). */
    private void readOutputFiles() {
        try {
            inpScanner = new Scanner(new FileInputStream("output.txt"));
            while (inpScanner.hasNextLine()) {
                String outputName = inpScanner.nextLine();
                String required_outputName = outputName.replace("output", "required_output");
                outputFiles.add(outputName);
                required_outputFiles.add(required_outputName);
            }
        } catch (Exception e) { System.out.println("Nem létezik output.txt!"); }
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
            System.out.println("0: kilépés");
            listInputFiles();

            int requiredTest = -1;
            inpScanner = new Scanner(inConsole);
            while (true) {
                try {
                    requiredTest = inpScanner.nextInt();
                    if (requiredTest >= 0 && requiredTest <= inputFiles.size())
                        break;
                } catch (Exception e) { }
                System.out.println("Adj meg érvényes teszteset számot!");
            }

            if (requiredTest == 0) break;
            testFile(requiredTest - 1, printAlsoToFile);
            Game.GetInstance().Reset();
            gameOver = false;
        }
    }

    /**
     * Elindítja a megadott relatív elérési úton található teszt futtatását
     * @param path - A futtatni kívánt teszteset relatív elérési útja
     */
    private void testFile(String path) {
        try {
            System.setIn(new FileInputStream(path));
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine() && !gameOver) {
                currLine = scanner.nextLine();
                lastScanner = scanner;
                interpretCommand(currLine);
            }
        } catch (Exception ex) { outConsole.println(ex.getStackTrace()); }
    }

    /**
     * Elindítja a megadott indexen szereplő teszt futtatását
     * @param testIdx - A futtatni kívánt teszteset indexe
     * @param correspOut - Ha igaz, a hozzá tartozó kimeneti fájlba fog írni
     */
    private void testFile(int testIdx, boolean correspOut) {
        try {
            if (correspOut) {
                String outPath = outputFiles.get(testIdx);
                String reqOutPath = required_outputFiles.get(testIdx);
                System.setOut(new PrintStream(outPath));
                Entry<String, String> inputEntry = inputFiles.get(testIdx);
                testFile(inputEntry.getKey());
                compareTestResults(outPath, reqOutPath, (testIdx + 1) + ": " + inputEntry.getValue());

            } else {
                testFile(inputFiles.get(testIdx).getKey());
            }
        } catch (FileNotFoundException ex) { outConsole.println(ex.toString()); }
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
    public static String GetCurrLine() {
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
     * Kiírja az átadott sztringet az átadott kimenetre - a konzolra, illetve fájlba is ha be van kapcsolva a funkció
     * @param line - átadott sztring, ezt írja ki
     */
    public static void PrintLine(String line) {
        if (printAlsoToFile)
            System.out.println(line);

        PrintStream ps = System.out;
        System.setOut(PrototypeController.GetInstance().GetOutConsole());
        System.out.println(line);
        System.setOut(ps);
    }

    /** Beolvas egy új sort a teszt bemenetéről (konzol vagy tesztfájl). A manipulátorok hívják
     * (a manipulate parancs után kell megadni a következők egyikét: stickify/slippify/break/change... */
    public static void GetNextLine() {
        currLine = lastScanner.nextLine();
    }

    /**
     * Végrehajtja a felhasználó által megadott parancsot
     * @param command - Maga a parancs
     */
    private void interpretCommand(String command) {

        String[] splitted = command.split(" ");
        Network network = game.GetNetwork();

        switch(splitted[0]) {
            /** add parancs végrehajtása */
            case "add":
                switch (splitted[1]) {
                    /** <elem_type> -ra */
                    case "cistern": network.AddNewCistern(splitted[2].equals("-1") ? null : network.GetElement(Integer.parseInt(splitted[2]))); break;
                    case "pipe": network.AddNewPipe(splitted[2].equals("-1") ? null : network.GetElement(Integer.parseInt(splitted[2]))); break;
                    case "pump": network.AddNewPump(splitted[2].equals("-1") ? null : network.GetElement(Integer.parseInt(splitted[2]))); break;
                    case "source": network.AddNewSource(splitted[2].equals("-1") ? null : network.GetElement(Integer.parseInt(splitted[2]))); break;

                    /** <player_type> -ra */
                    case "saboteur": game.AddPlayer(new Player(splitted[2]), Integer.parseInt(splitted[3])); break;
                    case "technician": game.AddPlayer(new Technician(splitted[2]), Integer.parseInt(splitted[3])); break;
                }
                break;

            /** connect parancs végrehajtása */
            case "connect": network.Connect(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]),
                                            Integer.parseInt(splitted[3])); break;

            /** controller parancs végrehajtása */
            case "controller":
                switch (splitted[1]) {
                    /** <toggle> -re */
                    case "toggle": toggleRandom(); break;

                    /** <break> -re */
                    case "break": game.BreakElementByController(Integer.parseInt(splitted[2])); break;

                    /** <sticky> -re */
                    case "stickify": game.StickifyPipeByController(Integer.parseInt(splitted[2])); break;

                    /** <slippy> -re */
                    case "slippify": game.SlippifyPipeByController(Integer.parseInt(splitted[2])); break;

                    /** <set> -re */
                    case "set":
                        switch (splitted[2]) {
                            /** <defaultCounter> -re */
                            case "defaultCounter": Game.SetDefaultCounter(Integer.parseInt(splitted[3])); break;

                            /** <maxCounter> -re */
                            case "maxScore": Game.SetMaxScore(Integer.parseInt(splitted[3])); break;
                        }
                        break;

                    /** <pipe> -re */
                    case "pump": network.ChangePumpDirs(
                        Integer.parseInt(splitted[2]), Integer.parseInt(splitted[5]), Integer.parseInt(splitted[7])); break;
                }
                break;

            /** load parancs végrehajtása */
            case "load": Game.GetInstance().Reset(); PrintLine("loading state from " + splitted[2]);
                testFile(splitted[2]); PrintLine(""); break;

            /** reset parancs végrehajtása */
            case "reset": Game.GetInstance().Reset(); PrintLine("game state was reset"); break;

            /** start parancs végrehajtása */
            case "start": PrintLine("game started\n" + Game.GetInstance().GetTurnInfo()); break;

            /** move parancs végrehajtása */
            case "move": Game.Input.MoveCurrPlayer(Integer.parseInt(splitted[1])); break;

            /** manipulate parancs végrehajtása */
            case "manipulate": game.GetCurrPlayer().GetCurrElem().PrintManipChoice();
                Game.Input.TryCurrElemManipulation(); break;

            /** pickup parancs végrehajtása */
            case "pickup": Game.Input.Pickup(); break;

            /** relocate parancs végrehajtása */
            case "relocate": Game.Input.TryPartRelocation(network.GetElement(Integer.parseInt(splitted[1]))); break;

            /** place parancs végrehajtása */
            case "place": Game.Input.TryPartPlacement(
                splitted.length > 1 ? network.GetElement(Integer.parseInt(splitted[1])) : null); break;

            /** next parancs végrehajtása */
            case "next": PrintLine(""); game.NextTurn(); break;

            /** print parancs végrehajtása */
            case "print":
                switch (splitted[1]) {
                    /** <inventory> -ra */
                    case "inventory": game.PrintInventory(splitted[2]); PrintLine(""); break;

                    /** <currElem> -re */
                    case "currElem": game.PrintCurrElem(splitted[2]); PrintLine(""); break;

                    /** <elem> -re */
                    case "elem": network.PrintElement(Integer.parseInt(splitted[2])); PrintLine(""); break;

                    /** <map> -re */
                    case "map": network.Print(); break;
                }
                break;

            default:
                break;
        }
    }

    /**

     add <elem_type> <nb_elem_idx>
     add <player_type> <name> <elem_idx>
     connect <elem_idx> <in_idx> <out_idx>
     controller toggle random
     controller break <elem_idx>
     controller stickify <elem_idx>
     controller slippify <elem_idx>
     controller set defaultCounter <turn_cnt>
     controller set maxScore <amount>
     controller pump <pump_idx> change input <elem_idx> output <elem_idx>

     load state <rel_file_name>
     reset state
     start game
     move <elem_idx>
     manipulate
        stickify
        slippify
        break
        change input <elem_idx> output <elem_idx>
     pickup
     relocate <elem_idx>
     place [elem_idx]
     next turn
     print inventory <technician_name>
     print currElem <name>
     print elem <elem_idx>
     print map

     ----------------------------------------------------------------

     1) lépés csőre, amin már állnak
     2) lépés csőre, amin még nem állnak
     3) lépés pumpára, amin már állnak

     4) lépés csúszós csőre, csúszós állapot elmúlása
     5) lépés ragadós csőre, ragadós állapot elmúlása
     6) cső csúszóssá tétele
     7) cső ragadóssá tétele
     8) pumpa bemenetének és kimenetének beállítása
     9) cső lyukasztása és megjavítása
     10) az elromlott pumpa megjavítsa

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


    /**
     * Összehasonlítja a teszt által generált kimenetet, az általunk előre definiált/elvárt kimenettel
     */
    private void compareTestResults(String outputName, String requiredOutputName, String testName) {
        try {
            /** A teszt által generált output sorainak beolvasása egy listába */
            List<String> outputList = new ArrayList<>();
            File output = new File(outputName);
            Scanner sc1 = new Scanner(output);
            while (sc1.hasNextLine()) {
                String data = sc1.nextLine();
                outputList.add(data);
            }
            sc1.close();

            /** Az általunk definiált output sorainak beolvasása egy listába */
            List<String> requiredOutputList = new ArrayList<>();
            File requiredOutput = new File(requiredOutputName);
            Scanner sc2 = new Scanner(requiredOutput);
            while (sc2.hasNextLine()) {
                String data = sc2.nextLine();
                requiredOutputList.add(data);
            }
            sc2.close();

            /** A két output összehasonlítása soronként - a teszt kiértékelése */
            if (outputList.size() != requiredOutputList.size()) outConsole.println("A megadott két fájl tartalma " +
                                                                                   "eltérő! -> A teszt: [" + testName + "] - sikertelen.");
            else {
                for (int i = 0; i < requiredOutputList.size(); i++) {
                    if (!requiredOutputList.get(i).equals(outputList.get(i))) {
                        outConsole.println("A megadott két fájl tartalma " +
                                           "eltérő! -> A teszt: [" + testName + "] - sikertelen.");
                        return;
                    }
                }
                outConsole.println("A megadott két fájl tartalma " +
                                   "megegyezik! -> A teszt: [" + testName + "] - sikeres.");
            }

        } catch (FileNotFoundException e) {
            outConsole.println("A fájlok valamelyike nem létezik, vagy hibásan adta meg elérési útvonalát!");
            e.printStackTrace();
        }
    }




}
