package org.runtimeerror.skeleton;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import org.runtimeerror.Main;
import org.runtimeerror.controller.Game;
import org.runtimeerror.model.map.*;
import org.runtimeerror.model.players.ManipulatorSaboteur;
import org.runtimeerror.model.players.ManipulatorTechnician;
import org.runtimeerror.model.players.Technician;
import org.runtimeerror.model.players.Player;


/**
 * Minden esetben SkeletonController indítja a külső hívást.
 */
public final class SkeletonController {
    /**
     * Attribútumok
     */
    private int tabCnt = 0;
    public static boolean isLogging = true;
    private Scanner in;
    private Map<Integer, String> TestNameMap = new HashMap<>(30); // Tesztszám - Tesztnév párosok
    public static Map<Object, String> ObjNameMap = new HashMap<>(8); // Objektum - "név : típus" string párosok
    public static Game _Game= new Game();
    public static Game.Input _GameI = new Game.Input();

    /**
     * Metódusok
     */
    public SkeletonController(Scanner input) {
        in = input;
        TestNameMap.put(0, "exit program");
        TestNameMap.put(1, "Move to empty Pipe");
        TestNameMap.put(2, "Move to occupied Pipe");
        TestNameMap.put(3, "Move to Pump");
        TestNameMap.put(4, "Move to Cistern");
        TestNameMap.put(5, "Move to Source");
        TestNameMap.put(6, "Fix broken Pump");
        TestNameMap.put(7, "Change directions of not broken Pump");
        TestNameMap.put(8, "Change directions of broken Pump");
        TestNameMap.put(9, "Cross Cisterns");
        TestNameMap.put(10, "Break not broken Pipe");
        TestNameMap.put(11, "Break broken Pipe");
        TestNameMap.put(12, "Fix not broken Pipe");
        TestNameMap.put(13, "Fix broken Pipe");
        TestNameMap.put(14, "Relocate relocatable Pipe");
        TestNameMap.put(15, "Relocate unrelocatable Element");
        TestNameMap.put(16, "Pick up Pump when possible");
        TestNameMap.put(17, "Pick up Pump when impossible");
        TestNameMap.put(18, "Place Pipe when placeable");
        TestNameMap.put(19, "Place Pipe when not placeable");
        TestNameMap.put(20, "Place Pump when placeable");
        TestNameMap.put(21, "Place Pump when not placeable");
        TestNameMap.put(22, "Flood not broken Pipe");
        TestNameMap.put(23, "Flood broken Pipe");
        TestNameMap.put(24, "Flood not broken Pump");
        TestNameMap.put(25, "Flood broken Pump");
        TestNameMap.put(26, "Flood Source");
        TestNameMap.put(27, "Flood Cistern");
        TestNameMap.put(28, "Produce Pipe");
        TestNameMap.put(29, "Impair Pump");
        ObjNameMap.put(this, ":SkeletonController");
    }

    public String GetTestName(int testNumber) {
        return TestNameMap.get(testNumber);
    }

    public void PrintAllTestNames() {
        for (int i = 0; i < 30; ++i)
            System.out.println(i + ": " + TestNameMap.get(i));
    }

    public void RunTest(int testNumber) {
        switch (testNumber) {
            case  1: test01_Move_to_empty_Pipe(); break;
            case  2: test02_Move_to_occupied_Pipe(); break;
            case  3: test03_Move_to_Pump(); break;
            case  4: test04_Move_to_Cistern(); break;
            case  5: test05_Move_to_Source(); break;
            case  6: test06_Fix_broken_Pump(); break;
            case  7: test07_Change_directions_of_not_broken_Pump(); break;
            case  8: test08_Change_directions_of_broken_Pump(); break;
            case  9: test09_Cross_Cisterns(); break;
            case 10: test10_Break_not_broken_Pipe(); break;
            case 11: test11_Break_broken_Pipe(); break;
            case 12: test12_Fix_not_broken_Pipe(); break;
            case 13: test13_Fix_broken_Pipe(); break;
            case 14: test14_Relocate_relocatable_Pipe(); break;
            case 15: test15_Relocate_unrelocatable_Element(); break;
            case 16: test16_Pick_up_Pump_when_possible(); break;
            case 17: test17_Pick_up_Pump_when_impossible(); break;
            case 18: test18_Place_Pipe_when_placeable(); break;
            case 19: test19_Place_Pipe_when_not_placeable(); break;
            case 20: test20_Place_Pump_when_placeable(); break;
            case 21: test21_Place_Pump_when_not_placeable(); break;
            case 22: test22_Flood_not_broken_Pipe(); break;
            case 23: test23_Flood_broken_Pipe(); break;
            case 24: test24_Flood_not_broken_Pump(); break;
            case 25: test25_Flood_broken_Pump(); break;
            case 26: test26_Flood_Source(); break;
            case 27: test27_Flood_Cistern(); break;
            case 28: test28_Produce_Pipe(); break;
            case 29: test29_Impair_Pump(); break;
        }
    }

