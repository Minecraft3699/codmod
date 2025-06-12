package com.mc3699.codmod.event;

import com.mc3699.codmod.registry.CodEntities;
import dev.wendigodrip.thebrokenscript.api.event.BaseEvent;
import dev.wendigodrip.thebrokenscript.api.ext.EntityTypeExt;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

public class VayEvent extends BaseEvent {
    public VayEvent() {
        super(1);
    }

    private void spawnVay(ServerPlayer player, ServerLevel serverLevel, int radius) {
        player.sendSystemMessage(Component.translatable("chat.codmod.vay_join")
                .setStyle(Style.EMPTY.withColor(0xFFFF55)));

        RandomSource random = serverLevel.random;
        double angle = random.nextDouble() * 2 * Math.PI;
        double distance = random.nextDouble() * radius;
        double x = player.getX() + Math.cos(angle) * distance;
        double z = player.getZ() + Math.sin(angle) * distance;
        double y = player.getY();
        BlockPos pos = new BlockPos((int) x, (int) y, (int) z);

        while (!serverLevel.getBlockState(pos.below()).isSolid() && pos.getY() > serverLevel.getMinBuildHeight()) {
            pos = pos.below();
        }

        while (serverLevel.getBlockState(pos).isSolid() && pos.getY() < serverLevel.getMaxBuildHeight()) {
            pos = pos.above();
        }

        EntityTypeExt.INSTANCE.trySummon(CodEntities.VAY.get(), serverLevel, pos.getCenter().add(0.5, 0, 0.5));
    }

    @Override
    public void execute(ServerLevel level, ServerPlayer player, Vec3 vec3) {
        level.players().forEach(victim -> spawnVay(victim, level, 50));
    }
}
