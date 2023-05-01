package org.runtimeerror.controller;

import org.runtimeerror.Main;
import org.runtimeerror.model.map.*;
import org.runtimeerror.model.players.Technician;
import org.runtimeerror.model.players.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A játékmenetet kezeli (vizsgálja, hogy véget ért-e a játék, valamint a köröket vezérli).
 * Számontartja és módosítja a csőrendszert (turn-önként véletlenszerűen pumpákat ront el,
 * round-onként pedig ciszternák szomszédos üres helyeire új csöveket helyez le).
 * Turn-ök elején folyatja a vizet a forrásokból a szabad végű / lyukas csövekig vagy ciszternáig,
 * illetve pontokat tud osztani a csapatoknak, amit szintén számontart.
 */
public class Game {
    /**
     * Attribútumok
     */
    private int currPlayerIdx; // a soron lévő játékos indexe a „players” gyűjteményben
    private int scoreTechnician; // a szerelők pontszáma
    private int scoreSaboteur; // a szabotőrök pontszáma
    private List<Player> players; // a játékban résztvevő játékosok
    private Network network; // a pálya elemeit tárolja, szortírozza

    private static Game singleInstance; // a játék egyetlen példánya (Singleton)
    private Random random; // véletlenszerű viselkedés lesz megvalósítva vele
    private boolean deterministic; // megadja, hogy a modell véletlenszerű viselkedése be van-e kapcsolva

    static {
        singleInstance = null;
    }

    /**
     * Konstruktor (privát láthatóságú, mert Singleton osztály).
     * A Reset függvényt hívja, ami alapállapotba helyezi a játékot.
     */
    private Game() {
        Reset();
    }

    /**
     * Visszaadja a játék egyetlen példányát. (Első híváskor hozza létre.)
     */
    public static Game GetInstance() {
        if (singleInstance == null)
            singleInstance = new Game();

        return singleInstance;
    }

    /**
     * Alapállapotba (egyetlen forrás, nem lesznek játékosok és kiosztott pontok, stb.) állítja az egész játékot.
     */
    public void Reset() {
        currPlayerIdx   = 0; // a soron lévő játékos indexe a „players” gyűjteményben
        scoreTechnician = 0; // a szerelők pontszáma
        scoreSaboteur   = 0; // a szabotőrök pontszáma
        players         = new ArrayList<>(6); // a játékban résztvevő játékosok
        network         = new Network(); // a pálya elemeit tárolja, szortírozza
        network.AddSource(new Source()); // csak egyetlen forrást tartalmaz kezdetben a pálya
        random         = new Random(); // véletlenszerű viselkedés lesz megvalósítva vele
    }

    /** Átadott számú pontot ad a szerelőknek. */
    public void AddTechnicianPoints(int points) {
        scoreTechnician += points;
    }

    /** Átadott számú pontot ad a szabotőröknek. */
    public void AddSaboteurPoints(int points) {
        scoreSaboteur += points;
    }

    /** A következő játékos turn-jét indítja el (véletlenszerűen pumpákat ront el, csökkenti a ragadós/csúszós elemek
     időszámlálóját alaphelyzetbe állítva őket a lejártával, áramoltatja a vizet, pontokat oszt a csapatoknak,
     és ha véget ért egy teljes kör (round), akkor új egy-egy új csövet teremt a ciszternák üres szomszédjai helyén).
     Ha az egyik csapat elérte a győzelemhez szükséges pontok számát, véget vet a játéknak. */
    public void NextTurn() {
        for (Pump pump : network.GetPumps()) { // végigmegyünk a pálya összes pumpáján
            if (shouldBreakPipe())
                pump.Break(); // elrontunk néhányat, ha be van kapcsolva a véletlenszerű viselkedés
        }

        for (Pipe pipe : network.GetPipes()) { // végigmegyünk a pálya összes csövén
            int counter = pipe.GetCounter();
            if (counter > 0) { // ha jelenleg csúszós vagy ragadós
                if (counter == 1) { // ha pont most járna le az időszámlálója
                    pipe.SetSticky(false); // már ne legyen ragadós
                    pipe.SetSlippery(false); // már ne legyen csúszós
                }
                pipe.SetCounter(counter - 1); // csökkentjük az időszámlálót
            }
        }

        network.Flood(); // elárasztjuk a pályát vízzel
    }

    /** Visszaadja a játékost, aki éppen soron van (akinek turnje van jelenleg). */
    public Player GetCurrPlayer() {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionReturned("GetCurrPlayer", "player");
        return players.get(currPlayerIdx);
    }

    /** Igazat ad vissza, ha éppen szerelő jön az adott körben. */
    public boolean IsTechnicianTurn() {
        Main.skeleton.PrintFunctionCalled(this);
        boolean re=currPlayerIdx%2==0;
        Main.skeleton.PrintFunctionReturned("IsTechnicianTurn", re ? "true" : "false");
        return re;
    }

