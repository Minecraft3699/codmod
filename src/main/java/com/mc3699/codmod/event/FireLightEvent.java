package com.mc3699.codmod.event;

import com.mc3699.codmod.entity.darkener.DarkenerEntity;
import com.mc3699.codmod.entity.firelight.FirelightEntity;
import dev.wendigodrip.thebrokenscript.api.event.BaseEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class FireLightEvent extends BaseEvent {
    public FireLightEvent() {
        super(4);
    }

    private void spawnFireLight(ServerPlayer player, ServerLevel serverLevel, int radius)
    {
        RandomSource random = serverLevel.random;
        double angle = random.nextDouble() * 2 * Math.PI;
        double distance = random.nextDouble() * radius;
        double x = player.getX() + Math.cos(angle) * distance;
        double z = player.getZ() + Math.sin(angle) * distance;
        double y = player.getY();
        BlockPos pos = new BlockPos((int)x, (int)y, (int)z);
        while (!serverLevel.getBlockState(pos.below()).isSolid() && pos.getY() > serverLevel.getMinBuildHeight()) {
            pos = pos.below();
        }
        while (serverLevel.getBlockState(pos).isSolid() && pos.getY() < serverLevel.getMaxBuildHeight()) {
            pos = pos.above();
        }
        FirelightEntity firelightEntity = new FirelightEntity(serverLevel);
        firelightEntity.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        serverLevel.addFreshEntity(firelightEntity);
    }

    @Override
    public void execute(@NotNull Level level, @NotNull Entity entity, @NotNull Vec3 vec3) {
        if(level instanceof ServerLevel serverLevel)
        {
            serverLevel.players().forEach(player -> {
                RandomSource random = serverLevel.random;
                if(random.nextInt(0,10) > 2)
                {
                    spawnFireLight(player, serverLevel, 50);
                }
            });
        }
    }
}
