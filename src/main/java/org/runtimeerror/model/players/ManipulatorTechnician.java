package org.runtimeerror.model.players;

import org.runtimeerror.controller.Game;
import org.runtimeerror.model.map.Pipe;
import org.runtimeerror.model.map.Pump;

/**
 * A szerelők konkrét manipulátora, ami definiálja, hogy hogyan manipulálhatják az összes lehetséges „gazda” elemüket.
 * A csövek javításának viselkedését valósítja meg, illetve a pumpák megjavításának viselkedésével helyettesíti a pumpaátállítást, ha az adott pumpa rossz.
 * A többi viselkedés megfelel az ősbélivel.
 */
public final class ManipulatorTechnician extends ManipulatorPlayer {

    /** Megjavítja az átadott p csövet, ha az lyukas. Beállítja turn counter-t véletlenszerű értékre, vagy fix 2-re (a
     * modell determinisztikusságától függően), ameddig nem lehet újra kilyukasztani. Utána pedig véget ér a
     * jelenlegi játékos köre. Ha "p" nem lyukas, akkor teljesen megegyezik a viselkedés az ősbéli (szabotőrök
     * manipulátorában megvalósított) alapértelmezett implementációval. Lásd: ManipulatorPlayer.Manipulate(Pipe p) */
    @Override
    public void Manipulate(Pipe p) {
        if (p.GetBroken()) { // ha törött a cső
            p.Fix(); // akkor megjavítja
            int newCounter = Game.GetInstance().GetDeterministic() // új értéket kap counter, ameddig nem lyukadhat ki
                ? 2 : Game.GetInstance().GetRandomUnbreakableCounter(); // fix 2 / sorsolunk (determinisztikusságtól függ)
            p.SetCounter(newCounter); // beállítjuk
            Game.GetInstance().NextTurn(); // következő kör
        } else {
            super.Manipulate(p); // különben megfelel az szabotőrök viselkedése is
        }
    }

    /** Megjavítja az átadott p pumpát, ha az elromlott, különben pedig átállítja
     (ez esetben meghívja az ősbéli megvalósítását a függvénynek, Lásd: ManipulatorPlayer.Manipulate(Pump p). */
    @Override
    public void Manipulate(Pump p) {
        if (p.GetBroken()) { // ha törött a pumpa
            p.Fix(); // akkor megjavítja
            Game.GetInstance().NextTurn(); // és véget ér a köre
        } else {
            super.Manipulate(p); // különben átállítja a bemenetét és a kimenetét, mint egy szabotőr
        }
    }
}