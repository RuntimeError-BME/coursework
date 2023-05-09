package org.runtimeerror.model.players;

import org.runtimeerror.controller.Game;
import org.runtimeerror.model.map.*;
import org.runtimeerror.model.map.Element;

import java.util.ArrayList;
import java.util.List;

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
    public void PickUpPart(Element e) {

        List<Element> elementPool = new ArrayList<>(GetCurrElem().GetNbs());
        Element targetElem = null; // az elem, amit megpróbál felvenni

        if(elementPool.contains(e))
            targetElem = e;

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

    /** Megkísérli elhelyezni a tárolt part-ját. A művelet sikerességével tér vissza.
     Csövet üres helyre tud lehelyezni, pumpát viszont úgy tesz le, hogy a jelenlegi
     elemet írja felül, ha az egy cső, ami 2 másik cső között van.
     Ezen kívül át kell adni egy "e" elemet (ami nem az amin áll), amihez csatolni fogja a lehelyezendőt (ha van ilyen).
     Felülírja az ősbéli megvalósítást, lásd: Player.PickUpPart(Element e) */
    @Override
    public boolean PlacePart(Element e) {

        Network network = Game.GetInstance().GetNetwork(); // a pálya
        Element currElem = GetCurrElem(); // az elem, amin áll
        Element storedPart = GetPart(); // az elem a szerelő tárolójában (a hívó gondoskodott róla, hogy nem null)
        boolean successfulPlacement = false; // a lehelyezés sikeressége, ezt fogjuk visszaadni

        if (storedPart.GetPickUpAble_onlyAttribute()) { // ha csövet akar letenni
            if (currElem.GetPickUpAble_onlyAttribute() && currElem.GetNbCnt() >= 2) // ha csövön áll és már nem lehet nb-je,
                return false; // akkor nem tudja letenni mellé
            if (e != null) {
                if (e.GetPickUpAble_onlyAttribute() && e.GetNbCnt() >= 2) // ha egy csövön áll,
                    return false; // és van már 2 szomszédja, akkor nem tudja semelyik irányba sem mellé tenni
            }

            successfulPlacement = storedPart.NetworkAdd(null);// berakjuk a network pipe-jai közé
            if (successfulPlacement){
                //Ha van elem amihez a végét kötnénk akkor azzal beállítjuk a szomszédi viszonyokat.
                if (e != null) {
                    storedPart.AddNb(e);
                    e.AddNb(storedPart);
                    storedPart.SetOutput(e);
                    e.SetInput(storedPart);
                }

                // beállítjuk az új cső szomszédjait, illetve be- és kimenetét (és fordított irányba is)
                currElem.AddNb(storedPart);
                storedPart.AddNb(currElem);
                storedPart.SetInput(currElem);
                currElem.SetOutput(storedPart);

            }
        } else { // ha pumpát akar lehelyezni
            if (currElem.GetBroken() || currElem.GetNbCnt() != 2) // ha nem felvehető (törött), vagy nincs 2 szomszédja
                return false; // akkor nem cserélheti ki a pumpát rá

            successfulPlacement = storedPart.NetworkAdd(currElem); // különben megpróbálhatjuk lecserélni
            if (successfulPlacement) {
               Player player = Game.GetInstance().GetCurrPlayer(); // áthelyezzük a játékost a pumpára
               player.MoveTo(storedPart);
            }
        }
        return successfulPlacement;
    }
}