    private void indent() {

        for (int i = 0; i < tabCnt; ++i)
             System.out.print('\t');
        ++tabCnt;
    }

    public void PrintFunctionCall(Object callerThis, String funName) {

        if (!isLogging)
            return;
        indent();
        System.out.print(ObjNameMap.get(callerThis) + " calls " + funName + "()");
    }
    public void PrintFunctionCall(Object callerThis, String funName, String param1) {

        if (!isLogging)
            return;
        indent();
        System.out.print(ObjNameMap.get(callerThis) + " calls " + funName + "(" +
                param1 + ")");
    }
    public void PrintFunctionCall(Object callerThis, String funName, String param1, String param2) {

        if (!isLogging)
            return;
        indent();
        System.out.print(ObjNameMap.get(callerThis) + " calls " + funName + "(" +
                param1 + "," + param2 + ")");
    }
    public void PrintFunctionCall(Object callerThis, String funName, Object param1) {

        if (!isLogging)
            return;
        indent();
        System.out.print(ObjNameMap.get(callerThis) + " calls " + funName + "(" +
                         ObjNameMap.get(param1).toString().split(":")[0] + ")");
    }

    public void PrintFunctionCall(Object callerThis, String funName, Object param1, Object param2) {

        if (!isLogging)
            return;
        indent();
        System.out.print(ObjNameMap.get(callerThis) + " calls " + funName + "(" +
                         ObjNameMap.get(param1).toString().split(":")[0] + ", " +
                         ObjNameMap.get(param2).toString().split(":")[0] + ")");
    }

    public void PrintFunctionCalled(Object callerThis) {
        if (isLogging)
            System.out.println(" on " + ObjNameMap.get(callerThis));
    }

    public void PrintFunctionReturned(String funName, String returnValue) {

        if (!isLogging)
            return;

        --tabCnt;
        for (int i = 0; i < tabCnt; ++i)
             System.out.print('\t');
        System.out.println(funName + " returned " + returnValue);
    }

