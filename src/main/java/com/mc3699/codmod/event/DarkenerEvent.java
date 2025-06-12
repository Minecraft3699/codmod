package com.mc3699.codmod.event;

import com.mc3699.codmod.entity.darkener.DarkenerEntity;
import com.mc3699.codmod.entity.vay.VayEntity;
import dev.wendigodrip.thebrokenscript.api.event.BaseEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class DarkenerEvent extends BaseEvent {
    public DarkenerEvent() {
        super(1);
    }

    private void spawnDarkener(ServerPlayer player, ServerLevel serverLevel, int radius) {
        RandomSource random = serverLevel.random;
        int maxAttempts = 20; // Number of position attempts

        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            // Random position in a sphere around the player
            double theta = random.nextDouble() * 2 * Math.PI;
            double phi = Math.acos(2 * random.nextDouble() - 1);
            double distance = random.nextDouble() * radius;
            double x = player.getX() + distance * Math.sin(phi) * Math.cos(theta);
            double y = player.getY() + (random.nextDouble() - 0.5) * radius; // Vertical spread
            double z = player.getZ() + distance * Math.sin(phi) * Math.sin(theta);

            // Clamp Y to below 64
            y = Math.max(serverLevel.getMinBuildHeight() + 2, Math.min(63, y));
            BlockPos pos = new BlockPos((int)x, (int)y, (int)z);

            // Check for air block and no skylight
            if (serverLevel.getBlockState(pos).isAir() && !serverLevel.canSeeSky(pos)) {
                // Spawn entity
                DarkenerEntity darkenerEntity = new DarkenerEntity(serverLevel);
                darkenerEntity.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                if (serverLevel.addFreshEntity(darkenerEntity)) {
                    return; // Success
                }
            }
        }
    }

    @Override
    public void execute(@NotNull Level level, @NotNull Entity entity, @NotNull Vec3 vec3) {
        if(level instanceof ServerLevel serverLevel)
        {
            serverLevel.players().forEach(player -> {
                RandomSource random = serverLevel.random;
                if(random.nextInt(0,10) > 8)
                {
                    serverLevel.getSunAngle(0);
                    spawnDarkener(player, serverLevel, 10);
                    return;
                }
            });
        }
    }
}
