package org.runtimeerror.skeleton;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

import org.runtimeerror.controller.Game;
import org.runtimeerror.model.players.ManipulatorSaboteur;
import org.runtimeerror.model.players.ManipulatorTechnician;
import org.runtimeerror.model.players.Technician;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import org.runtimeerror.model.map.Direction;
import org.runtimeerror.model.players.Player;
import org.runtimeerror.model.map.Pipe;
import org.runtimeerror.model.map.Pump;
import org.runtimeerror.model.map.Cistern;
import org.runtimeerror.model.map.Source;
import org.runtimeerror.controller.Game.Input;

public final class SkeletonController {

    private int tabCnt = 0;
    private boolean isLogging = true;
    private Scanner in;
    private Map<Integer, String> TestNameMap = new HashMap<>(30); // Tesztszám - Tesztnév párosok
    private Map<Object, String> ObjNameMap = new HashMap<>(8); // Objektum - "név : típus" string párosok

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
        /* inicializálás */
        isLogging = false;
        Player player = new Player("s1"); // akit mozgatunk (szabotőr, de technikussal is ugyanígy lenne)
        Pipe currElem = new Pipe(); // a cső, amin jelenleg áll
        player.SetCurrElem(currElem); // jelenleg ezen áll
        currElem.AddPlayer(player);
        Pipe targetElem = new Pipe(); // a cső, amire léptetni fogjuk
        /* a két cső egymás szomszédja (input/output beállítása lényegtelen ehhez a teszthez): */
        currElem.SetNb(new Direction(0), targetElem);
        targetElem.SetNb(new Direction(3), currElem);

        /* objektumok hozzáadása a map-hez */
        ObjNameMap.put(player, "player:Player");
        ObjNameMap.put(currElem, "currElem:Pipe");
        ObjNameMap.put(targetElem, "targetElem:Pipe");

        /* szekvencia innen */
        isLogging = true;
        PrintFunctionCall(this, "MoveTo", targetElem);
        player.MoveTo(targetElem);

        /* objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");
    }

    private void test02_Move_to_occupied_Pipe() {
        /* inicializálás */
        isLogging = false;
        Player player = new Player("s1"); // akit mozgatunk (szabotőr, de technikussal is ugyanígy lenne)
        Pipe currElem = new Pipe(); // a cső, amin jelenleg áll
        player.SetCurrElem(currElem); // jelenleg ezen áll
        currElem.AddPlayer(player);
        Pipe targetElem = new Pipe(); // a cső, amire léptetni fogjuk
        Player playerOnTarget = new Player("s2"); // a játékos, aki a cél pumpán áll
        playerOnTarget.SetCurrElem(targetElem);
        targetElem.AddPlayer(playerOnTarget);
        /* a két cső egymás szomszédja (input/output beállítása lényegtelen ehhez a teszthez): */
        currElem.SetNb(new Direction(0), targetElem);
        targetElem.SetNb(new Direction(3), currElem);

        /* objektumok hozzáadása a map-hez */
        ObjNameMap.put(player, "player:Player");
        ObjNameMap.put(currElem, "currElem:Pipe");
        ObjNameMap.put(targetElem, "targetElem:Pipe");
        ObjNameMap.put(playerOnTarget, "playerOnTarget:Player");

        /* szekvencia innen */
        isLogging = true;
        PrintFunctionCall(this, "MoveTo", targetElem);
        player.MoveTo(targetElem);

        /* objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");
    }

    private void test03_Move_to_Pump() {
        /* inicializálás */
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

        /* objektumok hozzáadása a map-hez */
        ObjNameMap.put(player, "player:Player");
        ObjNameMap.put(currElem, "currElem:Pipe");
        ObjNameMap.put(targetElem, "targetElem:Pump");
        ObjNameMap.put(playerOnTarget1, "playerOnTarget:Player1");
        ObjNameMap.put(playerOnTarget2, "playerOnTarget:Player2");

        /* szekvencia innen */
        isLogging = true;
        PrintFunctionCall(this, "MoveTo", targetElem);
        player.MoveTo(targetElem);

        /* objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");
    }

    private void test04_Move_to_Cistern() {
        /* inicializálás */
        isLogging = false;
        Player player = new Player("s1"); // akit mozgatunk (szabotőr, de technikussal is ugyanígy lenne)
        Pipe currElem = new Pipe(); // a pumpa, amin jelenleg áll
        player.SetCurrElem(currElem); // jelenleg ezen áll
        currElem.AddPlayer(player);
        Cistern targetElem = new Cistern(); // a ciszterna, amire léptetni fogjuk

