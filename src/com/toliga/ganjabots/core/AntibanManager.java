package com.toliga.ganjabots.core;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;

import java.util.ArrayList;
import java.util.List;

public class AntibanManager {
    private List<AntibanFeature> antibanFeatures;
    private AbstractScript context;

    public AntibanManager(AbstractScript context) {
        this.context = context;
        antibanFeatures = new ArrayList<>();
    }

    public void addFeature(AntibanFeature feature) {
        antibanFeatures.add(feature);
    }

    public void runFeatures() {
        for (AntibanFeature feature : antibanFeatures) {
            if (feature.isEnabled()) {
                if (Calculations.random(100 - (int)(feature.getProbability() * 100)) == 1) {
                    feature.execute(context);
                }
            }
        }
    }
}
