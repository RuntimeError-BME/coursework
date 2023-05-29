package org.runtimeerror.gui.controller;
import org.runtimeerror.controller.Game;
import org.runtimeerror.gui.buttons.ElementButton;
import org.runtimeerror.gui.frames.GameFrame;
import org.runtimeerror.gui.frames.MenuFrame;
import org.runtimeerror.model.map.*;
import org.runtimeerror.model.players.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;


/** TODO: CLASS INFORMATION - MÉG NAGYON SOK MÓDOSÍTÁS SZÜKSÉGES EBBEN AZ OSZTÁLYBAN
    - A pálya elemeinek megjelenítését végzi
    - A tartalmazott objektumokat eltárolja és esemény hatására azok megváltozásának kiváltására képes
    - Az objektumok pályán való elhelyezéséért is felel
 */


/** A grafikus megjelenítést végző osztály */
public class GuiController {
    private static GuiController guiController;
    private Game game = Game.GetInstance();
    private GameFrame frame; /** A pálya ablaka */
    private Network network;

    /** Az attribútumok iniciaizálása */
    public GuiController() {
        /** A keret indítása */
        try {
            /** Először csak egy menüt indítunk, később állítjuk be a kontrollálandó játék ablakot */
            JFrame mf = new MenuFrame();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static GuiController GetInstance() {
        if (guiController == null) guiController = new GuiController();
        return guiController;
    }

    /** A kontrollálandó játék ablakot beállító függvény */
    public void SetGameFrame(GameFrame gf) {
        this.frame = gf;
    }

    public void handleKeyboardEvent(int pressedKeyCode) {
        Player player = game.GetCurrPlayer();
        if (player == null)
            return;
        int currentElementIndex = player.GetCurrElem().GetIdx();
        //Network network = game.GetNetwork();

        Element currentElement = network.GetElement(currentElementIndex);
        switch(pressedKeyCode) {
            case KeyEvent.VK_D:
                game.SetDeterministic(!game.GetDeterministic());
                break;

            case KeyEvent.VK_M:
                Game.Input.TryCurrElemManipulation();
                break;

            case KeyEvent.VK_P:
                Game.Input.TryPartPlacement(currentElement);
                break;

            case KeyEvent.VK_U:
                Game.Input.Pickup();
                break;

            case KeyEvent.VK_R:
                ChoosePipeToBeRelocated();
                break;

            default:
                break;
        }
        frame.UpdateInventory();
    }

    /** Kezdőpálya építő segédfüggvények - low, medium, high complexity - és ráhelyezi a játékosokat a pályára */
    public void BuildLowComplexityMap() {
        /** Játék reset-elése és a hálózat inicializálása */
        game.Reset();
        network = game.GetNetwork();

        /** Pálya elemeinek létrehozása */
        Source s7 = new Source(7);
        Pipe pi32 = new Pipe(32);
        Cistern c40 = new Cistern(40);
        Pipe pi57 = new Pipe(57);
        Pipe pi58 = new Pipe(58);
        Pipe pi65 = new Pipe(65);
        Cistern c83 = new Cistern(83);
        Pipe pi90 = new Pipe(90);
        Pump pu96 = new Pump(96);
        Pipe pi97 = new Pipe(97);
        Pipe pi98 = new Pipe(98);
        Source s99 = new Source(99);
        Pipe pi108 = new Pipe(108);
        Pump pu109 = new Pump(109);
        Pipe pi115 = new Pipe(115);
        Pump pu120 = new Pump(120);
        Pipe pi121 = new Pipe(121);
        Pipe pi134 = new Pipe(134);
        Pipe pi138 = new Pipe(138);
        Pipe pi139 = new Pipe(139);
        Pump pu140 = new Pump(140);
        Pipe pi144 = new Pipe(144);
        Pipe pi145 = new Pipe(145);
        Pipe pi159 = new Pipe(159);
        Pipe pi163 = new Pipe(163);
        Pipe pi169 = new Pipe(169);
        Pump pu178 = new Pump(178);
        Pipe pi179 = new Pipe(179);
        Pipe pi180 = new Pipe(180);
        Pipe pi181 = new Pipe(181);
        Pump pu182 = new Pump(182);
        Pipe pi183 = new Pipe(183);
        Cistern c184 = new Cistern(184);
        Pipe pi185 = new Pipe(185);
        Pipe pi186 = new Pipe(186);
        Pipe pi187 = new Pipe(187);
        Pump pu188 = new Pump(188);
        Pipe pi189 = new Pipe(189);
        Pipe pi191 = new Pipe(191);
        Pump pu192 = new Pump(192);
        Pipe pi193 = new Pipe(193);
        Cistern c194 = new Cistern(194);
        Pipe pi195 = new Pipe(195);
        Pipe pi202 = new Pipe(202);
        Pipe pi203 = new Pipe(203);
        Pipe pi209 = new Pipe(209);
        Cistern c214 = new Cistern(214);
        Pipe pi215 = new Pipe(215);
        Pipe pi216 = new Pipe(216);
        Pipe pi220 = new Pipe(220);
        Pipe pi221 = new Pipe(221);
        Pump pu222 = new Pump(222);
        Pipe pi223 = new Pipe(223);
        Source s224 = new Source(224);
        Pipe pi227 = new Pipe(227);
        Pipe pi234 = new Pipe(234);
        Pipe pi239 = new Pipe(239);
        Source s250 = new Source(250);
        Pipe pi251 = new Pipe(251);
        Cistern c252 = new Cistern(252);
        Source s259 = new Source(259);
        Pipe pi264 = new Pipe(264);
        Pump pu289 = new Pump(289);
        Pipe pi290 = new Pipe(290);
        Pipe pi291 = new Pipe(291);
        Pipe pi292 = new Pipe(292);
        Cistern c293 = new Cistern(293);
        Pipe pi314 = new Pipe(314);
        Source s339 = new Source(339);

        /** Pálya elemek pályához való adása */
        network.AddSource(s7);
        network.AddPipe(pi32);
        network.AddCistern(c40);
        network.AddPipe(pi57);
        network.AddPipe(pi58);
        network.AddPipe(pi65);
        network.AddCistern(c83);
        network.AddPipe(pi90);
        network.AddPump(pu96);
        network.AddPipe(pi97);
        network.AddPipe(pi98);
        network.AddSource(s99);
        network.AddPipe(pi108);
        network.AddPump(pu109);
        network.AddPipe(pi115);
        network.AddPump(pu120);
        network.AddPipe(pi121);
        network.AddPipe(pi134);
        network.AddPipe(pi138);
        network.AddPipe(pi139);
        network.AddPump(pu140);
        network.AddPipe(pi144);
        network.AddPipe(pi145);
        network.AddPipe(pi159);
        network.AddPipe(pi163);
        network.AddPipe(pi169);
        network.AddPump(pu178);
        network.AddPipe(pi179);
        network.AddPipe(pi180);
        network.AddPipe(pi181);
        network.AddPump(pu182);
        network.AddPipe(pi183);
        network.AddCistern(c184);
        network.AddPipe(pi185);
        network.AddPipe(pi186);
        network.AddPipe(pi187);
        network.AddPump(pu188);
        network.AddPipe(pi189);
        network.AddPipe(pi191);
        network.AddPump(pu192);
        network.AddPipe(pi193);
        network.AddCistern(c194);
        network.AddPipe(pi195);
        network.AddPipe(pi202);
        network.AddPipe(pi203);
        network.AddPipe(pi209);
        network.AddCistern(c214);
        network.AddPipe(pi215);
        network.AddPipe(pi216);
        network.AddPipe(pi220);
        network.AddPipe(pi221);
        network.AddPump(pu222);
        network.AddPipe(pi223);
        network.AddSource(s224);
        network.AddPipe(pi227);
        network.AddPipe(pi234);
        network.AddPipe(pi239);
        network.AddSource(s250);
        network.AddPipe(pi251);
        network.AddCistern(c252);
        network.AddSource(s259);
        network.AddPipe(pi264);
        network.AddPump(pu289);
        network.AddPipe(pi290);
        network.AddPipe(pi291);
        network.AddPipe(pi292);
        network.AddCistern(c293);
        network.AddPipe(pi314);
        network.AddSource(s339);

        /** Pálya elemek összeköttetése a hálózatban */
        /** Első ág (ÉNY) */
        s7.AddNb(pi32);
        network.Connect(32,7,57);
        network.Connect(57, 32, 58);
        network.Connect(58,57,83);
        network.Connect(83, 58, 108);
        network.Connect(108, 83, 109);
        network.Connect(109, 108, 134);
        network.Connect(134, 109, 159);
        network.Connect(159, 134, 184);

        /** Második ág (NY) */
        s250.AddNb(pi251);
        network.Connect(251, 250, 252);
        network.Connect(252, 251, 227);
        network.Connect(227, 252, 202);
        network.Connect(202, 227, 203);
        network.Connect(203, 202, 178);
        network.Connect(178, 203, 179);
        network.Connect(179, 178, 180);
        network.Connect(180, 179, 181);
        network.Connect(181, 180, 182);
        network.Connect(182, 181, 183);
        network.Connect(183, 182, 184);

        /** Harmadik ár (DNY) */
        s259.AddNb(pi234);
        network.Connect(234, 259, 209);
        network.Connect(209, 234, 184);

        /** Negyedik ág (D) */
        s339.AddNb(pi314);
        network.Connect(314, 339, 289);
        network.Connect(292, 293, 291);
        network.Connect(291, 292, 290);
        network.Connect(290, 291, 289);
        network.Connect(264, 289, 239);
        network.Connect(239, 264, 214);
        network.Connect(214, 239, 189);
        network.Connect(189, 214, 188);
        network.Connect(188, 189, 187);
        network.Connect(187, 188, 186);
        network.Connect(186, 187, 185);
        network.Connect(185, 186, 184);

        /** Ötödik ág (ÉK) */
        network.Connect(65, 40, 90);
        network.Connect(90, 65, 115);
        network.Connect(115, 90, 140);
        network.Connect(140, 115, 139);
        network.Connect(139, 140, 138);
        network.Connect(138, 139, 163);
        network.Connect(163, 138, 188);

        /** Hatodik ág (ÉK/K) */
        s99.AddNb(pi98);
        network.Connect(98, 99, 97);
        network.Connect(97, 98, 96);
        network.Connect(96, 97, 121);
        network.Connect(121, 96, 120);
        network.Connect(120, 121, 145);
        network.Connect(145, 120, 144);
        network.Connect(144, 145, 169);
        network.Connect(169, 144, 194);

        s224.AddNb(pi223);
        network.Connect(223, 224, 222);
        network.Connect(222, 223, 221);
        network.Connect(221, 222, 220);
        network.Connect(220, 221, 195);
        network.Connect(195, 220, 194);
        network.Connect(194, 195, 193);
        network.Connect(193, 194, 192);
        network.Connect(192, 193, 191);
        network.Connect(191, 192, 216);
        network.Connect(216, 191, 215);
        network.Connect(215, 216, 214);
    }

    /**
     * A közepesen összetett pálya létrehozása a modellben.
     */
    public void BuildMediumComplexityMap() {
        Pump pu35 = new Pump(35);
        Pipe pi36 = new Pipe(36);
        Pipe pi41 = new Pipe(41);
        Pipe pi42 = new Pipe(42);
        Pipe pi43 = new Pipe(43);
        Pipe pi61 = new Pipe(61);
        Cistern c68 = new Cistern(68);
        Source s86 = new Source(86);
        Pipe pi87 = new Pipe(87);
        Pipe pi88 = new Pipe(88);
        Pump pu89 = new Pump(89);
        Pipe pi93 = new Pipe(93);
        Pipe pi118 = new Pipe(118);
        Pipe pi127 = new Pipe(127);
        Source s143 = new Source(143);
        Pipe pi152 = new Pipe(152);
        Pipe pi153 = new Pipe(153);
        Cistern c155 = new Cistern(155);
        Pump pu205 = new Pump(205);
        Pipe pi230 = new Pipe(230);
        Pump pu245 = new Pump(245);
        Pipe pi255 = new Pipe(255);
        Pipe pi256 = new Pipe(256);
        Pipe pi270 = new Pipe(270);
        Cistern c281 = new Cistern(281);
        Pipe pi294 = new Pipe(294);
        Pipe pi295 = new Pipe(295);
        Pipe pi305 = new Pipe(305);
        Pipe pi306 = new Pipe(306);
        Pipe pi318 = new Pipe(318);
        Pipe pi329 = new Pipe(329);
        Pump pu330 = new Pump(330);
        Pump pu343 = new Pump(343);
        Pipe pi344 = new Pipe(344);
        Source s354 = new Source(354);
        Source s369 = new Source(369);

        network.AddPump(pu35);
        network.AddPipe(pi36);
        network.AddPipe(pi41);
        network.AddPipe(pi42);
        network.AddPipe(pi43);
        network.AddPipe(pi61);
        network.AddCistern(c68);
        network.AddSource(s86);
        network.AddPipe(pi87);
        network.AddPipe(pi88);
        network.AddPump(pu89);
        network.AddPipe(pi93);
        network.AddPipe(pi118);
        network.AddPipe(pi127);
        network.AddSource(s143);
        network.AddPipe(pi152);
        network.AddPipe(pi153);
        network.AddCistern(c155);
        network.AddPump(pu205);
        network.AddPipe(pi230);
        network.AddPump(pu245);
        network.AddPipe(pi255);
        network.AddPipe(pi256);
        network.AddPipe(pi270);
        network.AddCistern(c281);
        network.AddPipe(pi294);
        network.AddPipe(pi295);
        network.AddPipe(pi305);
        network.AddPipe(pi306);
        network.AddPipe(pi318);
        network.AddPipe(pi329);
        network.AddPump(pu330);
        network.AddPump(pu343);
        network.AddPipe(pi344);
        network.AddSource(s354);
        network.AddSource(s369);

        s86.AddNb(pi61);
        network.Connect(61, 86, 36);
        network.Connect(36, 61, 35);
        network.Connect(87, 86, 88);
        network.Connect(88, 87, 89);
        network.Connect(89, 88, 90);

        s354.AddNb(pi329);
        network.Connect(329, 354, 330);
        network.Connect(330, 329, 305);
        network.Connect(305, 330, 306);
        network.Connect(306, 305, 281);
        network.Connect(281, 306, 256);
        network.Connect(256, 281, 255);
        network.Connect(255, 256, 230);
        network.Connect(230, 255, 205);
        network.Connect(205, 230, 180);
        Element pi180 = network.GetElement(180);
        c155.AddNb(pi180);
        pi180.AddNb(c155);

        s369.AddNb(pi344);
        network.Connect(344, 369, 343);
        network.Connect(343, 344, 318);
        network.Connect(318, 343, 293);
        network.Connect(293, 318, 294);
        network.Connect(294, 293, 295);
        network.Connect(295, 294, 270);
        network.Connect(270, 295, 245);
        network.Connect(245, 270, 220);

        Element pi144 = network.GetElement(144);
        pi144.AddNb(s143);
        s143.AddNb(pi144);
        s143.AddNb(pi118);
        network.Connect(118, 145, 93);
        network.Connect(93, 118, 68);
        network.Connect(68, 93, 43);
        network.Connect(43, 68, 42);
        network.Connect(42, 43, 41);
        network.Connect(41, 42, 40);

        network.Connect(153, 178, 152);
        network.Connect(152, 153, 127);
    }
    /**
     * A nagyon összetett pálya létrehozása a modellben.
     */
    public void BuildHighComplexityMap() {
        Source s13 = new Source(13);
        Pipe pi14 = new Pipe(14);
        Source s23 = new Source(23);
        Source s28 = new Source(28);
        Pipe pi39 = new Pipe(39);
        Pump pu46 = new Pump(46);
        Pipe pi47 = new Pipe(47);
        Pipe pi48 = new Pipe(48);
        Cistern c51 = new Cistern(51);
        Pipe pi52 = new Pipe(52);
        Pipe pi53 = new Pipe(53);
        Pipe pi54 = new Pipe(54);
        Pump pu55 = new Pump(55);
        Pipe pi56 = new Pipe(56);
        Pipe pi71 = new Pipe(71);
        Pipe pi76 = new Pipe(76);
        Pipe pi80 = new Pipe(80);
        Pipe pi101 = new Pipe(101);
        Pipe pi105 = new Pipe(105);
        Pump pu106 = new Pump(106);
        Pipe pi107 = new Pipe(107);
        Pipe pi123 = new Pipe(123);
        Source s126 = new Source(126);
        Pipe pi130 = new Pipe(130);
        Pipe pi148 = new Pipe(148);
        Cistern c173 = new Cistern(173);
        Pipe pi248 = new Pipe(248);
        Pipe pi260 = new Pipe(260);
        Pump pu261 = new Pump(261);
        Pipe pi262 = new Pipe(262);
        Pipe pi273 = new Pipe(273);
        Pipe pi277 = new Pipe(277);
        Pipe pi284 = new Pipe(284);
        Pipe pi287 = new Pipe(287);
        Pipe pi288 = new Pipe(288);
        Cistern c298 = new Cistern(298);
        Pump pu302 = new Pump(302);
        Pump pu309 = new Pump(309);
        Pipe pi310 = new Pipe(310);
        Pipe pi311 = new Pipe(311);
        Pipe pi323 = new Pipe(323);
        Pipe pi333 = new Pipe(333);
        Pipe pi334 = new Pipe(334);
        Pipe pi335 = new Pipe(335);
        Cistern c336 = new Cistern(336);
        Pipe pi337 = new Pipe(337);
        Pipe pi338 = new Pipe(338);
        Pipe pi347 = new Pipe(347);
        Pipe pi348 = new Pipe(348);
        Source s349 = new Source(349);
        Cistern c351 = new Cistern(351);
        Pipe pi352 = new Pipe(352);
        Pipe pi353 = new Pipe(353);
        Pipe pi355 = new Pipe(355);
        Pipe pi356 = new Pipe(356);
        Pipe pi357 = new Pipe(357);
        Cistern c358 = new Cistern(358);
        Source s361 = new Source(361);
        Pump pu372 = new Pump(372);

        network.AddSource(s13);
        network.AddPipe(pi14);
        network.AddSource(s23);
        network.AddSource(s28);
        network.AddPipe(pi39);
        network.AddPump(pu46);
        network.AddPipe(pi47);
        network.AddPipe(pi48);
        network.AddCistern(c51);
        network.AddPipe(pi52);
        network.AddPipe(pi53);
        network.AddPipe(pi54);
        network.AddPump(pu55);
        network.AddPipe(pi56);
        network.AddPipe(pi71);
        network.AddPipe(pi76);
        network.AddPipe(pi80);
        network.AddPipe(pi101);
        network.AddPipe(pi105);
        network.AddPump(pu106);
        network.AddPipe(pi107);
        network.AddPipe(pi123);
        network.AddSource(s126);
        network.AddPipe(pi130);
        network.AddPipe(pi148);
        network.AddCistern(c173);
        network.AddPipe(pi248);
        network.AddPipe(pi260);
        network.AddPump(pu261);
        network.AddPipe(pi262);
        network.AddPipe(pi273);
        network.AddPipe(pi277);
        network.AddPipe(pi284);
        network.AddPipe(pi287);
        network.AddPipe(pi288);
        network.AddCistern(c298);
        network.AddPump(pu302);
        network.AddPump(pu309);
        network.AddPipe(pi310);
        network.AddPipe(pi311);
        network.AddPipe(pi323);
        network.AddPipe(pi333);
        network.AddPipe(pi334);
        network.AddPipe(pi335);
        network.AddCistern(c336);
        network.AddPipe(pi337);
        network.AddPipe(pi338);
        network.AddPipe(pi347);
        network.AddPipe(pi348);
        network.AddSource(s349);
        network.AddCistern(c351);
        network.AddPipe(pi352);
        network.AddPipe(pi353);
        network.AddPipe(pi355);
        network.AddPipe(pi356);
        network.AddPipe(pi357);
        network.AddCistern(c358);
        network.AddSource(s361);
        network.AddPump(pu372);

        s13.AddNb(pi14);
        network.Connect(14, 13, 39);
        network.Connect(39, 14, 40);

        s23.AddNb(pi48);
        network.Connect(48, 23, 47);
        network.Connect(47, 48, 46);
        network.Connect(46, 47, 71);
        network.Connect(71, 46, 96);

        s28.AddNb(pi53);
        network.Connect(53, 28, 52);
        network.Connect(52, 53, 51);
        network.Connect(51, 52, 76);
        network.Connect(76, 51, 101);
        network.Connect(101, 76, 126);
        Element pi127 = network.GetElement(127);
        s126.AddNb(pi127);
        pi127.AddNb(s126);


        pi54.AddNb(pi53);
        pi53.AddNb(pi54);
        Element pi57 = network.GetElement(57);
        pi57.AddNb(pi56);
        pi56.AddNb(pi57);
        network.Connect(55, 54, 56);
        network.Connect(55, 56, 80);
        network.Connect(80, 55, 105);
        network.Connect(105, 80, 130);
        network.Connect(130, 105, 155);
        network.Connect(106, 105, 107);
        network.Connect(107, 106, 108);

        s349.AddNb(pi348);
        network.Connect(348, 349, 323);
        network.Connect(323, 348, 298);
        network.Connect(298, 323, 273);
        network.Connect(273, 298, 248);
        network.Connect(248, 273, 223);
        network.Connect(347, 348, 372);

        s361.AddNb(c336);
        network.Connect(336, 361, 337);
        network.Connect(337, 336, 338);
        network.Connect(338, 337, 339);
        network.Connect(336, 337, 335);
        network.Connect(335, 336, 334);
        network.Connect(334, 335, 333);
        network.Connect(333, 334, 358);
        network.Connect(358, 333, 357);
        network.Connect(357, 358, 356);
        network.Connect(356, 357, 355);
        network.Connect(355, 356, 354);
        network.Connect(354, 355, 353);
        network.Connect(353, 354, 352);
        network.Connect(352, 353, 351);

        network.Connect(260, 259, 261);
        network.Connect(261, 260, 262);
        network.Connect(262, 261, 287);
        network.Connect(287, 262, 288);
        network.Connect(288, 287, 289);
        network.Connect(284, 259, 309);
        network.Connect(309, 284, 310);
        network.Connect(309, 284, 310);
        network.Connect(310, 309, 311);
        network.Connect(336, 311, 361);
        pu309.AddNb(pi334);
        pi334.AddNb(pu309);
        pi310.AddNb(pi335);
        pi335.AddNb(pi310);

        network.Connect(277, 252, 302);
        network.Connect(148, 173, 123);
        network.Connect(123, 148, 98);
    }

    /** Frissíti a játék összes gombját. */
    public void UpdateButtons() {
        frame.UpdateButtons();
    }

    /**
     * Frissíti a gombok tooltipjeit aszerint, hogy milyen játékosok állnak rajtuk.
     */
    public void UpdateButtonTooltips() {
        frame.UpdateButtonTooltips();
    }

    /** Beállítja a jelenlegi játékos eleméhez tartozó gomb keretét sárgára. */
    public void UpdateCurrPlayerBtnBorder(int prevBtnIdx) {
        frame.UpdateCurrPlayerBtnBorder(prevBtnIdx);
    }

    /** Bekéri a felhasználótól, hogy melyik szomszédos csövet akarja felvenni. */
    private void ChoosePipeToBeRelocated() {
        if (!Game.GetInstance().IsTechnicianTurn()) {
            return;
        }

        List<Integer> candidatePipeIndices = new ArrayList<>();
        Player player = Game.GetInstance().GetCurrPlayer();

        for (Element targetElem : player.GetCurrElem().GetNbs()) {

            if (targetElem == null) // ha nincs elem arra,
                continue; // nem történik semmi

            if (!targetElem.GetPickUpAble() || player.GetPart() != null) // ha nem felvehető, vagy nem üres a tárolója
                continue; // akkor sem történik semmi

            if (targetElem.GetFlooded()) // ha van benne víz,
                continue; // akkor sem tudja felvenni

            candidatePipeIndices.add(targetElem.GetIdx());
        }
        if (candidatePipeIndices.isEmpty())
            return;

        // ha minden jó, akkor el fogja tudni tárolni
        Object[] options = candidatePipeIndices.toArray();
        int chosenIdx = JOptionPane.showOptionDialog(frame, "Which pipe do you want to pick up?", "Picking up a pipe",
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (chosenIdx == -1) return;
        Game.Input.TryPartRelocation(network.GetElement(candidatePipeIndices.get(chosenIdx)));

    }

    /** Felajánlja a játékosnak a 'centerIdx' indexű ElementButton-tól szomszédos összes üres ElementButtont, hogy
     * válasszon közülök. A választott indexével tér vissza. */
    public int GetEmptyElementButton(int centerIdx) {
        return frame.GetEmptyElementButton(centerIdx);
    }

    /** Dialógusban jeleníti meg annak a tényét, hogy nem sikerült letenni egy elemet. */
    public void DisplayPlacementFailureDialogue() {
        JOptionPane.showMessageDialog(null, "Element placement failed!",
                                      "Placement failure", JOptionPane.ERROR_MESSAGE);

    }

    /** Bekéri a felhasználótól, hogy milyen módon szeretné manipulálni a csövet. */
    public int GetHarmDialogue(Object[] options) {

        if (options.length > 2) {
            return JOptionPane.showOptionDialog(frame,
                                     "What do you want to do with the pipe?",
                                     "Manipulation choice",
                                     JOptionPane.YES_NO_CANCEL_OPTION,
                                     JOptionPane.QUESTION_MESSAGE,
                                     null,
                                     options,
                                     options[0]);
        }

        return JOptionPane.showOptionDialog(frame,
                                            "What do you want to do with the pipe?",
                                            "Manipulation choice",
                                            JOptionPane.YES_NO_OPTION,
                                            JOptionPane.QUESTION_MESSAGE,
                                            null,
                                            options,
                                            options[0]);
    }

    public int GetPumpDirectionDialogue(Object[] options, boolean input) {
        String str = input ? "input" : "output";
        return JOptionPane.showOptionDialog(frame, "Choose a new " + str + ":", "Changing pump directions",
                                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,
                                            options[0]);
    }

    /** Az átadott indexű elemnek a textúráját kicseréli egy pumpa textúrára. */
    public void ReplaceTextureToPump(int idx) {
        frame.ReplaceTextureToPump(idx);
    }

    /** Visszaállítja alapállapotába az adott indexű gomb kinézetét. */
    public void ResetElementButton(int idx) {
        frame.ResetElementButton(idx);
    }

    /** A GUIController egérkattintásra mozgatja a soron lévő játékost a lenyomott gombhoz tartozó pályaelemre */
    public void handleMoveEvent(int fromButtonId, int toButtonId, String playerType) {
        Game.Input.MoveCurrPlayer(toButtonId);
    }

    /**
        TODO: FONTOS!!!
        Az irányításon kívül, még három függvény összeköttetést kell megvalósítani a backend és frontend között, ezek pedig:
            1. A fenti információs sáv feltöltése a prototípus visszajelzéseivel (SetInformation(String info) függvény a Players osztályban) -ez nem lesz
            2. A csapatok által kapott pontok kiírása a GUI-ban (AddPoint(int idx, int nPoints) függvény a Players osztályban)
            3. A csapatok inventory-jainak kiírása a GUI-ba (ActualiseInventory(String team, String inventory) függvény a GameTimer osztályban)
     */
}
