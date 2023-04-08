package org.runtimeerror.controller;

import org.runtimeerror.model.map.Pipe;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import org.runtimeerror.model.players.Player;
import org.runtimeerror.model.map.Direction;
import org.runtimeerror.model.map.Network;

import java.util.List;

public class Game {

    private int currPlayerIdx = 0; // a soron lévő játékos indexe a „players” gyűjteményben
    private int scoreTechnician = 0; // a szerelők pontszáma
    private int scoreSaboteur = 0; // a szabotőrök pontszáma
    private List<Player> players; // a játékban résztvevő játékosok
    private Network network; // a pálya elemeit tárolja, szortírozza


    /* Átadott számú pontot ad a szerelőknek. */
    public void AddTechnicianPoints(int points) {
        throw new NotImplementedException();
    }

    /* Átadott számú pontot ad a szabotőröknek. */
    public void AddSaboteurPoints(int points) {
        throw new NotImplementedException();
    }

    /* A következő turn indítása (áramoltatja a vizet, pontokat oszt, véletlenszerűen pumpákat ront el,
     és ha véget ért egy teljes kör (round), akkor új csöveket teremt a ciszternák üres szomszédjai helyén).
     Ha az egyik csapat elérte a győzelemhez szükséges pontok számát, véget vet a játéknak. */
    public void NextTurn() {
        throw new NotImplementedException();
    }

    /* Visszaadja a játékost, aki éppen soron van (akinek turnje van jelenleg). */
    public Player GetCurrPlayer() {
        throw new NotImplementedException();
    }

    /* Igazat ad vissza, ha éppen szerelő jön az adott körben. */
    public boolean IsTechnicianTurn() {
        throw new NotImplementedException();
    }

    /* Visszaadja a pálya elemeit számontartó objektumot. */
    public Network GetNetwork() {
        throw new NotImplementedException();
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
            throw new NotImplementedException();
        }

        /* Pumpa átállításához visszaadja a soron lévő játékos által bevitt irányokat. */
        public Direction[] GetNewPumpDirections() {
            throw new NotImplementedException();
        }
    }


    /* --------- CSAK A JÁTÉK INICIALIZÁLÁSKOR HASZNÁLT FÜGGVÉNYEK (nincs hibakezelés bennük): --------- */

    /* Hozzáad a játékhoz egy játékost. */
    public void AddPlayer(Player p) {
        throw new NotImplementedException();
    }
}