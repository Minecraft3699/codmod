package com.mc3699.codmod.handlers;

import com.mc3699.codmod.Codmod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.LinkedList;
import java.util.List;

@EventBusSubscriber(modid = Codmod.MOD_ID)
public class CodScheduler {

    private static final List<ScheduledTask> active = new LinkedList<>();
    private static final List<ScheduledTask> pending = new LinkedList<>();

    public static void schedule(int delayTicks, Runnable action) {
        synchronized (pending) {
            pending.add(new ScheduledTask(delayTicks, action));
        }
    }

    @SubscribeEvent
    public static void serverTick(ServerTickEvent.Post event) {
        synchronized (pending) {
            if (!pending.isEmpty()) {
                active.addAll(pending);
                pending.clear();
            }
        }

        var it = active.iterator();
        while (it.hasNext()) {
            ScheduledTask t = it.next();
            t.ticks--;
            if (t.ticks <= 0) {
                try {
                    t.action.run();
                } catch (Exception ignored) {
                }
                it.remove();
            }
        }
    }

    private static class ScheduledTask {
        int ticks;
        Runnable action;

        ScheduledTask(int ticks, Runnable action) {
            this.ticks = delayToPositive(delayTicks(ticks));
            this.action = action;
        }

        private static int delayTicks(int t) {
            return Math.max(t, 0);
        }

        private static int delayToPositive(int t) {
            return t == 0 ? 1 : t;
        }
    }
}
