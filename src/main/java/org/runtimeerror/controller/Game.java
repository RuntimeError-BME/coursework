package org.runtimeerror.controller;

import org.runtimeerror.model.map.*;
import org.runtimeerror.model.players.Player;
import org.runtimeerror.prototype.PrototypeController;

import java.io.PrintStream;
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
public final class Game {
    /**
     * Attribútumok
     */
    private int currPlayerIdx; // a soron lévő játékos indexe a „players” gyűjteményben
    private int scoreTechnician; // a szerelők pontszáma
    private int scoreSaboteur; // a szabotőrök pontszáma
    private static int maxScore = 50; // a győzelemhez szükséges pontok száma
    private List<Player> players; // a játékban résztvevő játékosok
    private Network network; // a pálya elemeit tárolja, szortírozza

    private static Game singleInstance; // a játék egyetlen példánya (Singleton)
    private Random random; // véletlenszerű viselkedés lesz megvalósítva vele
    private boolean deterministic; // megadja, hogy a modell véletlenszerű viselkedése be van-e kapcsolva
    private static int defaultCounter = 2; // kikapcsolt véletlenszerű viselkedésnél ennyi ideig maradnak csúszósak/ragadósak a csövek

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
        random          = new Random(); // véletlenszerű viselkedés lesz megvalósítva vele
        deterministic   = false;
        defaultCounter  = 2; // alapértelmezett számláló visszaállítása
        maxScore        = 50; // győzelemhez szükséges pontszám visszaállítása
        network         = new Network(); // a pálya elemeit tárolja, szortírozza
        Element.Reset(); // az elemek indexelésének visszaállítása
    }

    /** Visszaadja a pálya elemeit számontartó objektumot.
     * Az inicializáláshoz használt függvény, hiszen a visszaadott Network-ön hívhatjuk a hozzáadó metódusokat. */
    public Network GetNetwork() {
        return network;
    }

    /** A játék inicializálásához használt függvény.
     * Hozzáad a játékhoz (azon belül a players listába) egy játékost. A játékos az átadott indexű elemen fog állni.
     * FIGYELEM! A hívó felelőssége, hogy CSAK a következő sorrendben adjon hozzá játékosokat a játékhoz a
     * segítségével: szerelő, szabotőr, szerelő, ... */
    public void AddPlayer(Player p, int elemIdx) {

        Element e = null; // erre az elemre fog kerülni
        for (Element element : network.GetElements()) { // megkeressük az adott indexű elemet
            if (element.GetIdx() == elemIdx) {
                e = element;
                break;
            }
        }
        if (e == null)
            return;

        e.AddPlayer(p); // hozzáadjuk az elemhez a játékost
        players.add(p); // hozzáadjuk a játékhoz a játékost (a játékosok listájába)

        if (PrototypeController.IsLogging())
            PrototypeController.PrintLine(p + " was added to element " + elemIdx + "\n");
    }

    /** Átadott számú pontot ad a szerelőknek. */
    public void AddTechnicianPoints(int points) {
        scoreTechnician += points;
    }

    /** Átadott számú pontot ad a szabotőröknek. */
    public void AddSaboteurPoints(int points) {
        scoreSaboteur += points;
    }

    /** A következő játékos turn-jét indítja el (véletlenszerűen pumpákat ront el (nemdeterminisztikus/véletlenszerű
     * viselkedés esetén), csökkenti a ragadós/csúszós elemek időszámlálóját alaphelyzetbe állítva őket a lejártával,
     * áramoltatja a vizet, pontokat oszt a csapatoknak, és ha véget ért egy teljes kör (round), akkor új egy-egy új
     * csövet teremt a ciszternák üres szomszédjai helyén).
     * Ha a játékos jelenleg egy ragadós elemen áll, akkor egyből véget is fog érni a köre (önmagát hívja a függvény).
     * Ha az egyik csapat elérte a győzelemhez szükséges pontok számát, véget vet a játéknak, és közli az eredményt. */
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
                    // megjegyzés: egyszerre csak az egyik lehet, az időszámláló közös a két állapotra
                }
                pipe.SetCounter(counter - 1); // csökkentjük az időszámlálót
            }
        }

        network.Flood(); // elárasztjuk a pályát vízzel (ez a függvény osztja közvetetten a pontokat is)

        // TODO: display the updated map in the GUI

        if (Input.IsGameOver()) { // ellenőrzi, hogy véget ért-e a játék, és közli, ha igen
            PrototypeController.GetInstance().SetGameOver(true);
            return; // ha véget ért a játék, már nem jelezzük ki a következő játékos körét
        }


        ++currPlayerIdx; // következő játékos jön
        if (currPlayerIdx >= players.size()) { // ha véget ért egy teljes kör (round)
            currPlayerIdx = 0; // akkor újra a kezdő játékos jön
            network.ProducePipesAroundCisterns(); // csövek lehelyezése a ciszternák körül, amennyiben lehetséges
        }

        PrototypeController.PrintLine(GetInstance().GetTurnInfo()); // kiírjuk a jelenlegi kör adatait

        // TODO: ezt majd a GUI felületére
        if (GetCurrPlayer().GetCurrElem().GetSticky()) { // ha a következő játékos alatt (még) ragadós cső van
            PrototypeController.PrintLine("");
            NextTurn(); // akkor egyből véget is ér a köre
        }
    }

    /** Visszaadja a játékost, aki éppen soron van (akinek turn-je van jelenleg). */
    public Player GetCurrPlayer() {
        return players.get(currPlayerIdx);
    }

    /**
     * Visszaadja a szerelők pontszámát.
     */
    public int GetTechnicianScore() {
        return scoreTechnician;
    }

    /**
     * Visszaadja a szabotőrök pontszámát.
     */
    public int GetSaboteurScore() {
        return scoreSaboteur;
    }


    /**
     * Visszaadja a győzelemhez szükséges pontok számát.
     */
    public static int GetMaxScore() { return maxScore; }

    /**
     * Szövegként adja vissza a jelenlegi kör adatait (aktuális játékos neve, szabotőrök és szerelők pontszáma).
     */
    public String GetTurnInfo() {
        return "it's " + GetCurrPlayer().GetName() + "'s turn!" +
               " (Points: saboteurs: " + GetSaboteurScore() + ", technicians: " + GetTechnicianScore() + ")";
    }

    /** Igazat ad vissza, ha éppen szerelő jön az adott körben.
     * FIGYELEM! Nagyon fontos, a függvény működéséhez, hogy az AddPlayer() függvénnyel a következő sorrendnek
     * megfelelően legyen hozzáadva a játékhoz a játékosok: szerelő, szabotőr, szerelő, ... */
    public boolean IsTechnicianTurn() {
        return currPlayerIdx % 2 == 0;
    }

    /**
     * Visszaadja, hogy a játék determinisztikus-e (nincsen bekapcsolva a véletlenszerű működés).
     */
    public boolean GetDeterministic() {
        return deterministic;
    }

    /**
     * Az átadott logikai érték szerint állítja be, hogy a játék determinisztikus lesz-e
     * (nem lesz-e benne véletlenszerű viselkedés).
     */
    public void SetDeterministic(boolean randomTurnedOff) {
        deterministic = randomTurnedOff;

        if (PrototypeController.IsLogging()) {
            String state = randomTurnedOff ? "off" : "on";
            PrototypeController.PrintLine("random behaviour was turned " + state + "\n");
        }
    }

    /** Inner-class. A felhasználót és a játékot köti össze. Irányokat / célpontokat tud bekérni a felhasználótól.
     Játékosmozgatást, cső- és pumpalehelyezést, illetve csőfelvevést kezdeményezhet vele.
     Azt is be tudja kérni a játékostól, hogy milyen módon szeretné manipulálni a csövet, amin éppen áll (tönkre tenni,
     csúszóssá tenni, ragadóssá tenni). A játék végét is ő kommunikálja a játékosok felé. */
    public static class Input {

        /**
         * Visszaadja, hogy véget ért-e a játék (egyik csapat elérte-e a győzelemhez szükséges pontszámot).
         * Közli a játék végét, ha az megtörtént. Kiírja, hogy melyik csapat nyert, és hány ponttal, illetve a
         * döntetlen helyzetet is (ha a két csapat egy körön belül lépi túl a győzelemhez szükséges pontszámot).
         */
        public static boolean IsGameOver() {

            int scrTech = GetInstance().GetTechnicianScore(); // szerelők pontszáma
            int scrSab = GetInstance().GetSaboteurScore(); // szabotőrök pontszáma
            int scrMax = GetMaxScore(); // a győzelemhez szükséges pontok száma

            if (scrTech < scrMax && scrSab < scrMax)
                return false; // ha egyik csapat sem érte el a szükséges pontszámot, nem történik semmi, jelezzük

            // feltételezzük, hogy a szabotőrök nyertek
            String notif = "A szabotőrök nyertek " + scrSab + " ponttal. (Szerelők pontszáma: " + scrTech + ")";

            if (scrTech >= scrMax && scrSab >= scrMax) { // ha mindkét csapat túllépte, akkor döntetlen
                notif = "A játék döntetlen, mert mindkét csapat ugyanabban a körben lépte át a " +
                        "győzelemhez szükséges pontszámot." +
                        "(Szerelők pontszáma: " + scrTech + ", szabotőrök pontszáma:" + scrSab + ")";
            } else if (scrTech > scrSab) { // ha a technikusok pontszáma több, akkor ők nyertek
                notif = "A szerelők nyertek " + scrTech + " ponttal. (Szabotőrök pontszáma: " + scrSab + ")";
            }

            PrototypeController.PrintLine(notif);
            // TODO: majd message box feldobása vagy vmi GUI-ban

            PrototypeController.GetInstance().SetGameOver(true); // jelzünk a proto kontrollernek, hogy véget ért
            // TODO: GUI-ban más jelzésre lehet majd szükség

            return true;
        }

        /** az átadott elemre mozgatja az épp soron lévő játékost, arról az elemről, amelyiken éppen tartózkodik.
         * (Hívja GetCurrPlayer()-t, és rajta StepOnto()-t az átadott elemmel.) */
        public static void MoveCurrPlayer(Element e) {
            GetInstance().GetCurrPlayer().StepOnto(e);
        }

        /** az átadott indexű elemre mozgatja az épp soron lévő játékost, arról az elemről, amelyiken éppen
         * tartózkodik. */
        public static void MoveCurrPlayer(int elemIdx) {
            Element e = null;
            for (Element element : GetInstance().GetNetwork().GetElements()) {
                if (element.GetIdx() == elemIdx) {
                    e = element;
                    break;
                }
            }
            if (e != null) {
                MoveCurrPlayer(e);
            }
        }

        /** Akkor hívandó függvény, amikor éppen azt az elemet szeretné manipulálni / interakcióba lépni vele a
         soron lévő játékos, amelyiken éppen áll.
         (Csövet lyukasztani / foltozni / ragadósság/csúszóssá tenni, pumpát javítani / átállítani vagy a következő
         ciszternára ugrani.)
         Hívja GetCurrPlayer()-t, és rajta a ManipulateCurrElem() metódust. */
        public static void TryCurrElemManipulation() {
            GetInstance().GetCurrPlayer().ManipulateCurrElem();
        }

        /** Akkor hívandó függvény, amikor a soron lévő játékos megpróbál egy szomszédos csövet
         felvenni, és a tárolójában eltárolni.
         Megnézi, hogy egy szerelő köre van-e jelenleg (IsTechnicianTurn()), és ha igen, akkor hívja GetCurrPlayer()
         -t, és rajta pedig PickUpPart()-ot az elemmel. */
        public static void TryPartRelocation(Element e) {
            if (GetInstance().IsTechnicianTurn()) {
                GetInstance().GetCurrPlayer().PickUpPart(e);
            }
        }

        /** Akkor hívandó függvény, amikor a soron lévő játékos megpróbálja a tárolt part-ját lehelyezni.
         Ehhez a jelenlegi játékosnak szerelőnek kell lennie, kell hogy legyen a tárolójában egy tárolt
         elemnek (cső esetén nem lehet több mint 2 szomszéd). */
        public static void TryPartPlacement(Element e) {
            if (!GetInstance().IsTechnicianTurn()) // ha nem egy szerelő köre van most (egy szabotőr köre van most)
                return; // akkor nem fog semmi történni (hiszen ők nem képesek erre)

            Player player = GetInstance().GetCurrPlayer(); // a jelenlegi játékos (aki biztos egy szerelő)
            Element storedPart = player.GetPart(); // a szerelő tárolójában lévő elem
            if (storedPart == null) // ha nincs nála elem, akkor
                return; // akkor nem fog semmi történni

            //TODO: get neighbouring element from player (IN GUI stage)
            boolean successfulPlacement = player.PlacePart(e); // megpróbálja elhelyezni
            if (successfulPlacement) { // ha sikerült elhelyezni
                player.SetPart(null); // akkor már nem a tárolójában lesz az elem (hanem a pályán)
                GetInstance().NextTurn(); // és véget ér a szerelő köre
            }
        }

        /** Akkor hívandó függvény, amikor egy játékos egy part-ot próbálna a tárolójába tenni
         (nem a játéktérről, hanem közvetlenül odaadva - pl. ciszternánál egy új pumpát).
         Ez csak akkor lesz sikeres, ha a játékos szerelő, üres a tárolója, és az elem amin áll, támogat ilyen
         funkcionalitást (felülírja megfelelő megvalósítással az OnPickup() fv-t). */
        public static void Pickup() {
            if (!GetInstance().IsTechnicianTurn()) // ha nem egy szerelő köre van most (egy szabotőr köre van most)
                return; // akkor nem fog semmi történni (hiszen ők nem képesek elemeket tárolni)

            Player player = GetInstance().GetCurrPlayer(); // a jelenlegi játékos (aki biztos egy szerelő)
            Element storedPart = player.GetPart(); // a szerelő tárolójában lévő elem

            if (storedPart == null) { // ha nincs a szerelő tárolójában elem, csak akkor kaphat potenciálisan egy újat
                Element currElem = player.GetCurrElem(); // az elem, amin a játékos éppen áll
                currElem.OnPickup(); // meghívjuk azt a metódust az elemen, amelyik képes új elemet adni a játékosnak
                // megjegyzés: ezt a metódust csak Cistern írja felül, és csak ő ad pumpát. Más elem nem ad elemet.
            }
        }

        /** Pumpa átállításához visszaadja a soron lévő játékos által bevitt irányokat. */
        public static Element[] GetNewPumpDirections() {

            // TODO: GUI-nál dialogue-gal lesz bekérve a két irány ehelyett
            String line = PrototypeController.GetCurrLine(); // a jelenlegi parancs sora szövegként
            String[] splitted = line.split(" ");
            int output = Integer.parseInt(splitted[splitted.length - 1]);
            int input = Integer.parseInt(splitted[splitted.length - 3]);

            Network network = Game.GetInstance().GetNetwork();
            return new Element[] {
                network.GetElement(input),
                network.GetElement(output)
            };
        }

        /**
         * Akkor hívandó függvény, amikor egy játékos egy csövet próbálna manipulálni.
         * Bekéri a játékostól, hogy mit szeretne vele tenni (csúszóssá tenni, ragadóssá tenni vagy kilyukasztani),
         * és ennek megfelelő értéket ad vissza (lásd Harm enumeráció).
         * Ha éppen egy szerelő köre van, akkor a csúszóssá tevés lehetőségét nem ajánlja fel, hiszen erre nem
         * képesek a szerelők. Ha pedig az átadott "canBeBroken" paraméter hamis, akkor nem ajánlja fel a lyukasztás
         * lehetőségét sem (ha még nem járt le a megjavítás utáni counter, amíg sérthetetlen - ezt a feltételt a
         * hívóoldalon kell megfogalmazni, és átadni neki).
         */
        public static Harm GetPipeHarm(boolean canBeBroken) {

            // TODO: GUI-nál dialogue-gal lesz bekérve ehelyett
            String line = PrototypeController.GetCurrLine(); // a jelenlegi parancs sora szövegként
            Harm harm;

            if (GetInstance().IsTechnicianTurn()) { // ha egy szerelőnek van most köre
                harm = Harm.STICKY; // feltételezzük, hogy ragadóssá szeretné tenni
                if (line.equals("break") && canBeBroken)
                    harm = Harm.BROKEN;
            } else { // ha egy szabotőrnek van most köre
                harm = Harm.BROKEN; // feltételezzük, hogy ki szeretné lyukasztani
                if (!canBeBroken) // ha nem lehet kilyukasztani
                    harm = Harm.SLIPPY; // akkor azt feltételezzük, hogy csúszóssá szeretné tenni

                if (line.equals("stickify"))
                    harm = Harm.STICKY;
                else if (line.equals("slippify"))
                    harm = Harm.SLIPPY;
            }
            return harm;
        }
    }


    /**
     * Megadja, hogy egy tetszőleges cső ellegyen-e törve (logikai értéked ad vissza).
     */
    private boolean shouldBreakPipe() {
        if (deterministic)
            return false;

        return random.nextInt(100) < 2; // 2% esély
    }

    /** Egy véletlenszerűen sorsolt logikai értéket ad vissza, ami megadja, hogy csúszós elemre lépés esetén,
     * amikor nemdeterminisztikusan viselkedik a modell, akkor a magasabb indexű szomszédra, vagy a kisebb
     * indexűre csússzon a játékos. Pipe.AddPlayer() függvény hívja. */
    public boolean SlipToHigherNb() {
        return random.nextBoolean();
    }

    /** Egy véletlenszerűen sorsolt egész számot ad vissza az [1, 6] tartományban, ami megadja, hogy hány körig legyen
     * ragadós a cső miután ragadóssá tették, amikor nemdeterminisztikusan viselkedik a modell ("counter" új értéke).
     * Lásd: ManipulatorPlayer.Manipulate(Pipe p) */
    public int GetRandomStickyCounter() {
        return random.nextInt(6) + 1; // [1, 6]
    }

    /** Egy véletlenszerűen sorsolt egész számot ad vissza az [1, 8] tartományban, ami megadja, hogy hány nem lehet a
     * megjavított cső újra kilyukasztva, amikor nemdeterminisztikusan viselkedik a modell ("counter" új értéke).
     * Lásd: ManipulatorTechnician.Manipulate(Pipe p) */
    public int GetRandomUnbreakableCounter() {
        return random.nextInt(8) + 1; // [1, 8]
    }

    /** Egy véletlenszerűen sorsolt egész számot ad vissza az [1, 10] tartományban, ami megadja, hogy hány körig legyen
     * csúszós a cső miután csúszóssá tették, amikor nemdeterminisztikusan viselkedik a modell ("counter" új értéke).
     * Lásd: ManipulatorPlayer.Manipulate(Pipe p) */
    public int GetRandomSlippyCounter() {
        return random.nextInt(10) + 1; // [1, 10]
    }

    /** Visszaadja az alapértelmezett időszámláló értékét. Lásd: defaultCounter attribútum. */
    public static int GetDefaultCounter() { return defaultCounter; }

    /** Az átadott értékre állítja az alapértelmezett időszámlálót. Lásd: defaultCounter attribútum. */
    public static void SetDefaultCounter(int value) {
        defaultCounter = value;
        if (PrototypeController.IsLogging())
            PrototypeController.PrintLine("defaultCounter was set to " + value + "\n");
    }

    /** Az átadott értékre állítja a győzelemhez szükséges pontok számát. Lásd: maxScore attribútum. */
    public static void SetMaxScore(int value) {
        maxScore = value;
        if (PrototypeController.IsLogging())
            PrototypeController.PrintLine("\nmaxScore was set to " + value);
    }

    /**
     * A megadott játékosnak kiírjuk az inventory-ját
     * @param pname - a játékos neve
     */
    public void PrintInventory(String pname) {
        for (Player p : players) {
            if (p.GetName().equals(pname)) {
                Element part = p.GetPart();
                if (PrototypeController.GetInstance().GetPrintAlsoToFile()) {
                    if (part != null) {
                        p.GetPart().Print("");
                    } else {
                        System.out.println("empty");
                    }
                }

                PrintStream ps = System.out;
                System.setOut(PrototypeController.GetInstance().GetOutConsole());
                if (part != null) {
                    p.GetPart().Print("");
                } else {
                    System.out.println("empty");
                }
                System.setOut(ps);
            }
        }
    }

    /** Egy controller általi elemelrontást csinál, azaz olyat, ami nem
     * eredményezi a jelenlegi játékos körének a végét. */
    public void BreakElementByController(int elemIdx) {
        Breakable p = network.GetPump(elemIdx);
        if (p == null) {
            p = network.GetPipe(elemIdx);
            if (p == null)
                return;
        }

        p.Break();
        PrototypeController.PrintLine("element " + elemIdx + " was broken by controller\n");
    }

    /** Egy controller általi cső csúszóssá tételt csinál, azaz olyat, ami nem
     * eredményezi a jelenlegi játékos körének a végét. */
    public void StickifyPipeByController(int pipeIdx) {
        Pipe p = network.GetPipe(pipeIdx);
        if (p == null)
            return;

        p.SetSticky(true);
        p.SetCounter(GetDeterministic() ? defaultCounter : GetRandomStickyCounter());
        PrototypeController.PrintLine("pipe " + pipeIdx + " was stickified by controller\n");
    }

    /** Egy controller általi cső ragadóssá tételt csinál, azaz olyat, ami nem
     * eredményezi a jelenlegi játékos körének a végét. */
    public void SlippifyPipeByController(int pipeIdx) {
        Pipe p = network.GetPipe(pipeIdx);
        if (p == null)
            return;

        p.SetSlippery(true);
        p.SetCounter(GetDeterministic() ? defaultCounter : GetRandomSlippyCounter());
        PrototypeController.PrintLine("pipe " + pipeIdx + " was slippified by controller\n");
    }

    /**
     * Kiírja a megadott nevű játékos jelenlegi elemének (amin áll) jellemzőit
     */
    public void PrintCurrElem(String pname) {
        Player p = null;
        for (Player player : players) {
            if (player.GetName().equals(pname)) {
                p = player;
                break;
            }
        }
        if (p == null)
            return;

        network.PrintElement(p.GetCurrElem().GetIdx());
    }
}