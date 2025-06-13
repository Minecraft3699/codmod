package com.mc3699.codmod.entity.friendlyFace;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Random;

public class AttackForTimeGoal extends Goal {
    private final FriendlyFaceEntity entity;
    private final Level level;
    private final double approachDistance;
    private final double attackSpeed;
    private final Random random;
    private Player target;

    public AttackForTimeGoal(FriendlyFaceEntity entity, Level level, double approachDistance, double attackSpeed) {
        this.entity = entity;
        this.level = level;
        this.approachDistance = approachDistance;
        this.attackSpeed = attackSpeed;
        this.random = new Random();
        this.entity.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(entity, Player.class, false));
    }

    @Override
    public boolean canUse() {
        target = (Player) entity.getTarget();
        return target != null && target.isAlive();
    }

    @Override
    public void start() {
        entity.getLookControl().setLookAt(target, 10.0F, entity.getMaxHeadXRot());
    }

    @Override
    public boolean canContinueToUse() {
        return target != null && target.isAlive() && entity.chaseTime > 0;
    }

    @Override
    public void tick() {
        if (target != null) {
            entity.getLookControl().setLookAt(target, 10.0F, entity.getMaxHeadXRot());
            if (entity.distanceToSqr(target) < approachDistance * approachDistance) {
                entity.getNavigation().moveTo(target, attackSpeed);
                if (entity.distanceToSqr(target) < 2.0) {
                    entity.doHurtTarget(target);
                }
            }

            entity.chaseTime--;
            if (entity.chaseTime <= 0) {
                entity.discard();
            }
        }
    }

    @Override
    public void stop() {
        target = null;
        entity.getNavigation().stop();
    }
}