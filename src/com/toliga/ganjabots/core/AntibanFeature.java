package com.toliga.ganjabots.core;

import org.dreambot.api.script.AbstractScript;

public abstract class AntibanFeature {
    private float probability;
    private boolean enabled;
    private String name;

    public AntibanFeature(String name, float probability, boolean enabled) {
        this.name = name;
        this.probability = probability;
        this.enabled = enabled;
    }

    public AntibanFeature(String name, float probability) {
        this.name = name;
        this.probability = probability;
        this.enabled = false;
    }

    public abstract void execute(AbstractScript context);

    public float getProbability() {
        return probability;
    }

    public void setProbability(float probability) {
        this.probability = probability;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
