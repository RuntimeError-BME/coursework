package org.runtimeerror.model.map;

public class Direction {

    private final int ordinal;

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