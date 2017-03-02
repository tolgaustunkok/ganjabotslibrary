package com.toliga.ganjabots.path;

import com.toliga.ganjabots.core.SaveManager;

import java.util.ArrayList;
import java.util.List;

public class PathProfile {
    private String name;
    private List<Element> pathsAndActions = new ArrayList<>();
    private static int index;

    public PathProfile(String name) {
        this.name = name;
    }

    public Element nextElement() {
        Element result = null;
        try {
            result = pathsAndActions.get(index++);
        } catch (Exception e) {
            index = 0;
        }

        return result;
    }

    public void addElement(Element element) {
        pathsAndActions.add(element);
    }

    public void saveProfile(SaveManager saveManager) {
        saveManager.saveProfile(this);
    }

    @Override
    public String toString() {
        return name;
    }

    public List<Element> getPathsAndActions() {
        return pathsAndActions;
    }

    public String getName() {
        return name;
    }
}
