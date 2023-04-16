package org.runtimeerror.model.map;
import org.runtimeerror.Main;
import org.runtimeerror.model.players.Player;
import org.runtimeerror.model.players.Manipulator;
import static org.runtimeerror.skeleton.SkeletonController.ObjNameMap;


/**
 * Olyan elromolható, felvehető elem, amelyen legfeljebb egy játékos tartózkodhat.
 * Ki lehet lyukasztani, a lyukas csövet pedig meg is lehet javítani.
 * Ha a cső nem lyukas akkor a kimenetéhez vizet tud juttatni.
 * Ha lyukas, vagy a kimenete üres, akkor a szabotőrök pontot kapnak, amikor víz érkezik belé.
 */
public class Pipe extends Breakable {
    /**
     * Metódusok
     */
    public Pipe(){
        super();
        ObjNameMap.put(this,"newPipe:Pipe");
        Main.skeleton.PrintFunctionCalled(this);
        pickUpAble=true;
        Main.skeleton.PrintFunctionReturned("<<create>>Pipe","newPipe");
    }

    /**
     *Felhelyezi az átadott játékost magára, ha nem áll rajta már más valaki. A művelet sikerességével tér vissza.
     */
    @Override
    public boolean AddPlayer(Player p) {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionCall(this, "GetPlayerCnt");
        if (GetPlayerCnt() == 0) {
            players.add(p);
            Main.skeleton.PrintFunctionReturned("AddPlayer", "true");
            return true;
        } else
            Main.skeleton.PrintFunctionReturned("AddPlayer", "false");
        return false;
    }

    /**Vízzel tölti meg magát. Ha lyukas a cső vagy nincs output-ja akkor pontot kapnak a szabotőrök,
     *különben pedig hívja tovább az output-ján a Flood() függvényt (ereszti tovább a vizet).
     *Flood volt itt.
     *
     */

    /**
     * Beállítja, d irányba a Pipe szomszédjának e-t ha még nincs 2 szomszédja.
     * @param d - irány
     * @param e - új szomszéd
     */
    @Override
    public void SetNb(Direction d, Element e){
        if(this.GetNbCnt()<2){super.SetNb(d,e);}
    }

    /**
     * Az átvett manipulátorral manipulálja ezt a konkrét elemet.
     * @param m - manipulátor
     */
    @Override
    public void Manipulate(Manipulator m) {
        Main.skeleton.PrintFunctionCalled(this);
        Main.skeleton.PrintFunctionCall(this,"Manipulate",this);
        m.Manipulate(this);
        Main.skeleton.PrintFunctionReturned("Manipulate","");
    }
}