    /** Visszaadja a pálya elemeit számontartó objektumot. */
    public Network GetNetwork() {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionReturned("GetNetwork","network");
        return network;
    }

    /** Inner-class. A felhasználót és a játékot köti össze. Irányokat / célpontokat tud bekérni a felhasználótól.
     Játékosmozgatást, cső- és pumpalehelyezést, illetve csőfelvevést kezdeményezhet vele. */
    public static class Input {

        /**
         *
         */
        public void EndGame() {

            System.out.println("");
        }

        /** d irányba próbálja mozgatni az épp soron lévő játékost, arról az elemről, amelyiken éppen tartózkodik. */
//        public void MoveCurrPlayer(Direction d) {
//            throw new NotImplementedException();
//        }
//
//        /** Akkor hívandó függvény, amikor éppen azt az elemet szeretné manipulálni / interakcióba lépni vele
//         (csövet lyukasztani / foltozni, pumpát javítani / átállítani vagy a következő ciszternára ugrani)
//         a soron lévő játékos, amelyiken éppen áll. */
//        public void TryCurrElemManipulation() {
//            throw new NotImplementedException();
//        }
//
//        /** Akkor hívandó függvény, amikor a soron lévő játékos megpróbál egy tőle d irányba lévő szomszédos csövet
//         felvenni, és a tárolójában eltárolni. */
//        public void TryPartRelocation(Direction d) {
//            throw new NotImplementedException();
//        }

        /** Akkor hívandó függvény, amikor a soron lévő játékos megpróbálja a tárolt part-ját tőle d irányba
         lehelyezni. */
        public void TryPartPlacement(Direction d) {
            Main.skeleton.PrintFunctionCalled(this);

            Main.skeleton.PrintFunctionCall(this, "IsTechnicianTurn");
            boolean techTurn = _Game.IsTechnicianTurn();

            if (techTurn) {
                Main.skeleton.PrintFunctionCall(this, "GetCurrPlayer");
                Technician playerT = (Technician) _Game.GetCurrPlayer();

                Main.skeleton.PrintFunctionCall(this, "GetPart");
                Breakable part = playerT.GetPart();
                if(part != null) {
                    Main.skeleton.PrintFunctionCall(this, "PlacePart","d");
                    boolean b = playerT.PlacePart(d);
                    if(b){
                        Main.skeleton.PrintFunctionCall(this,"SetPart", "null");
                        playerT.SetPart(null);


                        Main.skeleton.PrintFunctionCall(this,"NextTurn");
                        _Game.NextTurn();
                    }
                }

            }
            Main.skeleton.PrintFunctionReturned("TryPartPlacement", "");
        }

        /** Akkor hívandó függvény, amikor egy játékos egy part-ot próbálna a tárolójába tenni
         (nem a játéktérről, hanem közvetlenül odaadva). Ez csak akkor lesz sikeres, ha a játékos szerelő,
         üres a tárolója, és az elem amin áll, támogat ilyen funkcionalitást. */
        public void Pickup() {
            Main.skeleton.PrintFunctionCalled(this);

            Main.skeleton.PrintFunctionCall(this,"IsTechnicianTurn");
            boolean techTurn = _Game.IsTechnicianTurn();

            if(techTurn) {
                Main.skeleton.PrintFunctionCall(this, "GetCurrPlayer");
                Technician playerT = (Technician) _Game.GetCurrPlayer();

                Main.skeleton.PrintFunctionCall(this, "GetPart");
                Breakable part = playerT.GetPart();

                if(part == null) {

                    Main.skeleton.PrintFunctionCall(this, "GetCurrElem");
                    Element currElem = playerT.GetCurrElem();

                    Main.skeleton.PrintFunctionCall(this, "OnPickup");
                    currElem.OnPickup();

                }

            }
            Main.skeleton.PrintFunctionReturned("Pickup","");
        }

        /** Pumpa átállításához visszaadja a soron lévő játékos által bevitt irányokat. */
        public Direction[] GetNewPumpDirections() {
            Main.skeleton.PrintFunctionCalled(this);

            Direction[] dirs= new Direction[] {
                    new Direction(1),
                    new Direction(2)
            };

            Main.skeleton.PrintFunctionReturned("GetNewPumpDirections", "dirs");
            return dirs;
        }
    }


    /**
     * Megadja, hogy egy tetszőleges cső el legyen-e törve (logikai értéked ad vissza).
     */
    private boolean shouldBreakPipe() {
        if (deterministic)
            return false;

        return random.nextInt(100) < 2; // 2% esély
    }

    /** --------- CSAK A JÁTÉK INICIALIZÁLÁSKOR HASZNÁLT FÜGGVÉNYEK (nincs hibakezelés bennük): --------- */

    /** Hozzáad a játékhoz egy játékost. */
    public void AddPlayer(Player p) {
        Main.skeleton.PrintFunctionCalled(this);
        players.add(p);
        Main.skeleton.PrintFunctionReturned("AddPlayer", "");
    }
}