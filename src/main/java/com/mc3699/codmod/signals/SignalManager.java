package com.mc3699.codmod.signals;

import com.mc3699.codmod.Codmod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SignalManager {
    public static final int NUM_SIGNALS = 5;

    private final Map<Signal, Integer> signals = new HashMap<>();

    public void refresh(Random rand) {
        signals.clear();

        for (int i = 0; i < NUM_SIGNALS; i++) {
            Signal signal = Signals.ALL_SIGNALS.get(rand.nextInt(0, Signals.ALL_SIGNALS.size()));

            signals.put(signal, signal.liveTime());
        }
    }

    public void tick() {
        for (Map.Entry<Signal, Integer> entry : signals.entrySet()) {
            Signal signal = entry.getKey();
            int time = entry.getValue();

            signals.put(signal, time - 1);
        }
    }

    public Map<Signal, Integer> getSignals() {
        return Map.copyOf(signals);
    }

    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();

        for (Map.Entry<Signal, Integer> entry : signals.entrySet()) {
            tag.putInt(entry.getKey().id(), entry.getValue());
        }

        return tag;
    }

    public void deserialize(CompoundTag tag) {
        signals.clear();

        for (String key : tag.getAllKeys()) {
            if (!Signals.SIGNALS.containsKey(key)) {
                Codmod.LOGGER.warn("Unknown signal ID: {}", key);
                continue;
            }

            if (tag.getTagType(key) != Tag.TAG_INT) {
                Codmod.LOGGER.warn("Invalid tag type for signal data {}: {}", key, tag.getTagType(key));
                continue;
            }

            signals.put(Signals.SIGNALS.get(key), tag.getInt(key));
        }
    }
}
