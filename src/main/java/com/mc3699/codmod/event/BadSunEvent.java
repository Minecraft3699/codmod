package com.mc3699.codmod.event;

import dev.wendigodrip.thebrokenscript.api.event.BaseEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class BadSunEvent extends BaseEvent {
    public BadSunEvent() {
        super(1);
    }

    @Override
    public void execute(@NotNull Level level, @NotNull Entity entity, @NotNull Vec3 vec3) {

    }
}
