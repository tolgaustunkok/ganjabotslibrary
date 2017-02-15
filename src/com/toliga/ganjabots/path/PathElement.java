package com.toliga.ganjabots.path;

public class PathElement extends Element {

    public int x;
    public int y;

    public PathElement(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return x + ", " + y + " -- Tile";
    }
}