    private void test01_Move_to_empty_Pipe() {
        /** inicializálás */
        isLogging = false;
        Player player = new Player("s1"); // akit mozgatunk (szabotőr, de technikussal is ugyanígy lenne)
        Pipe currElem = new Pipe(); // a cső, amin jelenleg áll
        player.SetCurrElem(currElem); // jelenleg ezen áll
        currElem.AddPlayer(player);
        Pipe targetElem = new Pipe(); // a cső, amire léptetni fogjuk
        /** a két cső egymás szomszédja (input/output beállítása lényegtelen ehhez a teszthez): */
        currElem.SetNb(new Direction(0), targetElem);
        targetElem.SetNb(new Direction(3), currElem);

        /** objektumok hozzáadása a map-hez */
        ObjNameMap.put(player, "player:Player");
        ObjNameMap.put(currElem, "currElem:Pipe");
        ObjNameMap.put(targetElem, "targetElem:Pipe");

        /** szekvencia innen */
        isLogging = true;
        PrintFunctionCall(this, "MoveTo", targetElem);
        player.MoveTo(targetElem);

        /** objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");
    }

    private void test02_Move_to_occupied_Pipe() {
        /** inicializálás */
        isLogging = false;
        Player player = new Player("s1"); // akit mozgatunk (szabotőr, de technikussal is ugyanígy lenne)
        Pipe currElem = new Pipe(); // a cső, amin jelenleg áll
        player.SetCurrElem(currElem); // jelenleg ezen áll
        currElem.AddPlayer(player);
        Pipe targetElem = new Pipe(); // a cső, amire léptetni fogjuk
        Player playerOnTarget = new Player("s2"); // a játékos, aki a cél pumpán áll
        playerOnTarget.SetCurrElem(targetElem);
        targetElem.AddPlayer(playerOnTarget);
        /** a két cső egymás szomszédja (input/output beállítása lényegtelen ehhez a teszthez): */
        currElem.SetNb(new Direction(0), targetElem);
        targetElem.SetNb(new Direction(3), currElem);

        /** objektumok hozzáadása a map-hez */
        ObjNameMap.put(player, "player:Player");
        ObjNameMap.put(currElem, "currElem:Pipe");
        ObjNameMap.put(targetElem, "targetElem:Pipe");
        ObjNameMap.put(playerOnTarget, "playerOnTarget:Player");

        /** szekvencia innen */
        isLogging = true;
        PrintFunctionCall(this, "MoveTo", targetElem);
        player.MoveTo(targetElem);

        /** objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");
    }

    private void test03_Move_to_Pump() {
        /** inicializálás */
        isLogging = false;
        Player player = new Player("s1"); // akit mozgatunk (szabotőr, de technikussal is ugyanígy lenne)
        Pipe currElem = new Pipe(); // a pumpa, amin jelenleg áll
        player.SetCurrElem(currElem); // jelenleg ezen áll
        currElem.AddPlayer(player);
        Pump targetElem = new Pump(); // a cső, amire léptetni fogjuk

        Player playerOnTarget1 = new Player("s2"); // a játékos, aki a cél pumpán áll
        playerOnTarget1.SetCurrElem(targetElem);
        targetElem.AddPlayer(playerOnTarget1);

        Player playerOnTarget2 = new Player("s3"); // a játékos, aki a cél pumpán áll
        playerOnTarget2.SetCurrElem(targetElem);
        targetElem.AddPlayer(playerOnTarget2);

        /** objektumok hozzáadása a map-hez */
        ObjNameMap.put(player, "player:Player");
        ObjNameMap.put(currElem, "currElem:Pipe");
        ObjNameMap.put(targetElem, "targetElem:Pump");
        ObjNameMap.put(playerOnTarget1, "playerOnTarget:Player1");
        ObjNameMap.put(playerOnTarget2, "playerOnTarget:Player2");

        /** szekvencia innen */
        isLogging = true;
        PrintFunctionCall(this, "MoveTo", targetElem);
        player.MoveTo(targetElem);

        /** objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");
    }

    private void test04_Move_to_Cistern() {
        /** inicializálás */
        isLogging = false;
        Player player = new Player("s1"); // akit mozgatunk (szabotőr, de technikussal is ugyanígy lenne)
        Pipe currElem = new Pipe(); // a pumpa, amin jelenleg áll
        player.SetCurrElem(currElem); // jelenleg ezen áll
        currElem.AddPlayer(player);
        Cistern targetElem = new Cistern(); // a ciszterna, amire léptetni fogjuk

        Player playerOnTarget = new Player("s2"); // a játékos, aki a cél ciszternán áll
        playerOnTarget.SetCurrElem(targetElem);
        targetElem.AddPlayer(playerOnTarget);
        /** objektumok hozzáadása a map-hez */

        ObjNameMap.put(player, "player:Player");
        ObjNameMap.put(currElem, "currElem:Pipe");
        ObjNameMap.put(targetElem, "targetElem:Cistern");
        ObjNameMap.put(playerOnTarget, "playerOnTarget:Player");

        /** szekvencia innen */

        isLogging = true;
        PrintFunctionCall(this, "MoveTo", targetElem);
        player.MoveTo(targetElem);

        /** objektumok eltávolítás a map-ből */

        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");

    }

