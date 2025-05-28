package com.mc3699.codmod.entity.darkener;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class AttackPlayerInLightGoal extends Goal {
    private final PathfinderMob entity;
    private final Level level;
    private final double speed;
    private Player target;

    public AttackPlayerInLightGoal(PathfinderMob entity, double speed) {
        this.entity = entity;
        this.level = entity.level();
        this.speed = speed;
        this.entity.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(entity, Player.class, true));
    }

    @Override
    public boolean canUse() {

        target = (Player) entity.getTarget();

        if (target != null && (level.getBrightness(LightLayer.BLOCK, target.blockPosition()) <= 4 || level.getBrightness(LightLayer.SKY, target.blockPosition()) <= 4)) {
            return false;
        }

        return target != null && target.isAlive();
    }

    @Override
    public void start() {
        entity.getNavigation().moveTo(target, speed);
        entity.getLookControl().setLookAt(target, 10.0F, entity.getMaxHeadXRot());
    }

    @Override
    public boolean canContinueToUse() {
        // Continue only if in light and target is valid
        return target != null && target.isAlive()
                && !entity.getNavigation().isDone()
                && level.getBrightness(LightLayer.BLOCK, entity.blockPosition()) > 4;
    }

    @Override
    public void tick() {
        if (target != null) {
            entity.getLookControl().setLookAt(target, 10.0F, entity.getMaxHeadXRot());
            entity.getNavigation().moveTo(target, speed);
            if (entity.distanceToSqr(target) < 2.0) {
                entity.doHurtTarget(target);
            }
        }
    }

    @Override
    public void stop() {
        target = null;
        entity.getNavigation().stop();
    }
}