# Szoftver Projekt eligazítás
`Jegyzet március 1-éről`

## Ütemezés
- Új feladat minden héten.
- Beadás minden hétfőn (14:00 - 14:15) (élőben és hercules **is**).
- Május 1, Pünkösd: laboridőpontban.
- Egy tanuló több csapat doksijait is beadta.
- Nyomtatva, bugyiban.
- Late: -10% minden nap késésért (herkulesben leadni, szólni, h ne csökkenjen tovább, ha elkészült közben).

## Szabályok
- Forráskódot 3x lehet feltölteni Herculesre.
- Követelmények, Analízismodell => tesztterv, implementációs terv, grafika terv (70-es éveket idéző grafikát hogyan lehet rátenni.
- Mindig jó 0-ról indulni (vannak változások a feladatban, friss szemmel kell nézni).
- Forráskód: futtatható a kari szerveren, fusson, élőben is működik (másik csapat is fogja futtatni), azon a VM-en mindenképpen le kell futnia.

## Fázisok
1. Skeleton
    - Követelmények
    - Analízis modell
2. Proto
   - CLI-vel, grafika nélkül lehessen játszani a játékkal
   - Működő modell és vezérlés
   - Tesztelés parancssori támogatása
   - Minimális, "lábbalhajtós", futó szoftver a követelmények alapján
3. Grafikus
   - GUI illesztése
   - MVC Modell

## Fázisok, MVC (kidolgozottság)

### Szkeleton
Modell:     `=========`

View:       `===`

Controller: `=`

### Prototípus
Modell:     `========================`    (100)

View:       `========`                    (20)

Controller: `==================`          (70)

### Grafius
Modell:     `========================`    (100)

View:       `========================`    (100)

Controller: `========================`    (100)

## Kapcsolattartás
**E-Mail:** `projlab@iit.bme.hu`
**Konzi:** `szerdánként`, `pontok is itt`, `csak akkor kötelező, ha elő van írva`

## Bemutatás, tesztelés
`fázisok végén`, `kötelező jelenlét`

- **Olyan embert küldjünk be, aki írni-olvasni tud**.

- Érdemes több embert, mert ki tudják egészíteni egymás gondolatait.

- Általában igyekszik a jót is mondani, de nem feltétlen ez a célja.

- Kérdésekre készségesen válaszolnak (a "az jó lesz-e, hogy..." fajtájúakra is, szóval ajánlott felkészülni).

## Tanácsok
- Doksi: snapshot , ha elkészítettük a modellt, milyen állapotban van a dokumentum készítésének végére? Ne csak összekalapálni igyekezzünk.
- Minden anyagot úgy töltsünk ki, hogy kapcsolódjanak egymáshoz (minden összefügg).
- A részfeladatok pontozása kb. összefügg a szükséges energiabefektetéssel.
- Viszonylag ritka a bukás.
- Labvez: megrendelő és minőségbiztosító **is**.
- Unit teszt nem elvárás. Csak átvételi tesztelés van.
- Bármilyen környezetben lehet fejleszteni, csak fusson a VM-ben.
- Inkább kezdjük újra a diagramozást.
- Ne hagyjuk, hogy a csapattag elkallódjon.
- Naplót becsületesen írni.
- Félév közben változhat (valszleg. változni fog) a feladat követelménye, de nem túlságosan (ha jó a modell, +1 osztály körül).
- Próbáljuk a tervezős, mérnöki részét kidombodítani a feladatnak, és próbáljunk eltávolodni a javatól.

## Pontozás
- Mindegyik fázisra lehet 100-100 pontot gyűjteni.
- Súlyozva: 
  - Skeleton: 0.3x
  - Proto: 0.5x
  - Grafikus: 0.2x
- Félév végére nettó 100p.
- Tagok csapatpontszám alapján kapnak jegyet (minden fázis végén a csapat nyilatkozik a beletett effortról).
- Legalább 41% elérendő minden fázisban
- **Jegyhatárok:**

  - `2:` 41-54

  - `3:` 55-73

  - `4:` 74-82

  - `5:` 83-100