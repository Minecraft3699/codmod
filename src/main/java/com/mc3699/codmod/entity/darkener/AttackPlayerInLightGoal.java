package com.mc3699.codmod.entity.darkener;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;

public class AttackPlayerInLightGoal extends Goal {
    private final PathfinderMob entity;
    private final Level level;
    private final double speed;
    private Player target;

    public AttackPlayerInLightGoal(PathfinderMob entity, double speed) {
        this.entity = entity;
        this.level = entity.level();
        this.speed = speed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        this.entity.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(entity, Player.class, true));
    }

    @Override
    public boolean canUse() {
        target = (Player) entity.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        BlockPos targetPos = target.blockPosition();
        return level.getBrightness(LightLayer.BLOCK, targetPos) > 4 || level.getBrightness(LightLayer.SKY, targetPos) > 4;
    }

    @Override
    public void start() {
        if (target != null) {
            entity.getNavigation().moveTo(target, speed);
            entity.getLookControl().setLookAt(target, 10.0F, entity.getMaxHeadXRot());
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (target == null || !target.isAlive() || entity.getNavigation().isDone()) {
            return false;
        }
        BlockPos targetPos = target.blockPosition();
        return level.getBrightness(LightLayer.BLOCK, targetPos) > 4 || level.getBrightness(LightLayer.SKY, targetPos) > 4;
    }

    @Override
    public void tick() {
        if (target != null) {
            entity.getLookControl().setLookAt(target, 10.0F, entity.getMaxHeadXRot());
            entity.getNavigation().moveTo(target, speed);
            if (target.getY() > entity.getY() + 1.5) {
                BlockPos entityPos = entity.blockPosition();
                BlockPos belowPos = entityPos.below();
                BlockState belowState = level.getBlockState(belowPos);
                entity.getJumpControl().jump();
                if ((belowState.isAir() || belowState.canBeReplaced())) {
                    level.setBlock(belowPos, Blocks.DIRT.defaultBlockState(), 3);
                    entity.getJumpControl().jump();
                }
            }
            if (entity.distanceToSqr(target) <= 4.0) {
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