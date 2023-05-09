package org.runtimeerror.model.players;

import java.util.List;

import org.runtimeerror.controller.Game;
import org.runtimeerror.model.map.*;

/**
 * Ez az osztály Visitor tervezési mintát valósít meg a leszármazottaival együtt.
 * A leszármazottaival egy játékos típusnak készítik el a „gazda” elem manipulálását megvalósító viselkedést minden elem
 * esetére (pumpák, csövek, források, ciszternák). A szerelők konkrét manipulátora például definiálja, hogy mit tud
 * tenni egy szerelő, ha az előbb említett elemeken állva próbál interaktálni. A függvényei gyakran a kört is léptetik.
 * Ez az osztály a szabotőrök konkrét manipulátora, ami definiálja, hogy hogyan manipulálhatják az összes lehetséges
 * „gazda” elemüket a szabotőrök. A leszármazottja (ManipulatorTechnician) pedig a szerelők konkrét manipulátora. */
public class ManipulatorPlayer {

    /** Ezt a ManipulatorPlayer ősbéli csőmanipuláló viselkedést a szabotőrök fogják megkapni (ManipulatorTechnician
     * felül fogja írni, hiszen, ők meg is tudják javítani a csövet, de ők is hívni fogják az őst, ha nem tudnak
     * javítani, mert a többi viselkedésre megfelel nekik is ez).
     * Ha az átadott cső lyukas, akkor nem történik semmi (lyukas csövet nem lehet más módon manipulálni).
     * Ha a cső ragadós vagy csúszós, akkor nem lehet állni rajta anélkül, hogy véget érjen a kör, vagy nem csússzon
     * tovább egy másik elemre a játékos, tehát ezeket az eseteket nem kell vizsgálni, mert ilyen csöveket nem lehet
     * ezek miatt manipulálni. Egyszerre csak egy állapotban lehet a cső ezek közül: lyukas, ragadós vagy csúszós. */
    public void Manipulate(Pipe p) {

        if (p.GetBroken()) // ha a cső lyukas
            return; // akkor nem lehet semmit tenni vele
        // a ragadósságot azért nem kell ellenőrizni, mert ragadós csövön azonnal véget ér a kör
        // csúszósságot pedig azért nem, mert csúszós csövön nem lehet állni, továbbcsúszik egy másik elemre

        // ha a számlálója nagyobb nullánál (és azt tudjuk, hogy "p" nem lehet ragadós és csúszós),
        // akkor tudjuk, hogy még nem telt le a megjavítás utáni lyukasztás cooldown, tehát még nem lehet kilyukasztani
        boolean canBeBroken = (p.GetCounter() == 0);
        Harm harm = Game.Input.GetPipeHarm(canBeBroken); // bekérjük, hogy a játékos milyen módon szeretné manipulálni
        if (harm == Harm.SLIPPY) { // ha csúszóssá tudja, és akarja tenni
            p.SetSlippery(true); // akkor csúszóssá teszi
            int newCounter = Game.GetInstance().GetDeterministic() // megadunk egy új értéket counter-nek
                ? 2 : Game.GetInstance().GetRandomSlippyCounter(); // fix 2 / sorsolunk (determinisztikusságtól függ)
            p.SetCounter(newCounter); // ennyi ideig lesz csúszós a cső

        } else if (harm == Harm.STICKY) { // ha ragadóssá tudja, és akarja tenni
            p.SetSticky(true); // akkor ragadóssá teszi
            int newCounter = Game.GetInstance().GetDeterministic() // megadunk egy új értéket counter-nek
                ? 2 : Game.GetInstance().GetRandomStickyCounter(); // fix 2 / sorsolunk (determinisztikusságtól függ)
            p.SetCounter(newCounter); // ennyi ideig lesz ragadós a cső

            // fontos, hogy a játékos ne maradjon a ragadós csövön, hiszen akkor önmagát szabotálja
            Player player = Game.GetInstance().GetCurrPlayer(); // a jelenlegi játékos
            Element nb1 = p.GetNbs().get(0); // az egyik szomszédja a jelenlegi csőnek
            Element nb2 = p.GetNbs().get(1); // a másik szomszédja a jelenlegi csőnek
            nb1.AddPlayer(player); // először megpróbáljuk a kisebb indexű szomszédra tenni őt
            if (player.GetCurrElem() == p) // ha még rajta áll, akkor nem sikerült (mert a célpont egy cső, amin már állnak)
                nb2.AddPlayer(player); // akkor megpróbáljuk a nagyobb sorszámú irányba lévő szomszédra is rátenni
            // ha ez sem lehetséges, nyilván marad a ragadós csövön, és ilyenkor nem tudja elkerülni az önszabotálást

        } else if (harm == Harm.BROKEN) { // ha ki tudja, és akarja lyukasztani
            p.Break(); // akkor kilyukasztja (ilyenkor counter-t nem kell változtatni)
        }
        Game.GetInstance().NextTurn(); // léptetjük a kört
    }

    /** Átállítja az átadott pumpát (GameInput-ot használja a bemenetért). Ezután véget ér a játékos köre (turn).
     * Nem veszi figyelembe, hogy a pumpa elvan-e romolva (át tudja állítani akkor is, ha nem működik a pumpa).
     * Ez a viselkedés megfelel a szabotőröknek, viszont a szerelők manipulátorában felül kell írni a függvényt úgy,
     * hogy elromlott pumpát megjavítsák, ne átállítsák, és csak a működő pumpát állítsák át. */
    public void Manipulate(Pump p) {
        Element[] elems = Game.Input.GetNewPumpDirections(); // az új input és output elemek

        Element newInp = elems[0]; // az elem, ami az új bemenete lesz
        p.SetInput(newInp); // beállítjuk a pumpa bemeneteként

        Element newOut = elems[1]; // az elem, ami az új kimenete lesz
        p.SetOutput(newOut); // beállítjuk a pumpa kimeneteként

        Game.GetInstance().NextTurn(); // véget ér a jelenlegi játékos köre
    }

    /** Átlépteti a jelenlegi játékost a következő ciszternára. Ezzel nem ér véget a játékos köre (turn). */
    public void Manipulate(Cistern c) {
        Network network = Game.GetInstance().GetNetwork(); // a pálya
        List<Cistern> cists = network.GetCisterns(); // a pálya ciszternáit tárolja

        int idx = cists.indexOf(c); // a jelenlegi ciszterna indexe a ciszternák gyűjteményében, amin éppen áll
        idx += 1; // az egyel nagyobb indexen lévő lesz a következő ciszterna
        if (idx == cists.size()) // ha éppen az utolsó ciszterna volt
            idx = 0; // akkor az első ciszterna a következő ciszterna

        Cistern targetElem = cists.get(idx); // a célpont ciszterna (a "következő ciszterna"), ahová kerülni fog
        Player player = Game.GetInstance().GetCurrPlayer(); // a jelenlegi játékos
        player.MoveTo(targetElem); // áthelyezzük őt oda
    }

    /** Üres törzsű függvény, jelenleg a játékosok nem tesznek semmit a forráson állva.
     (A jövőbeli bővíthetőségre fenntartva, illetve a Dynamic Dispatch hibátlan működése miatt kell,
     hogy ne kelljen Type-checking a hívóoldalon.) */
    public void Manipulate(Source s) { }
}