package com.toliga.ganjabots.path;

public class ActionElement extends Element {
    public int objectID;
    public String actionName;

    public ActionElement(int objectID, String actionName) {
        this.objectID = objectID;
        this.actionName = actionName;
    }

    @Override
    public String toString() {
        return objectID + ", " + actionName+ " -- Action";
    }
}
