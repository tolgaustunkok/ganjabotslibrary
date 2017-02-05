package com.toliga.ganjabots.core;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;

import java.util.ArrayList;
import java.util.List;

public abstract class AntibanManager {
    private List<AntibanFeature> antibanFeatures;
    private AbstractScript context;

    public AntibanManager(AbstractScript context) {
        this.context = context;
        antibanFeatures = new ArrayList<>();
    }

    public void addFeature(String name) {
        antibanFeatures.add(createFeature(name));
    }

    protected abstract AntibanFeature createFeature(String name);

    public void runFeatures() {
        for (AntibanFeature feature : antibanFeatures) {
            if (feature.isEnabled()) {
                if (Calculations.random(100) < feature.getProbability() * 100) {
                    feature.execute(context);
                }
            }
        }
    }

    public AntibanFeature getFeature(String name) {
        AntibanFeature result = null;

        for (AntibanFeature feature : antibanFeatures) {
            if (feature.getName().equalsIgnoreCase(name)) {
                result = feature;
                break;
            }
        }

        return result;
    }
}
