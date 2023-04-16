package org.runtimeerror.controller;

import org.runtimeerror.Main;
import org.runtimeerror.model.map.*;
import org.runtimeerror.model.players.Technician;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import org.runtimeerror.model.players.Player;

import static org.runtimeerror.skeleton.SkeletonController._Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * A játékmenetet kezeli (vizsgálja, hogy véget ért-e a játék, valamint a köröket vezérli).
 * Számontartja és módosítja a csőrendszert (turn-önként véletlenszerűen pumpákat ront el, round-onként pedig ciszternák szomszédos üres helyeire új csöveket helyez le).
 * Turn-ök elején folyatja a vizet a forrásokból a szabad végű / lyukas csövekig vagy ciszternáig, illetve pontokat tud osztani a csapatoknak, amit szintén számontart.
 */
public class Game {
    /**
     * Attribútumok
     */
    private int currPlayerIdx = 0; // a soron lévő játékos indexe a „players” gyűjteményben
    private int scoreTechnician = 0; // a szerelők pontszáma
    private int scoreSaboteur = 0; // a szabotőrök pontszáma
    private List<Player> players; // a játékban résztvevő játékosok
    private Network network; // a pálya elemeit tárolja, szortírozza

    /**
     * Konstruktor
     */
    public Game(){
        currPlayerIdx = 0; // a soron lévő játékos indexe a „players” gyűjteményben
        scoreTechnician = 0; // a szerelők pontszáma
        scoreSaboteur = 0; // a szabotőrök pontszáma
        players=new ArrayList<>(); // a játékban résztvevő játékosok
        network=null; // a pálya elemeit tárolja, szortírozza
    }

    /**
     * Metódusok
     */
    public void setNetwork(Network network) {
        this.network = network;
    }

    /* Átadott számú pontot ad a szerelőknek. */
    public void AddTechnicianPoints(int points) {
        Main.skeleton.PrintFunctionCalled(this);
        scoreTechnician += points;
        Main.skeleton.PrintFunctionReturned("AddTechnicianPoints","");
    }

    /* Átadott számú pontot ad a szabotőröknek. */
    public void AddSaboteurPoints(int points) {
        Main.skeleton.PrintFunctionCalled(this);
        scoreSaboteur += points;
        Main.skeleton.PrintFunctionReturned("AddSaboteurPoints","");
    }

    /* A következő turn indítása (áramoltatja a vizet, pontokat oszt, véletlenszerűen pumpákat ront el,
     és ha véget ért egy teljes kör (round), akkor új csöveket teremt a ciszternák üres szomszédjai helyén).
     Ha az egyik csapat elérte a győzelemhez szükséges pontok számát, véget vet a játéknak. */
    public void NextTurn() {
        Main.skeleton.PrintFunctionCalled(this);

        Main.skeleton.PrintFunctionReturned("NextTurn","");
        //throw new NotImplementedException();
    }

    /* Visszaadja a játékost, aki éppen soron van (akinek turnje van jelenleg). */
    public Player GetCurrPlayer() {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionReturned("GetCurrPlayer", "player");
        return players.get(currPlayerIdx);
    }

    /* Igazat ad vissza, ha éppen szerelő jön az adott körben. */
    public boolean IsTechnicianTurn() {
        Main.skeleton.PrintFunctionCalled(this);
        boolean re=currPlayerIdx%2==0;
        Main.skeleton.PrintFunctionReturned("IsTechnicianTurn", re ? "true" : "false");
        return re;
    }

    /* Visszaadja a pálya elemeit számontartó objektumot. */
    public Network GetNetwork() {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionReturned("GetNetwork","network");
        return network;
    }


    /* Inner-class. A felhasználót és a játékot köti össze. Irányokat / célpontokat tud bekérni a felhasználótól.
     Játékosmozgatást, cső- és pumpalehelyezést, illetve csőfelvevést kezdeményezhet vele. */
    public static class Input {

        /* d irányba próbálja mozgatni az épp soron lévő játékost, arról az elemről, amelyiken éppen tartózkodik. */
        public void MoveCurrPlayer(Direction d) {
            throw new NotImplementedException();
        }

        /* Akkor hívandó függvény, amikor éppen azt az elemet szeretné manipulálni / interakcióba lépni vele
         (csövet lyukasztani / foltozni, pumpát javítani / átállítani vagy a következő ciszternára ugrani)
         a soron lévő játékos, amelyiken éppen áll. */
        public void TryCurrElemManipulation() {
            throw new NotImplementedException();
        }

        /* Akkor hívandó függvény, amikor a soron lévő játékos megpróbál egy tőle d irányba lévő szomszédos csövet
         felvenni, és a tárolójában eltárolni. */
        public void TryPartRelocation(Direction d) {
            throw new NotImplementedException();
        }

        /* Akkor hívandó függvény, amikor a soron lévő játékos megpróbálja a tárolt part-ját tőle d irányba
         lehelyezni. */
        public void TryPartPlacement(Direction d) {
            throw new NotImplementedException();
        }

        /* Akkor hívandó függvény, amikor egy játékos egy part-ot próbálna a tárolójába tenni
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

        /* Pumpa átállításához visszaadja a soron lévő játékos által bevitt irányokat. */
        public Direction[] GetNewPumpDirections() {
            Main.skeleton.PrintFunctionCalled(this);

//            Scanner sc1 = new Scanner(System.in);
//            int inputValue1 = sc1.nextInt();
//
//            Scanner sc2 = new Scanner(System.in);
//            int inputValue2 = sc2.nextInt();

            Direction[] dirs= new Direction[] {
                    new Direction(1),
                    new Direction(2)
            };

            Main.skeleton.PrintFunctionReturned("GetNewPumpDirections", "dirs");
            return dirs;
        }
    }


    /* --------- CSAK A JÁTÉK INICIALIZÁLÁSKOR HASZNÁLT FÜGGVÉNYEK (nincs hibakezelés bennük): --------- */

    /* Hozzáad a játékhoz egy játékost. */
    public void AddPlayer(Player p) {
        Main.skeleton.PrintFunctionCalled(this);
        players.add(p);
        Main.skeleton.PrintFunctionReturned("AddPlayer", "");
    }
}