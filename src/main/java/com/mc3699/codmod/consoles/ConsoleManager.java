package com.mc3699.codmod.consoles;

import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ConsoleManager {
    private static final Map<UUID, ConsoleData> CONSOLE_DATA = new HashMap<>();

    public static CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();

        for (Map.Entry<UUID, ConsoleData> entry : CONSOLE_DATA.entrySet()) {
            tag.put(entry.getKey().toString(), entry.getValue().serialize());
        }

        return tag;
    }

    public static void deserialize(CompoundTag tag) {
        CONSOLE_DATA.clear();

        for (String key : tag.getAllKeys()) {
            ConsoleData data = new ConsoleData();

            data.deserialize(tag.getCompound(key));
            CONSOLE_DATA.put(UUID.fromString(key), data);
        }
    }

    public static ConsoleData getConsoleData(UUID uuid) {
        return CONSOLE_DATA.computeIfAbsent(uuid, u -> new ConsoleData());
    }
}
