package com.toliga.ganjabots.core;

import org.dreambot.api.script.AbstractScript;

public class StateScheduler {
    private boolean isStarted;
    private State nextState;
    private AbstractScript context;

    public StateScheduler(AbstractScript context, State startState) {
        this.nextState = startState;
        this.context = context;
    }

    public void executeState(AntibanManager antibanManager) {
        if (nextState.execute(context, antibanManager)) {
            nextState = nextState.next();
        }
    }

    public void setStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }

    public boolean isStarted() {
        return isStarted;
    }
}
