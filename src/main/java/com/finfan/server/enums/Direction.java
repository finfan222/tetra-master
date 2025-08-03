package com.finfan.server.enums;

public enum Direction {
    LEFT_UP,
    UP,
    RIGHT_UP,
    LEFT,
    RIGHT,
    LEFT_DOWN,
    DOWN,
    RIGHT_DOWN;

    public static final int SIZE = Direction.values().length;

    public int getMask() {
        return 1 << ordinal();
    }

}
