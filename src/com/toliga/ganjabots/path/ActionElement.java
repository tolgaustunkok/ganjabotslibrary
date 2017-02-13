package com.toliga.ganjabots.path;

public class ActionElement extends Element {
    public int objectID;
    public String actionName;

    @Override
    public String toString() {
        return objectID + ", " + actionName+ " - Action";
    }
}
