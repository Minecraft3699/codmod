package com.mc3699.codmod.signals;

import java.util.HashMap;
import java.util.List;

public class Signals {
    public static final HashMap<String, Signal> SIGNALS = new HashMap<>();

    public static final List<Signal> ALL_SIGNALS = List.of(
            // TODO
    );

    static {
        for (Signal signal : ALL_SIGNALS) {
            SIGNALS.put(signal.id(), signal);
        }
    }
}
