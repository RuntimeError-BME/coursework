package org.runtimeerror.model.map;


/**
 * A geometriát igyekeztünk eltávolítani. A Direction típus a modell szempontjából tetszőleges absztrakció lehet.
 */
public class Direction {
    /**
     * Attribútumok
     */
    private final int ordinal;

    /**
     * Metódusok
     */
    public Direction(int value) {
        if (value < 0)
            throw new IllegalArgumentException("Csak pozitív lehet az irány (indexelésre lesz használva).");
        ordinal = value;
    }

    public final int ordinal() { return ordinal; }
}

/* implementation from the GUI stage:
public enum Direction {
    UP, LEFT, RIGHT, DOWN;
} */