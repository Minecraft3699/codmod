package com.mc3699.codmod.event;

import com.mc3699.codmod.entity.vay.VayEntity;
import dev.wendigodrip.thebrokenscript.api.responses.ChatResponse;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VayChat extends ChatResponse {
    @Override
    public @NotNull List<String> getTriggers() {
        return List.of("Vay aren't you banned?","Vay arent you banned?","Vay arent you banned","Vay aren't you banned");
    }

    private void spawnVay(ServerPlayer player, ServerLevel serverLevel, int radius)
    {
        player.sendSystemMessage(Component.literal("im_vay joined the game").setStyle(Style.EMPTY.withColor(0xFFFF55)));
        RandomSource random = serverLevel.random;
        double angle = random.nextDouble() * 2 * Math.PI;
        double distance = random.nextDouble() * radius;
        double x = player.getX() + Math.cos(angle) * distance;
        double z = player.getZ() + Math.sin(angle) * distance;
        double y = player.getY();
        // Find safe Y position
        BlockPos pos = new BlockPos((int)x, (int)y, (int)z);
        while (!serverLevel.getBlockState(pos.below()).isSolid() && pos.getY() > serverLevel.getMinBuildHeight()) {
            pos = pos.below();
        }
        while (serverLevel.getBlockState(pos).isSolid() && pos.getY() < serverLevel.getMaxBuildHeight()) {
            pos = pos.above();
        }

        // Spawn entity
        VayEntity vayEntity = new VayEntity(serverLevel);
        vayEntity.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        vayEntity.setAggressive(true);
        vayEntity.setTarget(player);
        serverLevel.addFreshEntity(vayEntity);
    }

    @Override
    public void respond(@NotNull Level level, @NotNull Player player) {
        spawnVay((ServerPlayer) player, (ServerLevel) level, 10);
        player.sendSystemMessage(Component.literal("<im_vay> FUCK YOU!! "+player.getDisplayName().getString()+" I am gonna fucking kill you"));
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
