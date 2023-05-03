package org.runtimeerror.model.players;

import org.runtimeerror.Main;
import org.runtimeerror.controller.Game;
import org.runtimeerror.model.map.*;
import org.runtimeerror.model.map.Element;

/**
 * Olyan játékos, vagy máshogy fogalmazva annyiban tér el egy szabotőrtől, hogy képes csöveket és pumpákat javítani.
 * Emellett képes vagy egy csövet, vagy egy pumpát tárolni magánál, amit el is tud helyezni a megfelelő feltételek
 * fennállása esetén.
 */
public class Technician extends Player {
    /**
     * Attribútumok
     */
    private Element part = null; // az elem, amelyet a szerelő felvett a pályáról. Ha nincs nála ilyen, akkor null.

    /** Konstruktorok */
    public Technician(String name) { super(name, new ManipulatorTechnician()); }
    public Technician(String name, ManipulatorPlayer m) { super(name, m); }


    /** Visszaadja az elemet („part” attribútumot), ami a szerelőnél van.
     * Felülírja az ősbéli megvalósítást, lásd: Player.GetPart() */
    @Override
    public Element GetPart() {
        return part;
    }

    /** Az átadott Element-re állítja a „part” attribútumot.
     * Felülírja az ősbéli megvalósítást, lásd: Player.SetPart() */
    @Override
    public void SetPart(Element e) {
        part = e;
    }


    /** Megkísérel felvenni egy part-ot a jelenlegi elemtől (NEM KÖRÜLE).
     * Hívja a GetCurrElem()-et, és rajta pedig OnPickup()-ot.
     (Csak ciszterna esetén fog tényleg sikerülni egy pumpát felvennie.) */
    public void PickUpPart() {
        GetCurrElem().OnPickup();
    }

    /** Megkísérli felvenni a d irányban lévő part-ot, ha van olyan.
     Felülírja az ősbéli megvalósítást, lásd: Player.PickUpPart(Direction d) */
    @Override
    public void PickUpPart(Direction d) {

        Element targetElem = GetCurrElem().GetNb(d); // az elem, amit megpróbál felvenni
        if (targetElem == null) // ha nincs elem arra,
            return; // nem történik semmi

        if (targetElem.GetPickUpAble() || GetPart() != null) // ha nem felvehető, vagy nem üres a tárolója
            return; // akkor sem történik semmi

        if (targetElem.GetFlooded() || targetElem.GetBroken()) // ha van benne víz vagy törött,
            return; // akkor sem tudja felvenni

        // ha minden jó, akkor el fogja tudni tárolni
        Network network = Game.GetInstance().GetNetwork(); // a pálya
        network.RemoveElem(targetElem); // eltávolítjuk a szortírozó gyűjteményéből, hogy NextTurn ne befolyásolja már
        SetPart(targetElem); // eltároljuk a tárolójába
    }

    /** Megkísérli elhelyezni a tárolt part-ját d irányba. A művelet sikerességével tér vissza.
     A csövet az elemtől d irányba lévő üres helyre tudja lehelyezni, pumpát viszont úgy tesz le, hogy a jelenlegi
     elemet írja felül, ha az egy cső, ami 2 másik cső között van (ilyenkor d tetszőleges, nem használt irány).
     Felülírja az ősbéli megvalósítást, lásd: Player.PickUpPart(Direction d) */
    @Override
    public boolean PlacePart(Direction d) {

        Network network = Game.GetInstance().GetNetwork(); // a pálya
        Element currElem = GetCurrElem(); // az elem, amin áll
        Element targetElem = currElem.GetNb(d); // a célpont elem
        Element storedPart = GetPart(); // az elem a szerelő tárolójában (a hívó gondoskodott róla, hogy nem null)
        boolean successfulPlacement = false; // a lehelyezés sikeressége, ezt fogjuk visszaadni

        if (storedPart.GetPickUpAble_onlyAttribute() && targetElem == null) { // ha szabad helyre akar csövet tenni
            if (currElem.GetPickUpAble_onlyAttribute() && currElem.GetNbCnt() >= 2) // ha egy csövön áll,
                return false; // és van már 2 szomszédja, akkor nem tudja semelyik irányba sem mellé tenni
            return network.AddPipe((Pipe)targetElem); // különben hozzáadhatjuk a pályához (megpróbálhatjuk legalábbis)
        } else if (!storedPart.GetPickUpAble_onlyAttribute()) { // ha pumpát akar lehelyezni
            if (!currElem.GetPickUpAble()) // ha nem felvehető (nem cső, törött vagy van rajta játékos),
                return false; // akkor nem cserélheti ki a pumpát rá
                return network.AddPump((Pump)targetElem, currElem); // különben megpróbálhatjuk lecserélni
        }
        return false; // más különben nem sikeres a lehelyezés
    }
}