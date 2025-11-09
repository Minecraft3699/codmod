package com.mc3699.codmod.handlers;

import com.mc3699.codmod.Codmod;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = Codmod.MOD_ID)
public class BloodHandler {

    @SubscribeEvent
    public static void damageEvent(LivingDamageEvent.Post event) {

        LivingEntity entity = event.getEntity();

        if (entity.level() instanceof ServerLevel serverLevel) {

            // disabled for now

        }

    }

    private static void spawnBloodBurst(ServerLevel level, Vec3 pos) {
        var random = level.getRandom();
        int particleCount = 80;

        for (int i = 0; i < particleCount; i++) {
            double dx = (random.nextDouble() - 0.5) * 1.5;
            double dy = random.nextDouble() * 2.0 + 0.4;
            double dz = (random.nextDouble() - 0.5) * 1.5;

            Vec3 dir = new Vec3(dx, dy, dz).normalize()
                    .scale(0.7 + random.nextDouble() * 0.8);


            level.sendParticles(
                    new BlockParticleOption(ParticleTypes.BLOCK, Blocks.REDSTONE_BLOCK.defaultBlockState()),
                    pos.x, pos.y + 0.6, pos.z,
                    1,
                    dir.x, dir.y, dir.z,
                    0.0
            );
        }
    }


}
