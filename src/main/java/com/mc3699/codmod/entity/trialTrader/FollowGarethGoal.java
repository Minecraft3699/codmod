package com.mc3699.codmod.entity.trialTrader;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.Random;
import java.util.UUID;

public class FollowGarethGoal extends Goal {

    UUID MC3699_UUID = UUID.fromString("6479c5ab-902a-4f50-952c-91eeb4552e05");
    UUID GARETH_UUID = UUID.fromString("1c1f8991-0677-4a81-bc79-aedb0cdc907c");
    private Player targetPlayer;
    private final TrialTraderEntity entity;
    Random random = new Random();

    public FollowGarethGoal(TrialTraderEntity entity) {
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        targetPlayer = entity.level().getPlayerByUUID(GARETH_UUID);
        return targetPlayer != null && !targetPlayer.isSpectator() && targetPlayer.distanceTo(entity) < 16;
    }

    @Override
    public boolean canContinueToUse() {
        return targetPlayer != null || targetPlayer.distanceTo(entity) > 32;
    }

    @Override
    public void tick() {
        if(targetPlayer != null && entity.level() instanceof ServerLevel serverLevel)
        {
            entity.getNavigation().moveTo(targetPlayer, 1.5f);
            entity.getLookControl().setLookAt(targetPlayer);

            if(targetPlayer.distanceTo(entity) < 3)
            {
                serverLevel.sendParticles(ParticleTypes.HEART, entity.position().x, entity.position().y+1.8f, entity.position().z, 3,0,0.3f,0, 0.2f);
            }
        }
    }

    @Override
    public void stop() {
        entity.getNavigation().stop();
        targetPlayer = null;
    }
}
