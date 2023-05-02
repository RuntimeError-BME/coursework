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

    public Direction Opposite() {
        switch (ordinal % 4) {
            case 0: return new Direction(ordinal + 3);
            case 1: return new Direction(ordinal + 1);
            case 2: return new Direction(ordinal - 1);
            case 3: return new Direction(ordinal - 3);
        }
        throw new IllegalArgumentException("Érvénytelen irány!");
    }

    public final int ordinal() { return ordinal; }
}

/* sufficient implementation from the GUI stage:
public enum Direction {
    UP, LEFT, RIGHT, DOWN;

    public Direction Opposite() {
        switch (ordinal()) {
            case 0: return DOWN;
            case 1: return RIGHT;
            case 2: return LEFT;
            case 3: return UP;
        }
        throw new IllegalArgumentException("Érvénytelen irány!");
    }
} */