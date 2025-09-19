package com.mc3699.codmod.event;

import dev.wendigodrip.thebrokenscript.api.event.BaseEvent;
import dev.wendigodrip.thebrokenscript.api.ext.EntityTypeExt;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.Vec3;

public class SalmonEvent extends BaseEvent {
    public SalmonEvent() {
        super(1);
    }
//dont blame me someone suggested this - nuz
    @Override
    public void execute(ServerLevel level, ServerPlayer player, Vec3 vec3) {
        level.players().forEach(victim -> {
            EntityTypeExt.INSTANCE.trySummon(EntityType.SALMON, level, victim.position());
            victim.sendSystemMessage(Component.literal("DISGUSTING CREATURE").setStyle(Style.EMPTY.withColor(0xFF0000)));
        });
    }
}
