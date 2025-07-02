package com.mc3699.codmod.event;

import dev.wendigodrip.thebrokenscript.api.event.BaseEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class WorstSunEvent extends BaseEvent {
    public WorstSunEvent() {
        super(0);
    }

    @Override
    protected void execute(@NotNull ServerLevel serverLevel, @NotNull ServerPlayer serverPlayer, @NotNull Vec3 vec3) {

    }
}
