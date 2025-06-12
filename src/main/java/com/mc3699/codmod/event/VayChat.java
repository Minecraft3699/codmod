package com.mc3699.codmod.event;

import com.mc3699.codmod.entity.vay.VayEntity;
import com.mc3699.codmod.registry.CodEntities;
import dev.wendigodrip.thebrokenscript.api.ext.EntityTypeExt;
import dev.wendigodrip.thebrokenscript.api.responses.ChatResponse;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VayChat extends ChatResponse {
    @Override
    public @NotNull List<String> getTriggers() {
        return List.of(
                "Vay aren't you banned?",
                "Vay arent you banned?",
                "Vay arent you banned",
                "Vay aren't you banned"
        );
    }

    private void spawnVay(ServerPlayer player, ServerLevel serverLevel, int radius) {
        player.sendSystemMessage(Component.literal("im_vay joined the game").setStyle(Style.EMPTY.withColor(0xFFFF55)));
        RandomSource random = serverLevel.random;
        double angle = random.nextDouble() * 2 * Math.PI;
        double distance = random.nextDouble() * radius;
        double x = player.getX() + Math.cos(angle) * distance;
        double z = player.getZ() + Math.sin(angle) * distance;
        double y = player.getY();
        // Find safe Y position
        BlockPos pos = new BlockPos((int) x, (int) y, (int) z);
        while (!serverLevel.getBlockState(pos.below()).isSolid() && pos.getY() > serverLevel.getMinBuildHeight()) {
            pos = pos.below();
        }
        while (serverLevel.getBlockState(pos).isSolid() && pos.getY() < serverLevel.getMaxBuildHeight()) {
            pos = pos.above();
        }

        // Spawn entity
        VayEntity vay = EntityTypeExt.INSTANCE.trySummonTyped(
                CodEntities.VAY.get(),
                serverLevel,
                pos.getCenter().add(0.5, 0, 0.5)
        );

        vay.setAggressive(true);
        vay.setTarget(player);
    }

    @Override
    public void respond(ServerLevel level, ServerPlayer player) {
        spawnVay(player, level, 10);
        player.sendSystemMessage(Component.literal("<im_vay> FUCK YOU!! " +
                                                   player.getDisplayName().getString() +
                                                   " I am gonna fucking kill you"));
    }

    @Override
    public boolean getCaseSensitive() {
        return false;
    }

    @Override
    public boolean isFullMessage() {
        return true;
    }

    @Override
    public int getDelay() {
        return 1;
    }
}