    private void test05_Move_to_Source() {
        /** inicializálás */
        isLogging = false;
        Player player = new Player("s1"); // akit mozgatunk (szabotőr, de technikussal is ugyanígy lenne)
        Pipe currElem = new Pipe(); // a pumpa, amin jelenleg áll
        player.SetCurrElem(currElem); // jelenleg ezen áll
        currElem.AddPlayer(player);
        Source targetElem = new Source(); // a ciszterna, amire léptetni fogjuk

        /** objektumok hozzáadása a map-hez */

        ObjNameMap.put(player, "player:Player");
        ObjNameMap.put(currElem, "currElem:Pipe");
        ObjNameMap.put(targetElem, "targetElem:Source");

        /** szekvencia innen */
        isLogging = true;
        PrintFunctionCall(this, "MoveTo", targetElem);
        player.MoveTo(targetElem);

        /** objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");

    }

    private void test06_Fix_broken_Pump() {
        /** inicializálás */
        isLogging=false;
        ManipulatorTechnician manipulator = new ManipulatorTechnician();
        Technician player = new Technician("t1",manipulator);
        Pump currElem = new Pump(); // a pumpa, amin jelenleg áll
        player.SetCurrElem(currElem); // jelenleg ezen áll
        currElem.AddPlayer(player);
        _Game = new Game();

        currElem.Break();

        /** objektumok hozzáadása a map-hez */

        ObjNameMap.put(player, "player:Technician");
        ObjNameMap.put(currElem, "currElem:Pump");
        ObjNameMap.put(manipulator, "manipulator:ManipulatorTechnician");
        ObjNameMap.put(_Game, ":Game");

        /** szekvencia innen */
        isLogging = true;
        PrintFunctionCall(this, "ManipulateCurrElem");
        player.ManipulateCurrElem();

        /** objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");
    }

    private void test07_Change_directions_of_not_broken_Pump() {
        /** Saboteur */
        System.out.println("\nSaboteur:\n");

        /** inicializálás */
        isLogging = false;
        ManipulatorSaboteur manipulatorS = new ManipulatorSaboteur();
        Player playerS = new Player("s1",manipulatorS);
        Pump currElemS = new Pump();
        playerS.SetCurrElem(currElemS);
        currElemS.AddPlayer(playerS);
        _Game = new Game();
        Pipe p1S=new Pipe();
        Pipe p2S=new Pipe();
        currElemS.SetNb(new Direction(1),p1S);
        currElemS.SetNb(new Direction(2),p2S);

        /** objektumok hozzáadása a map-hez */
        ObjNameMap.put(playerS, "player:Player");
        ObjNameMap.put(currElemS, "currElem:Pump");
        ObjNameMap.put(manipulatorS, "manipulator:ManipulatorSaboteur");
        ObjNameMap.put(_Game, ":Game");
        ObjNameMap.put(_GameI, ":GameInput");

        /** szekvencia innen */
        isLogging=true;
        PrintFunctionCall(this, "ManipulateCurrElem");
        playerS.ManipulateCurrElem();

        /** objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");


        /** Technician */
        System.out.println("\nTechnician:\n");

        /** inicializálás */
        isLogging = false;
        ManipulatorTechnician manipulatorT = new ManipulatorTechnician();
        Technician playerT = new Technician("s1",manipulatorT); //player->Technician
        Pump currElemT = new Pump();
        playerT.SetCurrElem(currElemT);
        currElemT.AddPlayer(playerT);
        _Game = new Game();
        Pipe p1T=new Pipe();
        Pipe p2T=new Pipe();
        currElemT.SetNb(new Direction(1),p1T);
        currElemT.SetNb(new Direction(2),p2T);

        /** objektumok hozzáadása a map-hez */
        ObjNameMap.put(playerT, "player:Technician");
        ObjNameMap.put(currElemT, "currElem:Pump");
        ObjNameMap.put(manipulatorT, "manipulator:ManipulatorTechnician");
        ObjNameMap.put(_Game, ":Game");
        ObjNameMap.put(_GameI, ":GameInput");

        /** szekvencia innen */
        isLogging=true;
        PrintFunctionCall(this, "ManipulateCurrElem");
        playerT.ManipulateCurrElem();

        /** objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");



    }

    private void test08_Change_directions_of_broken_Pump() {
        /** Saboteur */
        System.out.println("\nSaboteur:\n");

        /** inicializálás */
        isLogging = false;
        ManipulatorSaboteur manipulatorS = new ManipulatorSaboteur();
        Player playerS = new Player("s1",manipulatorS);
        Pump currElemS = new Pump();
        playerS.SetCurrElem(currElemS);
        currElemS.AddPlayer(playerS);
        _Game = new Game();
        Pipe p1S=new Pipe();
        Pipe p2S=new Pipe();
        currElemS.SetNb(new Direction(1),p1S);
        currElemS.SetNb(new Direction(2),p2S);

        /** objektumok hozzáadása a map-hez */
        ObjNameMap.put(playerS, "player:Player");
        ObjNameMap.put(currElemS, "currElem:Pump");
        ObjNameMap.put(manipulatorS, "manipulator:ManipulatorSaboteur");
        ObjNameMap.put(_Game, ":Game");
        ObjNameMap.put(_GameI, ":GameInput");

        /** szekvencia innen */
        isLogging=true;
        PrintFunctionCall(this, "ManipulateCurrElem");
        playerS.ManipulateCurrElem();

        /** objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");


        /** Technician */
        System.out.println("\nTechnician:\n");

        /** inicializálás */
        isLogging=false;
        ManipulatorTechnician manipulator = new ManipulatorTechnician();
        Technician player = new Technician("t1",manipulator);
        Pump currElem = new Pump(); // a pumpa, amin jelenleg áll
        player.SetCurrElem(currElem); // jelenleg ezen áll
        currElem.AddPlayer(player);
        _Game = new Game();

        currElem.Break();

        /** objektumok hozzáadása a map-hez */

        ObjNameMap.put(player, "player:Technician");
        ObjNameMap.put(currElem, "currElem:Pump");
        ObjNameMap.put(manipulator, "manipulator:ManipulatorTechnician");
        ObjNameMap.put(_Game, ":Game");

        /** szekvencia innen */
        isLogging = true;
        PrintFunctionCall(this, "ManipulateCurrElem");
        player.ManipulateCurrElem();

        /** objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");
    }

    private void test09_Cross_Cisterns() {
        /** inicializálás */
        isLogging = false;
        ManipulatorSaboteur manipulator=new ManipulatorSaboteur();
        Player player = new Player("s1", manipulator);
        Cistern currElem = new Cistern();
        player.SetCurrElem(currElem);
        currElem.AddPlayer(player);
        _Game=new Game();
        _Game.AddPlayer(player);
        Network network = new Network();
        Cistern targetElem = new Cistern();
        _Game.setNetwork(network);
        network.AddCistern(currElem);
        network.AddCistern(targetElem);

        Player playerOnTarget=new Player("s2");
        playerOnTarget.SetCurrElem(targetElem);
        targetElem.AddPlayer(playerOnTarget);

        /** objektumok hozzáadása a map-hez */

        ObjNameMap.put(player, "player:Player");
        ObjNameMap.put(currElem, "currElem:Cistern");
        ObjNameMap.put(manipulator, "manipulator:ManipulatorSaboteur");
        ObjNameMap.put(network, "network:Network");
        ObjNameMap.put(targetElem, "targetElem:Cistern");
        ObjNameMap.put(_Game, ":Game");

        /** szekvencia innen */
        isLogging = true;
        PrintFunctionCall(this, "ManipulateCurrElem");
        player.ManipulateCurrElem();

        /** objektumok eltávolítás a map-ből */

        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");
    }

