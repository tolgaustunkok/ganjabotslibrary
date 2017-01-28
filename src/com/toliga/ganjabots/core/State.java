package com.toliga.ganjabots.core;

import org.dreambot.api.script.AbstractScript;

public interface State {
    boolean execute(AbstractScript context);
    State next();
}
