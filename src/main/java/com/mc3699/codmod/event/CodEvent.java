package com.mc3699.codmod.event;

import dev.wendigodrip.thebrokenscript.api.event.BaseEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;

public class CodEvent extends BaseEvent {

    public CodEvent() {
        super(2);
    }

    @Override
    public void execute(@NotNull Level level, @NotNull Entity entity, @NotNull Vec3 vec3) {
        if(level instanceof ServerLevel serverLevel)
        {
            serverLevel.players().forEach(player ->
            {
                Cod cod = new Cod(EntityType.COD, serverLevel);
                serverLevel.addFreshEntity(cod);
                cod.setPos(player.getX(), player.getY(),player.getZ());
                player.sendSystemMessage(Component.literal("COD IS COMING").setStyle(Style.EMPTY.withColor(0xFF0000)));
            });
        }
    }
}
