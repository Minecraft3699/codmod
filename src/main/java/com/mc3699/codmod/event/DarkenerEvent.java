package com.mc3699.codmod.event;

import com.mc3699.codmod.registry.CodEntities;
import dev.wendigodrip.thebrokenscript.api.event.BaseEvent;
import dev.wendigodrip.thebrokenscript.api.ext.EntityTypeExt;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

public class DarkenerEvent extends BaseEvent {
    public DarkenerEvent() {
        super(1);
    }

    private void spawnDarkener(ServerPlayer player, ServerLevel level, int radius) {
        RandomSource random = level.random;
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
            y = Math.max(level.getMinBuildHeight() + 2, Math.min(63, y));
            BlockPos pos = new BlockPos((int) x, (int) y, (int) z);

            // Check for air block and no skylight
            if (level.getBlockState(pos).isAir() && !level.canSeeSky(pos)) {
                // Spawn entity
                EntityTypeExt.INSTANCE.trySummon(CodEntities.DARKENER.get(), level, pos.getCenter().add(0.5, 0, 0.5));
            }
        }
    }

    @Override
    public void execute(ServerLevel level, ServerPlayer player, Vec3 vec3) {
        level.players().forEach(victim -> {
            RandomSource random = level.random;

            if (random.nextInt(0, 10) > 8) {
                level.getSunAngle(0);
                spawnDarkener(victim, level, 10);
            }
        });
    }
}