        Player playerOnTarget = new Player("s2"); // a játékos, aki a cél ciszternán áll
        playerOnTarget.SetCurrElem(targetElem);
        targetElem.AddPlayer(playerOnTarget);
        /* objektumok hozzáadása a map-hez */

        ObjNameMap.put(player, "player:Player");
        ObjNameMap.put(currElem, "currElem:Pipe");
        ObjNameMap.put(targetElem, "targetElem:Cistern");
        ObjNameMap.put(playerOnTarget, "playerOnTarget:Player");

        /* szekvencia innen */

        isLogging = true;
        PrintFunctionCall(this, "MoveTo", targetElem);
        player.MoveTo(targetElem);

        /* objektumok eltávolítás a map-ből */

        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");

    }

    private void test05_Move_to_Source() {
        /* inicializálás */
        isLogging = false;
        Player player = new Player("s1"); // akit mozgatunk (szabotőr, de technikussal is ugyanígy lenne)
        Pipe currElem = new Pipe(); // a pumpa, amin jelenleg áll
        player.SetCurrElem(currElem); // jelenleg ezen áll
        currElem.AddPlayer(player);
        Source targetElem = new Source(); // a ciszterna, amire léptetni fogjuk

        /* objektumok hozzáadása a map-hez */

        ObjNameMap.put(player, "player:Player");
        ObjNameMap.put(currElem, "currElem:Pipe");
        ObjNameMap.put(targetElem, "targetElem:Source");

        /* szekvencia innen */
        isLogging = true;
        PrintFunctionCall(this, "MoveTo", targetElem);
        player.MoveTo(targetElem);

        /* objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");

    }

    private void test06_Fix_broken_Pump() {
        /* inicializálás */
        isLogging=true;
        ManipulatorTechnician manipulator = new ManipulatorTechnician();
        Technician player = new Technician("t1",manipulator);
        Pump currElem = new Pump(); // a pumpa, amin jelenleg áll
        player.SetCurrElem(currElem); // jelenleg ezen áll
        currElem.AddPlayer(player);
        Game game = new Game();
        game.AddPlayer(player);
        currElem.Break();

        /* objektumok hozzáadása a map-hez */

        ObjNameMap.put(player, "player:Player");
        ObjNameMap.put(currElem, "currElem:Pump");
        ObjNameMap.put(manipulator, "manipulator:ManipulatorTechnician");
        ObjNameMap.put(game, ":Game");

        /* szekvencia innen */
        isLogging = true;
        PrintFunctionCall(this, "ManipulateCurrElem");
        player.ManipulateCurrentElement();

        /* objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");
    }

    private void test07_Change_directions_of_not_broken_Pump() {
        /* inicializálás */
        isLogging = false;
        ManipulatorSaboteur manipulatorS = new ManipulatorSaboteur();
        Player playerS = new Player("s1",manipulatorS);
        Pump currElem = new Pump();
        playerS.SetCurrElem(currElem);
        currElem.AddPlayer(playerS);
        _Game = new Game();

        /* objektumok hozzáadása a map-hez */

        /* szekvencia innen */
        isLogging=true;
        PrintFunctionCall(this, "ManipulateCurrElem");
        playerS.ManipulateCurrentElement();




        /* objektumok eltávolítás a map-ből */
        ObjNameMap.clear();
        ObjNameMap.put(this, ":SkeletonController");


    }

    private void test08_Change_directions_of_broken_Pump() {
        throw new NotImplementedException();
        /* inicializálás */

        /* objektumok hozzáadása a map-hez */

        /* szekvencia innen */

        /* objektumok eltávolítás a map-ből */
    }

    private void test09_Cross_Cisterns() {
        throw new NotImplementedException();
        /* inicializálás */

        /* objektumok hozzáadása a map-hez */

        /* szekvencia innen */

        /* objektumok eltávolítás a map-ből */
    }

    private void test10_Break_not_broken_Pipe() {
        throw new NotImplementedException();
        /* inicializálás */

        /* objektumok hozzáadása a map-hez */

        /* szekvencia innen */

        /* objektumok eltávolítás a map-ből */
    }

    private void test11_Break_broken_Pipe() {
        throw new NotImplementedException();
        /* inicializálás */

        /* objektumok hozzáadása a map-hez */

        /* szekvencia innen */

        /* objektumok eltávolítás a map-ből */
    }

    private void test12_Fix_not_broken_Pipe() {
        throw new NotImplementedException();
        /* inicializálás */

        /* objektumok hozzáadása a map-hez */

        /* szekvencia innen */

        /* objektumok eltávolítás a map-ből */
    }

    private void test13_Fix_broken_Pipe() {
        throw new NotImplementedException();
        /* inicializálás */

        /* objektumok hozzáadása a map-hez */

        /* szekvencia innen */

        /* objektumok eltávolítás a map-ből */
    }

    private void test14_Relocate_relocatable_Pipe() {
        throw new NotImplementedException();
        /* inicializálás */

        /* objektumok hozzáadása a map-hez */

        /* szekvencia innen */

        /* objektumok eltávolítás a map-ből */
    }

    private void test15_Relocate_unrelocatable_Element() {
        throw new NotImplementedException();
        /* inicializálás */

        /* objektumok hozzáadása a map-hez */

        /* szekvencia innen */

        /* objektumok eltávolítás a map-ből */
    }

    private void test16_Pick_up_Pump_when_possible() {
        throw new NotImplementedException();
        /* inicializálás */

        /* objektumok hozzáadása a map-hez */

        /* szekvencia innen */

        /* objektumok eltávolítás a map-ből */
    }

    private void test17_Pick_up_Pump_when_impossible() {
        throw new NotImplementedException();
        /* inicializálás */

        /* objektumok hozzáadása a map-hez */

        /* szekvencia innen */

        /* objektumok eltávolítás a map-ből */
    }

    private void test18_Place_Pipe_when_placeable() {
        throw new NotImplementedException();
        /* inicializálás */

        /* objektumok hozzáadása a map-hez */

        /* szekvencia innen */

        /* objektumok eltávolítás a map-ből */
    }

    private void test19_Place_Pipe_when_not_placeable() {
        throw new NotImplementedException();
        /* inicializálás */

        /* objektumok hozzáadása a map-hez */

        /* szekvencia innen */

        /* objektumok eltávolítás a map-ből */
    }

    private void test20_Place_Pump_when_placeable() {
        throw new NotImplementedException();
        /* inicializálás */

        /* objektumok hozzáadása a map-hez */

        /* szekvencia innen */

        /* objektumok eltávolítás a map-ből */
    }

    private void test21_Place_Pump_when_not_placeable() {
        throw new NotImplementedException();
        /* inicializálás */

        /* objektumok hozzáadása a map-hez */

        /* szekvencia innen */

        /* objektumok eltávolítás a map-ből */
    }

    private void test22_Flood_not_broken_Pipe() {
        throw new NotImplementedException();
        /* inicializálás */

        /* objektumok hozzáadása a map-hez */

        /* szekvencia innen */

        /* objektumok eltávolítás a map-ből */
    }

    private void test23_Flood_broken_Pipe() {
        throw new NotImplementedException();
        /* inicializálás */

        /* objektumok hozzáadása a map-hez */

        /* szekvencia innen */

        /* objektumok eltávolítás a map-ből */
    }

    private void test24_Flood_not_broken_Pump() {
        throw new NotImplementedException();
        /* inicializálás */

        /* objektumok hozzáadása a map-hez */

        /* szekvencia innen */

        /* objektumok eltávolítás a map-ből */
    }

    private void test25_Flood_broken_Pump() {
        throw new NotImplementedException();
        /* inicializálás */

        /* objektumok hozzáadása a map-hez */

        /* szekvencia innen */

        /* objektumok eltávolítás a map-ből */
    }

    private void test26_Flood_Source() {
        throw new NotImplementedException();
        /* inicializálás */

        /* objektumok hozzáadása a map-hez */

        /* szekvencia innen */

        /* objektumok eltávolítás a map-ből */
    }

    private void test27_Flood_Cistern() {
        throw new NotImplementedException();
        /* inicializálás */

        /* objektumok hozzáadása a map-hez */

        /* szekvencia innen */

        /* objektumok eltávolítás a map-ből */
    }

    private void test28_Produce_Pipe() {
        throw new NotImplementedException();
        /* inicializálás */

        /* objektumok hozzáadása a map-hez */

        /* szekvencia innen */

        /* objektumok eltávolítás a map-ből */
    }

    private void test29_Impair_Pump() {
        throw new NotImplementedException();
        /* inicializálás */

        /* objektumok hozzáadása a map-hez */

        /* szekvencia innen */

        /* objektumok eltávolítás a map-ből */
    }
}