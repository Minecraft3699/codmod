package com.mc3699.codmod.bad_sun;

import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.function.Consumer;

public class DelayUtil {

    public static void schedule(ServerLevel level, int ticks, Runnable task) {
        NeoForge.EVENT_BUS.addListener(new Consumer<ServerTickEvent.Post>() {
            int counter = 0;
            @Override
            public void accept(ServerTickEvent.Post event) {
                if (++counter >= ticks) {
                    task.run();
                    NeoForge.EVENT_BUS.unregister(this); // Remove listener after execution
                }
            }
        });
    }

}
