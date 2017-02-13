package com.toliga.ganjabots.path;

public class PathElement extends Element {

    public int x;
    public int y;

    @Override
    public String toString() {
        return x + ", " + y + " - Tile";
    }
}
