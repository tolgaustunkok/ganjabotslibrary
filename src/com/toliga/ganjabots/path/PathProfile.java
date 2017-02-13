package com.toliga.ganjabots.path;

import com.toliga.ganjabots.core.SaveManager;

import java.util.ArrayList;
import java.util.List;

public class PathProfile {
    private String name;
    private List<Element> pathsAndActions = new ArrayList<>();
    private static int index;
    private SaveManager saveManager;

    public PathProfile(String name, SaveManager saveManager) {
        this.name = name;
        this.saveManager = saveManager;
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

    public void saveProfile() {
        saveManager.saveOption("profile", name);

        for (int i = 0; i < pathsAndActions.size(); i++) {
            saveManager.saveOption("-", pathsAndActions.get(i).toString());
        }
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