    private void test10_Break_not_broken_Pipe() {
        /** inicializálás */
        isLogging = false;
        ManipulatorSaboteur manipulator = new ManipulatorSaboteur();
        Player player = new Player("s1", manipulator);
        Pipe currElem = new Pipe();
        currElem.AddPlayer(player);
        player.SetCurrElem(currElem);
        _Game = new Game();
        _Game.AddPlayer(player);

        /** objektumok hozzáadása a map-hez */

        ObjNameMap.put(player, "player:Player");
        ObjNameMap.put(currElem, "currElem:Pipe");
        ObjNameMap.put(manipulator, "manipulator:ManipulatorSaboteur");
        ObjNameMap.put(_Game, ":Game");

        /** szekvencia innen */

        isLogging = true;
        PrintFunctionCall(this, "ManipulateCurrElem");
        player.ManipulateCurrElem();

        /** objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");
    }

    private void test11_Break_broken_Pipe() {
        /** inicializálás */
        isLogging = false;
        ManipulatorSaboteur manipulator=new ManipulatorSaboteur();
        Player player=new Player("s1",manipulator);
        Pipe currElem= new Pipe();
        currElem.AddPlayer(player);
        player.SetCurrElem(currElem);
        currElem.Break();


        /** objektumok hozzáadása a map-hez */
        ObjNameMap.put(player, "player:Player");
        ObjNameMap.put(currElem, "currElem:Pipe");
        ObjNameMap.put(manipulator, "manipulator:ManipulatorSaboteur");

        /** szekvencia innen */
        isLogging = true;
        Main.skeleton.PrintFunctionCall(this,"ManipulateCurrElem");
        player.ManipulateCurrElem();

        /** objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");
    }

    private void test12_Fix_not_broken_Pipe() {
        /** inicializálás */
        isLogging = false;
        ManipulatorTechnician manipulator = new ManipulatorTechnician();
        Pipe currElem = new Pipe();
        Technician playerT = new Technician("t1",manipulator);
        currElem.AddPlayer(playerT);
        playerT.SetCurrElem(currElem);

        /** objektumok hozzáadása a map-hez */

        ObjNameMap.put(playerT, "player:Technician");
        ObjNameMap.put(currElem, "currElem:Pipe");
        ObjNameMap.put(manipulator, "manipulator:ManipulatorTechnician");

        /** szekvencia innen */

        isLogging = true;
        Main.skeleton.PrintFunctionCall(this,"ManipulateCurrElem");
        playerT.ManipulateCurrElem();

        /** objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");
    }

    private void test13_Fix_broken_Pipe() {
        /** inicializálás */
        isLogging = false;
        ManipulatorTechnician manipulator = new ManipulatorTechnician();
        Pipe currElem = new Pipe();
        Technician playerT = new Technician("t1",manipulator);
        currElem.AddPlayer(playerT);
        playerT.SetCurrElem(currElem);
        _Game = new Game();
        _Game.AddPlayer(playerT);

        currElem.Break();

        /** objektumok hozzáadása a map-hez */

        ObjNameMap.put(playerT, "player:Technician");
        ObjNameMap.put(currElem, "currElem:Pipe");
        ObjNameMap.put(manipulator, "manipulator:ManipulatorTechnician");
        ObjNameMap.put(_Game, ":Game");

        /** szekvencia innen */

        isLogging = true;
        Main.skeleton.PrintFunctionCall(this,"ManipulateCurrElem");
        playerT.ManipulateCurrElem();

        /** objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");
    }

    private void test14_Relocate_relocatable_Pipe() {
        /** inicializálás */
        isLogging=false;
        Technician player= new Technician("t1");
        Pipe currElem=new Pipe();
        currElem.AddPlayer(player);
        player.SetCurrElem(currElem);
        _Game=new Game();
        Pipe nonTargetNb=new Pipe();
        Pipe targetElem = new Pipe();
        currElem.SetNb(new Direction(0),nonTargetNb);
        currElem.SetNb(new Direction(1),targetElem);
        Network network = new Network();
        network.AddPipe(targetElem);
        _Game.setNetwork(network);

        /** objektumok hozzáadása a map-hez */
        ObjNameMap.put(player, "player:Technician");
        ObjNameMap.put(currElem, "currElem:Pipe");
        ObjNameMap.put(_Game, ":Game");
        ObjNameMap.put(targetElem, "targetElem:Pipe");
        ObjNameMap.put(nonTargetNb, "nonTargetNb:Pipe");
        ObjNameMap.put(network, "network:Network");

        /** szekvencia innen */
        isLogging=true;
        Main.skeleton.PrintFunctionCall(this,"PickUpPart", "d");
        player.PickUpPart(new Direction(1));

        /** objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");
    }

    private void test15_Relocate_unrelocatable_Element() {
        /** inicializálás */
        isLogging = false;
        Technician playerT = new Technician("t1");
        Pipe currElem = new Pipe();
        currElem.AddPlayer(playerT);
        playerT.SetCurrElem(currElem);

        _Game=new Game();
        _Game.AddPlayer(playerT);

        Pipe nonTargetNb = new Pipe();
        Network network = new Network();
        Pipe targetElem = new Pipe();
        currElem.SetNb(new Direction(0),nonTargetNb);
        currElem.SetNb(new Direction(1),targetElem);
        network.AddPipe(targetElem);
        targetElem.Break();

        /** objektumok hozzáadása a map-hez */

        ObjNameMap.put(playerT, "player:Technician");
        ObjNameMap.put(currElem, "currElem:Pipe");
        ObjNameMap.put(_Game, ":Game");
        ObjNameMap.put(nonTargetNb, "nonTargetNb:Pipe");
        ObjNameMap.put(network, "network:Network");
        ObjNameMap.put(targetElem, "targetElem:Pipe");

        /** szekvencia innen */

        isLogging = true;
        Main.skeleton.PrintFunctionCall(this,"PickUpPart", "d");
        playerT.PickUpPart(new Direction(1));

        /** objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");
    }

    private void test16_Pick_up_Pump_when_possible() {
        /** inicializálás */
        isLogging = false;
        _GameI = new Game.Input();
        _Game = new Game();
        Technician playerT = new Technician("t1");
        Cistern currElem = new Cistern();
        currElem.AddPlayer(playerT);
        playerT.SetCurrElem(currElem);
        _Game.AddPlayer(playerT);

        /** objektumok hozzáadása a map-hez */
        ObjNameMap.put(_Game, ":Game");
        ObjNameMap.put(_GameI, ":GameInput");
        ObjNameMap.put(playerT, "player:Technician");
        ObjNameMap.put(currElem, "currElem:Cistern");


        /** szekvencia innen */
        isLogging = true;
        Main.skeleton.PrintFunctionCall(this, "Pickup");
        _GameI.Pickup();

        /** objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");
    }

    private void test17_Pick_up_Pump_when_impossible() {
        /** inicializálás */
        isLogging = false;
        _GameI = new Game.Input();
        _Game = new Game();
        Technician playerT = new Technician("t1");
        Cistern currElem = new Cistern();
        currElem.AddPlayer(playerT);
        playerT.SetCurrElem(currElem);
        _Game.AddPlayer(playerT);
        playerT.SetPart(new Pipe());

        /** objektumok hozzáadása a map-hez */
        ObjNameMap.put(_GameI, ":GameInput");
        ObjNameMap.put(_Game, ":Game");
        ObjNameMap.put(playerT, "player:Technician");
        ObjNameMap.put(currElem, "currElem:Cistern");


        /** szekvencia innen */
        isLogging = true;
        Main.skeleton.PrintFunctionCall(this, "Pickup");
        _GameI.Pickup();

        /** objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");
    }

    private void test18_Place_Pipe_when_placeable() {

        /** inicializálás */
        isLogging = false;
        _GameI = new Game.Input();
        _Game = new Game();
        Technician playerT = new Technician("t1");
        Pipe currElem = new Pipe();
        Pipe storedPart = new Pipe();
        Network network = new Network();
        _Game.AddPlayer(playerT);
        _Game.setNetwork(network);
        currElem.AddPlayer(playerT);
        playerT.SetCurrElem(currElem);
        playerT.SetPart(storedPart);


        /** objektumok hozzáadása a map-hez */
        ObjNameMap.put(_GameI, ":GameInput");
        ObjNameMap.put(_Game, ":Game");
        ObjNameMap.put(playerT, "player:Technician");
        ObjNameMap.put(currElem, "currElem:Pipe");
        ObjNameMap.put(storedPart, "storedPart:Pipe");
        ObjNameMap.put(network, "network:Network");


        /** szekvencia innen */
        isLogging = true;
        Main.skeleton.PrintFunctionCall(this, "TryPartPlacement", "d");
        _GameI.TryPartPlacement(new Direction(0));

        /** objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");
    }

    private void test19_Place_Pipe_when_not_placeable() {
        /** inicializálás */
        isLogging = false;
        _GameI = new Game.Input();
        _Game = new Game();
        Technician playerT = new Technician("t1");
        Pipe currElem = new Pipe();
        Pipe storedPart = new Pipe();
        Network network = new Network();
        Pipe targetElem = new Pipe();
        _Game.AddPlayer(playerT);
        _Game.setNetwork(network);
        currElem.AddPlayer(playerT);
        playerT.SetCurrElem(currElem);
        playerT.SetPart(storedPart);
        currElem.SetNb(new Direction(0),targetElem);


        /** objektumok hozzáadása a map-hez */
        ObjNameMap.put(_GameI, ":GameInput");
        ObjNameMap.put(_Game, ":Game");
        ObjNameMap.put(playerT, "player:Technician");
        ObjNameMap.put(currElem, "currElem:Pipe");
        ObjNameMap.put(storedPart, "storedPart:Pipe");
        ObjNameMap.put(network, "network:Network");
        ObjNameMap.put(targetElem, "targetElem:Pipe");

        /** szekvencia innen */
        isLogging = true;
        Main.skeleton.PrintFunctionCall(this, "TryPartPlacement", "d");
        _GameI.TryPartPlacement(new Direction(0));



        /** objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");
    }

    private void test20_Place_Pump_when_placeable() {
        /** inicializálás */
        isLogging = false;
        _GameI = new Game.Input();
        _Game = new Game();
        Network network = new Network();
        Technician player = new Technician("t1");
        _Game.AddPlayer(player);
        Pipe currElem = new Pipe();
        currElem.AddPlayer(player);
        player.SetCurrElem(currElem);
        Pump storedPart = new Pump();
        player.SetPart(storedPart);
        Pipe targetElem = new Pipe();
        currElem.SetNb(new Direction(0), targetElem);
        _Game.setNetwork(network);
        network.AddPipe(targetElem);

        /** objektumok hozzáadása a map-hez */
        ObjNameMap.put(_GameI, ":GameInput");
        ObjNameMap.put(_Game, ":Game");
        ObjNameMap.put(network, "network:Network");
        ObjNameMap.put(player, "player:Player");
        ObjNameMap.put(currElem, "currElem:Pipe");
        ObjNameMap.put(storedPart, "storedPart:Pump");
        ObjNameMap.put(targetElem, "targetElem:Pipe");

        /** szekvencia innen */
        isLogging = true;
        Main.skeleton.PrintFunctionCall(this, "TryPlacement", "d");
        _GameI.TryPartPlacement(new Direction(0));

        /** objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");
    }

    private void test21_Place_Pump_when_not_placeable() {
        /** inicializálás */
        isLogging = false;
        _GameI = new Game.Input();
        _Game = new Game();
        Network network = new Network();
        Technician player = new Technician("t1");
        _Game.AddPlayer(player);
        Pipe currElem = new Pipe();
        currElem.AddPlayer(player);
        player.SetCurrElem(currElem);
        Pump storedPart = new Pump();

        player.SetPart(storedPart);
        _Game.setNetwork(network);

        /** objektumok hozzáadása a map-hez */
        ObjNameMap.put(_GameI, ":GameInput");
        ObjNameMap.put(_Game, ":Game");
        ObjNameMap.put(network, "network:Network");
        ObjNameMap.put(player, "player:Player");
        ObjNameMap.put(currElem, "currElem:Pipe");
        ObjNameMap.put(storedPart, "storedPart:Pump");

        /** szekvencia innen */
        isLogging = true;
        Main.skeleton.PrintFunctionCall(this, "TryPlacement", "d");
        _GameI.TryPartPlacement(new Direction(0));

        /** objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");
    }

    private void test22_Flood_not_broken_Pipe() {
        /** inicializálás */
        isLogging=false;
        Pipe pipe=new Pipe();
        Pipe output=new Pipe();
        _Game=new Game();
        pipe.SetOutput(output);

        /** objektumok hozzáadása a map-hez */
        ObjNameMap.put(pipe,"pipe:Pipe");
        ObjNameMap.put(output,"output:Pipe");
        ObjNameMap.put(_Game,":Pipe");

        /** szekvencia innen */
        isLogging=true;
        Main.skeleton.PrintFunctionCall(this,"Flood");
        pipe.Flood();

        /** objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");
    }

    private void test23_Flood_broken_Pipe() {
        /** inicializálás */
        isLogging = false;
        Pipe pipe = new Pipe();
        pipe.Break();
        _Game = new Game();

        /** objektumok hozzáadása a map-hez */
        ObjNameMap.put(pipe, "pipe:Pipe");
        ObjNameMap.put(_Game, ":Game");

        /** szekvencia innen */
        isLogging = true;
        Main.skeleton.PrintFunctionCall(this, "Flood");
        pipe.Flood();

        /** objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");
    }

    private void test24_Flood_not_broken_Pump() {
        /** inicializálás */
        isLogging=false;
        Pump pump=new Pump();
        Pipe output=new Pipe();
        _Game=new Game();
        pump.SetOutput(output);

        /** objektumok hozzáadása a map-hez */
        ObjNameMap.put(pump,"pump:Pump");
        ObjNameMap.put(output,"output:Pipe");
        ObjNameMap.put(_Game,":Pipe");

        /** szekvencia innen */
        isLogging=true;
        Main.skeleton.PrintFunctionCall(this,"Flood");
        pump.Flood();

        /** objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");
    }

    private void test25_Flood_broken_Pump() {
        /** inicializálás */
        isLogging = false;
        Pump pump = new Pump();
        pump.Break();

        /** objektumok hozzáadása a map-hez */
        ObjNameMap.put(pump, "pump:Pump");

        /** szekvencia innen */
        isLogging = true;
        Main.skeleton.PrintFunctionCall(this, "Flood");
        pump.Flood();

        /** objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");
    }

    private void test26_Flood_Source() {
        /** inicializálás */
        isLogging = false;
        Source source = new Source();
        Pipe nb = new Pipe();
        _Game = new Game();
        source.SetNb(new Direction(0),nb);

        /** objektumok hozzáadása a map-hez */
        ObjNameMap.put(source, "source:Source");
        ObjNameMap.put(nb, "nb:Pipe");
        ObjNameMap.put(_Game, ":Game");

        /** szekvencia innen */
        isLogging = true;
        Main.skeleton.PrintFunctionCall(this, "Flood");
        source.Flood();

        /** objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");
    }

    private void test27_Flood_Cistern() {
        /** inicializálás */
        isLogging = false;
        Cistern cistern = new Cistern();
        _Game = new Game();

        /** objektumok hozzáadása a map-hez */
        ObjNameMap.put(cistern, "cistern:Cistern");
        ObjNameMap.put(_Game, ":Game");

        /** szekvencia innen */
        isLogging = true;
        Main.skeleton.PrintFunctionCall(this, "Flood");
        cistern.Flood();

        /** objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");
    }

    private void test28_Produce_Pipe() {

        /** inicializálás */
        isLogging = false;
        Cistern cistern = new Cistern();
        Pipe nb0 = new Pipe();
        Pipe nb1 = new Pipe();
        cistern.SetNb(new Direction(0),nb0);
        cistern.SetNb(new Direction(1),nb1);

        /** objektumok hozzáadása a map-hez */
        ObjNameMap.put(cistern, "cistern:Cistern");
        ObjNameMap.put(nb0, "nb0:Pipe");
        ObjNameMap.put(nb1, "nb1:Pipe");


        /** szekvencia innen */
        isLogging = true;
        Main.skeleton.PrintFunctionCall(this, "ProducePipe");
        cistern.ProducePipe();



        /** objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");

    }

    private void test29_Impair_Pump() {
        /** inicializálás */
        isLogging = false;
        Pump pump = new Pump();

        /** objektumok hozzáadása a map-hez */
        ObjNameMap.put(pump, "pump:Pump");

        /** szekvencia innen */
        isLogging = true;
        Main.skeleton.PrintFunctionCall(this, "Break");
        pump.Break();

        /** objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");
    }
}