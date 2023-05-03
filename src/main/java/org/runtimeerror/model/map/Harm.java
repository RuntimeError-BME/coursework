package org.runtimeerror.model.map;

/**
 * Egy enumeráció, amely megadja, hogy milyen manipulációkat tudnak a játékosok végrehajtani egy csővel:
 * SLIPPY: csúszóssá tenni (ezt csak a szabotőrök tudják)
 * STICKY: ragadóssá tenni
 * BROKEN: kilyukasztani
 */
public enum Harm {
    SLIPPY, STICKY, BROKEN;
}
